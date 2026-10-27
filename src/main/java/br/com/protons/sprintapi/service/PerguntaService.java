package br.com.protons.sprintapi.service;

import br.com.protons.sprintapi.dto.AlternativaRequest;
import br.com.protons.sprintapi.dto.PerguntaRequest;
import br.com.protons.sprintapi.dto.PerguntaResponse;
import br.com.protons.sprintapi.exception.RecursoNaoEncontradoException;
import br.com.protons.sprintapi.exception.RegraNegocioException;
import br.com.protons.sprintapi.model.Alternativa;
import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.model.Pergunta;
import br.com.protons.sprintapi.repository.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final PerguntaRepository repository;

    // Cria uma pergunta após validar suas alternativas
    @Transactional
    public PerguntaResponse criar(PerguntaRequest req) {
        validarAlternativas(req);
        Pergunta pergunta = new Pergunta();
        preencher(pergunta, req);
        return PerguntaResponse.de(repository.save(pergunta));
    }

    // Lista perguntas, opcionalmente filtradas por categoria
    @Transactional(readOnly = true)
    public List<PerguntaResponse> listar(Categoria categoria) {
        List<Pergunta> perguntas = (categoria == null)
                ? repository.findAll()
                : repository.findByCategoria(categoria);
        return perguntas.stream().map(PerguntaResponse::de).toList();
    }

    // Busca uma pergunta pelo ID
    @Transactional(readOnly = true)
    public PerguntaResponse buscarPorId(Long id) {
        return PerguntaResponse.de(obter(id));
    }

    // Busca perguntas pelo texto do enunciado
    @Transactional(readOnly = true)
    public List<PerguntaResponse> buscarPorTexto(String texto) {
        return repository.findByEnunciadoContainingIgnoreCase(texto.trim())
                .stream().map(PerguntaResponse::de).toList();
    }

    // Atualiza os dados de uma pergunta existente
    @Transactional
    public PerguntaResponse atualizar(Long id, PerguntaRequest req) {
        validarAlternativas(req);
        Pergunta pergunta = obter(id);
        preencher(pergunta, req);
        return PerguntaResponse.de(repository.save(pergunta));
    }

    // Exclui uma pergunta pelo ID
    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    // Busca uma pergunta ou lança exceção se não existir
    private Pergunta obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pergunta " + id + " nao encontrada"));
    }

    // Preenche os dados da pergunta e suas alternativas
    private void preencher(Pergunta pergunta, PerguntaRequest req) {
        pergunta.setEnunciado(req.enunciado().trim());
        pergunta.setCategoria(req.categoria());
        pergunta.setSetor(req.setor());
        pergunta.getAlternativas().clear();
        for (AlternativaRequest a : req.alternativas()) {
            pergunta.getAlternativas().add(new Alternativa(a.texto().trim(), a.correta(), pergunta));
        }
    }

    // Valida as alternativas e impede textos repetidos.
    private void validarAlternativas(PerguntaRequest req) {
        long corretas = req.alternativas().stream().filter(a -> Boolean.TRUE.equals(a.correta())).count();

        if (corretas != 1) {
            throw new RegraNegocioException("Cada pergunta deve ter exatamente 1 alternativa correta");
        }

        long distintas = req.alternativas().stream()
                .map(a -> a.texto().trim().toLowerCase()).distinct().count();

        if (distintas != req.alternativas().size()) {
            throw new RegraNegocioException("As alternativas nao podem ter textos repetidos");
        }
    }
}
