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
        shell.setSize(350, 200);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        shell.setLayout(gridLayout);

        Label fullNameLabel = new Label(shell, SWT.NULL);
        fullNameLabel.setText("Full name: ");
        Text fullNameText = new Text(shell, SWT.BORDER);
        GridData fillNameGridData = new GridData(GridData.FILL_HORIZONTAL);
        fullNameText.setLayoutData(fillNameGridData);

        Text firstNameText = new Text(shell, SWT.READ_ONLY);
        firstNameText.setEditable(false);
        Text familyNameText = new Text(shell, SWT.READ_ONLY);
        familyNameText.setEditable(false);
        Text patronymicText = new Text(shell, SWT.READ_ONLY);
        patronymicText.setEditable(false);
        GridData nameGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        nameGridData.horizontalSpan = 3;

        firstNameText.setLayoutData(nameGridData);
        familyNameText.setLayoutData(nameGridData);
        patronymicText.setLayoutData(nameGridData);


        Text livingAddressText = new Text(shell,SWT.BORDER);

        /* DateTime dateOfBirthText = new DateTime(shell, SWT.NONE); */

        Label label = new Label(shell, SWT.NONE);
        label.setText("I\'m NaturalPersonListShell");
        label.pack();
        return shell;
    }
}