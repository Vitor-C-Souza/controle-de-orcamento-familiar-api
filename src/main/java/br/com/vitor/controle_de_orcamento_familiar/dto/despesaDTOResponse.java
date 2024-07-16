package br.com.vitor.controle_de_orcamento_familiar.dto;

import br.com.vitor.controle_de_orcamento_familiar.model.Despesa;

import java.time.format.DateTimeFormatter;

public record despesaDTOResponse(
        Long id,
        String descricao,
        double valor,
        String data
) {
    public despesaDTOResponse(Despesa receita) {
        this(
                receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
