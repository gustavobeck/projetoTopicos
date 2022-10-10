/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import modelo.Veterinario;
import modelo.VeterinarioDAO;

import java.util.List;

/**
 * @author gubec
 */
public class VeterinarioTableModel extends GenericTableModel {

    public VeterinarioTableModel(final List vDados) {
        super(vDados, new String[]{"Nome", "email", "Telefone"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Veterinario veterinario = (Veterinario) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return veterinario.getNome();
            case 1:
                return veterinario.getEmail();
            case 2:
                return veterinario.getTelefone();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Veterinario veterinario = (Veterinario) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                veterinario.setNome((String) aValue);
                break;
            case 1:
                veterinario.setEmail((String) aValue);
                break;
            case 2:
                veterinario.setTelefone((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        VeterinarioDAO.getInstance().update(veterinario);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
