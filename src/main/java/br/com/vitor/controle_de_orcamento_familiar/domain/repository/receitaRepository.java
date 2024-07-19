package br.com.vitor.controle_de_orcamento_familiar.domain.repository;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface receitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Receita r " +
            "WHERE r.descricao = :descricao " +
            "AND MONTH(r.data) = :month AND YEAR(r.data) = :year")
    boolean existsByDescricaoAndMonth(@Param("descricao") String descricao, @Param("month") int month, @Param("year") int ano);

    @Query("SELECT r FROM Receita r WHERE :descricao IS NULL OR r.descricao LIKE %:descricao%")
    Page<Receita> findAllByDescricao(Pageable paginacao, String descricao);

    @Query("SELECT r FROM Receita r WHERE YEAR(r.data) = :year AND MONTH(r.data) = :month")
    Page<Receita> listarReceitasPorMes(Pageable paginacao, @Param("year") int ano, @Param("month") int mes);

    @Query("SELECT COALESCE(SUM(r.valor), 0.0) FROM Receita r WHERE YEAR(r.data) = :year AND MONTH(r.data) = :month")
    double valorTotalMes(@Param("year") int ano, @Param("month") int mes);
}
