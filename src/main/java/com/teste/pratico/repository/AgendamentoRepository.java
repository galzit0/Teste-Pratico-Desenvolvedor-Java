package com.teste.pratico.repository;

import com.teste.pratico.model.Agendamento;
import com.teste.pratico.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AgendamentoRepository extends JpaRepository <Agendamento, Long>{

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.vaga.id = :vagaId")
    Long countByVaga(@Param("vagaId") Long vagaId);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.vaga.id = :vagaId AND a.solicitante.id = :solicitanteId")
    Long countByVagaAndSolicitante(@Param("vagaId") Long vagaId, @Param("solicitanteId") Long solicitanteId);

    @Query("SELECT a FROM Agendamento a WHERE a.solicitante = :solicitante AND a.data BETWEEN :inicio AND :fim")
    List<Agendamento> findBySolicitanteAndDataBetween(
            @Param("solicitante") Solicitante solicitante,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("SELECT a FROM Agendamento a WHERE a.data BETWEEN :inicio AND :fim")
    List<Agendamento> findByDataBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("SELECT s.nome, COUNT(a.id) as totalAgendamentos, SUM(v.quantidade) as quantidadeVagas, " +
            "(CAST(COUNT(a.id) AS float) / CAST(SUM(v.quantidade) AS float) * 100) as percentualUtilizado " +
            "FROM Agendamento a " +
            "JOIN a.solicitante s " +
            "JOIN a.vaga v " +
            "WHERE a.data BETWEEN :inicio AND :fim " +
            "AND (:solicitanteId IS NULL OR s.id = :solicitanteId) " +
            "GROUP BY s.id, s.nome")
    List<Object[]> consultarTotalAgendamentosPorSolicitante(@Param("inicio") LocalDate inicio,
                                                            @Param("fim") LocalDate fim,
                                                            @Param("solicitanteId") Long solicitanteId);
}
