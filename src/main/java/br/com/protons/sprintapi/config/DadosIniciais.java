package br.com.protons.sprintapi.config;

import br.com.protons.sprintapi.model.Alternativa;
import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.model.Pergunta;
import br.com.protons.sprintapi.model.Setor;
import br.com.protons.sprintapi.repository.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Dados de teste: carrega perguntas de exemplo quando a tabela esta vazia. */
@Component
@RequiredArgsConstructor
public class DadosIniciais implements CommandLineRunner {

    private final PerguntaRepository repository;

    @NullMarked
    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        // A primeira alternativa de cada pergunta e a correta
        criar("Qual aparelho usa radiacao para tirar fotos dos ossos do corpo?", Categoria.CRIANCA, Setor.SAUDE,
                "Raio-X", "Microscopio", "Termometro", "Binoculo");

        criar("Por que algumas frutas e legumes recebem radiacao depois de colhidos?", Categoria.CRIANCA, Setor.AGRICULTURA,
                "Para durarem mais tempo sem estragar", "Para ficarem mais doces", "Para mudarem de cor", "Para crescerem mais rapido");

        criar("Qual tecnica da medicina nuclear usa substancias radioativas para gerar imagens e ajudar no diagnostico?", Categoria.JOVEM, Setor.SAUDE,
                "Cintilografia", "Hemograma", "Audiometria", "Espirometria");

        criar("Para que serve a gamagrafia industrial?", Categoria.JOVEM, Setor.INDUSTRIA, "Inspecionar soldas e pecas sem destrui-las", "Aquecer metais",
                "Pintar tubulacoes", "Medir a temperatura do ambiente");

        criar("O que e a fissao nuclear?", Categoria.JOVEM, Setor.ENERGIA, "A divisao do nucleo de um atomo pesado, liberando energia",
                "A juncao de dois atomos leves", "A queima de carvao", "A oxidacao do ferro");

        criar("Qual unidade do SI mede a dose equivalente de radiacao ionizante, considerando o efeito biologico?", Categoria.ADULTO, Setor.PESQUISA,
                "Sievert", "Becquerel", "Pascal", "Volt");

        criar("Qual e o principio basico da radioterapia no tratamento do cancer?", Categoria.ADULTO, Setor.SAUDE,
                "Usar radiacao ionizante para danificar as celulas tumorais", "Congelar o tumor", "Bloquear hormonios", "Dissolver o tumor com medicamentos");

        criar("Na agricultura, a tecnica do inseto esteril usa radiacao para qual finalidade?", Categoria.ADULTO, Setor.AGRICULTURA,
                "Controlar pragas sem inseticidas quimicos", "Acelerar a germinacao das sementes", "Aumentar o tamanho dos frutos", "Medir a umidade do solo");

        criar("Desde 1979, o Programa Nuclear da Marinha atua principalmente em quais dominios?", Categoria.ADULTO, Setor.ENERGIA,
                "Ciclo do combustivel nuclear e planta nuclear de geracao de energia eletrica", "Exploracao de petroleo em aguas profundas", "Fabricacao de satelites",
                "Producao de medicamentos");
    }

    // Monta uma pergunta com suas alternativas (a primeira é a correta)
    private void criar(String enunciado, Categoria categoria, Setor setor, String... alternativas) {
        Pergunta p = new Pergunta();
        p.setEnunciado(enunciado);
        p.setCategoria(categoria);
        p.setSetor(setor);
        for (int i = 0; i < alternativas.length; i++) {
            p.getAlternativas().add(new Alternativa(alternativas[i], i == 0, p));
        }
        repository.save(p);
    }
}
