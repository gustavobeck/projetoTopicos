package modelo;

public class Exame {

    private Integer id;
    private String nome;
    private Integer idConsulta;

    public Exame(final Integer id, final String nome, final Integer idConsulta) {
        this.id = id;
        this.nome = nome;
        this.idConsulta = idConsulta;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Integer getIdConsulta() {
        return this.idConsulta;
    }

    public void setIdConsulta(final Integer idConsulta) {
        this.idConsulta = idConsulta;
    }
}
