package com.tradesignal.frames;

import com.tradesignal.*;
import com.tradesignal.configuration.*;
import com.tradesignal.spring.*;
import com.tradesignal.themes.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import com.tradesignal.signals.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CMainFrame extends JFrame implements IPriceLoadListener, IErrorListener {

    private CPriceSignals priceSignals;

    private CAppConfig appConfig;

    private JTable persantTable;
    private JLabel attentionLabelJustLabel;

    public CMainFrame() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        init();
    }

    private void init() {
        appConfig = CSpringContextUtils.getApplicationContext().getBean(CAppConfig.class);
        priceSignals = CSpringContextUtils.createSignal(appConfig.getExchange());
        priceSignals.addPriceLoopListener(this);
        priceSignals.addErrorListener(this);

        createComponents();
        initMenu();
        priceSignals.startLoadedPriceLoop();
    }

    private void createComponents() {
        persantTable = new JTable(null, new Object[]{"Currency", "Percant"});
        persantTable.setModel(new ThisColumnModel(new HashMap<>()));
        JScrollPane sp = new JScrollPane(persantTable);
        sp.setPreferredSize(new Dimension(250, 500));

        attentionLabelJustLabel = new JLabel();

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(sp);
        p.add(attentionLabelJustLabel);
        getContentPane().add(p);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsItem = new JMenu("Настройки");
        menuBar.add(settingsItem);

        JMenuItem generalSettingMenu = new JMenuItem("Общие настройки");
        generalSettingMenu.addActionListener(event -> {
            CGeneralSettingFrame generalSettingFrame = new CGeneralSettingFrame();
            generalSettingFrame.setVisible(true);
        });
        settingsItem.add(generalSettingMenu);

        JMenuItem signalSettingMenu = new JMenuItem("Настройки сигналов");
        signalSettingMenu.addActionListener(event -> {
            CSettingsFrame settingsFrame = new CSettingsFrame();
            settingsFrame.setSize(500, 500);
            settingsFrame.setVisible(true);
        });
        settingsItem.add(signalSettingMenu);

        JMenu themeItem = new JMenu("Темы");
        Arrays.stream(EThema.values()).map(thema -> {
            JRadioButtonMenuItem radioMenuItem = new JRadioButtonMenuItem(thema.getRealName());
            radioMenuItem.setSelected(thema == appConfig.getTheme());
            radioMenuItem.addActionListener(a -> {
                if (thema == appConfig.getTheme()) {
                    return;
                }
                appConfig.setTheme(thema);
                appConfig.savePropFile();
                dispose();
                try {
                    Launcher.main();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return radioMenuItem;
        }).forEach(themeItem::add);
        settingsItem.add(themeItem);

        JMenuItem muteMenu = new JMenuItem("Sound " + (appConfig.getMute() ? "ON" : "OFF"));
        muteMenu.addActionListener(event -> {
            appConfig.setMute(!appConfig.getMute());
            muteMenu.setText("Sound " + (appConfig.getMute() ? "ON" : "OFF"));
            appConfig.savePropFile();
        });
        settingsItem.add(muteMenu);

        setJMenuBar(menuBar);
    }

    private void updatePersantTable(Map<String, BigDecimal> persant) {
        persantTable.setModel(new ThisColumnModel(persant));
    }

    private void updateSignalPanel(Map<String, BigDecimal> signalMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><font size='14' color='red'>");
        signalMap.forEach((ticker, val) ->
                builder.append(ticker).append(" = ").append(val).append("<br>")
        );
        builder.append("</font></html>");
        attentionLabelJustLabel.setText(builder.toString());
    }

    private void audioSignal() {
        if (appConfig.getMute()) {
            return;
        }
        File mp3 = findSignalMp3();
        if (mp3 == null) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            new javafx.embed.swing.JFXPanel();
            Media hit = new Media(mp3.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }

    @Override
    public void errorHandler(Exception e) {
        JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
    }

    class ThisColumnModel extends AbstractTableModel {
        List<Map.Entry<String, BigDecimal>> persant;

        public ThisColumnModel(Map<String, BigDecimal> persant) {
            this.persant = persant.entrySet().stream().sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Валютная пара";
            } else if (column == 1) {
                return "Изменение за " + CSpringContextUtils.getExchangeConfig(appConfig.getExchange()).getChangesByTime() + "секунд, %";
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return persant.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex > getRowCount() || rowIndex < 0) {
                return null;
            }
            if (columnIndex < 0 || columnIndex > getColumnCount()) {
                return null;
            }

            Map.Entry e = persant.get(rowIndex);
            if (columnIndex == 0) {
                return e.getKey();
            } else if (columnIndex == 1) {
                return e.getValue();
            }
            return null;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        priceSignals.stopLoadedPriceLoop();
    }

    private File findSignalMp3() {
        return Optional.ofNullable(appConfig.getNotificationSound())
                .map(path -> new File(path))
                .filter(file -> file.exists() && file.canRead() && file.isFile())
                .orElse(null);
    }

    @Override
    public void pricesLoaded(Map<String, BigDecimal> allTickerPrices, Map<String, BigDecimal> signalPrices) {
        SwingUtilities.invokeLater(() -> {
            updatePersantTable(allTickerPrices);
            updateSignalPanel(signalPrices);
            if (!signalPrices.isEmpty()) {
                audioSignal();
            }
        });
    }
}
