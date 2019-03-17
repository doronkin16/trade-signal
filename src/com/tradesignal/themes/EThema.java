/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.themes;

import javax.swing.*;

public enum EThema {
    SYSTEM("System", UIManager.getSystemLookAndFeelClassName()),
    METAL("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"),
    NIMBUS("Nimbus", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"),
    MOTIF("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
    ACRYL("Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel"),
    AERO("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel"),
    ALUMINIUM("Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"),
    BERNSTEIN("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"),
    FAST("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel"),
    GRAPHITE("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel"),
    HIFI("HI-FI", "com.jtattoo.plaf.hifi.HiFiLookAndFeel"),
    LUNA("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel"),
    MCWIN("Mcwin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel"),
    MINT("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel"),
    NOIRE("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel"),
    SMART("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel"),
    TEXTURE("Texture", "com.jtattoo.plaf.texture.TextureLookAndFeel");

    String name;
    String cls;

    EThema(String name, String cls) {
        this.name = name;
        this.cls = cls;
    }

    public String getRealName() {
        return name;
    }

    public String getCls() {
        return cls;
    }
}
