package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.Country;
import com.teldir.core.CountryList;
import com.teldir.core.DistrictList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Arrays;

public class AddressShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Address Shell");
        shell.setSize(900, 360);
        shell.setLayout(new GridLayout(3, false));

        Label lblIndex = new Label(shell, SWT.NONE);
        lblIndex.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblIndex.setText("Index");

        Text txtIndex = new Text(shell, SWT.BORDER);
        txtIndex.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(shell, SWT.NONE);

        Label lblCountry = new Label(shell, SWT.NONE);
        lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountry.setText("Country");

        Combo comboCountry = new Combo(shell, SWT.NONE);
        comboCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        comboCountry.setItems(DBInterfaceProvider.getCountryNames());

        Button btnCountryNew = new Button(shell, SWT.NONE);
        btnCountryNew.setText("New Country...");

        Label lblDistrict = new Label(shell, SWT.NONE);
        lblDistrict.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDistrict.setText("District");

        Combo comboDistrict = new Combo(shell, SWT.NONE);
        comboDistrict.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        comboCountry.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                comboDistrict.setItems(DBInterfaceProvider.getDistrictNames(comboCountry.getText()));
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        Button btnDistrictNew = new Button(shell, SWT.NONE);
        btnDistrictNew.setText("New District...");

        Label lblCity = new Label(shell, SWT.NONE);
        lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCity.setText("City");

        Combo comboCity = new Combo(shell, SWT.NONE);
        comboCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        comboDistrict.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                comboCity.setItems(DBInterfaceProvider.getCityNames(comboDistrict.getText()));
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        Button btnCityNew = new Button(shell, SWT.NONE);
        btnCityNew.setText("New City...");

        Label lblStreet = new Label(shell, SWT.NONE);
        lblStreet.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblStreet.setText("Street");

        Text txtStreet = new Text(shell, SWT.BORDER);
        txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        Label lblBuilding = new Label(shell, SWT.NONE);
        lblBuilding.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblBuilding.setText("Building");

        Text txtBuilding = new Text(shell, SWT.BORDER);
        txtBuilding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        new Label(shell, SWT.NONE);

        Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        new Label(shell, SWT.NONE);

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.setSelection(true);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");

        return shell;
    }

    /*
    public static String askAddress(Display display) {
        Shell addressShell = AddressShellConstructor.construct(display);

    }
    */
}