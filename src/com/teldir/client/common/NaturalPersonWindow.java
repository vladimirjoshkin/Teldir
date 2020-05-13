package com.teldir.client.common;

import com.teldir.core.Address;
import com.teldir.core.NaturalPerson;
import com.teldir.core.StringUtils;
import org.eclipse.swt.SWT;
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

    public void prefill(NaturalPerson naturalPerson) {
        this.naturalPerson = naturalPerson;
        prefilled = true;
        txtFullName.setText(naturalPerson.getFullName());
        fillNameFields(naturalPerson.getFullName());
        dtDOB.setDate(naturalPerson.getDOBYear(), naturalPerson.getDOBMonth(), naturalPerson.getDOBDay());
        address = naturalPerson.getLivingAddress();
        txtAddress.setText(address.toString());
        list.setItems(naturalPerson.getPhoneNumbersAsStringArray());
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

    public void construct(Display display) {
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
                        address = addressWindow.getAddress();
                        txtAddress.setText(address.toString());
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
        new Label(shell, SWT.NONE);

        btnPhoneNumberEdit = new Button(shell, SWT.NONE);
        btnPhoneNumberEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberEdit.setText("Edit...");
        new Label(shell, SWT.NONE);

        btnPhoneNumberDelete = new Button(shell, SWT.NONE);
        btnPhoneNumberDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnPhoneNumberDelete.setText("Delete");
        new Label(shell, SWT.NONE);

        lblHorizontalLine = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        lblHorizontalLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);

        btnCancel = new Button(shell, SWT.NONE);
        btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCancel.setText("Cancel");

        btnSave = new Button(shell, SWT.NONE);
        btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnSave.setText("Save");
    }
}