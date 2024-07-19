package br.com.vitor.controle_de_orcamento_familiar.controller;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.LoginDtoRequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.LoginDtoResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Usuario;
import br.com.vitor.controle_de_orcamento_familiar.domain.service.loginService;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.TokenJWT;
import br.com.vitor.controle_de_orcamento_familiar.infra.security.tokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    private loginService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private tokenService tokenService;

    @PostMapping("/cadastro")
    @Operation(summary = "Cria um novo usuario para acessar a API", tags = {"login"})
    public ResponseEntity<LoginDtoResponse> cadastrar(@RequestBody @Valid LoginDtoRequest dto, UriComponentsBuilder uri){
        String senhaEncriptada = BCrypt.hashpw(dto.senha(), BCrypt.gensalt());
        LoginDtoResponse cadastro = service.cadastro(dto, senhaEncriptada);

        URI endereco = uri.path("/login/cadastro/{id}").buildAndExpand(cadastro.id()).toUri();

        return ResponseEntity.created(endereco).body(cadastro);
    }

    @PostMapping
    @Operation(summary = "Logar no sistema e obter o TokenJWT", tags = {"login"})
    public ResponseEntity<TokenJWT> logar(@RequestBody @Valid LoginDtoRequest dto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.GerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}
