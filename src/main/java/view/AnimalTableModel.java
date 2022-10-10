/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import modelo.Animal;
import modelo.AnimalDAO;
import modelo.Especie;
import modelo.EspecieDAO;

import java.util.List;

/**
 * @author gubec
 */
public class AnimalTableModel extends GenericTableModel {

    public AnimalTableModel(final List vDados) {
        super(vDados, new String[]{"Nome", "AnoNasc", "Sexo", "Espécie"});
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Animal animal = (Animal) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return animal.getNome();
            case 1:
                return animal.getAnoNasc();
            case 2:
                return animal.getSexo();
            case 3:
                final Especie especie = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                if (especie != null) {
                    return especie.getNome();
                }
                return "";
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Animal animal = (Animal) this.vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                animal.setNome((String) aValue);
                break;
            case 1:
                animal.setAnoNasc((Integer) aValue);
                break;
            case 2:
                animal.setSexo((String) aValue);
                break;
            case 3:
                Especie especie = EspecieDAO.getInstance().retrieveByName((String) aValue);
                if (especie == null) {
                    especie = EspecieDAO.getInstance().create((String) aValue);
                }
                animal.setIdEspecie(especie.getId());
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        AnimalDAO.getInstance().update(animal);
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return true;
    }
}
