package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.receitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/receitas")
@SecurityRequirement(name = "bearer-key")
public class receitaController {

    @Autowired
    private receitaService service;

    @PostMapping
    @Operation(summary = "Registra um novo item para receita.", tags = {"receita"})
    public ResponseEntity<receitaDTOResponse> cadastrarReceita(@RequestBody @Valid receitaDTORequest dto, UriComponentsBuilder uri){
        receitaDTOResponse dtoResponse = service.cadastrarReceita(dto);

        URI endereco = uri.path("receitas/{id}").buildAndExpand(dtoResponse.id()).toUri();

        return ResponseEntity.created(endereco).body(dtoResponse);
    }

    @GetMapping
    @Operation(summary = "Lista todas as receitas", tags = {"receita"}, description = "Se enviado um parametro chamado 'descricao' retorna aqueles que tem a descrição enviada")
    public ResponseEntity<Page<receitaDTOResponse>> listarReceitas(@PageableDefault @ParameterObject Pageable paginacao, @RequestParam(value = "descricao", defaultValue = "", required = false) String descricao){
        return ResponseEntity.ok(service.listarReceitas(paginacao, descricao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontre uma receita pelo seu id.", tags = {"receita"})
    public ResponseEntity<receitaDTOResponse> encontrarReceita(@PathVariable Long id){
        receitaDTOResponse dtoResponse = service.encontrarReceita(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita determinada pelo seu id.", tags = {"receita"})
    public ResponseEntity<receitaDTOResponse> atualizarReceita(@PathVariable Long id, @RequestBody @Valid receitaDTORequest dto){
        receitaDTOResponse dtoResponse = service.atualizarReceita(id, dto);

        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma receita determinada pelo seu id", tags = {"receita"})
    @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id){
        service.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ano}/{mes}")
    @Operation(summary = "Lista receitas de um determinado mês.", tags = {"receita"})
    public ResponseEntity<Page<receitaDTOResponse>> listarReceitasPorMes(@PathVariable int ano, @PathVariable int mes, @PageableDefault @ParameterObject Pageable paginacao){
        Page<receitaDTOResponse> dtoResponses = service.listarReceitasPorMes(paginacao, ano, mes);
        return ResponseEntity.ok(dtoResponses);
    }
}
