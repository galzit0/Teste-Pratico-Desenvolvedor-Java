package com.teste.pratico.repository;

import com.teste.pratico.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    @Query("SELECT v FROM Vaga v WHERE :dataAgendamento BETWEEN v.inicio AND v.fim")
    Optional<Vaga> findVagaByDataAgendamento(@Param("dataAgendamento") LocalDate dataAgendamento);
}