package br.com.protons.sprintapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

// Centraliza o tratamento de exceções da API
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata recursos não encontrados
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> naoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(404, ex.getMessage()));
    }

    // Trata violações de regras de negócio
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> regraNegocio(RegraNegocioException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroResponse(422, ex.getMessage()));
    }

    // Trata erros de validação dos campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            String msg = erro.getDefaultMessage() == null ? "Valor invalido" : erro.getDefaultMessage();
            campos.merge(erro.getField(), msg, (a, b) -> a + "; " + b);
        }

        return ResponseEntity.badRequest()
                .body(new ErroResponse(400, "Dados invalidos", campos));
    }

    // Trata JSON inválido ou malformado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> corpoInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ErroResponse(400,
                "Corpo da requisicao invalido. Verifique o JSON e os valores de categoria e setor."));
    }

    // Trata parâmetros com tipos incompatíveis
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> parametroInvalido(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(new ErroResponse(400,
                "Valor invalido para o parametro '" + ex.getName() + "'"));
    }
}
