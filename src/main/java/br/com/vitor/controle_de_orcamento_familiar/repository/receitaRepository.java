package br.com.vitor.controle_de_orcamento_familiar.repository;

import br.com.vitor.controle_de_orcamento_familiar.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface receitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Receita r " +
            "WHERE r.descricao = :descricao " +
            "AND MONTH(r.data) = :month")
    boolean existsByDescricaoAndMonth(@Param("descricao") String descricao, @Param("month") int month);
}
