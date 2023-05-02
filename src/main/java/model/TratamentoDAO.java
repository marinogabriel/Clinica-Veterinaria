package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.dateFormat;
import static model.DAO.getConnection;


public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    private TratamentoDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

    // CRUD    
    public Tratamento create(String nome, Calendar dataIni, Calendar dataFim, int idAnimal, boolean finalizado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamento (id_animal, nome, dataIni, dataFim, finalizado) VALUES (?,?,?,?,?)");
            stmt.setInt(1, idAnimal);
            stmt.setString(2, nome);
            stmt.setString(3, dateFormat.format(dataIni.getTime()));
            stmt.setString(4, dateFormat.format(dataFim.getTime()));
            stmt.setInt(5, (finalizado?1:0));
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamento","id"));
    }

    private Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            Calendar dtIni = Calendar.getInstance();
            Calendar dtFim = Calendar.getInstance();
            dtIni.setTime(dateFormat.parse(rs.getString("dataIni")));
            dtFim.setTime(dateFormat.parse(rs.getString("dataFim")));
            tratamento = new Tratamento(rs.getInt("id"), rs.getString("nome"), dtIni, dtFim, rs.getInt("id_animal"), rs.getInt("finalizado")==1);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tratamento;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento","id"));
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }
        
    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET id_animal, nome, dataIni, dataFim, finalizado, WHERE id=?");
            stmt.setInt(1, tratamento.getIdAnimal());
            stmt.setString(2, tratamento.getNome());
            stmt.setString(3, dateFormat.format(tratamento.getDataIni().getTime()));
            stmt.setString(4, dateFormat.format(tratamento.getDataFim().getTime()));
            stmt.setInt(5, tratamento.isFinalizado()?1:0);
            stmt.setInt(6, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
}
