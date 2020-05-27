package com.teldir.client.common;

import com.teldir.client.standalone.DBInterfaceProvider;
import com.teldir.core.Country;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import java.util.HashMap;

public class NameWindow {

    public static final int COUNTRY = 100;
    public static final int DISTRICT = 110;
    public static final int CITY = 120;

    public static final int HEADING = 200;

    private Shell shell;
    private Text txtName;
    private Button btnCancel;
    private Button btnSave;

    private int type;
    private Country country;

    public NameWindow(Display display, int type) {
        if(type == HEADING) {
            this.type = type;
            construct(display);
            shell.setText("New Heading");
        } else {
            MessageBox messageBox = new MessageBox(null, SWT.ICON_ERROR | SWT.OK);
            messageBox.setText("Error");
            messageBox.setMessage("NameWindow initialization with unsupported object type.");
            messageBox.open();
        }
    }

    public NameWindow(Display display, Country country) {
        this.type = DISTRICT;
        this.country = country;
        construct(display);
        shell.setText("New District in " + country.getName());
    }

    public void open() {
        shell.open();
    }

    public String getContentString() {
        return txtName.getText();
    }

    public Integer getContentInteger() {
        return Integer.parseInt(txtName.getText());
    }

    private void construct(Display display) {
        shell = new Shell(display);
        shell.setLayout(new GridLayout(3, false));
        shell.setSize(450, 150);

        txtName = new Text(shell, SWT.BORDER);
        txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
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
                if(type == DISTRICT) {
                    if(districtWithEnteredNameAlreadyExistsInCountry()) {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                        messageBox.setText("Error");
                        messageBox.setMessage("District with same name already exists in " + country.getName() + ".\nPlease use another name.");
                        messageBox.open();
                    } else {
                        DBInterfaceProvider.saveDistrict(country, txtName.getText());
                        shell.close();
                    }
                } else if(type == HEADING) {
                    if(headingWithEnteredNameAlreadyExists()) {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                        messageBox.setText("Error");
                        messageBox.setMessage("Heading with same name already exists.\nPlease use another name.");
                        messageBox.open();
                    } else {
                        String headingName = txtName.getText();
                        DBInterfaceProvider.saveHeading(headingName);
                        shell.close();
                    }
                }
            }
        });
        shell.pack();
    }

    public Button getBtnSave() {
        return btnSave;
    }

    private boolean headingWithEnteredNameAlreadyExists() {
        HashMap<String, Integer> headings = DBInterfaceProvider.getHeadings();
        for(String headingName : headings.keySet()) {
            if(txtName.getText().equals(headingName)) {
                return true;
            }
        }
        return false;
    }

    private boolean districtWithEnteredNameAlreadyExistsInCountry() {
        HashMap<String, Integer> districts = DBInterfaceProvider.getDistricts(country.getId());
        for(String districtName : districts.keySet()) {
            if(txtName.getText().equals(districtName)) {
                return true;
            }
        }
        return false;
    }
}
