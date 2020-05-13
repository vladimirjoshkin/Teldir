package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddressWindow {

    private Shell   shell;
    private Label   lblIndex;
    private Text    txtIndex;
    private Label   lblCountry;
    private Combo   comboCountry;
    private Button  btnCountryNew;
    private Label   lblDistrict;
    private Combo   comboDistrict;
    private Button  btnDistrictNew;
    private Label   lblCity;
    private Combo   comboCity;
    private Button  btnCityNew;
    private Label   lblStreet;
    private Text    txtStreet;
    private Label   lblBuilding;
    private Text    txtBuilding;
    private Label   label;
    private Button  btnCancel;
    private Button  btnSave;

    private Address address;

    private HashMap<String, Integer> countries;
    private HashMap<String, Integer> districts;
    private HashMap<String, Integer> cities;

    public AddressWindow(Display display) {
        construct(display);
    }

    public void open() {
        shell.open();
    }

    private void construct(Display display) {
        shell = new Shell(display);
        shell.setText("Address Shell");
        shell.setSize(900, 360);
        shell.setLayout(new GridLayout(3, false));

        lblIndex = new Label(shell, SWT.NONE);
        lblIndex.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblIndex.setText("Index");

        txtIndex = new Text(shell, SWT.BORDER);
        txtIndex.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(shell, SWT.NONE);

        lblCountry = new Label(shell, SWT.NONE);
        lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountry.setText("Country");

        comboCountry = new Combo(shell, SWT.NONE);
        comboCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnCountryNew = new Button(shell, SWT.NONE);
        btnCountryNew.setText("New Country...");

        lblDistrict = new Label(shell, SWT.NONE);
        lblDistrict.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDistrict.setText("District");

        comboDistrict = new Combo(shell, SWT.NONE);
        comboDistrict.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnDistrictNew = new Button(shell, SWT.NONE);
        btnDistrictNew.setText("New District...");

        lblCity = new Label(shell, SWT.NONE);
        lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCity.setText("City");

        comboCity = new Combo(shell, SWT.NONE);
        comboCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        updateCountries();
        comboCountry.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                updateDistricts();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        comboDistrict.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                updateCities();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        btnCityNew = new Button(shell, SWT.NONE);
        btnCityNew.setText("New City...");

        lblStreet = new Label(shell, SWT.NONE);
        lblStreet.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblStreet.setText("Street");

        txtStreet = new Text(shell, SWT.BORDER);
        txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        lblBuilding = new Label(shell, SWT.NONE);
        lblBuilding.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblBuilding.setText("Building");

        txtBuilding = new Text(shell, SWT.BORDER);
        txtBuilding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        new Label(shell, SWT.NONE);

        label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                address = null;
                shell.close();
            }
        });

        btnSave = new Button(shell, SWT.NONE);
        btnSave.setSelection(true);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");

        btnSave.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                formAddress();
                shell.close();
            }
        });
    }

    private void setItems(Combo combo, HashMap<String, Integer> hashMap) {
        for(String i : hashMap.keySet()) {
            combo.add(i);
        }
    }

    private int getId(Combo combo, HashMap<String, Integer> hashMap) {
        return hashMap.get(combo.getText());
    }

    private int getSelectedCountryId() {
        return getId(comboCountry, countries);
    }

    private int getSelectedDistrictId() {
        return getId(comboDistrict, districts);
    }

    private int getSelectedCityId() {
        return getId(comboCity, cities);
    }

    private void updateCountries() {
        countries = DBInterfaceProvider.getCountries();
        comboCountry.removeAll();
        comboDistrict.removeAll();
        comboCity.removeAll();
        setItems(comboCountry, countries);
    }

    private void updateDistricts() {
        districts = DBInterfaceProvider.getDistricts(getId(comboCountry, countries));
        comboDistrict.removeAll();
        comboCity.removeAll();
        setItems(comboDistrict, districts);
    }

    private void updateCities() {
        cities = DBInterfaceProvider.getCities(getId(comboDistrict, districts));
        comboCity.removeAll();
        setItems(comboCity, cities);
    }

    private Address formAddress() {
        System.out.println(txtIndex.getText() + " " + getSelectedCityId() + " " + txtStreet.getText() + " " + txtBuilding.getText());
        address = DBInterfaceProvider.saveAddress(txtIndex.getText(), getSelectedCityId(), txtStreet.getText(), txtBuilding.getText());
        return address;
    }

    public Address getAddress() {
        return address;
    }

    public Button getBtnSave() {
        return btnSave;
    }
}