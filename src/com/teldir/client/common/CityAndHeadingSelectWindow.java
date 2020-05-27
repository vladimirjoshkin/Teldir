package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.client.standalone.FileUtils;
import com.teldir.core.City;
import com.teldir.core.Heading;
import com.teldir.core.report.CityNaturalPersonPhoneNumbersReport;
import com.teldir.core.report.SpecificLegalEntityPhoneNumberReport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import java.util.HashMap;

public class CityAndHeadingSelectWindow {

    private Shell shell;
    private Combo comboCity;
    private Combo comboHeading;
    private Button btnCancel;
    private Button btnSave;

    private HashMap<String, Integer> cities;
    private HashMap<String, Integer> headings;

    public CityAndHeadingSelectWindow(Display display) {
        construct(display);
        updateCities();
        updateHeadings();
    }

    public void open() {
        shell.open();
    }

    private void construct(Display display) {
        shell = new Shell(display);
        shell.setLayout(new GridLayout(3, false));
        //shell.setSize(450, 150);

        comboCity = new Combo(shell, SWT.NONE);
        comboCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        new Label(shell, SWT.NONE);

        comboHeading = new Combo(shell, SWT.NONE);
        comboHeading.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                shell.close();
            }
        });

        btnSave = new Button(shell, SWT.NONE);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Form Report...");

        btnSave.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                City selectedCity = DBInterfaceProvider.getCity(getId(comboCity, cities));
                Heading selectedHeading = DBInterfaceProvider.getHeading(getId(comboHeading, headings));
                SpecificLegalEntityPhoneNumberReport report = new SpecificLegalEntityPhoneNumberReport(DBInterfaceProvider.getLegalEntitiesArrayList(selectedCity), selectedCity, selectedHeading);
                FileUtils.saveFile(shell, selectedCity.getName() + selectedHeading.getName() + "LENumbers.html", report.getReport());
                shell.close();
            }
        });
        shell.pack();
    }

    public Button getBtnSave() {
        return btnSave;
    }

    private void updateCities() {
        cities = DBInterfaceProvider.getCities();
        comboCity.removeAll();
        setItems(comboCity, cities);
    }

    private void updateHeadings() {
        headings = DBInterfaceProvider.getHeadings();
        comboHeading.removeAll();
        setItems(comboHeading, headings);
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
}
