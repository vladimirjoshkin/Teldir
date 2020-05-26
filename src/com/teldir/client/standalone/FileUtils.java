package com.teldir.client.standalone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void saveFile(Shell shell, String fileName, String content) {
        FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
        fileDialog.setFileName(fileName);
        String dest = fileDialog.open();
        System.out.println(dest);
        try {
            FileWriter fileWriter = new FileWriter(dest);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}