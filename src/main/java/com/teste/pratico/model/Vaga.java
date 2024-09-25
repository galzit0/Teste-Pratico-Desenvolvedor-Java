package com.teste.pratico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VAGAS")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de Início não pode ser anterior à data de hoje.")
    @Column(name = "INICIO")
    private LocalDate inicio;

    @NotNull(message = "A data de término é obrigatória.")
    @FutureOrPresent(message = "A data de Fim não pode ser anterior à data de hoje.")
    @Column(name = "FIM")
    private LocalDate fim;

    @Min(value = 1, message = "A quantidade de vagas deve ser pelo menos 1")
    @Column(name = "QUANTIDADE")
    private int quantidade;

}