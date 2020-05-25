package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.District;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import java.util.HashMap;

public class CityWindow {
    private Shell shell;

    private Text txtCityName;
    private Spinner spinnerCityCode;

    private Label lblCityName;
    private Label lblCityCode;

    private Button btnCancel;
    private Button btnSave;

    private District district;

    public CityWindow(Display display, District district) {
        this.district = district;
        construct(display);
    }

    public void open() {
        shell.open();
    }

    public void construct(Display display) {
        shell = new Shell(display);
        shell.setText("SWT Application");
        shell.setSize(500, 190);
        shell.setLayout(new GridLayout(3, false));

        lblCityName = new Label(shell, SWT.NONE);
        lblCityName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCityName.setText("City Name");

        txtCityName = new Text(shell, SWT.BORDER);
        txtCityName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        lblCityCode = new Label(shell, SWT.NONE);
        lblCityCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCityCode.setText("City Phone Code");

        spinnerCityCode = new Spinner(shell, SWT.BORDER);
        spinnerCityCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        spinnerCityCode.setMinimum(1);
        spinnerCityCode.setMaximum(9999);
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(txtCityName.getText().length() > 0) {
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
                if(cityWithEnteredNameAlreadyExistsInDistrict()) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                    messageBox.setText("Error");
                    messageBox.setMessage("City with same name already exists in " + district.getName() + ".\nPlease use another name.");
                    messageBox.open();
                } else {
                    if(cityWithEnteredCodeExistsInCountry()) {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                        messageBox.setText("Error");
                        messageBox.setMessage("City with same phone code already exists in " + district.getCountry().getName() + ".\nPlease use another phone code.");
                        messageBox.open();
                    } else {
                        String cityName = txtCityName.getText();
                        int cityCode = Integer.parseInt(spinnerCityCode.getText());
                        DBInterfaceProvider.saveCity(district, cityName, cityCode);
                        shell.close();
                    }
                }
            }
        });
    }

    private boolean cityWithEnteredNameAlreadyExistsInDistrict() {
        HashMap<String, Integer> cities = DBInterfaceProvider.getCities(district.getId());
        for(String cityName : cities.keySet()) {
            if(txtCityName.getText().equals(cityName)) {
                return true;
            }
        }
        return false;
    }

    private boolean cityWithEnteredCodeExistsInCountry() {
        HashMap<String, Integer> cities = DBInterfaceProvider.getCountryCities(district.getCountry().getId());
        for(int cityCode : cities.values()) {
            if(Integer.parseInt(spinnerCityCode.getText()) == cityCode) {
                return true;
            }
        }
        return false;
    }

    public Button getBtnSave() {
        return btnSave;
    }
}
