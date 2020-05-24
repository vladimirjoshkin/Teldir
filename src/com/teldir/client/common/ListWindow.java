package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.NaturalPerson;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ListWindow {

    private Shell shell;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private Text txtSearch;
    private GridData gd_txtSearch;
    private Button btnSearch;

    private Button btnFilter;
    private Table table;
    private GridData gd_table;

    private TableColumn tcId;
    private TableColumn tcName;
    private TableColumn tcDOB;
    private TableColumn tcAddress;
    private TableColumn tcPhoneNumbers;

    public static final int NATURAL_PERSON = 10;
    public static final int LEGAL_ENTITY = 20;
    public static final int PHONE_NUMBER = 40;
    public static final int HEADING = 50;

    public ListWindow(Display display, int type) {
        construct(display, type);
    }

    public void open() {
        shell.open();
    }

    public void prefill(int type) {
        if (type == ListWindow.NATURAL_PERSON) {
            table.removeAll();
            ResultSet resultSet = DBInterfaceProvider.getNaturalPersons();
            try {
                while (resultSet.next()) {
                    TableItem tcItem = new TableItem(table, SWT.NONE);
                    tcItem.setText(DBInterfaceProvider.getNaturalPerson(resultSet.getInt("id")).toStringArray());
                }
            } catch (SQLException e) {
                MessageBox messageBox = new MessageBox(shell, SWT.ERROR);
                messageBox.setText("Error");
                messageBox.setMessage("Natural Person List cannot be loaded from database.\n" + e.getMessage());
                messageBox.open();
            }
            //TableItem tiTest = new TableItem(table, SWT.NONE);
            //tiTest.setText(new String[] {"Test name", "01.01.2000", "Test addr", "+7test00-00-00"});
        }
    }

    private void construct(Display display, int type) {
        shell = new Shell(display);
        shell.setSize(900, 600);
        shell.setLayout(new GridLayout(7, false));

        btnAdd = new Button(shell, SWT.NONE);
        btnAdd.setText("Add...");

        btnEdit = new Button(shell, SWT.NONE);
        btnEdit.setText("Edit...");
        btnEdit.setEnabled(false);

        btnDelete = new Button(shell, SWT.NONE);
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        new Label(shell, SWT.NONE);

        txtSearch = new Text(shell, SWT.BORDER);
        gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_txtSearch.widthHint = 325;
        txtSearch.setLayoutData(gd_txtSearch);

        btnSearch = new Button(shell, SWT.NONE);
        btnSearch.setText("Search");

        btnFilter = new Button(shell, SWT.TOGGLE);
        btnFilter.setText("Filter");

        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 7, 1);
        gd_table.widthHint = 647;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        if(type == ListWindow.NATURAL_PERSON) {
            shell.setText("Natural Person List");
            tcId = new TableColumn(table, SWT.LEFT);
            tcName = new TableColumn(table, SWT.LEFT);
            tcName.setText("Full name");
            tcName.setWidth(150);
            tcDOB = new TableColumn(table, SWT.LEFT);
            tcDOB.setText("Date of birth");
            tcDOB.setWidth(100);
            tcAddress = new TableColumn(table, SWT.LEFT);
            tcAddress.setText("Address");
            tcAddress.setWidth(200);
            tcPhoneNumbers = new TableColumn(table, SWT.LEFT);
            tcPhoneNumbers.setText("Phone numbers");
            tcPhoneNumbers.setWidth(100);
            prefill(ListWindow.NATURAL_PERSON);

            btnAdd.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    NaturalPersonWindow naturalPersonWindow = new NaturalPersonWindow(display);
                    naturalPersonWindow.open();

                    naturalPersonWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            prefill(ListWindow.NATURAL_PERSON);
                        }
                    });
                }
            });

            table.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    btnDelete.setEnabled(true);
                    if(table.getSelection().length == 1) {
                        btnEdit.setEnabled(true);
                    } else {
                        btnEdit.setEnabled(false);
                    }
                }
            });

            btnEdit.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    NaturalPersonWindow naturalPersonEditWindow = new NaturalPersonWindow(display);
                    naturalPersonEditWindow.prefill(DBInterfaceProvider.getNaturalPerson(Integer.parseInt(table.getSelection()[0].getText())));
                    naturalPersonEditWindow.open();

                    naturalPersonEditWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            prefill(ListWindow.NATURAL_PERSON);
                        }
                    });
                }
            });
        }
    }
}