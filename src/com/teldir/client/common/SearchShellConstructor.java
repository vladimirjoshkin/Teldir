package com.teldir.client.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class SearchShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Search Shell");
        shell.setSize(900, 300);
        shell.setLayout(new GridLayout(3, false));

        Button btnType = new Button(shell, SWT.CHECK);
        btnType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnType.setText("Type");

        Button btnNaturalPerson = new Button(shell, SWT.RADIO);
        btnNaturalPerson.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        btnNaturalPerson.setText("Natural Person");
        new Label(shell, SWT.NONE);

        Button btnLegalEntity = new Button(shell, SWT.RADIO);
        btnLegalEntity.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        btnLegalEntity.setText("Legal Entity");

        Label lblName = new Label(shell, SWT.NONE);
        lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblName.setText("Name");

        Text txtName = new Text(shell, SWT.BORDER);
        txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        Label lblAddress = new Label(shell, SWT.NONE);
        lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblAddress.setText("Address");

        Text txtAddress = new Text(shell, SWT.BORDER);
        txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Button btnAddressChange = new Button(shell, SWT.NONE);
        btnAddressChange.setText("Change...");

        Label lblPhoneNumber = new Label(shell, SWT.NONE);
        lblPhoneNumber.setText("Phone Number");

        Text txtPhoneNumber = new Text(shell, SWT.BORDER);
        txtPhoneNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        new Label(shell, SWT.NONE);

        Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        new Label(shell, SWT.NONE);

        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        Button btnSearch = new Button(shell, SWT.NONE);
        btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSearch.setText("Search");
        
        return shell;
    }
}