package com.tradesignal.configuration;

import static com.tradesignal.themes.EThema.*;
import static com.tradesignal.themes.EThema.SMART;
import static com.tradesignal.themes.EThema.TEXTURE;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.metal.*;

import com.jtattoo.plaf.*;
import com.tradesignal.themes.EThema;

public class CAppConfig extends CAbstractConfigFile {

    private final static String CONFIG_FILE_NAME = "trade_signal.cfg";

    private final static String PROP_THEME = "theme";
    private final static String PROP_EXCHANGE = "exchange";
    private final static String PROP_NOTIFICATION_SOUND = "notification.sound";
    private final static String PROP_MUTE = "mute";

    @Override
    public String getConfigFileName() {
        return CONFIG_FILE_NAME;
    }

    @Override
    public void initProperties() {
        File propFile = new File(getConfigFileName());

        if (!propFile.exists()) {
            properties.setProperty(PROP_THEME, String.valueOf(EThema.SYSTEM));
            savePropFile();
        } else {
            loadPropFile();
        }
    }

    public EThema getTheme() {
        return EThema.valueOf(properties.getProperty(PROP_THEME, EThema.SYSTEM.name()));
    }

    public void setTheme(EThema theme) {
        properties.setProperty(PROP_THEME, theme.name());
    }

    public void setExchange(EExchange exchange) {
        properties.setProperty(PROP_EXCHANGE, exchange.name());
    }

    public EExchange getExchange() {
        return EExchange.valueOf(properties.getProperty(PROP_EXCHANGE, EExchange.BINANCE.name()));
    }

    public void setNotificationSound(String path) {
        properties.setProperty(PROP_NOTIFICATION_SOUND, path);
    }

    public String getNotificationSound() {
        return properties.getProperty(PROP_NOTIFICATION_SOUND);
    }

    public void setMute(boolean mute) {
        properties.setProperty(PROP_MUTE, String.valueOf(mute));
    }

    public boolean getMute() {
        return Boolean.parseBoolean(properties.getProperty(PROP_MUTE, "false"));
    }

    public void updateLookAndFeel(EThema thema) {
        try {
            // If new look handles the WindowDecorationStyle not in the same manner as the old look
            // we have to reboot our application.
            LookAndFeel oldLAF = UIManager.getLookAndFeel();
            boolean oldDecorated = false;
            if (oldLAF instanceof MetalLookAndFeel) {
                oldDecorated = true;
            }
            if (oldLAF instanceof AbstractLookAndFeel) {
                oldDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }

            // reset to default theme
            if (thema == EThema.METAL) {
                javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
            } else if (thema == EThema.ACRYL) {
                com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default");
            } else if (thema == EThema.AERO) {
                com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
            } else if (thema == EThema.ALUMINIUM) {
                com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
            } else if (thema == EThema.BERNSTEIN) {
                com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme("Default");
            } else if (thema == EThema.FAST) {
                com.jtattoo.plaf.fast.FastLookAndFeel.setTheme("Default");
            } else if (thema == EThema.GRAPHITE) {
                com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Default");
            } else if (thema == EThema.HIFI) {
                com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
            } else if (thema == EThema.LUNA) {
                com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme("Default");
            } else if (thema == EThema.MCWIN) {
                com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
            } else if (thema == EThema.MINT) {
                com.jtattoo.plaf.mint.MintLookAndFeel.setTheme("Default");
            } else if (thema == EThema.NOIRE) {
                com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Default");
            } else if (thema == EThema.SMART) {
                com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme("Default");
            } else if (thema == EThema.TEXTURE) {
                com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme("Default");
            }
            UIManager.setLookAndFeel(thema.getCls());

            LookAndFeel newLAF = UIManager.getLookAndFeel();
            boolean newDecorated = false;
            if (newLAF instanceof MetalLookAndFeel) {
                newDecorated = true;
            }
            if (newLAF instanceof AbstractLookAndFeel) {
                newDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }
            if (oldDecorated != newDecorated) {
                // Reboot the application
                setTheme(thema);
            }
        } catch (Exception ex) {
            //System.out.println("Failed loading L&F: " + guiProps.getLookAndFeel() + " Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
