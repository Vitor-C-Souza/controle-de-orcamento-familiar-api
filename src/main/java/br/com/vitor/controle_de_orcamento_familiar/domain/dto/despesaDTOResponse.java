package br.com.vitor.controle_de_orcamento_familiar.domain.dto;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.CategoriasDespesa;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Despesa;

import java.time.format.DateTimeFormatter;

public record despesaDTOResponse(
        Long id,
        String descricao,
        double valor,
        String data,
        CategoriasDespesa categoria
) {
    public despesaDTOResponse(Despesa despesa) {
        this(
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                despesa.getCategoria()
        );
    }
}
