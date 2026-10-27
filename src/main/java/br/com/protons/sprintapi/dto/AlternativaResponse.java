package br.com.protons.sprintapi.dto;

import br.com.protons.sprintapi.model.Alternativa;

// Dados de uma alternativa devolvidos pela API
public record AlternativaResponse(Long id, String texto, boolean correta) {

    // Converte a entidade Alternativa neste DTO de resposta
    public static AlternativaResponse de(Alternativa a) {
        return new AlternativaResponse(a.getId(), a.getTexto(), a.isCorreta());
    }
}
