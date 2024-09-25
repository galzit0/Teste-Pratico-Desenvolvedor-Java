package com.teste.pratico.bean;

import com.teste.pratico.model.Agendamento;
import com.teste.pratico.model.Solicitante;
import com.teste.pratico.model.Vaga;
import com.teste.pratico.service.AgendamentoService;
import com.teste.pratico.service.SolicitanteService;
import com.teste.pratico.utils.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Data
@RequiredArgsConstructor
public class ConsultaAgendamentoBean {

    private final AgendamentoService agendamentoService;
    private final SolicitanteService solicitanteService;

    private LocalDate inicio;
    private LocalDate fim;
    private Long solicitanteId;
    private List<Agendamento> agendamentos;
    private List<Solicitante> solicitantes;
    private Solicitante solicitante;
    private List<Object[]> resultadoConsulta;

    @PostConstruct
    public void init() {
        solicitantes = solicitanteService.findAll();
    }

    public void consultarAgendamentos() {
        try {
            Utils.validarDatas(inicio, fim);
            Solicitante solicitante = null;
            if (solicitanteId != null) {
                solicitante = solicitanteService.findById(solicitanteId)
                        .orElseThrow(() -> new IllegalArgumentException("Solicitante não encontrado"));
            }

            agendamentos = agendamentoService.findAgendamentos(inicio, fim, solicitante);
            if (agendamentos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhum agendamento encontrado", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na consulta de agendamentos", e.getMessage()));
        }
    }

    public void consultarTotalAgendamentos() {
        try {
            resultadoConsulta = agendamentoService.consultarTotalAgendamentosPorSolicitante(inicio, fim, solicitanteId);
            if (resultadoConsulta.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhum agendamento encontrado.", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar agendamentos.", null));
        }
    }


    public void limparFiltros() {
        solicitanteId = null;
        inicio = null;
        fim = null;
        agendamentos = new ArrayList<>();
        resultadoConsulta = new ArrayList<>();
    }

}
