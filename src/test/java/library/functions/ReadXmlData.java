/*
 * This source file is proprietary property of Pavandeep Puddupakkam.
 */
package library.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ReadXmlData {
    public String value;

    public String commonData(String node) {
        return readData(node, "src/test/java/library/data/CommonData.xml");
    }

    private String readData(String node, String dataSource) {
        try {
            value = null;
            File file = new File(dataSource);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                if (((String) enuKeys.nextElement()).contains(node)) {
                    value = properties.getProperty(node);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
