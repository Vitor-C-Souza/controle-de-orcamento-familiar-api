package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.dto.*;
import br.com.vitor.controle_de_orcamento_familiar.service.despesaService;
import jakarta.validation.Valid;
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
public class despesaController {

    @Autowired
    private despesaService service;

    @PostMapping
    public ResponseEntity<despesaDTOResponse> cadastrarDespesa(@RequestBody @Valid despesaDTORequest dto, UriComponentsBuilder uri){
        despesaDTOResponse dtoResponse = service.cadastrarDespesa(dto);

        URI endereco = uri.path("despesa/{id}").buildAndExpand(dtoResponse.id()).toUri();

        return ResponseEntity.created(endereco).body(dtoResponse);
    }

    @GetMapping
    public Page<despesaDTOResponse> listarDespesas(@PageableDefault Pageable paginacao, @RequestParam(value = "descricao", defaultValue = "", required = false) String descricao){
        return service.listarDespesas(paginacao, descricao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<despesaDTOResponse> encontrarDespesa(@PathVariable Long id){
        despesaDTOResponse dtoResponse = service.encontrarDespesa(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<despesaDTOResponse> atualizarDespesa(@PathVariable Long id, @RequestBody @Valid despesaDTORequest dto){
        despesaDTOResponse dtoResponse = service.atualizarDespesa(id, dto);

        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarDespesa(@PathVariable Long id){
        service.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
