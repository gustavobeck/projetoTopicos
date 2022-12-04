package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.*;
import view.*;

import javax.swing.*;
import java.util.ArrayList;

import java.util.List;

public class Controller {

    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario veterinarioSelecionado = null;
    private static Consulta consultaSelecionada = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField veterinarioSelecionadoTextField = null;
    private static JTextField consultaSelecionadaTextField = null;

    public static void setTextFields(final JTextField cliente, final JTextField animal, final JTextField veterinario, final JTextField consulta) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        veterinarioSelecionadoTextField = veterinario;
        consultaSelecionadaTextField = consulta;
    }

    public static void limparSelecao() {
        animalSelecionado = null;
        animalSelecionadoTextField.setText("");
        clienteSelecionado = null;
        clienteSelecionadoTextField.setText("");
        veterinarioSelecionado = null;
        veterinarioSelecionadoTextField.setText("");
        consultaSelecionada = null;
        consultaSelecionadaTextField.setText("");
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
        } else if (selected instanceof Veterinario) {
            veterinarioSelecionado = (Veterinario) selected;
            veterinarioSelecionadoTextField.setText(veterinarioSelecionado.getNome());
        } else if (selected instanceof Consulta) {
            consultaSelecionada = (Consulta) selected;
            consultaSelecionadaTextField.setText(consultaSelecionada.getId().toString());
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
            if (table.getSelectedRow() >= 0) {
                Object item = ((GenericTableModel) table.getModel()).getItem(table.getSelectedRow());
                ((EspecieTableModel) table.getModel()).removeItem(table.getSelectedRow());
                EspecieDAO.getInstance().delete((Especie) item);
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione uma espécie para deletar.");
            }
        }
    }

    //Consultas
        public static boolean adicionaTabela2(final JTable table) {
        if (table.getModel() instanceof ConsultaTableModel) {
            if((clienteSelecionado!=null) && (animalSelecionado!=null) && (veterinarioSelecionado!=null)){
                ((GenericTableModel) table.getModel()).addItem(ConsultaDAO.getInstance().create(LocalDate.now(), "", "", animalSelecionado.getId(), veterinarioSelecionado.getId(), 0, false));
            }
            else {
                return false;
            }
        } else if (table.getModel() instanceof ExameTableModel) {
            ((GenericTableModel) table.getModel()).addItem(ExameDAO.getInstance().create("", consultaSelecionada.getId()));
        } else if(table.getModel() instanceof TratamentoTableModel) {
            ((GenericTableModel) table.getModel()).addItem(TratamentoDAO.getInstance().create("", LocalDate.now(), LocalDate.now(), animalSelecionado.getId(), false));
        }
        return true;
    }
        
    public static void removeTable2(final JTable table, final JFrame jFrame) {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(jFrame, "Não há nenhum registro para ser deletado.");
        } else if (table.getModel() instanceof ConsultaTableModel) {
            if (consultaSelecionada != null) {
                ConsultaDAO.getInstance().delete(consultaSelecionada);
                ((ConsultaTableModel) table.getModel()).removeItem(table.getSelectedRow());
                consultaSelecionada = null;
                consultaSelecionadaTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione uma consulta para ser deletada.");
            }
        } else if (table.getModel() instanceof ExameTableModel) {
            if (table.getSelectedRow() >= 0) {
                Object item = ((GenericTableModel) table.getModel()).getItem(table.getSelectedRow());
                ((GenericTableModel) table.getModel()).removeItem(table.getSelectedRow());
                ExameDAO.getInstance().delete((Exame) item);
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione um exame para ser deletado.");
            }
        } else if (table.getModel() instanceof TratamentoTableModel) {
            if (table.getSelectedRow() >= 0) {
                Object item = ((GenericTableModel) table.getModel()).getItem(table.getSelectedRow());
                ((GenericTableModel) table.getModel()).removeItem(table.getSelectedRow());
                TratamentoDAO.getInstance().delete((Tratamento) item);
            } else {
                JOptionPane.showMessageDialog(jFrame, "Selecione um tratamento para ser deletado.");
            }
        }
    }
    
    public static List getTodasConsultas() {
        return ConsultaDAO.getInstance().retrieveAll();
    }
    
    public static void filtraConsultas(JTable table, JToggleButton jtTodas, JToggleButton jtHoje, JToggleButton jtVet) {
        if(table.getModel() instanceof ConsultaTableModel) {
            String where = "";
            if(jtTodas.isSelected()) {
                where = "";
            }
            else if(jtHoje.isSelected()) {
                if(jtVet.isSelected()) {
                     where = " WHERE data = '" + dateFormat.format(LocalDate.now()) + "' and id_vet = " + veterinarioSelecionado.getId();
                }
                else {
                    where = " WHERE data = '" + dateFormat.format(LocalDate.now()) + "'";
                }
            }
            else if(jtVet.isSelected()) {
                if(jtHoje.isSelected()) {
                    where = " WHERE data = '" + dateFormat.format(LocalDate.now()) + "' and id_vet = " + veterinarioSelecionado.getId();
                }
               else {
                    where = " WHERE id_vet = " + veterinarioSelecionado.getId();
                }
            }
            
            String query = "SELECT * from consulta" + where + " ORDER BY data, horario"; 
            ((GenericTableModel) table.getModel()).addListOfItems(ConsultaDAO.getInstance().retrieve(query));
        }
    }
    
    
    public static boolean jRadioButtonExameSelecionado(final JTable table) {
        if (consultaSelecionada != null) {
            setTableModel(table, new ExameTableModel(ExameDAO.getInstance().retrieveByIdConsulta(consultaSelecionada.getId())));
            return true;
        }
        return false;
    }
    
    public static boolean jRadioButtonTratamentoSelecionado(final JTable table) {
        if (animalSelecionado != null) {
            setTableModel(table, new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByIdAnimal(animalSelecionado.getId())));
            return true;
        }
        return false;
    }
}
