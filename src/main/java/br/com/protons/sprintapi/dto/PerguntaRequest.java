package br.com.protons.sprintapi.dto;

import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.model.Setor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

// Dados de entrada para cadastrar ou atualizar uma pergunta
public record PerguntaRequest(

        @NotBlank(message = "O enunciado e obrigatorio")
        @Size(min = 10, max = 300, message = "O enunciado deve ter entre 10 e 300 caracteres")
        String enunciado,

        @NotNull(message = "A categoria e obrigatoria (CRIANCA, JOVEM ou ADULTO)")
        Categoria categoria,

        @NotNull(message = "O setor e obrigatorio (SAUDE, AGRICULTURA, INDUSTRIA, PESQUISA ou ENERGIA)")
        Setor setor,

        @NotNull(message = "As alternativas sao obrigatorias")
        @Size(min = 4, max = 4, message = "A pergunta deve ter exatamente 4 alternativas")
        @Valid
        List<AlternativaRequest> alternativas) {
}
