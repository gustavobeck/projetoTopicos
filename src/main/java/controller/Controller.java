package controller;

import modelo.*;
import view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Especie especieSelecionada = null;
    private static Veterinario veterinarioSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField veterinarioSelecionadoTextField = null;
    private static JTextField especieSelecionadaTextField = null;

    public static void setTextFields(final JTextField cliente, final JTextField animal, final JTextField veterinario, final JTextField especie) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        veterinarioSelecionadoTextField = veterinario;
        especieSelecionadaTextField = especie;
    }

    public static void limparSelecao() {
        animalSelecionado = null;
        animalSelecionadoTextField.setText("");
        clienteSelecionado = null;
        clienteSelecionadoTextField.setText("");
        veterinarioSelecionado = null;
        veterinarioSelecionadoTextField.setText("");
        especieSelecionada = null;
        especieSelecionadaTextField.setText("");
    }

    public static void setTableModel(final JTable table, final GenericTableModel tableModel) {
        table.setModel(tableModel);
    }

    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public static List getTodosClientes() {
        return ClienteDAO.getInstance().retrieveAll();
    }

    public static void setSelected(final Object selected) {
        if (selected instanceof Cliente) {
            clienteSelecionado = (Cliente) selected;
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        } else if (selected instanceof Animal) {
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        } else if (selected instanceof Especie) {
            especieSelecionada = (Especie) selected;
            especieSelecionadaTextField.setText(especieSelecionada.getNome());
        } else if (selected instanceof Veterinario) {
            veterinarioSelecionado = (Veterinario) selected;
            veterinarioSelecionadoTextField.setText(veterinarioSelecionado.getNome());
        }
    }

    public static void jRadioButtonClientesSelecionado(final JTable table) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
    }

    public static boolean jRadioButtonAnimaisSelecionado(final JTable table) {
        if (getClienteSelecionado() != null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(Controller.getClienteSelecionado().getId())));
            return true;
        } else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }

    public static void jRadioButtonEspeciesSelecionado(final JTable table) {
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveAll()));
    }

    public static void jRadioButtonVeterinariosSelecionado(final JTable table) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }

    public static void atualizaNomeParecido(final JTable table, final String nomeParecido) {
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(ClienteDAO.getInstance().retrieveBySimilarName(nomeParecido));
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveBySimilarName(nomeParecido));
        } else if (table.getModel() instanceof AnimalTableModel) {
            final Cliente cliente = getClienteSelecionado();
            if (cliente != null) {
                ((GenericTableModel) table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveBySimilarName(cliente.getId(), nomeParecido));
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveBySimilarName(nomeParecido));
        }
    }

    public static void atualizaBotaoTodos(final JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getTodosClientes());
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveAll());
        } else if (table.getModel() instanceof AnimalTableModel) {
            final Cliente cliente = getClienteSelecionado();
            if (cliente != null) {
                ((GenericTableModel) table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId()));
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveAll());
        }
    }

    //CRUD Banco
    public static void adiciona(final JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel) table.getModel()).addItem(ClienteDAO.getInstance().create("", "", "", "", ""));
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel) table.getModel()).addItem(VeterinarioDAO.getInstance().create("", "", ""));
        } else if (table.getModel() instanceof AnimalTableModel) {
            ((GenericTableModel) table.getModel()).addItem(AnimalDAO.getInstance().create("", 0, "", 0, getClienteSelecionado()));
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel) table.getModel()).addItem(EspecieDAO.getInstance().create(""));
        }
    }

    public static void remove(final JTable table, final JFrame jFrame) {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(jFrame, "Não há nenhum registro para ser deletado.");
        } else if (table.getModel() instanceof ClienteTableModel) {
            final Cliente cliente = getClienteSelecionado();
            if (cliente != null) {
                AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId()).forEach(animal -> AnimalDAO.getInstance().delete((Animal) animal));
                ClienteDAO.getInstance().delete(cliente);
                ((ClienteTableModel) table.getModel()).removeItem(table.getSelectedRow());
                clienteSelecionado = null;
                clienteSelecionadoTextField.setText("");
                animalSelecionado = null;
                animalSelecionadoTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione um cliente para deletar.");
            }
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            if (veterinarioSelecionado != null) {
                VeterinarioDAO.getInstance().delete(veterinarioSelecionado);
                ((VeterinarioTableModel) table.getModel()).removeItem(table.getSelectedRow());
                veterinarioSelecionado = null;
                veterinarioSelecionadoTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione um veterinário para deletar.");
            }
        } else if (table.getModel() instanceof AnimalTableModel) {
            if (animalSelecionado != null) {
                AnimalDAO.getInstance().delete(animalSelecionado);
                ((AnimalTableModel) table.getModel()).removeItem(table.getSelectedRow());
                animalSelecionado = null;
                animalSelecionadoTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione um animal para deletar.");
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            if (especieSelecionada != null) {
                EspecieDAO.getInstance().delete(especieSelecionada);
                ((EspecieTableModel) table.getModel()).removeItem(table.getSelectedRow());
                especieSelecionada = null;
                especieSelecionadaTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione uma espécie para deletar.");
            }
        }
    }

    //Consultas
    public static List getTodasConsultas() {
        return ConsultaDAO.getInstance().retrieveAll();
    }
}
