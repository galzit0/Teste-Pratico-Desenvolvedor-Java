package com.teste.pratico.bean;

import com.teste.pratico.model.Agendamento;
import com.teste.pratico.model.Solicitante;
import com.teste.pratico.model.Vaga;
import com.teste.pratico.service.AgendamentoService;
import com.teste.pratico.service.SolicitanteService;
import com.teste.pratico.service.VagaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.List;
import java.util.Optional;

@Component
@ViewScoped
@Data
@RequiredArgsConstructor
public class AgendamentoBean {

    private final AgendamentoService agendamentoService;
    private final SolicitanteService solicitanteService;
    private final VagaService vagaService;

    private Agendamento agendamento;

    private List<Agendamento> agendamentos;

    private List<Solicitante> solicitantes;

    private Long solicitanteId;

    @PostConstruct
    public void init() {
        agendamento = new Agendamento();
        agendamentos = agendamentoService.findAll();
        solicitantes = solicitanteService.findAll();
    }

    public void salvar() {
        try {
            Solicitante solicitante = solicitanteService.findById(solicitanteId)
                    .orElseThrow(() -> new IllegalArgumentException("Solicitante não encontrado"));
            agendamento.setSolicitante(solicitante);

            verificaVagasNoPeriodo(agendamento);
            verificaQtdVagasDisponiveisNoPeriodo(agendamento);
            validarLimiteAgendamentosPorSolicitante(agendamento);
            agendamentoService.saveCreate(agendamento);
            agendamentos = agendamentoService.findAll();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Agendamento salvo com sucesso", null));

            agendamento = new Agendamento();
            solicitanteId = null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar o agendamento", e.getMessage()));
        }
    }

    private void verificaVagasNoPeriodo(Agendamento agendamento) throws Exception {
            Optional<Vaga> vagaDisponivel = vagaService.findVagaByData(agendamento.getData());

            if (vagaDisponivel.isPresent()) {
                agendamento.setVaga(vagaDisponivel.get());
            } else {
                throw new Exception("Não há vagas disponíveis para a data selecionada.");
            }
    }

    private void verificaQtdVagasDisponiveisNoPeriodo(Agendamento agendamento) throws Exception {
        Vaga vaga = agendamento.getVaga();

        Long quantidadeAgendamentos = agendamentoService.countByVaga(vaga.getId());

        if (quantidadeAgendamentos >= vaga.getQuantidade()) {
            throw new Exception("O limite de agendamentos para este periodo foi excedido.");
        }
    }

    private void validarLimiteAgendamentosPorSolicitante(Agendamento agendamento) throws Exception {
        Vaga vaga = agendamento.getVaga();
        Solicitante solicitante = agendamento.getSolicitante();

        int limitePermitido = (int) Math.ceil(vaga.getQuantidade() * 0.25);

        Long totalAgendamentosSolicitante = agendamentoService.countByVagaAndSolicitante(vaga.getId(), solicitante.getId());

        if (totalAgendamentosSolicitante >= limitePermitido) {
            throw new Exception("O limite de agendamentos para este solicitante no periodo de ("
                    +vaga.getInicio() +" a " +vaga.getFim()+") foi atingido. O máximo permitido é de " +
                    limitePermitido+" agendamento");
        }
    }


    public void excluirAgendamento(Long id) {
        try {
            agendamentoService.delete(id);
            agendamentos = agendamentoService.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Agendamento excluído com sucesso", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao excluir o agendamento", null));
        }
    }
}
