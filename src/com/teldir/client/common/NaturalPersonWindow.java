package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.Address;
import com.teldir.core.NaturalPerson;
import com.teldir.core.PhoneNumber;
import com.teldir.core.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Objects;

public class NaturalPersonWindow {

    private Shell shell;
    private Label lblFullName;
    private Text  txtFullName;
    private Text txtFirstName;
    private Text txtLastName;
    private Text txtPatronymic;
    private Label lblDOB;
    private DateTime dtDOB;
    private Label lblAddress;
    private Text txtAddress;
    private Button btnChangeAddress;
    private Label lblPhoneNumbers;
    private List list;
    private Button btnPhoneNumberAdd;
    private Button btnPhoneNumberEdit;
    private Button btnPhoneNumberDelete;
    private Label lblHorizontalLine;
    private Button btnCancel;
    private Button btnSave;

    private Address address;
    private NaturalPerson naturalPerson;
    private boolean prefilled = false;

    public NaturalPersonWindow(Display display) {
        construct(display);
    }

    public void open() {
        shell.open();
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public void prefill(NaturalPerson naturalPerson) {
        this.naturalPerson = naturalPerson;
        prefilled = true;
        shell.setText(naturalPerson.getFullName());
        txtFullName.setText(naturalPerson.getFullName());
        fillNameFields(naturalPerson.getFullName());
        dtDOB.setDate(naturalPerson.getDOBYear(), naturalPerson.getDOBMonth(), naturalPerson.getDOBDay());
        address = naturalPerson.getLivingAddress();
        txtAddress.setText(address.toString());
        list.setItems(naturalPerson.getPhoneNumbersAsStringArray());

        btnPhoneNumberAdd.setEnabled(true);
    }

    private boolean changed() {
        if(prefilled) {
            if (!txtFirstName.getText().equals(naturalPerson.getFirstName()) ||
            !txtLastName.getText().equals(naturalPerson.getFamilyName()) ||
            !txtPatronymic.getText().equals(naturalPerson.getPatronymic()) ||
            dtDOB.getYear() != naturalPerson.getDOBYear() ||
            dtDOB.getMonth() != naturalPerson.getDOBMonth() ||
            dtDOB.getDay() != naturalPerson.getDOBDay()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void fillNameFields(String fullName) {
        String[] names = StringUtils.deleteDoubleSpaces(fullName).split(" ");
        try {
            txtFirstName.setText(names[0]);
            txtLastName.setText(names[1]);
            txtPatronymic.setText(names[2]);
        } catch (IndexOutOfBoundsException ex) {
            // ignore
        }
    }

    private boolean filledCorrectly() {
        if (txtFirstName.getText().length() > 0 && txtLastName.getText().length() > 0 && txtAddress.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void construct(Display display) {
        shell = new Shell(display);
        shell.setText("Natural Person Shell");
        shell.setSize(900, 410);
        shell.setLayout(new GridLayout(4, false));

        lblFullName = new Label(shell, SWT.NONE);
        lblFullName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblFullName.setText("Full name");

        txtFullName = new Text(shell, SWT.BORDER);
        txtFullName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);

        txtFirstName = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
        txtFirstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        txtLastName = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
        txtLastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        txtPatronymic = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
        txtPatronymic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        txtFullName.addListener(SWT.FocusOut, new Listener() {
            @Override
            public void handleEvent(Event event) {
                fillNameFields(txtFullName.getText());
            }
        });

        lblDOB = new Label(shell, SWT.NONE);
        lblDOB.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDOB.setText("Date of birth");

        dtDOB = new DateTime(shell, SWT.DATE | SWT.BORDER);
        dtDOB.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

        lblAddress = new Label(shell, SWT.NONE);
        lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblAddress.setText("Address");

        txtAddress = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
        txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        btnChangeAddress = new Button(shell, SWT.NONE);
        btnChangeAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnChangeAddress.setText("Change...");

        btnChangeAddress.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                AddressWindow addressWindow = new AddressWindow(display);
                if(Objects.nonNull(address)) {
                    addressWindow.prefillAddress(address);
                }
                addressWindow.open();
                addressWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                        if (addressWindow.closedCorectly()) {
                            address = addressWindow.getAddress();
                            txtAddress.setText(address.toString());
                        }
                    }
                });
            }
        });

        lblPhoneNumbers = new Label(shell, SWT.NONE);
        lblPhoneNumbers.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
        lblPhoneNumbers.setText("Phone Numbers");

        list = new List(shell, SWT.BORDER);
        list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 3));

        btnPhoneNumberAdd = new Button(shell, SWT.NONE);
        btnPhoneNumberAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberAdd.setText("Add...");
        btnPhoneNumberAdd.setEnabled(false);
        new Label(shell, SWT.NONE);

        btnPhoneNumberAdd.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (prefilled) {
                    PhoneNumberWindow phoneNumberWindow = new PhoneNumberWindow(display, DBInterfaceProvider.getOwner(naturalPerson));
                    phoneNumberWindow.open();
                    phoneNumberWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            System.out.println("NaturalPersonWindow > PhoneNumberWindow : btnSave Event : list update");
                            list.removeAll();
                            list.setItems(DBInterfaceProvider.getNaturalPerson(naturalPerson.getId()).getPhoneNumbersAsStringArray());
                        }
                    });
                }
            }
        });

        btnPhoneNumberEdit = new Button(shell, SWT.NONE);
        btnPhoneNumberEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberEdit.setText("Edit...");
        btnPhoneNumberEdit.setEnabled(false);
        new Label(shell, SWT.NONE);

        btnPhoneNumberEdit.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                String selectedNumber = list.getSelection()[0];
                PhoneNumber selectedPhoneNumber = DBInterfaceProvider.getPhoneNumber(selectedNumber);
                PhoneNumberWindow phoneNumberWindow = new PhoneNumberWindow(display, DBInterfaceProvider.getOwner(naturalPerson));
                phoneNumberWindow.prefill(selectedPhoneNumber);
                phoneNumberWindow.open();
                phoneNumberWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                        System.out.println("NaturalPersonWindow > PhoneNumberWindow : btnSave Event : list update");
                        list.removeAll();
                        list.setItems(DBInterfaceProvider.getNaturalPerson(naturalPerson.getId()).getPhoneNumbersAsStringArray());
                    }
                });
            }
        });

        btnPhoneNumberDelete = new Button(shell, SWT.NONE);
        btnPhoneNumberDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberDelete.setText("Delete");
        btnPhoneNumberDelete.setEnabled(false);
        new Label(shell, SWT.NONE);

        btnPhoneNumberDelete.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                String selectedNumber = list.getSelection()[0];
                PhoneNumber selectedPhoneNumber = DBInterfaceProvider.getPhoneNumber(selectedNumber);
                DBInterfaceProvider.deletePhoneNumber(selectedPhoneNumber.getId());

                list.removeAll();
                list.setItems(DBInterfaceProvider.getNaturalPerson(naturalPerson.getId()).getPhoneNumbersAsStringArray());

                btnPhoneNumberEdit.setEnabled(false);
                btnPhoneNumberDelete.setEnabled(false);
            }
        });

        list.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                btnPhoneNumberEdit.setEnabled(true);
                btnPhoneNumberDelete.setEnabled(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        lblHorizontalLine = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        lblHorizontalLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(txtFullName.getText().length() > 0) {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
                    messageBox.setText("Confirmation");
                    messageBox.setMessage("All unsaved changes will be lost. Exit anyway?");
                    int response = messageBox.open();
                    if (response == SWT.YES) {
                        shell.close();
                    }
                } else {
                    shell.close();
                }
            }
        });

        btnSave = new Button(shell, SWT.NONE);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");

        btnSave.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (filledCorrectly()) {
                    String patronymic = "";
                    if (txtPatronymic.getText().length() > 0) {
                        patronymic = txtPatronymic.getText();
                    }
                    String dob = String.valueOf(dtDOB.getYear()) + "." + String.valueOf(dtDOB.getMonth()) + "." + String.valueOf(dtDOB.getDay());
                    if(prefilled) {
                        if (changed()) {
                            System.out.println("NaturalPersonWindow data prefilled changed");
                            DBInterfaceProvider.updateNaturalPerson(naturalPerson.getId(), txtFirstName.getText(), txtLastName.getText(), patronymic, dob);
                        }
                    } else {
                        DBInterfaceProvider.saveNaturalPerson(txtFirstName.getText(), txtLastName.getText(), patronymic, dob, DBInterfaceProvider.getAddress(address.getId()));
                    }
                    shell.close();
                } else {
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                    messageBox.setText("Form filled incorrectly");
                    messageBox.setMessage("Please form all fields with correspondents values.\nNatural Person has to have First name and Family name.");
                    messageBox.open();
                }
            }
        });
    }
}