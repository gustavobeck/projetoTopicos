/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import modelo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author gubec
 */
public class ConsultaTableModel extends GenericTableModel {

    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsultaTableModel(final List vDados) {
        super(vDados, new String[]{"Data", "Hora", "Cliente", "Animal", "Veterinário", "Observações", "Fim"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 1:
                return Integer.class;
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                return String.class;
            case 6:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Consulta consulta = (Consulta) this.vDados.get(rowIndex);
        final Animal animal;

        switch (columnIndex) {
            case 0:
                return dateFormat.format(consulta.getDataConsulta());
            case 1:
                return consulta.getHorario();
            case 2:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return ClienteDAO.getInstance().retrieveById(animal.getIdCliente()).getNome();
            case 3:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return animal.getNome();
            case 4:
                return VeterinarioDAO.getInstance().retrieveById(consulta.getIdVeterinario()).getNome();
            case 5:
                return consulta.getHistorico();
            case 6:
                return consulta.isTerminou();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Consulta consulta = (Consulta) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                consulta.setDataConsulta(LocalDate.parse((String) aValue));
                break;
            case 1:
                consulta.setHorario((String) aValue);
                break;
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                consulta.setHistorico((String) aValue);
                break;
            case 6:
                consulta.setTerminou((Boolean) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        ConsultaDAO.getInstance().update(consulta);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return (columnIndex < 2) || (columnIndex > 4);
    }
}
