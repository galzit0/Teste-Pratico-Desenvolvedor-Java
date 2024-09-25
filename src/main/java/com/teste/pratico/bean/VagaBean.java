package com.teste.pratico.bean;

import com.teste.pratico.model.Agendamento;
import com.teste.pratico.model.Solicitante;
import com.teste.pratico.model.Vaga;
import com.teste.pratico.service.VagaService;
import com.teste.pratico.utils.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.time.LocalDate;
import java.util.List;

@Component
@ViewScoped
@Data
@RequiredArgsConstructor
public class VagaBean {

    private final VagaService vagaService;

    private Vaga vaga;
    private List<Vaga> vagas;

    @PostConstruct
    public void init() {
        vaga = new Vaga();
        carregarVagas();
    }

    public void salvar(){
        try {
            if (validaPeriodo(vaga.getInicio(), vaga.getFim())) {
                throw new Exception("Já existem vagas cadastrada neste período.");
            }
            Utils.validarDatas(vaga.getInicio(), vaga.getFim());
            vagaService.saveCreate(vaga);
            vaga = new Vaga();
            carregarVagas();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vaga salva!", ""));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar vaga: " + e.getMessage(), ""));
        }
    }

    public void carregarVagas() {
        vagas = vagaService.findAll();
    }

    private boolean validaPeriodo(LocalDate inicio, LocalDate fim) {
        for (Vaga vaga : vagas) {
            if (vaga.getInicio() != null && vaga.getFim() != null) {
                if (inicio.isBefore(vaga.getFim()) && fim.isAfter(vaga.getInicio())) {
                    return true;
                }
            }
        }
        return false;
    }

}
