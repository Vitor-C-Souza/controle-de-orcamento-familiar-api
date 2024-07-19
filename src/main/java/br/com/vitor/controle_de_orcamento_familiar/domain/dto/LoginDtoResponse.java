package br.com.vitor.controle_de_orcamento_familiar.domain.dto;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.Usuario;

public record LoginDtoResponse(
        Long id,
        String usuario,
        String senha
) {
    public LoginDtoResponse(Usuario usuario, String senha){
        this(
                usuario.getId(),
                usuario.getUsuario(),
                senha
        );
    }
}
