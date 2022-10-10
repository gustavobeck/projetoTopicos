package modelo;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gubec
 */
public class Cliente {

    private Integer id;
    private String nome;
    private String endereco;
    private String telefone;
    private String cep;
    private String email;
    // private List<Animal> animais;

    public Cliente(final Integer id, final String nome, final String endereco, final String telefone, final String cep, final String email) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cep = cep;
        this.email = email;
        //this.animais = new ArrayList<>();
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

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        if (StringUtils.isBlank(this.email)) {
            this.email = email;
        }
    }

   /* public List<Animal> getAnimais() {
        return new ArrayList<>(this.animais);
    }

    public void setAnimais(final List<Animal> animais) {
        this.animais = animais;
    }

    public void addAnimal(final Animal animal) {
        if (StringUtils.isNotBlank(animal.getNome())) {
            this.animais.add(animal);
        }
    }*/

    @Override
    public String toString() {
//        final String clienteAnimais = this.animais.toString();
//        return desc + "\n" + clienteAnimais;
        return "Cliente{" + "nome=" + this.nome + ", endereco=" + this.endereco + ", telefone=" + this.telefone + ", cep=" + ", email=" + this.email + "}";
    }
}
