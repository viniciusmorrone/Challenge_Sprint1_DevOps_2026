package br.com.protons.sprintapi.exception;

import java.time.LocalDateTime;
import java.util.Map;

// Formato padrão das respostas de erro da API
public record ErroResponse(LocalDateTime timestamp, int status, String mensagem, Map<String, String> campos) {

    // Erro simples, só com status e mensagem
    public ErroResponse(int status, String mensagem) {
        this(LocalDateTime.now(), status, mensagem, null);
    }

    // Erro de validação, com a mensagem de cada campo inválido
    public ErroResponse(int status, String mensagem, Map<String, String> campos) {
        this(LocalDateTime.now(), status, mensagem, campos);
    }
}
