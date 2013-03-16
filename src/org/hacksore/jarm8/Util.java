package org.hacksore.jarm8;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {
    public static String file_get_contents(String filePath) {
        if (filePath == null)
            return "";
        try {
            StringBuffer fileData = new StringBuffer(10000);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void file_put_contents(String filepath, String str) {
        File f = new File(filepath);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(filepath, false));
            w.write(str);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyFile(File from, File to) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        try { in = new BufferedInputStream(new FileInputStream(from));
            out = new BufferedOutputStream(new FileOutputStream(to));
            byte[] buf = new byte[1024];
            int len;
            while ((len = in .read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            out.close(); in .close();
        }
    }

    public static String readFileAsString(String file) {
        try {
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer str = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                str.append(line);
                line = br.readLine();
            }

            return str.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}