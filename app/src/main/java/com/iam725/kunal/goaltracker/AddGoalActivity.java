package com.iam725.kunal.goaltracker;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGoalActivity extends AppCompatActivity {

        //        DatePickerDialog startDatePicker;
//        DatePickerDialog endDatePicker;
        private final static String TAG = "MainActivity";
        private EditText startDate;
        private EditText endDate;
        private TextInputEditText goalDescription;
        private String key;
        private String status;

        /*final Strings*/
        final String DESCRIPTION = "description";
        final String START_DATE = "startDate";
        final String END_DATE = "endDate";;
        final String KEY = "key";
        final String STATUS = "status";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.add_goal_view);

                startDate = (EditText) findViewById(R.id.startDate);
                endDate = (EditText) findViewById(R.id.endDate);
                goalDescription = (TextInputEditText) findViewById(R.id.goalDescription);

                Button doneButton = (Button) findViewById(R.id.button_done);
                //creating underline under button "done"
                doneButton.setPaintFlags(doneButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (validate()) {
                                        Intent i = getIntent();
                                        i.putExtra(DESCRIPTION, goalDescription.getText().toString());
                                        i.putExtra(START_DATE, startDate.getText().toString());
                                        i.putExtra(END_DATE, endDate.getText().toString());
                                        i.putExtra(STATUS, status);
                                        i.putExtra(KEY, key);
                                        AddGoalActivity.this.setResult(RESULT_OK, i);
                                        finish();
                                }
                        }
                });

        }

        private boolean validate() {
                if (TextUtils.isEmpty(startDate.getText().toString())) {
                        startDate.setError("Start Date is required");
                        return false;
                }
                if (TextUtils.isEmpty(endDate.getText().toString())) {
                        endDate.setError("End Date is required");
                        return false;
                }
                if (TextUtils.isEmpty(goalDescription.getText().toString())) {
                        goalDescription.setError("Goal Description is required");
                        return false;
                }
                return true;

        }

        @Override
        protected void onStart() {
                super.onStart();
                Bundle bundle = getIntent().getExtras();
                Log.d(TAG, "Bundle = " + bundle);

                if (bundle != null) {
                        String[] strArray = bundle.getStringArray("strArray");
                        if (strArray != null) {
                                Log.d(TAG, "strArray is not null");
                                Log.d(TAG, "strArray[1] = " + strArray[1]);
                                Log.d(TAG, "strArray[2] = " + strArray[2]);
                                Log.d(TAG, "strArray[3] = " + strArray[3]);
                                Log.d(TAG, "strArray[0] = " + strArray[0]);
                                Log.d(TAG, "strArray[4] = " + strArray[4]);
                                goalDescription.setText(strArray[1]);
                                startDate.setText(strArray[2]);
                                endDate.setText(strArray[3]);
                                key = strArray[0];
                                status = strArray[4];
                        }
                }


        }
}
