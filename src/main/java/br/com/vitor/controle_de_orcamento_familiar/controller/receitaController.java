package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.dto.*;
import br.com.vitor.controle_de_orcamento_familiar.service.receitaService;
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
@RequestMapping("/receitas")
public class receitaController {

    @Autowired
    private receitaService service;

    @PostMapping
    public ResponseEntity<receitaDTOResponse> cadastrarReceita(@RequestBody @Valid receitaDTORequest dto, UriComponentsBuilder uri){
        receitaDTOResponse dtoResponse = service.cadastrarReceita(dto);

        URI endereco = uri.path("receitas/{id}").buildAndExpand(dtoResponse.id()).toUri();

        return ResponseEntity.created(endereco).body(dtoResponse);
    }

    @GetMapping
    public Page<receitaDTOResponse> listarReceitas(@PageableDefault Pageable paginacao, @RequestParam(value = "descricao", defaultValue = "", required = false) String descricao){
        return service.listarReceitas(paginacao, descricao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<receitaDTOResponse> encontrarReceita(@PathVariable Long id){
        receitaDTOResponse dtoResponse = service.encontrarReceita(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<receitaDTOResponse> atualizarReceita(@PathVariable Long id, @RequestBody @Valid receitaDTORequest dto){
        receitaDTOResponse dtoResponse = service.atualizarReceita(id, dto);

        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarReceita(@PathVariable Long id){
        service.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }
}
