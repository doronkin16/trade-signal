/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.frames;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;

import com.tradesignal.configuration.*;
import com.tradesignal.spring.*;

public class CGeneralSettingFrame extends JFrame {

    JLabel lblSelectedMp3;
    JLabel lblMp3Path;
    JButton btnSave;
    JComboBox<EExchange> cmbExchange;
    CAppConfig appConfig;

    public CGeneralSettingFrame() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        init();
    }

    private void init() {
        appConfig = CSpringContextUtils.getAppConfig();

        cmbExchange = new JComboBox<>(EExchange.values());
        //set visible true after add new Exchange
        cmbExchange.setSelectedItem(appConfig.getExchange());
        cmbExchange.setVisible(true);

        btnSave = new JButton("Save");
        btnSave.setPreferredSize(btnSave.getPreferredSize());
        btnSave.addActionListener(event -> save());

        lblSelectedMp3 = new JLabel("Звук оповещения: ");
        lblMp3Path = new JLabel(appConfig.getNotificationSound() == null ? "Default" : appConfig.getNotificationSound());
        JButton btnSelect = new JButton("Выбрать");
        btnSelect.addActionListener(e -> {
                JFileChooser fileopen = new JFileChooser();
                fileopen.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mp3");
                    }

                    @Override
                    public String getDescription() {
                        return "MP3 (*.mp3)";
                    }
                });

                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    lblMp3Path.setText(file.getAbsolutePath());
                }
        });
        JPanel selectMp3 = new JPanel();
        selectMp3.add(lblSelectedMp3);
        selectMp3.add(lblMp3Path);
        selectMp3.add(btnSelect);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(cmbExchange);
        p.add(selectMp3);
        p.add(btnSave);
        getContentPane().add(p);
    }

    private void save() {
        appConfig.setNotificationSound(lblMp3Path.getText());
        appConfig.setExchange((EExchange) cmbExchange.getSelectedItem());

        appConfig.savePropFile();
    }
}