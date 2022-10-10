package modelo;

import java.time.LocalDate;

public class Consulta {

    private Integer id;
    private LocalDate dataConsulta;
    private String horario;
    private String historico;
    private Integer idAnimal;
    private Integer idVeterinario;
    private Integer idTratamento;
    private boolean terminou;

    public Consulta(final Integer id, final LocalDate dataConsulta, final String horario, final String historico, final Integer idAnimal, final Integer idVeterinario, final Integer idTratamento, final boolean terminou) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.horario = horario;
        this.historico = historico;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVeterinario;
        this.idTratamento = idTratamento;
        this.terminou = terminou;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDate getDataConsulta() {
        return this.dataConsulta;
    }

    public void setDataConsulta(final LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getHistorico() {
        return this.historico;
    }

    public void setHistorico(final String historico) {
        this.historico = historico;
    }

    public Integer getIdAnimal() {
        return this.idAnimal;
    }

    public void setIdAnimal(final Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Integer getIdVeterinario() {
        return this.idVeterinario;
    }

    public void setIdVeterinario(final Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public Integer getIdTratamento() {
        return this.idTratamento;
    }

    public void setIdTratamento(final Integer idTratamento) {
        this.idTratamento = idTratamento;
    }

    public boolean isTerminou() {
        return this.terminou;
    }

    public void setTerminou(final boolean terminou) {
        this.terminou = terminou;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(final String horario) {
        this.horario = horario;
    }
}
