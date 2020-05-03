package com.example.newrecyclerview;

public class Students {
    private String edtfistName, edtLastName, radioButton,edtChoseBirthday, edtPhoneNumber, edtAddress,
          linkAvt;
    private boolean permission;

    public Students() {
    }

    public Students(String edtfistName, String edtLastName, String radioButton, String edtChoseBirthday, String edtPhoneNumber, String edtAddress, String linkAvt, boolean permission) {
        this.edtfistName = edtfistName;
        this.edtLastName = edtLastName;
        this.radioButton = radioButton;
        this.edtChoseBirthday = edtChoseBirthday;
        this.edtPhoneNumber = edtPhoneNumber;
        this.edtAddress = edtAddress;
        this.linkAvt = linkAvt;
        this.permission = permission;
    }

    public String getEdtfistName() {
        return edtfistName;
    }

    public void setEdtfistName(String edtfistName) {
        this.edtfistName = edtfistName;
    }

    public String getEdtLastName() {
        return edtLastName;
    }

    public void setEdtLastName(String edtLastName) {
        this.edtLastName = edtLastName;
    }

    public String getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(String radioButton) {
        this.radioButton = radioButton;
    }

    public String getEdtChoseBirthday() {
        return edtChoseBirthday;
    }

    public void setEdtChoseBirthday(String edtChoseBirthday) {
        this.edtChoseBirthday = edtChoseBirthday;
    }

    public String getEdtPhoneNumber() {
        return edtPhoneNumber;
    }

    public void setEdtPhoneNumber(String edtPhoneNumber) {
        this.edtPhoneNumber = edtPhoneNumber;
    }

    public String getEdtAddress() {
        return edtAddress;
    }

    public void setEdtAddress(String edtAddress) {
        this.edtAddress = edtAddress;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
