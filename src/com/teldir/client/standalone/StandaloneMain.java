package com.teldir.client.standalone;

import com.teldir.core.Address;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import com.teldir.client.common.*;

import java.util.List;
import java.util.Objects;

public class StandaloneMain {

    public static final String PROGRAM_NAME = "Teldir";
    public static final String PROGRAM_MODE = "Standalone";
    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 1;

    private static Display display = new Display();
    private static Shell shell = new Shell(display);

    public static void main(String[] args) {
        System.out.println(PROGRAM_NAME + " v" + VERSION_MAJOR + "." + VERSION_MINOR + " (" + PROGRAM_MODE + ") " + "runned.");
        //DBInterfaceProvider.initializeData();
        DBInterfaceProvider.connect();
        System.out.println(DBInterfaceProvider.getCountries());
        System.out.println(DBInterfaceProvider.getCountry(643).toString());
        System.out.println("Districts in " + DBInterfaceProvider.getCountry(643) + ": " + DBInterfaceProvider.getDistricts(643));
        System.out.println("INRTU: " + DBInterfaceProvider.getAddress(1).toString());
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

        createNewNaturalPersonItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                NaturalPersonWindow naturalPersonWindow = new NaturalPersonWindow(display);
                naturalPersonWindow.open();
            }
        });

        MenuItem showNaturalPersonListItem = new MenuItem(naturalPersonSubmenu, SWT.PUSH);
        showNaturalPersonListItem.setText("Show Natural Person List...");

        showNaturalPersonListItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                ListWindow listWindow = new ListWindow(display, ListWindow.NATURAL_PERSON);
                listWindow.open();
            }
        });

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
        debugNaturalPersonShellItem.setText("Show Natural Person Shell... (" + DBInterfaceProvider.getNaturalPerson(1).getFullName() + ")");
        debugNaturalPersonShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                /*
                NaturalPersonWindow debugNaturalPersonWindow = new NaturalPersonWindow(display);
                debugNaturalPersonWindow.open();
                */
                NaturalPersonWindow debugNaturalPersonWindow = new NaturalPersonWindow(display);
                debugNaturalPersonWindow.prefill(DBInterfaceProvider.getNaturalPerson(1));
                debugNaturalPersonWindow.open();
            }
        });

        MenuItem debugAddressShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugAddressShellItem.setText("Show Address Shell...");
        debugAddressShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                AddressWindow debugAddressWindow = new AddressWindow(display);
                debugAddressWindow.open();
                debugAddressWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                        if (debugAddressWindow.closedCorectly() == true) {
                            System.out.println("From Standalone main: " + debugAddressWindow.getAddress().toString());
                        }
                    }
                });
            }
        });

        MenuItem debugLegalEntityShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugLegalEntityShellItem.setText("Show Legal Entity Shell...");
        debugLegalEntityShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                Shell debugLegalEntityShell = LegalEntityShellConstructor.construct(display);
                debugLegalEntityShell.open();
            }
        });

        /*
        MenuItem debugListShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugListShellItem.setText("Show List Shell...");
        debugListShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                ListWindow listWindow = new ListWindow(display);
                listWindow.open();
            }
        });
        */

        MenuItem debugListItem = new MenuItem(debugSubmenu, SWT.CASCADE);
        debugListItem.setText("List");

        Menu debugListSubmenu = new Menu(shell, SWT.DROP_DOWN);
        debugListItem.setMenu(debugListSubmenu);

        MenuItem debugListNaturalPersonItem = new MenuItem(debugListSubmenu, SWT.PUSH);
        debugListNaturalPersonItem.setText("Natural Person List...");
        debugListNaturalPersonItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                ListWindow listWindow = new ListWindow(display, ListWindow.NATURAL_PERSON);
                listWindow.open();
            }
        });

        MenuItem debugLegalEntityListItem = new MenuItem(debugListSubmenu, SWT.PUSH);
        debugLegalEntityListItem.setText("Legal Entity List...");

        MenuItem debugPhoneNumberListItem = new MenuItem(debugListSubmenu, SWT.PUSH);
        debugPhoneNumberListItem.setText("Phone Number List...");

        MenuItem debugHeadingListItem = new MenuItem(debugListSubmenu, SWT.PUSH);
        debugHeadingListItem.setText("Heading List...");

        MenuItem debugPhoneNumberShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugPhoneNumberShellItem.setText("Show Phone Number Shell...");
        debugPhoneNumberShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                Shell debugPhoneNumberShell = PhoneNumberShellConstructor.construct(display);
                debugPhoneNumberShell.open();
            }
        });

        MenuItem debugSearchShellItem = new MenuItem(debugSubmenu, SWT.PUSH);
        debugSearchShellItem.setText("Show Search Shell...");
        debugSearchShellItem.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                Shell debugSearchShell = SearchShellConstructor.construct(display);
                debugSearchShell.open();
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