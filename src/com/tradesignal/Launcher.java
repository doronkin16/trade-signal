package com.tradesignal;

import com.tradesignal.frames.*;
import com.tradesignal.spring.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

public class Launcher {

    public static void main(String... args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(CContextConfiguration.class);
        new CSpringContextUtils().setApplicationContext(context);

        CSpringContextUtils.getAppConfig().updateLookAndFeel(CSpringContextUtils.getAppConfig().getTheme());

        CMainFrame mainFrame = new CMainFrame();
        mainFrame.setVisible(true);
    }
}
