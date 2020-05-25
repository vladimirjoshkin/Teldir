package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.Address;
import com.teldir.core.LegalEntity;
import com.teldir.core.NaturalPerson;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Objects;

public class LegalEntityWindow {
    private Shell shell;
    private Label lblName;
    private Text txtName;
    private Label lblAddress;
    private Text txtAddress;
    private Button btnAddressChange;
    private Label lblPhoneNumbers;
    private List listPhoneNumbers;
    private Button btnPhoneNumberAdd;
    private Button btnPhoneNumberEdit;
    private Button btnPhoneNumberDelete;
    private Label label;
    private Button btnCancel;
    private Button btnSave;

    private Address address;
    private LegalEntity legalEntity;
    private boolean prefilled = false;

    public LegalEntityWindow(Display display) {
        construct(display);
    }

    public void open() {
        shell.open();
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public void prefill(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
        prefilled = true;
        shell.setText(legalEntity.getFullName());
        txtName.setText(legalEntity.getFullName());
        address = legalEntity.getAddress();
        txtAddress.setText(address.toString());
        listPhoneNumbers.setItems(legalEntity.getPhoneNumbersAsStringArray());

        btnPhoneNumberAdd.setEnabled(true);
    }

    private boolean changed() {
        if(prefilled) {
            if (!txtName.getText().equals(legalEntity.getFullName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void construct(Display display) {
        shell = new Shell(display);
        shell.setText("New Legal Entity");
        shell.setSize(900, 340);
        shell.setLayout(new GridLayout(3, false));

        lblName = new Label(shell, SWT.NONE);
        lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblName.setText("Name");

        txtName = new Text(shell, SWT.BORDER);
        txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        lblAddress = new Label(shell, SWT.NONE);
        lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblAddress.setText("Address");

        txtAddress = new Text(shell, SWT.BORDER);
        txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnAddressChange = new Button(shell, SWT.NONE);
        btnAddressChange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnAddressChange.setText("Change...");

        btnAddressChange.addListener(SWT.Selection, new Listener() {
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
        lblPhoneNumbers.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        lblPhoneNumbers.setText("Phone Numbers");

        listPhoneNumbers = new List(shell, SWT.BORDER);
        listPhoneNumbers.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 3));

        btnPhoneNumberAdd = new Button(shell, SWT.NONE);
        btnPhoneNumberAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberAdd.setText("Add...");
        btnPhoneNumberAdd.setEnabled(false);
        new Label(shell, SWT.NONE);

        btnPhoneNumberAdd.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (prefilled) {
                    PhoneNumberWindow phoneNumberWindow = new PhoneNumberWindow(display, DBInterfaceProvider.getOwner(legalEntity));
                    phoneNumberWindow.open();
                    phoneNumberWindow.getBtnSave().addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            System.out.println("LegalEntityWindow > PhoneNumberWindow : btnSave Event : list update");
                            listPhoneNumbers.removeAll();
                            listPhoneNumbers.setItems(DBInterfaceProvider.getNaturalPerson(legalEntity.getId()).getPhoneNumbersAsStringArray());
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

        btnPhoneNumberDelete = new Button(shell, SWT.NONE);
        btnPhoneNumberDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberDelete.setText("Delete");
        btnPhoneNumberDelete.setEnabled(false);
        new Label(shell, SWT.NONE);

        label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnCancel.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if(txtName.getText().length() > 0) {
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
                    if(prefilled) {
                        if (changed()) {
                            System.out.println("LegalEntityWindow data prefilled changed");
                            DBInterfaceProvider.updateLegalEntity(legalEntity.getId(), txtName.getText());
                        }
                    } else {
                        DBInterfaceProvider.saveLegalEntity(txtName.getText(), DBInterfaceProvider.getAddress(address.getId()));
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

    private boolean filledCorrectly() {
        if (txtName.getText().length() > 0 && txtAddress.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }
}