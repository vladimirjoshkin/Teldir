package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneNumberWindow {
    private Shell shell;
    private Label lblHeading;
    private Combo comboHeading;
    private Label lblCountry;
    private Combo comboCountry;
    private Label lblPhoneNumberType;
    private Button btnPhoneNumberCity;
    private Combo comboCity;
    private Button btnPhoneNumberMobile;
    private Label lblPhoneNumber;
    private Text txtPhoneNumberCountryCode;
    private Text txtPhoneNumberAreaCode;
    private Text txtPhoneNumberBody;
    private Label label;
    private Button btnCancel;
    private Button btnSave;

    private Owner owner;

    private HashMap<String, Integer> headings;
    private HashMap<String, Integer> countries;
    private HashMap<String, Integer> cities;

    private boolean prefilled = false;
    private PhoneNumber prefilledPhoneNumber;

    public PhoneNumberWindow(Display display, Owner owner) {
        construct(display);
        this.owner = owner;
    }

    public void open() {
        shell.open();
    }

    public void prefill(PhoneNumber phoneNumber) {
        prefilled = true;
        prefilledPhoneNumber = phoneNumber;
        comboHeading.select(getIndex(phoneNumber.getHeading().getId(), headings));
        Country country = DBInterfaceProvider.getCountryByCode(phoneNumber.getCountryCode());
        comboCountry.select(getIndex(country.getId(), countries));
        txtPhoneNumberCountryCode.setText("+" + country.getCode());
        updateCities();
        ArrayList<City> _cities = DBInterfaceProvider.getCountryCitiesArrayList(country.getId());
        for(int i = 0; i < _cities.size(); i++) {
            if(phoneNumber.getAreaCode() == _cities.get(i).getCode()) {
                btnPhoneNumberCity.setSelection(true);
                comboCity.select(getIndex(_cities.get(i).getId(), cities));
                txtPhoneNumberAreaCode.setText(String.valueOf(_cities.get(i).getCode()));
            }
        }
        txtPhoneNumberBody.setText(String.valueOf(phoneNumber.getBody()));
        shell.setText("Edit " + phoneNumber.getNumber());
    }

    private void construct(Display display) {
        shell = new Shell(display);
        shell.setText("New Phone Number");
        shell.setSize(900, 320);
        shell.setLayout(new GridLayout(4, false));

        lblHeading = new Label(shell, SWT.NONE);
        lblHeading.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblHeading.setText("Heading");

        comboHeading = new Combo(shell, SWT.NONE);
        comboHeading.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        updateHeadings();

        lblCountry = new Label(shell, SWT.NONE);
        lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountry.setText("Country");

        comboCountry = new Combo(shell, SWT.NONE);
        comboCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

        lblPhoneNumberType = new Label(shell, SWT.NONE);
        lblPhoneNumberType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblPhoneNumberType.setText("Type");

        btnPhoneNumberCity = new Button(shell, SWT.RADIO);
        btnPhoneNumberCity.setText("City number");

        comboCity = new Combo(shell, SWT.NONE);
        comboCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        comboCity.setEnabled(false);
        new Label(shell, SWT.NONE);

        updateCountries();

        comboCountry.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                updateCities();
                Country selectedCountry = DBInterfaceProvider.getCountry(getId(comboCountry, countries));
                txtPhoneNumberCountryCode.setText("+" + selectedCountry.getCode());
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        btnPhoneNumberCity.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(btnPhoneNumberCity.getSelection()) {
                    comboCity.setEnabled(true);
                    txtPhoneNumberAreaCode.setEditable(false);
                }
            }
        });

        comboCity.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                City selectedCity = DBInterfaceProvider.getCity(getId(comboCity, cities));
                txtPhoneNumberAreaCode.setText(String.valueOf(selectedCity.getCode()));
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        btnPhoneNumberMobile = new Button(shell, SWT.RADIO);
        btnPhoneNumberMobile.setText("Mobile number");
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        btnPhoneNumberMobile.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                comboCity.setEnabled(false);
                txtPhoneNumberAreaCode.setEditable(true);
                txtPhoneNumberAreaCode.setText("");
            }
        });

        lblPhoneNumber = new Label(shell, SWT.NONE);
        lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblPhoneNumber.setText("Phone Number");

        txtPhoneNumberCountryCode = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
        txtPhoneNumberCountryCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        txtPhoneNumberAreaCode = new Text(shell, SWT.BORDER);
        txtPhoneNumberAreaCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        txtPhoneNumberBody = new Text(shell, SWT.BORDER);
        txtPhoneNumberBody.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(shell, SWT.NONE);

        label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(txtPhoneNumberCountryCode.getText().length() > 0 ||
                   txtPhoneNumberAreaCode.getText().length() > 0 ||
                   txtPhoneNumberBody.getText().length() > 0) {
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
                if(filledCorrectly()) {
                    if (numberAlreadyExists(getNumber())) {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                        messageBox.setText("Number already exists");
                        messageBox.setMessage("Number you entered already exists. Please enter other number.");
                        messageBox.open();
                    } else {
                        Heading heading = DBInterfaceProvider.getHeading(getId(comboHeading, headings));
                        if(prefilled) {
                            DBInterfaceProvider.updatePhoneNumber(prefilledPhoneNumber.getId(), heading, owner, getNumber());
                        } else {
                            DBInterfaceProvider.savePhoneNumber(heading, owner, getNumber());
                        }
                        shell.close();
                    }
                }
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

    private int getIndex(int id, HashMap<String, Integer> hashMap) {
        for(int i = 0; i < hashMap.size(); i++) {
            if(Integer.parseInt(hashMap.values().toArray()[i].toString()) == id) {
                return i;
            }
        }
        return 0;
    }

    private void updateHeadings() {
        headings = DBInterfaceProvider.getHeadings();
        comboHeading.removeAll();
        setItems(comboHeading, headings);
    }

    private void updateCountries() {
        countries = DBInterfaceProvider.getCountries();
        comboCountry.removeAll();
        comboCity.removeAll();
        setItems(comboCountry, countries);
    }

    private void updateCities() {
        cities = DBInterfaceProvider.getCountryCities(getId(comboCountry, countries));
        comboCity.removeAll();
        setItems(comboCity, cities);
    }

    private boolean numberAlreadyExists(String number) {
        ArrayList<PhoneNumber> existedPhoneNumbers = DBInterfaceProvider.getPhoneNumbers();
        for(int i = 0; i < existedPhoneNumbers.size(); i++) {
            if(number.equals(existedPhoneNumbers.get(i).getNumber())) {
                return true;
            }
        }
        return false;
    }

    private String getNumber() {
        return txtPhoneNumberCountryCode.getText() + "(" + txtPhoneNumberAreaCode.getText() + ")" + txtPhoneNumberBody.getText();
    }

    private boolean filledCorrectly() {
        if (comboHeading.getText().length() > 0 &&
            txtPhoneNumberCountryCode.getText().length() > 0 &&
            txtPhoneNumberAreaCode.getText().length() > 0 &&
            txtPhoneNumberBody.getText().length() > 0) {
            return true;
        } else {
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
            messageBox.setText("Form filled incorrectly");
            messageBox.setMessage("Please form all fields with correspondents values.");
            messageBox.open();
            return false;
        }
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public Shell getShell() {
        return shell;
    }
}