/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import modelo.Exame;
import modelo.ExameDAO;

import java.util.List;

/**
 * @author gubec
 */
public class ExameTableModel extends GenericTableModel {

    public ExameTableModel(final List vDados) {
        super(vDados, new String[]{"Nome"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        }
        throw new IndexOutOfBoundsException("columnIndex out of bounds");
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

        if (columnIndex == 0) {
            exame.setNome((String) aValue);
        } else {
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        ExameDAO.getInstance().update(exame);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
