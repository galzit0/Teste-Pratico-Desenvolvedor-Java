package com.teste.pratico.service;

import com.teste.pratico.model.Vaga;
import com.teste.pratico.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void saveCreate(Vaga vaga) {
        vaga.setId(null);
        save(vaga);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Vaga saveUpdate(Vaga vaga, Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new EntityNotFoundException("Solicitante id: " + id + " inexistente");
        }
        vaga.setId(id);
        return save(vaga);
    }

    public Vaga save(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        vagaRepository.deleteById(id);
    }

    public Optional<Vaga> findById(Long id) {
        return vagaRepository.findById(id);
    }

    public List<Vaga> findAll() {
        return vagaRepository.findAll();
    }

    public Optional<Vaga> findVagaByData(LocalDate dataAgendamento) {
        return vagaRepository.findVagaByDataAgendamento(dataAgendamento);
    }
}
