package com.android.eventers;

import java.util.ArrayList;

/**
 * Created by radhikaparmar on 21/06/17.
 */

public class Contacts {
    private String name;
    private ArrayList<String> mobileNumber = new ArrayList<>();
    private Boolean flag;
    private String selectedMobileNumber;

    public String getSelectedMobileNumber() {
        return selectedMobileNumber;
    }

    public void setSelectedMobileNumber(String selectedMobileNumber) {
        this.selectedMobileNumber = selectedMobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber1) {
        mobileNumber.add(mobileNumber1);
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }


}
