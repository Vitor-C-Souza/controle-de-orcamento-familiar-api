package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.dto.resumoDto;
import br.com.vitor.controle_de_orcamento_familiar.service.resumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumo")
public class resumoController {

    @Autowired
    private resumoService service;

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<resumoDto> resumoDoMes(@PathVariable int ano, @PathVariable int mes){
        resumoDto dto = service.resumoDoMes(ano, mes);
        return ResponseEntity.ok(dto);
    }
}
