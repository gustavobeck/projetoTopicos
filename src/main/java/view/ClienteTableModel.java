/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import modelo.Cliente;
import modelo.ClienteDAO;

import java.util.List;

/**
 * @author gubec
 */
public class ClienteTableModel extends GenericTableModel {

    public ClienteTableModel(final List vDados) {
        super(vDados, new String[]{"Nome", "Endereço", "CEP", "email", "Telefone"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Cliente cliente = (Cliente) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getEndereco();
            case 2:
                return cliente.getCep();
            case 3:
                return cliente.getEmail();
            case 4:
                return cliente.getTelefone();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Cliente cliente = (Cliente) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                cliente.setNome((String) aValue);
                break;
            case 1:
                cliente.setEndereco((String) aValue);
                break;
            case 2:
                cliente.setCep((String) aValue);
                break;
            case 3:
                cliente.setEmail((String) aValue);
                break;
            case 4:
                cliente.setTelefone((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        ClienteDAO.getInstance().update(cliente);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
