package model;


public class Animal {
    private int id;
    private String nome;
    private String sexo;
    private int idEspecie;
    private int idCliente;
    
    public Animal(int id, String nome, String sexo, int idEspecie, int idCliente) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idEspecie = idEspecie;
        this.idCliente = idCliente;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdEspecie() {
        return idEspecie;
    }
    
    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}