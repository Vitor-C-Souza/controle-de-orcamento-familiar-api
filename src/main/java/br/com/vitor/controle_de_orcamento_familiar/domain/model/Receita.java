package br.com.vitor.controle_de_orcamento_familiar.domain.model;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.receitaDTORequest;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private double valor;
    private LocalDate data;

    public Receita(receitaDTORequest dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dto.data(), formatter);
    }

    public void update(receitaDTORequest dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dto.data(), formatter);
    }
}
