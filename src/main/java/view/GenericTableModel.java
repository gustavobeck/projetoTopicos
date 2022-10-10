/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gubec
 */
public abstract class GenericTableModel extends AbstractTableModel {

    protected ArrayList<Object> vDados;
    protected String[] colunas;

    public GenericTableModel(final List vDados, final String[] colunas) {
        this.colunas = colunas;
        this.vDados = (ArrayList) vDados;
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public int getRowCount() {
        return this.vDados.size();
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return this.colunas[columnIndex];
    }

    public Object getItem(final int indiceLinha) {
        if (indiceLinha < 0) {
            return null;
        }
        return this.vDados.get(indiceLinha);
    }

    public void addItem(final Object obj) {
        this.vDados.add(obj);
        final int ultimoIndice = this.getRowCount() - 1;
        this.fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeItem(final int indiceLinha) {
        this.vDados.remove(indiceLinha);
        this.fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListOfItems(final List<Object> vItens) {
        this.clear();
        for (final Object obj : vItens) {
            this.addItem(obj);
        }
    }

    public void clear() {
        this.vDados.clear();
        this.fireTableDataChanged();
    }

    public boolean isEmpty() {
        return this.vDados.isEmpty();
    }
}
