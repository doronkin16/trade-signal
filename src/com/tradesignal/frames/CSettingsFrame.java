package com.tradesignal.frames;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tradesignal.configuration.*;
import com.tradesignal.spring.*;

public class CSettingsFrame extends JFrame {

    JComboBox<EExchange> cmbExchange;
    CAbstractExchangeConfig exchangeConfig;

    JTable persantTable;
    JButton btnSave;
    JSpinner spnChangesBy;

    public CSettingsFrame() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        init();
    }

    private void init() {
        exchangeConfig = CSpringContextUtils.getExchangeConfig(CSpringContextUtils.getAppConfig().getExchange());
        createComponents();
    }

    private void createComponents() {
        cmbExchange = new JComboBox<>(EExchange.values());
        //set visible true after add new Exchange
        cmbExchange.setSelectedItem(exchangeConfig.getExchangeType());
        cmbExchange.setVisible(false);
        cmbExchange.addItemListener(event -> {
            exchangeConfig = CSpringContextUtils.getExchangeConfig((EExchange) cmbExchange.getSelectedItem());
            persantTable.setModel(new ThisColumnModel(exchangeConfig.getTickersValues()));
        });

        persantTable = new JTable(null, new Object[]{"Currency", "Percant"});
        persantTable.setModel(new ThisColumnModel(exchangeConfig.getTickersValues()));
        JScrollPane sp = new JScrollPane(persantTable);

        btnSave = new JButton("Save");
        btnSave.setPreferredSize(btnSave.getPreferredSize());
        btnSave.addActionListener(event -> save());

        JPanel setChangesByPanel = new JPanel();
        JLabel lblChangesBY = new JLabel("Показвать изменения за ");
        spnChangesBy = new JSpinner(new SpinnerNumberModel(exchangeConfig.getChangesByTime(), 20, 1000, 5));
        JLabel lblSeconds = new JLabel(" секунд");
        setChangesByPanel.add(lblChangesBY);
        setChangesByPanel.add(spnChangesBy);
        setChangesByPanel.add(lblSeconds);


        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(cmbExchange);
        p.add(setChangesByPanel);
        p.add(sp);
        p.add(btnSave);
        getContentPane().add(p);
    }

    private void save() {
        ((ThisColumnModel) persantTable.getModel()).rows.forEach(entry -> exchangeConfig.setChangesProp(entry.getKey(), entry.getValue()));
        exchangeConfig.setChangesByTime(((Number) spnChangesBy.getValue()).intValue());

        exchangeConfig.savePropFile();
    }

    private class ThisColumnModel extends AbstractTableModel {
        List<Map.Entry<String, Double>> rows;

        public ThisColumnModel(Map<String, Double> rows) {
            this.rows = rows.entrySet().stream().sorted((e1, e2) -> -e1.getKey().compareTo(e2.getKey())).collect(Collectors.toList());
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Валютная пара";
            } else if (column == 1) {
                return "Сигнализировать при увеличении на, %";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex > getRowCount() || rowIndex < 0) {
                return null;
            }
            if (columnIndex < 0 || columnIndex > getColumnCount()) {
                return null;
            }

            Map.Entry e = rows.get(rowIndex);
            if (columnIndex == 0) {
                return e.getKey();
            } else if (columnIndex == 1) {
                return e.getValue();
            }
            return null;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (rowIndex > getRowCount() || rowIndex < 0) {
                return;
            }
            if (columnIndex < 1 || columnIndex > getColumnCount()) {
                return;
            }

            if (columnIndex == 1) {
                try {
                    rows.get(rowIndex).setValue(Double.parseDouble((String) aValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                return true;
            }
            return super.isCellEditable(rowIndex, columnIndex);
        }
    }
}