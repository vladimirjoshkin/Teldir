package com.teldir.client.common;

import com.sun.rowset.internal.Row;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class NaturalPersonShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("Natural Person Shell");
        shell.setSize(900, 410);
        shell.setLayout(new GridLayout(4, false));

        Label lblFullName = new Label(shell, SWT.NONE);
        lblFullName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblFullName.setText("Full name");

        Text txtFullName = new Text(shell, SWT.BORDER);
        txtFullName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);

        Text txtFirstName = new Text(shell, SWT.BORDER);
        txtFirstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Text txtLastName = new Text(shell, SWT.BORDER);
        txtLastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Text txtPatronymic = new Text(shell, SWT.BORDER);
        txtPatronymic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Label lblDOB = new Label(shell, SWT.NONE);
        lblDOB.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDOB.setText("Date of birth");

        DateTime dtDOB = new DateTime(shell, SWT.DATE | SWT.BORDER);
        dtDOB.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

        Label lblAddress = new Label(shell, SWT.NONE);
        lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblAddress.setText("Address");

        Text txtAddress = new Text(shell, SWT.BORDER);
        txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        Button btnChangeAddress = new Button(shell, SWT.NONE);
        btnChangeAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnChangeAddress.setText("Change...");

        Label lblPhoneNumbers = new Label(shell, SWT.NONE);
        lblPhoneNumbers.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
        lblPhoneNumbers.setText("Phone Numbers");

        List list = new List(shell, SWT.BORDER);
        list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 3));

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

        Label lblHorizontalLine = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        lblHorizontalLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
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