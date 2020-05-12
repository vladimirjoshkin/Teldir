package com.teldir.client.common;

import com.sun.rowset.internal.Row;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class PhoneNumberShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Phone Number Shell");
        shell.setSize(900, 320);
        shell.setLayout(new GridLayout(4, false));

        Label lblHeading = new Label(shell, SWT.NONE);
        lblHeading.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblHeading.setText("Heading");

        Combo comboHeading = new Combo(shell, SWT.NONE);
        comboHeading.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

        Label lblCountry = new Label(shell, SWT.NONE);
        lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblCountry.setText("Country");

        Combo comboCountry = new Combo(shell, SWT.NONE);
        comboCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

        Label lblPhoneNumberType = new Label(shell, SWT.NONE);
        lblPhoneNumberType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblPhoneNumberType.setText("Type");

        Button btnPhoneNumberCity = new Button(shell, SWT.RADIO);
        btnPhoneNumberCity.setText("City number");

        Combo comboCity = new Combo(shell, SWT.NONE);
        comboCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        new Label(shell, SWT.NONE);

        Button btnPhoneNumberMobile = new Button(shell, SWT.RADIO);
        btnPhoneNumberMobile.setText("Mobile number");
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        Label lblPhoneNumber = new Label(shell, SWT.NONE);
        lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblPhoneNumber.setText("Phone Number");

        Text txtPhoneNumberCountryCode = new Text(shell, SWT.BORDER);
        txtPhoneNumberCountryCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Text txtProneNumberAreaCode = new Text(shell, SWT.BORDER);
        txtProneNumberAreaCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Text txtPhoeNumberBody = new Text(shell, SWT.BORDER);
        txtPhoeNumberBody.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(shell, SWT.NONE);

        Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        Button btnSave = new Button(shell, SWT.NONE);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");
        
        return shell;
    }
}