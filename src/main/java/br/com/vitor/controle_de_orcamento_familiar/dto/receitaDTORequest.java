package br.com.vitor.controle_de_orcamento_familiar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record receitaDTORequest(
        @NotBlank(message = "Descrição não pode ser vazia")
        String descricao,
        @Positive(message = "Valor deve ser positivo")
        double valor,
        @NotBlank(message = "Data não pode ser vazia")
        String data
) {
}
