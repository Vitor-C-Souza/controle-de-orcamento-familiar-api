package br.com.vitor.controle_de_orcamento_familiar.model;

import br.com.vitor.controle_de_orcamento_familiar.dto.despesaDTORequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "despesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private double valor;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private CategoriasDespesa categoria = CategoriasDespesa.OUTRAS;

    public Despesa(despesaDTORequest dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dto.data(), formatter);
        try {
            if (dto.categoria() != null) {
                this.categoria = CategoriasDespesa.valueOf(dto.categoria().toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida!");
        }
    }

    public void update(despesaDTORequest dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dto.data(), formatter);
    }
}
