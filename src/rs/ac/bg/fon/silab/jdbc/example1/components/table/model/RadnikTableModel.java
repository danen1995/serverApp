/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.jdbc.example1.components.table.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.silab.jdbc.example1.domen.IDomainEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.KupacEntity;
import rs.ac.bg.fon.silab.jdbc.example1.domen.RadnikEntity;

/**
 *
 * @author FON
 */
public class RadnikTableModel extends AbstractTableModel {

    private final List<RadnikEntity> radnici;
    private String[] columnNames = new String[]{"ID radnika", "Ime", "Prezime"};

    public RadnikTableModel(List<RadnikEntity> radnici) {
        this.radnici = radnici;
    }

    public RadnikTableModel() {
        radnici = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        //ternarni operator
        return radnici == null ? 0 : radnici.size();

    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RadnikEntity radnik = radnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return radnik.getIdRadnika();
            case 1:
                return radnik.getImeRadnika();
            case 2:
                return radnik.getPrezimeRadnika();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void odjavi(RadnikEntity r) {
        radnici.remove(r);
        fireTableDataChanged();
    }

    public void dodaj(RadnikEntity r) {
        radnici.add(r);
        fireTableDataChanged();
    }

}
