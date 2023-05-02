package model;

import java.util.Calendar;


public class Consulta {
    private int id;
    private Calendar data;
    private int hora;
    private String descricao;
    private String exames;
    private int idAnimal;
    private int idVeterinario;
    private int idTratamento;
    private boolean finalizado;

    public Consulta(int id, Calendar data, int hora, String descricao, String exames, int idAnimal, int idVeterinario, int idTratamento, boolean finalizado) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.exames = exames;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVeterinario;
        this.idTratamento = idTratamento;
        this.finalizado = finalizado;
    }
    
    public int getId() {
        return id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getExames() {
        return exames;
    }

    public void setExames(String exames) {
        this.exames = exames;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public int getIdTratamento() {
        return idTratamento;
    }
    
    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
    
}