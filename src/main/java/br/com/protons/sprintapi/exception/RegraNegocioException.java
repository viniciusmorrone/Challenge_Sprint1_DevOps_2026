package br.com.protons.sprintapi.exception;

// Exceção para violações de regras de negócio
public class RegraNegocioException extends RuntimeException {

    // Define a mensagem do erro
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
