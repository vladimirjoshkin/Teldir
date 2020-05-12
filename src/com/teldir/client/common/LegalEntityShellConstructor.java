package com.teldir.client.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class LegalEntityShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Legal Entity Shell");
        shell.setSize(900, 340);
        shell.setLayout(new GridLayout(3, false));

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
        btnAddressChange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnAddressChange.setText("Change...");

        Label lblPhoneNumbers = new Label(shell, SWT.NONE);
        lblPhoneNumbers.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        lblPhoneNumbers.setText("Phone Numbers");

        List listPhoneNumbers = new List(shell, SWT.BORDER);
        listPhoneNumbers.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 3));

        Button btnPhoneNumberAdd = new Button(shell, SWT.NONE);
        btnPhoneNumberAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberAdd.setText("Add...");
        new Label(shell, SWT.NONE);

        Button btnPhoneNumberEdit = new Button(shell, SWT.NONE);
        btnPhoneNumberEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberEdit.setText("Edit...");
        new Label(shell, SWT.NONE);

        Button btnPhoneNumberDelete = new Button(shell, SWT.NONE);
        btnPhoneNumberDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberDelete.setText("Delete");
        new Label(shell, SWT.NONE);

        Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
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