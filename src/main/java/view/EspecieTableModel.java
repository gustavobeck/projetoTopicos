/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import modelo.Especie;
import modelo.EspecieDAO;

import java.util.List;

/**
 * @author gubec
 */
public class EspecieTableModel extends GenericTableModel {

    public EspecieTableModel(final List vDados) {
        super(vDados, new String[]{"Espécie"});
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
        final Especie especie = (Especie) this.vDados.get(rowIndex);

        if (columnIndex == 0) {
            return especie.getNome();
        }
        throw new IndexOutOfBoundsException("columnIndex out of bounds");
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Especie especie = (Especie) this.vDados.get(rowIndex);

        if (columnIndex == 0) {
            especie.setNome((String) aValue);
        } else {
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        EspecieDAO.getInstance().update(especie);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
