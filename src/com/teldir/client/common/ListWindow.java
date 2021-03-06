package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.LegalEntity;
import com.teldir.core.NaturalPerson;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        } else if(type == ListWindow.LEGAL_ENTITY) {
            table.removeAll();
            ResultSet resultSet = DBInterfaceProvider.getLegalEntities();
            try {
                while (resultSet.next()) {
                    TableItem tcItem = new TableItem(table, SWT.NONE);
                    tcItem.setText(DBInterfaceProvider.getLegalEntity(resultSet.getInt("id")).toStringArray());
                }
            } catch (SQLException e) {
                MessageBox messageBox = new MessageBox(shell, SWT.ERROR);
                messageBox.setText("Error");
                messageBox.setMessage("Legal Entity List cannot be loaded from database.\n" + e.getMessage());
                messageBox.open();
            }
            //TableItem tiTest = new TableItem(table, SWT.NONE);
            //tiTest.setText(new String[] {"Test name", "01.01.2000", "Test addr", "+7test00-00-00"});
        }
    }

    private void filter(int type, String contain) {
        if(type == ListWindow.NATURAL_PERSON) {
            prefill(ListWindow.NATURAL_PERSON);
            ArrayList<Integer> naturalPersonMatchCriteriaId = new ArrayList<Integer>();
            for(TableItem tableItem : table.getItems()) {
                String tableItemText = "";
                for(int i = 0; i < table.getColumnCount(); i++) {
                    tableItemText += tableItem.getText(i) + " ";
                }
                if(tableItemText.contains(contain)) {
                    naturalPersonMatchCriteriaId.add(Integer.parseInt(tableItem.getText()));
                }
            }
            table.removeAll();
            for(int i = 0; i < naturalPersonMatchCriteriaId.size(); i++) {
                TableItem tcItem = new TableItem(table, SWT.NONE);
                tcItem.setText(DBInterfaceProvider.getNaturalPerson(naturalPersonMatchCriteriaId.get(i)).toStringArray());
            }
        } else if(type == ListWindow.LEGAL_ENTITY) {
            prefill(ListWindow.LEGAL_ENTITY);
            ArrayList<Integer> legalEntityMatchCriteriaId = new ArrayList<Integer>();
            for(TableItem tableItem : table.getItems()) {
                String tableItemText = "";
                for(int i = 0; i < table.getColumnCount(); i++) {
                    tableItemText += tableItem.getText(i) + " ";
                }
                if(tableItemText.contains(contain)) {
                    legalEntityMatchCriteriaId.add(Integer.parseInt(tableItem.getText()));
                }
            }
            table.removeAll();
            for(int i = 0; i < legalEntityMatchCriteriaId.size(); i++) {
                TableItem tcItem = new TableItem(table, SWT.NONE);
                tcItem.setText(DBInterfaceProvider.getLegalEntity(legalEntityMatchCriteriaId.get(i)).toStringArray());
            }
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
        btnFilter.setVisible(false);

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

            btnDelete.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    NaturalPerson selectedNaturalPerson = DBInterfaceProvider.getNaturalPerson(Integer.parseInt(table.getSelection()[0].getText()));
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
                    messageBox.setText("Confirmation");
                    messageBox.setMessage("You are about to delete " + selectedNaturalPerson.getFullName() + "." + "\n" + "Object deletion is permanent and cannot be aborted. All data, including phone numbers associated with " + selectedNaturalPerson.getFullName() + " will be permanently lost." + "\n" + "Delete " + selectedNaturalPerson.getFullName() + "?");
                    int response = messageBox.open();
                    if (response == SWT.YES) {
                        DBInterfaceProvider.deleteNaturalPerson(selectedNaturalPerson);
                        prefill(ListWindow.NATURAL_PERSON);
                    }
                }
            });

            btnSearch.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    if(txtSearch.getText().length() > 0) {
                        filter(ListWindow.NATURAL_PERSON, txtSearch.getText());
                    } else {
                        prefill(ListWindow.NATURAL_PERSON);
                    }
                }
            });
        }

        if(type == ListWindow.LEGAL_ENTITY) {
            shell.setText("Legal Entity List");
            tcId = new TableColumn(table, SWT.LEFT);
            tcName = new TableColumn(table, SWT.LEFT);
            tcName.setText("Name");
            tcName.setWidth(150);
            tcAddress = new TableColumn(table, SWT.LEFT);
            tcAddress.setText("Address");
            tcAddress.setWidth(200);
            tcPhoneNumbers = new TableColumn(table, SWT.LEFT);
            tcPhoneNumbers.setText("Phone numbers");
            tcPhoneNumbers.setWidth(100);
            prefill(ListWindow.LEGAL_ENTITY);

            btnAdd.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    LegalEntityWindow legalEntityWindow = new LegalEntityWindow(display);
                    legalEntityWindow.open();

                    legalEntityWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            prefill(ListWindow.LEGAL_ENTITY);
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
                    LegalEntityWindow legalEntityEditWindow = new LegalEntityWindow(display);
                    legalEntityEditWindow.prefill(DBInterfaceProvider.getLegalEntity(Integer.parseInt(table.getSelection()[0].getText())));
                    legalEntityEditWindow.open();

                    legalEntityEditWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            prefill(ListWindow.LEGAL_ENTITY);
                        }
                    });
                }
            });

            btnDelete.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    LegalEntity selectedLegalEntity = DBInterfaceProvider.getLegalEntity(Integer.parseInt(table.getSelection()[0].getText()));
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
                    messageBox.setText("Confirmation");
                    messageBox.setMessage("You are about to delete " + selectedLegalEntity.getFullName() + "." + "\n" + "Object deletion is permanent and cannot be aborted. All data, including phone numbers associated with " + selectedLegalEntity.getFullName() + " will be permanently lost." + "\n" + "Delete " + selectedLegalEntity.getFullName() + "?");
                    int response = messageBox.open();
                    if (response == SWT.YES) {
                        DBInterfaceProvider.deleteLegalEntity(selectedLegalEntity);
                        prefill(ListWindow.LEGAL_ENTITY);
                    }
                }
            });

            btnSearch.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    if(txtSearch.getText().length() > 0) {
                        filter(ListWindow.LEGAL_ENTITY, txtSearch.getText());
                    } else {
                        prefill(ListWindow.LEGAL_ENTITY);
                    }
                }
            });
        }
    }
}