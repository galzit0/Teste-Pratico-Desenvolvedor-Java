package com.teste.pratico.bean;

import com.teste.pratico.model.Solicitante;
import com.teste.pratico.service.SolicitanteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.List;


@Component
@ViewScoped
@Data
@RequiredArgsConstructor
public class SolicitanteBean {

    private final SolicitanteService solicitanteService;

    private Solicitante solicitante;

    private List<Solicitante> solicitantes;

    @PostConstruct
    public void init() {
        solicitante = new Solicitante();
        carregarSolicitantes();
    }

    public void salvar() {
        try {
            solicitanteService.saveCreate(solicitante);
            solicitante = new Solicitante();
            carregarSolicitantes();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitante salvo!", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar solicitante: " + e.getMessage(), ""));
        }
    }

    public void carregarSolicitantes() {
        solicitantes = solicitanteService.findAll();
    }

    public void excluir(Long id) {
        try {
            solicitanteService.delete(id);
            carregarSolicitantes();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitante excluído!", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir solicitante: " + e.getMessage(), ""));
        }
    }
}
