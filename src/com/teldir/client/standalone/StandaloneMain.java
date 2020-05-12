package com.teldir.client.standalone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import com.teldir.client.common.*;

public class StandaloneMain {

    public static final String PROGRAM_NAME = "Teldir";
    public static final String PROGRAM_MODE = "Standalone";
    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 1;

    private static Display display = new Display();
    private static Shell shell = new Shell(display);

    public static void main(String[] args) {
        System.out.println(PROGRAM_NAME + " v" + VERSION_MAJOR + "." + VERSION_MINOR + " ("+ PROGRAM_MODE + ") " + "runned.");

        System.out.println("GUI Main.");

        shell.setText(PROGRAM_NAME);

        Menu bar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(bar);

        /* File menu */
        MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
        fileItem.setText("&File");

        Menu fileSubmenu = new Menu(shell, SWT.DROP_DOWN);
        fileItem.setMenu(fileSubmenu);

        MenuItem createNewDatabaseItem = new MenuItem(fileSubmenu, SWT.PUSH);
        createNewDatabaseItem.setText("Create new database...");

        MenuItem openDatabaseItem = new MenuItem(fileSubmenu, SWT.PUSH);
        openDatabaseItem.setText("Open database...");

        /* Separator */
        new MenuItem(fileSubmenu, SWT.SEPARATOR);

        MenuItem sayHelloItem = new MenuItem(fileSubmenu, SWT.PUSH);
        sayHelloItem.setText("Say Hello");
        sayHelloItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MessageBox sayHelloMessageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                sayHelloMessageBox.setText("Hello!");
                sayHelloMessageBox.setMessage("Hello, again.");
                sayHelloMessageBox.open();
            }
        });

        /* Separator */
        new MenuItem(fileSubmenu, SWT.SEPARATOR);

        MenuItem exitItem = new MenuItem(fileSubmenu, SWT.PUSH);
        exitItem.setText("Exit");
        exitItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                display.dispose();
            }
        });

        /* Natural person menu */
        MenuItem naturalPersonItem = new MenuItem(bar, SWT.CASCADE);
        naturalPersonItem.setText("&Natural Person");

        Menu naturalPersonSubmenu = new Menu(shell, SWT.DROP_DOWN);
        naturalPersonItem.setMenu(naturalPersonSubmenu);

        MenuItem createNewNaturalPersonItem = new MenuItem(naturalPersonSubmenu, SWT.PUSH);
        createNewNaturalPersonItem.setText("Create new Natural Person...");

        MenuItem showNaturalPersonListItem = new MenuItem(naturalPersonSubmenu, SWT.PUSH);
        showNaturalPersonListItem.setText("Show Natural Person List...");

        /* Legal Entity menu */
        MenuItem legalEntityItem = new MenuItem(bar, SWT.CASCADE);
        legalEntityItem.setText("&Legal Entity");

        Menu legalEntitySubmenu = new Menu(shell, SWT.DROP_DOWN);
        legalEntityItem.setMenu(legalEntitySubmenu);

        MenuItem createNewLegalEntityItem = new MenuItem(legalEntitySubmenu, SWT.PUSH);
        createNewLegalEntityItem.setText("Create new Legal Entity...");

        MenuItem showLegalEntityListItem = new MenuItem(legalEntitySubmenu, SWT.PUSH);
        showLegalEntityListItem.setText("Show Legal Entity List...");

        /* Debug */
        MenuItem debugItem = new MenuItem(bar, SWT.CASCADE);
        debugItem.setText("DEBUG");

        Menu debugSubmenu = new Menu(shell, SWT.DROP_DOWN);
        debugItem.setMenu(debugSubmenu);

        MenuItem debugNaturalPersonShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugNaturalPersonShellItem.setText("Show Natural Person Shell...");
        debugNaturalPersonShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                Shell debugNaturalPersonShell = NaturalPersonShellConstructor.construct(display);
                debugNaturalPersonShell.open();
            }
        });

        shell.setSize(200, 200);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}