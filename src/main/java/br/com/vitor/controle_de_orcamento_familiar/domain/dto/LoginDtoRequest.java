package br.com.vitor.controle_de_orcamento_familiar.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDtoRequest(
        @NotBlank
        String usuario,
        @NotBlank
        String senha
) {
}
