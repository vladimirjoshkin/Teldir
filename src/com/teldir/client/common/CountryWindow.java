package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.Country;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import java.util.ArrayList;
import java.util.HashMap;

public class CountryWindow {
    private Shell shell;
    
    private Label lblCountryId;
    private Label lblCountryName;
    private Label lblCountryCode;
    
    private Spinner spinnerCountryId;
    private Text txtCountryName;
    private Spinner spinnerCountryCode;
    private Button btnCancel;
    private Button btnSave;

    public CountryWindow(Display display) {
        construct(display);
    }

    public void open() {
        shell.open();
    }

    public void construct(Display display) {
        shell = new Shell(display);
        shell.setText("New Country");
        shell.setSize(500, 230);
        shell.setLayout(new GridLayout(3, false));

        lblCountryId = new Label(shell, SWT.NONE);
        lblCountryId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountryId.setText("ISO 3166 Numeric Code");

        spinnerCountryId = new Spinner(shell, SWT.BORDER);
        spinnerCountryId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        spinnerCountryId.setMinimum(1);
        spinnerCountryId.setMaximum(999);

        lblCountryName = new Label(shell, SWT.NONE);
        lblCountryName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountryName.setText("Country Name");

        txtCountryName = new Text(shell, SWT.BORDER);
        txtCountryName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        lblCountryCode = new Label(shell, SWT.NONE);
        lblCountryCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountryCode.setText("Country Phone Code");

        spinnerCountryCode = new Spinner(shell, SWT.BORDER);
        spinnerCountryCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        spinnerCountryCode.setMinimum(1);
        spinnerCountryCode.setMaximum(999);
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(txtCountryName.getText().length() > 0) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
                    messageBox.setText("Confirmation");
                    messageBox.setMessage("All unsaved changes will be lost. Exit anyway?");
                    int response = messageBox.open();
                    if (response == SWT.YES) {
                        shell.close();
                    }
                } else {
                    shell.close();
                }
            }
        });

        btnSave = new Button(shell, SWT.NONE);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");

        btnSave.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(countryWithEnteredIdAlreadyExists()) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                    messageBox.setText("Error");
                    messageBox.setMessage("Country with same ISO 3166 code already exists.\nPlease use another code.");
                    messageBox.open();
                } else {
                    if(countryWithEnteredNameAlreadyExists()) {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                        messageBox.setText("Error");
                        messageBox.setMessage("Country with same name already exists.\nPlease use another name.");
                        messageBox.open();
                    } else {
                        if(countryWithEnteredCodeAlreadyExists()) {
                            MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                            messageBox.setText("Error");
                            messageBox.setMessage("Country with same phone code already exists. Countries with same phone codes is not supported yet.\nPlease use another phone code.");
                            messageBox.open();
                        } else {
                            int id = Integer.parseInt(spinnerCountryId.getText());
                            String name = txtCountryName.getText();
                            int code = Integer.parseInt(spinnerCountryCode.getText());
                            DBInterfaceProvider.saveCountry(id, name, code);
                            shell.close();
                        }
                    }
                }
            }
        });
    }

    private boolean countryWithEnteredNameAlreadyExists() {
        HashMap<String, Integer> countries = DBInterfaceProvider.getCountries();
        for (String countryName : countries.keySet()) {
            if(txtCountryName.equals(countryName)) {
                return true;
            }
        }
        return false;
    }

    private boolean countryWithEnteredIdAlreadyExists() {
        HashMap<String, Integer> countries = DBInterfaceProvider.getCountries();
        for (int countryId : countries.values()) {
            if(Integer.parseInt(spinnerCountryId.getText()) == countryId) {
                return true;
            }
        }
        return false;
    }

    private boolean countryWithEnteredCodeAlreadyExists() {
        ArrayList<Country> countries = DBInterfaceProvider.getCountriesArrayList();
        for(int i = 0; i < countries.size(); i++) {
            if(Integer.parseInt(spinnerCountryCode.getText()) == countries.get(i).getCode()) {
                return true;
            }
        }
        return false;
    }

    public Button getBtnSave() {
        return btnSave;
    }
}
