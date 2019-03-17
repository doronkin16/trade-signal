/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.configuration;

import java.io.*;
import java.util.Properties;

public abstract class CAbstractConfigFile {

    protected final Properties properties = new Properties();

    protected abstract String getConfigFileName();

    public void savePropFile() {
        try (OutputStream output = new FileOutputStream(getConfigFileName())) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void loadPropFile() {
        try (InputStream input = new FileInputStream(getConfigFileName())) {
            properties.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public abstract void initProperties();
}
