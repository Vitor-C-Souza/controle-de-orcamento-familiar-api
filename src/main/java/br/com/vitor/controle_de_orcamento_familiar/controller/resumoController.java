package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.resumoDto;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.resumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumo")
@SecurityRequirement(name = "bearer-key")
public class resumoController {

    @Autowired
    private resumoService service;

    @GetMapping("/{ano}/{mes}")
    @Operation(summary = "Exibe o resumo do orçamento do mês", tags = {"resumo"})
    public ResponseEntity<resumoDto> resumoDoMes(@PathVariable int ano, @PathVariable int mes){
        resumoDto dto = service.resumoDoMes(ano, mes);
        return ResponseEntity.ok(dto);
    }
}
