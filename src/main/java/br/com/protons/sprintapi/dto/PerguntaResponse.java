package br.com.protons.sprintapi.dto;

import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.model.Pergunta;
import br.com.protons.sprintapi.model.Setor;

import java.util.List;

// Dados de uma pergunta devolvidos pela API
public record PerguntaResponse(
        Long id,
        String enunciado,
        Categoria categoria,
        Setor setor,
        int pontos,
        List<AlternativaResponse> alternativas) {

    // Converte a entidade Pergunta (e suas alternativas) neste DTO de resposta
    public static PerguntaResponse de(Pergunta p) {
        return new PerguntaResponse(
                p.getId(),
                p.getEnunciado(),
                p.getCategoria(),
                p.getSetor(),
                p.getCategoria().getPontos(),
                p.getAlternativas().stream().map(AlternativaResponse::de).toList());
    }
}
