package com.android.eventers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    ContactsAdapter mAdapter;
    String contactName;
    String mobileNumber;
    String mobileNumberSelected;
    Contacts contactsObject;
    TextView noItem;
    private ArrayList<Contacts> contactsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        noItem = (TextView)findViewById(R.id.no_listitem_in_contacts);
        noItem.setVisibility(View.GONE);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_fab_in_main);
        contactsArrayList = new ArrayList<Contacts>();


        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE NOCASE ASC");
        while (phones.moveToNext()) {


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
                               Log.e("phone: ", " " + phoneNumber);
                            contactsObject.setMobileNumber(mobileNumber);

                        }

                    } else {

                        if (contactsObject!=null) {
                            contactsArrayList.add(contactsObject);
                        }
                        contactsObject = new Contacts();

                        contactName = name;
                        mobileNumber = String.valueOf(phoneNumber.getNationalNumber());
                         Log.e("name: ", " " + name);
                          Log.e("phone: ", " " + mobileNumber);

                        contactsObject.setName(name);
                        contactsObject.setMobileNumber(mobileNumber);
                        contactsObject.setFlag(false);
                        contactsObject.setSelectedMobileNumber(mobileNumber);
                    }

                }
            } catch (Exception e) {
            }
            if(phones.isLast()){
                contactsArrayList.add(contactsObject);
            }

        }
        phones.close();


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_in_contacts);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ContactsAdapter(contactsArrayList, ContactsActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        if(contactsArrayList.size()==0){
            noItem.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < contactsArrayList.size(); i++) {

            Log.e("name:", "" + contactsArrayList.get(i).getName());
            for (int j = 0;j<contactsArrayList.get(i).getMobileNumber().size();j++) {
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

                        data = data + "\n" + singleContact.getName().toString()+"    "+singleContact.getSelectedMobileNumber();
                        counter++;
                    }

                }

                Toast.makeText(ContactsActivity.this, "Selected Students: \n" + data, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ContactsActivity.this,ReportActivity.class);
                intent.putExtra("TOTAL_KEY",contactsArrayList.size()+"");
                intent.putExtra("SELECTED_KEY",counter+"");
                startActivity(intent);

            }
        });
    }

    @Override
    public void onListItemClick(final int clickedItemIndex, int whichClick) {

        switch (whichClick){
            case CHECK_CLICK:{
                //Toast.makeText(ContactsActivity.this, "Clicked on Checkbox: "+clickedItemIndex , Toast.LENGTH_SHORT).show();
               if(contactsArrayList.get(clickedItemIndex).getMobileNumber().size()>1) {
                   final String items[] = new String[contactsArrayList.get(clickedItemIndex).getMobileNumber().size()] ;
                   for (int j = 0;j<contactsArrayList.get(clickedItemIndex).getMobileNumber().size();j++) {
                       items[j] = contactsArrayList.get(clickedItemIndex).getMobileNumber().get(j);
                   }
//                   String items []= {"1234123443","1234567890","1234432143"};
//                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                   alertDialogBuilder.setMessage("Please select mobile number");
//                   alertDialogBuilder.setSingleChoiceItems( items, -1, new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                       }
//                   });
//                   alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface arg0, int arg1) {
//                           Toast.makeText(ContactsActivity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
//                       }
//                   });
//
//                   alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                       @Override
//                       public void onClick(DialogInterface dialog, int which) {
//                           finish();
//                       }
//                   });

//                   AlertDialog alertDialog = alertDialogBuilder.create();
//                   alertDialog.show();





                   AlertDialog levelDialog;

// Strings to Show In Dialog with Radio Buttons
              //     final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};

                   // Creating and Building the Dialog
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setTitle("Please select the mobile number");
                   builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
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
}
