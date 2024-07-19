package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTORequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.despesaDTOResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.despesaService;
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
@RequestMapping("/despesas")
@SecurityRequirement(name = "bearer-key")
public class despesaController {

    @Autowired
    private despesaService service;

    @PostMapping
    @Operation(summary = "Cadastra um nova despesa.", tags = {"despesa"})
    public ResponseEntity<despesaDTOResponse> cadastrarDespesa(@RequestBody @Valid despesaDTORequest dto, UriComponentsBuilder uri){
        despesaDTOResponse dtoResponse = service.cadastrarDespesa(dto);

        URI endereco = uri.path("despesa/{id}").buildAndExpand(dtoResponse.id()).toUri();

        return ResponseEntity.created(endereco).body(dtoResponse);
    }

    @GetMapping
    @Operation(summary = "Lista todas as despesas", tags = {"despesa"}, description = "Se enviado um parametro chamado 'descricao' retorna aqueles que tem a descrição enviada")
    public ResponseEntity<Page<despesaDTOResponse>> listarDespesas(@PageableDefault @ParameterObject Pageable paginacao, @RequestParam(value = "descricao", defaultValue = "", required = false) String descricao){
        return ResponseEntity.ok(service.listarDespesas(paginacao, descricao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontre uma determinada despesa pelo seu id", tags = {"despesa"})
    public ResponseEntity<despesaDTOResponse> encontrarDespesa(@PathVariable Long id){
        despesaDTOResponse dtoResponse = service.encontrarDespesa(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma despesa determinada pelo seu id", tags = {"despesa"})
    public ResponseEntity<despesaDTOResponse> atualizarDespesa(@PathVariable Long id, @RequestBody @Valid despesaDTORequest dto){
        despesaDTOResponse dtoResponse = service.atualizarDespesa(id, dto);

        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma despesa determinada pelo seu id", tags = {"despesa"})
    @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id){
        service.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ano}/{mes}")
    @Operation(summary = "Lista todas as depesas de um determinado mês.", tags = {"despesa"})
    public Page<despesaDTOResponse> listarDespesasPorMes(@PathVariable int ano, @PathVariable int mes, @PageableDefault @ParameterObject Pageable paginacao){
        return service.listarDespesasPorMes(paginacao, ano, mes);
    }
}
