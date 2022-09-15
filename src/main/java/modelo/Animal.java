package modelo;

/**
 * @author gubec
 */
public class Animal {

    private Integer id;
    private String nome;
    private Integer anoNasc;
    private String sexo;
    private Integer idEspecie;
    private Integer idCliente;

    public Animal(final Integer id, final String nome, final Integer anoNasc, final String sexo, final Integer idEspecie, final Integer idCliente) {
        this.id = id;
        this.nome = nome;
        this.anoNasc = anoNasc;
        this.sexo = sexo;
        this.idEspecie = idEspecie;
        this.idCliente = idCliente;
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

    public Integer getAnoNasc() {
        return this.anoNasc;
    }

    public void setAnoNasc(final Integer anoNasc) {
        this.anoNasc = anoNasc;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(final String sexo) {
        this.sexo = sexo;
    }

    public Integer getIdEspecie() {
        return this.idEspecie;
    }

    public void setIdEspecie(final Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(final Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Animal{" + "nome=" + this.nome + "}";
    }
}
