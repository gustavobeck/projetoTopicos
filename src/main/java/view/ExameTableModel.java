/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import java.util.List;
import modelo.Exame;
import modelo.ExameDAO;

/**
 *
 * @author gubec
 */
public class ExameTableModel extends GenericTableModel {

    public ExameTableModel(final List vDados) {
        super(vDados, new String[]{"Nome"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Exame exame = (Exame) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return exame.getNome();
            case 1:
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Exame exame = (Exame) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                exame.setNome((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        ExameDAO.getInstance().update(exame);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
