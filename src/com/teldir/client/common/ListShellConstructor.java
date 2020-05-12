package com.teldir.client.common;

import com.sun.rowset.internal.Row;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class ListShellConstructor {
    public static Shell construct(Display display) {
        Shell shell = new Shell(display);
        shell.setText("List Shell");
        shell.setSize(900, 600);
        shell.setLayout(new GridLayout(7, false));

        Button btnAdd = new Button(shell, SWT.NONE);
        btnAdd.setText("Add...");

        Button btnEdit = new Button(shell, SWT.NONE);
        btnEdit.setText("Edit...");

        Button btnDelete = new Button(shell, SWT.NONE);
        btnDelete.setText("Delete");
        new Label(shell, SWT.NONE);

        Text txtSearch = new Text(shell, SWT.BORDER);
        GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_txtSearch.widthHint = 325;
        txtSearch.setLayoutData(gd_txtSearch);

        Button btnSearch = new Button(shell, SWT.NONE);
        btnSearch.setText("Search");

        Button btnFilter = new Button(shell, SWT.TOGGLE);
        btnFilter.setText("Filter");

        Table table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 7, 1);
        gd_table.widthHint = 647;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        return shell;
    }
}