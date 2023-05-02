package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Especie;
import model.EspecieDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.ConsultaTableModel;
import view.EspecieTableModel;
import view.GenericTableModel;
import view.VeterinarioTableModel;


public class Controller {
    
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario veterinarioSelecionado = null;
    private static Especie especieSelecionada = null;
    private static Consulta consultaSelecionada = null;
    
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField veterinarioSelecionadoTextField = null;

    public static Consulta getConsultaSelecionada() {
        return consultaSelecionada;
    }

    public static void setConsultaSelecionada(Consulta consultaSelecionada) {
        Controller.consultaSelecionada = consultaSelecionada;
    }

    public static void setTextFields(JTextField cliente, JTextField animal, JTextField veterinario) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        veterinarioSelecionadoTextField = veterinario;
    }     
    
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }
    
    public static Animal getAnimalSelecionado() {
        return animalSelecionado;
    }
    
    public static Especie getEspecieSelecionada() {
        return especieSelecionada;
    }
    
    public static Veterinario getVeterinarioSelecionado() {
        return veterinarioSelecionado;
    }
    
    public static void setSelected(Object selected) {
        if(selected instanceof Cliente) {
            clienteSelecionado = (Cliente) selected;
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        }else if(selected instanceof Animal) {
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        }
        else if(selected instanceof Especie) {
            especieSelecionada = (Especie) selected;
        }
        else if(selected instanceof Veterinario) {
            veterinarioSelecionado = (Veterinario) selected;
            veterinarioSelecionadoTextField.setText(veterinarioSelecionado.getNome());
        }
        else if(selected instanceof Consulta) {
            consultaSelecionada = (Consulta) selected;
        }
    }
    
    public static void clearSelected(Object selected) {
         if(selected instanceof Cliente) {
            clienteSelecionadoTextField.setText("");
            animalSelecionadoTextField.setText("");
        } else if(selected instanceof Animal) {
            animalSelecionadoTextField.setText("");
        }
        /*else if(selected instanceof Especie) {
            especieSelecionada = (Especie) selected;
        }*/
        else if(selected instanceof Veterinario) {
            veterinarioSelecionado = (Veterinario) selected;
        }
    }
    
    public static boolean jRadioButtonClientes(JTable table) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
        return true;
    }
    
    public static boolean jRadioButtonVeterinarios(JTable table) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
        return true;
    }
    
    public static boolean jRadioButtonEspecies(JTable table) {
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveAll()));
        return true;
    }
    
    public static boolean jRadioButtonAnimais(JTable table) {
        if(getClienteSelecionado() != null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(getClienteSelecionado().getId())));
            return true;
        }else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static boolean jRadioButtonConsultas(JTable table) {
        setTableModel(table, new ConsultaTableModel(ConsultaDAO.getInstance().retrieveAll()));
        return true;
    }
    
    public static boolean buscaCliente(JTable table, String text) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveBySimilarName(text)));
        return true;
    }
    
    public static boolean buscaAnimal(JTable table, String text) {
        setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveBySimilarName(text)));
        return true;
    }
    
    public static boolean buscaEspecie(JTable table, String text) {
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveBySimilarName(text)));
        return true;
    }
    
    public static boolean buscaVeterinario(JTable table, String text) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveBySimilarName(text)));
        return true;
    }
    
    public static Consulta adicionaConsulta() {
        return ConsultaDAO.getInstance().create(Calendar.getInstance(),8,"","",animalSelecionado.getId(), veterinarioSelecionado.getId(),0, false);
    }
    
    public static boolean atualizaBotaoNovo(JTable table) {
        if((clienteSelecionado!=null)&&(animalSelecionado!=null)&&(veterinarioSelecionado!=null)) {
            ((GenericTableModel) table.getModel()).addItem(adicionaConsulta());
            return true;
        }
        else
            return false;
    }
    
    public static void apagaCliente(Cliente cliente) {
        List<Animal> animais = AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId());
        for(Animal animal : animais)
            AnimalDAO.getInstance().delete(animal);
        ClienteDAO.getInstance().delete(cliente);
    }
    
    public static void filtraConsultas(JTable table, JToggleButton hoje, JToggleButton vet, JToggleButton pend) {
        String where = "WHERE ";
        if(hoje.isSelected()) {
            where += "data BETWEEN 1670814000000 and 1670900400000 ";
            if(vet.isSelected()) {
                
                where += "AND id_veterinario = "+ veterinarioSelecionado.getId();
                if(pend.isSelected())
                    where += " AND finalizado = false";
            }
            else if(pend.isSelected())
                where += "AND finalizado = false";
        }
        else if(vet.isSelected()) {
            where += "id_veterinario = "+ veterinarioSelecionado.getId();
            if(pend.isSelected()) {
                where += " AND finalizado = false";
            }
        }
        else if(pend.isSelected()) {
            where += "finalizado = false";
        }
        else {
            where = "";
            where += "ORDER BY data, hora";
        }
        String query = "SELECT * FROM consulta "+where;
        ((GenericTableModel) table.getModel()).addListOfItems(ConsultaDAO.getInstance().retrieve(query));
        table.repaint();
    }
}
