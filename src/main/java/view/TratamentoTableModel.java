/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Animal;
import modelo.AnimalDAO;
import modelo.Tratamento;
import modelo.TratamentoDAO;
/**
 *
 * @author gubec
 */
public class TratamentoTableModel extends GenericTableModel {
    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TratamentoTableModel(final List vDados) {
        super(vDados, new String[]{"Nome", "Data Inicio", "Data Fim", "Animal", "Terminou"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
                return String.class;
            case 4:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Tratamento tratamento = (Tratamento) this.vDados.get(rowIndex);
        final Animal animal;

        switch (columnIndex) {
            case 0:
                return tratamento.getNome();
            case 1:
                return dateFormat.format(tratamento.getDataInicio());
            case 2:
                return dateFormat.format(tratamento.getDataFim());
            case 3:
                animal = AnimalDAO.getInstance().retrieveById(tratamento.getIdAnimal());
                return animal.getNome();
            case 4:
                return tratamento.isTerminou();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Tratamento tratamento = (Tratamento) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                tratamento.setNome((String) aValue);
                break;
            case 1:
                String date = (String) aValue;
                LocalDate dataTratamento = LocalDate.parse(date, dateFormat);
                tratamento.setDataInicio(dataTratamento);
                break;
            case 2:
                String dataFim = (String) aValue;
                LocalDate dataTratamentoFim = LocalDate.parse(dataFim, dateFormat);
                tratamento.setDataFim(dataTratamentoFim);
                break;
            case 3:
                break;
            case 4:
                tratamento.setTerminou((Boolean) aValue); 
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        TratamentoDAO.getInstance().update(tratamento);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return (columnIndex != 3);
    }
}
