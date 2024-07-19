package br.com.vitor.controle_de_orcamento_familiar.domain.repository;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.CategoriasDespesa;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface despesaRepository extends JpaRepository<Despesa, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
            "FROM Despesa d " +
            "WHERE d.descricao = :descricao " +
            "AND MONTH(d.data) = :month AND YEAR(d.data) = :year")
    boolean existsByDescricaoAndMonthAndYear(@Param("descricao") String descricao, @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM Despesa d WHERE :descricao IS NULL OR d.descricao LIKE %:descricao%")
    Page<Despesa> findAllByDescricao(Pageable paginacao, String descricao);

    @Query("SELECT d FROM Despesa d WHERE YEAR(d.data) = :year AND MONTH(d.data) = :month")
    Page<Despesa> listarDespesasPorMes(Pageable paginacao, @Param("year") int ano, @Param("month") int mes);

    @Query("SELECT COALESCE(SUM(d.valor), 0.0) FROM Despesa d WHERE YEAR(d.data) = :year AND MONTH(d.data) = :month")
    double valorTotalMes(@Param("year") int ano, @Param("month") int mes);

    @Query("SELECT COALESCE(SUM(d.valor), 0.0) FROM Despesa d WHERE YEAR(d.data) = :year AND MONTH(d.data) = :month AND d.categoria = :categoria")
    double valorDaCategoriaDoMes(@Param("categoria") CategoriasDespesa categoria, @Param("year") int ano, @Param("month") int mes);
}
