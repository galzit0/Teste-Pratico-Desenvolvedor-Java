package com.teste.pratico.service;

import com.teste.pratico.model.Solicitante;
import com.teste.pratico.repository.SolicitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolicitanteService {

    private final SolicitanteRepository solicitanteRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void saveCreate(Solicitante solicitante) {
        solicitante.setId(null);
        save(solicitante);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Solicitante saveUpdate(Solicitante solicitante, Long id) {
        if (!solicitanteRepository.existsById(id)) {
            throw new EntityNotFoundException("Solicitante id: " + id + " inexistente");
        }
        solicitante.setId(id);
        return save(solicitante);
    }

    public Solicitante save(Solicitante solicitante) {
        return solicitanteRepository.save(solicitante);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        solicitanteRepository.deleteById(id);
    }

    public Optional<Solicitante> findById(Long id) {
        return solicitanteRepository.findById(id);
    }

    public List<Solicitante> findAll() {
        return solicitanteRepository.findAll();
    }

}
