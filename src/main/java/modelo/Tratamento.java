package modelo;

import java.time.LocalDate;

public class Tratamento {

    private Integer id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer idAnimal;
    private boolean terminou;

    public Tratamento(final Integer id, final LocalDate dataInicio, final LocalDate dataFim, final Integer idAnimal, final boolean terminou) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idAnimal = idAnimal;
        this.terminou = terminou;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(final LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(final LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getIdAnimal() {
        return this.idAnimal;
    }

    public void setIdAnimal(final Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public boolean isTerminou() {
        return this.terminou;
    }

    public void setTerminou(final boolean terminou) {
        this.terminou = terminou;
    }
}
