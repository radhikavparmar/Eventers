package com.android.eventers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    private TextView mTotalTextView, mSelectedTextview;
    FloatingActionButton mFloatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mSelectedTextview = (TextView)findViewById(R.id.selected_in_report);
        mTotalTextView = (TextView)findViewById(R.id.total_in_report);

        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("TOTAL_KEY");
        String value2 = extras.getString("SELECTED_KEY");
        mTotalTextView.setText("Total: "+value1);
        mSelectedTextview.setText("Selected: "+value2);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_in_report);



        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
