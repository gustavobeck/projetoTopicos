package modelo;

public class Exame {

    private Integer id;
    private String descricao;
    private Integer idConsulta;

    public Exame(final Integer id, final String descricao, final Integer idConsulta) {
        this.id = id;
        this.descricao = descricao;
        this.idConsulta = idConsulta;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdConsulta() {
        return this.idConsulta;
    }

    public void setIdConsulta(final Integer idConsulta) {
        this.idConsulta = idConsulta;
    }
}
