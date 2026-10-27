package br.com.protons.sprintapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Dados de entrada de uma alternativa
public record AlternativaRequest(

        @NotBlank(message = "O texto da alternativa e obrigatorio")
        @Size(max = 200, message = "O texto da alternativa deve ter no maximo 200 caracteres")
        String texto,

        @NotNull(message = "Informe se a alternativa e a correta")
        Boolean correta) {
}
