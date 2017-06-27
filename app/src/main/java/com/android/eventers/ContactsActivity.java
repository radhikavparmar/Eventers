package com.android.eventers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.util.ArrayList;
import java.util.Locale;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.ListItemClickListener {

    private static final int CHECK_CLICK = 1;
    private static final String LIST_STATE_KEY = "list_state";
    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    ContactsAdapter mAdapter;
    String contactName;
    String mobileNumber;
    String mobileNumberSelected;
    Contacts contactsObject;
    TextView noItem;
    private ArrayList<Contacts> contactsArrayList;
    ArrayList<String> tempList;
    private Parcelable mListState;
    private LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    private boolean mCalledFromOncreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mCalledFromOncreate = true;
        noItem = (TextView) findViewById(R.id.no_listitem_in_contacts);
        noItem.setVisibility(View.GONE);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_fab_in_main);
        contactsArrayList = new ArrayList<Contacts>();


        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
//        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE NOCASE ASC");
//        while (phones.moveToNext()) {
//
//
//            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String phoneNumberStr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            try {
//
//
//                final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//                PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberStr, Locale.getDefault().getCountry());
//                PhoneNumberUtil.PhoneNumberType phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber);
//
//
//                if (phoneNumberType == PhoneNumberType.MOBILE) {
//
//                    if (name.equals(contactName)) {
//
//
//                        phoneNumberStr = phoneNumberStr.replaceAll(" ", "");
//
//                        if (phoneNumberStr.contains(mobileNumber)) {
//
//                        } else {
//                            mobileNumber = String.valueOf(phoneNumber.getNationalNumber());
//                            if(!tempList.contains(mobileNumber)) {
//                                Log.e("phone: ", " " + phoneNumber);
//                                contactsObject.setMobileNumber(mobileNumber);
//                                tempList.add(mobileNumber);
//                            }
//                        }
//
//                    } else {
//
//                        if (contactsObject != null) {
//                            contactsArrayList.add(contactsObject);
//                        }
//                        contactsObject = new Contacts();
//                        tempList = new ArrayList<String>();
//                        contactName = name;
//                        mobileNumber = String.valueOf(phoneNumber.getNationalNumber());
//                        tempList.add(mobileNumber);
//                        Log.e("name: ", " " + name);
//                        Log.e("phone: ", " " + mobileNumber);
//
//                        contactsObject.setName(name);
//                        contactsObject.setMobileNumber(mobileNumber);
//                        contactsObject.setFlag(mSharedPreferences.getBoolean("checkbox_" + name, false));
//                        contactsObject.setSelectedMobileNumber(mSharedPreferences.getString("selected_mobile_number_for_" + name, mobileNumber));
//                    }
//
//                }
//            } catch (Exception e) {
//            }
//            if (phones.isLast()) {
//                contactsArrayList.add(contactsObject);
//            }
//
//        }
//        phones.close();
        launchConacts();


        for (int i = 0; i < contactsArrayList.size(); i++) {

            Log.e("name:", "" + contactsArrayList.get(i).getName());
            for (int j = 0; j < contactsArrayList.get(i).getMobileNumber().size(); j++) {
                Log.e("num:", contactsArrayList.get(i).getMobileNumber().get(j));
            }
        }
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = "";
                int counter = 0;


                for (int i = 0; i < contactsArrayList.size(); i++) {
                    Contacts singleContact = contactsArrayList.get(i);
                    if (contactsArrayList.get(i).getFlag()) {

                        data = data + "\n" + singleContact.getName().toString() + "    " + singleContact.getSelectedMobileNumber();
                        counter++;
                        mEditor.putBoolean("checkbox_" + contactsArrayList.get(i).getName(), true);
                        mEditor.putString("selected_mobile_number_for_" + contactsArrayList.get(i).getName(), "" + singleContact.getSelectedMobileNumber());
                    } else {
                        mEditor.putBoolean("checkbox_" + contactsArrayList.get(i).getName(), false);
                        mEditor.putString("selected_mobile_number_for_" + contactsArrayList.get(i).getName(), "" + singleContact.getSelectedMobileNumber());
                    }

                }
                mEditor.commit();
                Toast.makeText(ContactsActivity.this, "Selected contacts: \n" + data, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ContactsActivity.this, ReportActivity.class);
                intent.putExtra("TOTAL_KEY", contactsArrayList.size() + "");
                intent.putExtra("SELECTED_KEY", counter + "");
                startActivity(intent);

            }
        });
    }

    @Override
    public void onListItemClick(final int clickedItemIndex, int whichClick) {

        switch (whichClick) {
            case CHECK_CLICK: {
                //Toast.makeText(ContactsActivity.this, "Clicked on Checkbox: "+clickedItemIndex , Toast.LENGTH_SHORT).show();
                int selectedMobileNumberPosition = 0;
                String selectedMobileNumber = contactsArrayList.get(clickedItemIndex).getSelectedMobileNumber();
                if (contactsArrayList.get(clickedItemIndex).getMobileNumber().size() > 1) {
                    final String items[] = new String[contactsArrayList.get(clickedItemIndex).getMobileNumber().size()];
                    for (int j = 0; j < contactsArrayList.get(clickedItemIndex).getMobileNumber().size(); j++) {
                        items[j] = contactsArrayList.get(clickedItemIndex).getMobileNumber().get(j);
                        if (items[j].contains(selectedMobileNumber)) {
                            selectedMobileNumberPosition = j;
                        }
                    }


                    AlertDialog levelDialog;


                    // Creating and Building the Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Please select the mobile number");


                    builder.setSingleChoiceItems(items, selectedMobileNumberPosition, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {


                            mobileNumberSelected = items[item];
                            contactsArrayList.get(clickedItemIndex).setSelectedMobileNumber(mobileNumberSelected);
                            // levelDialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Toast.makeText(ContactsActivity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                        }
                    });
                    levelDialog = builder.create();
                    levelDialog.show();

                }

                break;
            }
        }

    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mListState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
    }


    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if (state != null)
            mListState = state.getParcelable(LIST_STATE_KEY);
    }


    @Override
    protected void onResume() {
        super.onResume();


        if (!mCalledFromOncreate) {
            contactsArrayList = new ArrayList<Contacts>();
            launchConacts();
            mAdapter.notifyDataSetChanged();
            Log.e("Inside", "onResume after clear");
        }


        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    void launchConacts() {

        //Cursor pho = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE NOCASE ASC");

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE NOCASE ASC");
       Log.i("Size is "," "+phones.getCount());
        if (phones != null && (phones.getCount() > 0)) {
            phones.moveToFirst();
            phones.move(0);

            for (int i = 0; i < phones.getCount(); i++) {


                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumberStr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                try {


                    final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                    PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberStr, Locale.getDefault().getCountry());
                    PhoneNumberUtil.PhoneNumberType phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber);


                    if (phoneNumberType == PhoneNumberType.MOBILE) {

                        if (name.equals(contactName)) {

                            phoneNumberStr = phoneNumberStr.replaceAll(" ", "");

                            if (phoneNumberStr.contains(mobileNumber)) {

                            } else {
                                mobileNumber = String.valueOf(phoneNumber.getNationalNumber());
                                if (!tempList.contains(mobileNumber)) {
                                    //  Log.e("phone: ", " " + phoneNumber);
                                    contactsObject.setMobileNumber(mobileNumber);
                                    tempList.add(mobileNumber);
                                }
                            }

                        } else {

                            if (contactsObject != null) {
                                contactsArrayList.add(contactsObject);
                                Log.e("object added", contactsObject.getName());
                            }
                            contactsObject = new Contacts();
                            tempList = new ArrayList<String>();
                            contactName = name;
                            mobileNumber = String.valueOf(phoneNumber.getNationalNumber());
                            tempList.add(mobileNumber);
                            // Log.e("name: ", " " + name);
                            // Log.e("phone: ", " " + mobileNumber);

                            contactsObject.setName(name);
                            contactsObject.setMobileNumber(mobileNumber);
                            contactsObject.setFlag(mSharedPreferences.getBoolean("checkbox_" + name, false));
                            contactsObject.setSelectedMobileNumber(mSharedPreferences.getString("selected_mobile_number_for_" + name, mobileNumber));
                        }

                    }
                } catch (Exception e) {

                } finally {

                }
                if (phones.isLast()) {
                    contactsArrayList.add(contactsObject);
                   // Log.e("object added last>>>>>", contactsObject.getName());
                }
                phones.moveToNext();
            }
            //phones.close();
        }




        mRecyclerView = (RecyclerView)

                findViewById(R.id.recycler_view_in_contacts);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new

                LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new

                ContactsAdapter(contactsArrayList, ContactsActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        if (contactsArrayList.size() == 0)

        {
            noItem.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCalledFromOncreate = false;
    }
}
