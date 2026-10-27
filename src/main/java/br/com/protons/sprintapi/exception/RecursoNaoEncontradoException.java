package br.com.protons.sprintapi.exception;

// Exceção para recursos não encontrados
public class RecursoNaoEncontradoException extends RuntimeException {

    // Define a mensagem do erro
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
