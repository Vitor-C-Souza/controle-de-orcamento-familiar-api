package br.com.vitor.controle_de_orcamento_familiar.repository;

import br.com.vitor.controle_de_orcamento_familiar.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface despesaRepository extends JpaRepository<Despesa, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
            "FROM Despesa d " +
            "WHERE d.descricao = :descricao " +
            "AND MONTH(d.data) = :month")
    boolean existsByDescricaoAndMonth(@Param("descricao") String descricao, @Param("month") int month);
}
