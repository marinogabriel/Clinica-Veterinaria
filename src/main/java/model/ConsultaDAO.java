package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.getConnection;


public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

    // CRUD    
    public Consulta create(Calendar data, int hora, String descricao, String exames, int idAnimal, int idVet, int idTratamento, boolean finalizado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, hora, descricao, exames, id_animal, id_veterinario, id_tratamento, finalizado) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setDate(1, new java.sql.Date(data.getTimeInMillis()));
            stmt.setInt(2, hora);
            stmt.setString(3, descricao);
            stmt.setString(4, exames);
            stmt.setInt(5, idAnimal);
            stmt.setInt(6, idVet);
            stmt.setInt(7, idTratamento);
            stmt.setInt(8, (finalizado?1:0));
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }

    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("data"));
            consulta = new Consulta(rs.getInt("id"), dt, rs.getInt("hora"), rs.getString("descricao"), rs.getString("exames"), rs.getInt("id_animal"), rs.getInt("id_veterinario"), rs.getInt("id_tratamento"), rs.getInt("finalizado")==1);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta ORDER BY data, hora");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
        
    // Update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, hora=?, descricao=?, exames=?, id_animal=?, id_veterinario=?, id_tratamento=?, finalizado=? WHERE id=?");
            stmt.setDate(1, new Date(consulta.getData().getTimeInMillis()));
            stmt.setInt(2, consulta.getHora());
            stmt.setString(3, consulta.getDescricao());
            stmt.setString(4, consulta.getExames());
            stmt.setInt(5, consulta.getIdAnimal());
            stmt.setInt(6, consulta.getIdVeterinario());
            stmt.setInt(7, consulta.getIdTratamento());
            stmt.setInt(8, consulta.isFinalizado()?1:0);
            stmt.setInt(9, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
}
