package modelo;

/**
 * @author gubec
 */
public class Animal {

    private Integer id;
    private String nome;
    private Integer idade;
    private Integer sexo;

    public Animal(final Integer id, final String nome, final Integer idade, final Integer sexo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
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

    public Integer getIdade() {
        return this.idade;
    }

    public void setIdade(final Integer idade) {
        this.idade = idade;
    }

    public Integer getSexo() {
        return this.sexo;
    }

    public void setSexo(final Integer sexo) {
        this.sexo = sexo;
    }
}
