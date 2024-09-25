package com.teste.pratico.service;

import com.teste.pratico.model.Agendamento;
import com.teste.pratico.model.Solicitante;
import com.teste.pratico.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void saveCreate(Agendamento agendamento) {
        agendamento.setId(null);
        save(agendamento);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Agendamento saveUpdate(Agendamento agendamento, Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento id: " + id + " inexistente");
        }
        agendamento.setId(id);
        return save(agendamento);
    }

    public Agendamento save(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Optional<Agendamento> findById(Long id) {
        return agendamentoRepository.findById(id);
    }

    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    public Long countByVaga(Long vagaId) {
        return agendamentoRepository.countByVaga(vagaId);
    }

    public Long countByVagaAndSolicitante(Long vagaId , Long solicitanteId) {
        return agendamentoRepository.countByVagaAndSolicitante(vagaId, solicitanteId);
    }

    public List<Agendamento> findAgendamentos(LocalDate inicio, LocalDate fim, Solicitante solicitante) {
        if (solicitante != null) {
            return agendamentoRepository.findBySolicitanteAndDataBetween(solicitante, inicio, fim);
        } else {
            return agendamentoRepository.findByDataBetween(inicio, fim);
        }
    }

    public List<Object[]> consultarTotalAgendamentosPorSolicitante(LocalDate inicio, LocalDate fim, Long solicitanteId) {
        return agendamentoRepository.consultarTotalAgendamentosPorSolicitante(inicio, fim, solicitanteId);
    }
}
