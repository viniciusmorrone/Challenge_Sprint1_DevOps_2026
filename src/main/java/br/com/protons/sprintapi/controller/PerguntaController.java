package br.com.protons.sprintapi.controller;

import br.com.protons.sprintapi.dto.PerguntaRequest;
import br.com.protons.sprintapi.dto.PerguntaResponse;
import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.service.PerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/perguntas")
@RequiredArgsConstructor
@Tag(name = "Perguntas", description = "CRUD das perguntas do quiz sobre tecnologia nuclear")
public class PerguntaController {

    private final PerguntaService service;

    // Cadastra uma pergunta e devolve 201, 400 ou 422 com a URL do novo recurso no cabeçalho Location
    @PostMapping
    @Operation(summary = "Cadastra uma pergunta com 4 alternativas (apenas 1 correta)")
    @ApiResponse(responseCode = "201", description = "Pergunta criada")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "422", description = "Regra de negocio violada")
    public ResponseEntity<PerguntaResponse> criar(@Valid @RequestBody PerguntaRequest req) {
        PerguntaResponse criada = service.criar(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    // Lista todas as perguntas ou só as da categoria informada
    @GetMapping
    @Operation(summary = "Lista as perguntas, com filtro opcional por categoria")
    @ApiResponse(responseCode = "200", description = "Lista de perguntas")
    public List<PerguntaResponse> listar(
            @Parameter(description = "CRIANCA, JOVEM ou ADULTO")
            @RequestParam(required = false) Categoria categoria) {
        return service.listar(categoria);
    }

    // Busca perguntas cujo enunciado contém o texto informado (ignora maiúsculas)
    @GetMapping("/busca")
    @Operation(summary = "Busca perguntas pelo texto do enunciado")
    @ApiResponse(responseCode = "200", description = "Perguntas encontradas (lista vazia se nenhuma)")
    public List<PerguntaResponse> buscar(
            @Parameter(description = "Trecho do enunciado, ex.: radiacao")
            @RequestParam String texto) {
        return service.buscarPorTexto(texto);
    }

    // Consulta uma pergunta pelo id
    @GetMapping("/{id}")
    @Operation(summary = "Consulta uma pergunta pelo id")
    @ApiResponse(responseCode = "200", description = "Pergunta encontrada")
    @ApiResponse(responseCode = "404", description = "Pergunta nao encontrada")
    public PerguntaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Atualiza uma pergunta existente, validando os dados e as regras de negócio
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pergunta")
    @ApiResponse(responseCode = "200", description = "Pergunta atualizada")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Pergunta nao encontrada")
    @ApiResponse(responseCode = "422", description = "Regra de negocio violada")
    public PerguntaResponse atualizar(@PathVariable Long id, @Valid @RequestBody PerguntaRequest req) {
        return service.atualizar(id, req);
    }

    // Exclui uma pergunta pelo id
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma pergunta")
    @ApiResponse(responseCode = "204", description = "Pergunta excluida")
    @ApiResponse(responseCode = "404", description = "Pergunta nao encontrada")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
