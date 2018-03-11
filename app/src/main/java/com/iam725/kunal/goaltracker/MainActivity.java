package com.iam725.kunal.goaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

        private final static String TAG = "MainActivity";
        String[] spinnerItems = {"Started", "In progress", "Completed", "Not done"};
        private ListView listView;
        private FirebaseListAdapter<Goal> firebaseListAdapter;
        private final int result = 1;
        final String DESCRIPTION = "description";
        final String START_DATE = "startDate";
        final String END_DATE = "endDate";
        final String KEY = "key";
        final String STATUS = "status";

        DatabaseReference mRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                Log.d(TAG, "onCreate fired...");
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                mRef = FirebaseDatabase.getInstance().getReference();

        /*ListView*/
                listView = (ListView) findViewById(R.id.listView);

//        final TextView tvgoal = (TextView) findViewById(R.id.goalDetail);
//        final TextView tvStartDate = (TextView) findViewById(R.id.startDate);
//        final TextView tvEndDate = (TextView) findViewById(R.id.endDate);
//        final TextView tvstatus = (TextView) findViewById(R.id.status);
//        Log.e(TAG, "tvgoal = "+tvgoal);
//        Log.e(TAG, "tvStartDate = "+tvStartDate);
//        Log.e(TAG, "tvEndDate = "+ tvEndDate);
//        Log.e(TAG, "tvStatus = "+tvstatus);
        /*FirebaseListAdapter*/
                firebaseListAdapter = new FirebaseListAdapter<Goal>(
                        this,
                        Goal.class,
                        R.layout.goal_view,
                        mRef
                ) {
                        @Override
                        protected void populateView(View v, final Goal model, int position) {

                    /*Declaring all the views in goal_view.xml file */
                                final TextView tvgoal = (TextView) v.findViewById(R.id.goalDetail);
                                final TextView tvStartDate = (TextView) v.findViewById(R.id.startDate);
                                final TextView tvEndDate = (TextView) v.findViewById(R.id.endDate);
//                    final TextView tvstatus = (TextView) v.findViewById(R.id.statusText);
                                final TextView tvgoalNumber = (TextView) v.findViewById(R.id.goalNumber);
                                final ImageButton deleteButton = (ImageButton) v.findViewById(R.id.delete_button);
                                final ImageButton editButton = (ImageButton) v.findViewById(R.id.edit_button);
//                    final ArrayList<String> goalList = new ArrayList<>();
//                    goalList.add()

//                    final TextInputEditText etStatus = (TextInputEditText) v.findViewById(R.id.status);
//                    final Button changeButton = (Button) v.findViewById(R.id.change_button);

                                Log.d(TAG, "populateView fired...");
//                    Log.d(TAG, "goalDescriptionModel = "+model.getDescription());
//                    Log.d(TAG, "startDateModel = "+ model.getStartDate());
//                    Log.d(TAG, "endDateModel = "+ model.getEndDate());

                                String goalNumber = "Goal " + (position + 1);
                                final String deleteKey = model.getKey();        //key to be deleted
                                Log.d(TAG, model.toString());
//                    Log.d(TAG, "deleteKey = "+deleteKey);
                                tvgoalNumber.setText(goalNumber);
                                tvgoal.setText(model.getDescription());
                                tvStartDate.setText(model.getStartDate());
                                tvEndDate.setText(model.getEndDate());
//                    etStatus.setText(model.getStatus());
                                final Goal goal = new Goal(deleteKey,
                                        model.getDescription(),
                                        model.getStartDate(),
                                        model.getEndDate(),
                                        model.getStatus());
                    /*delete this view from listview on clicking deleteButton*/
                                deleteButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                if (mRef.child(deleteKey) != null)
                                                        mRef.child(deleteKey).removeValue();
                                                        final Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Goal deleted", Snackbar.LENGTH_LONG);
                                                        snackbar.setAction("UNDO", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                                mRef.child(deleteKey).setValue(goal);
                                                        }
                                                });
                                                snackbar.show();
                                        }
                                });
                                final String[] strArray = {deleteKey, model.getDescription(), model.getStartDate(), model.getEndDate()
                                        , model.getStatus()};
                                editButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                Intent i = new Intent(MainActivity.this, AddGoalActivity.class);
                                                i.putExtra("strArray", strArray);
                                                startActivityForResult(i, result);
                                        }
                                });
                                 /*Setting the spin Adapter*/
//                                String list[] = {"Started", "In progress", "Completed", "Not done"};
                                ArrayList<String> aList = new ArrayList<>();
                                aList.add("Started");
                                aList.add("In progress");
                                aList.add("Completed");
                                aList.add("Not done");

                                final Spinner spin = (Spinner) v.findViewById(R.id.spinner);
                                ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, aList);
                                spin.setAdapter(spinAdapter);
                                spin.setSelection(aList.indexOf(model.getStatus()));
                                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.primeColorOld));
                                                String s = parent.getItemAtPosition(position).toString();
                                                Log.d(TAG, "model.getStatus = "+ model.getStatus());
                                                Log.d(TAG, "s = "+ s);
                                                if(!model.getStatus().trim().equals(s.trim())) {
                                                        Log.d(TAG, "Inside");
                                                        model.setStatus(s);
                                                        mRef.child(deleteKey).setValue(model);
                                                }

//                                                Goal newGoal = new Goal(deleteKey,
//                                                        model.getDescription(),
//                                                        model.getStartDate(),
//                                                        model.getEndDate(),
//                                                        s);
//                                                mRef.child(deleteKey).setValue(newGoal);
//                                                mRef.child(deleteKey).child(STATUS).setValue(s);
//                                         String name = parent.getItemAtPosition(position).toString();
//                                        Log.d(TAG, "name = "+name);
//                                        etStatus.setText(name);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                });
                        }
                };
                listView.setAdapter(firebaseListAdapter);
        /*Add goal button*/
                FloatingActionButton addGoal = (FloatingActionButton) findViewById(R.id.addGoal);
                addGoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                Intent i = new Intent(MainActivity.this, AddGoalActivity.class);
                                startActivityForResult(i, result);

                        }
                });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                Log.d(TAG, "onActivityResult fired");

                if (requestCode == this.result) {
                        if (resultCode == RESULT_OK) {
                                String goalDescription = data.getStringExtra(DESCRIPTION);
                                String startDate = data.getStringExtra(START_DATE);
                                String endDate = data.getStringExtra(END_DATE);
                                String key = data.getStringExtra(KEY);
                                String status = data.getStringExtra(STATUS);
                                Log.d(TAG, "key1 = " + key);
                                Log.d(TAG, "status  = "+status);
                                if (key == null)                key = mRef.push().getKey();
                                if(status == null)            status = "started";
                                Log.d(TAG, "goalDescription = " + goalDescription);
                                Log.d(TAG, "startDate = " + startDate);
                                Log.d(TAG, "endDate = " + endDate);
                                Log.d(TAG, "key = " + key);

                                Goal newGoal = new Goal(key, goalDescription, startDate, endDate, status);
                                Map<String, String> map = newGoal.toMap();
                                mRef.child(key).setValue(map);
                        }
                }
        }

        @Override
        protected void onStart() {
                Log.d(TAG, "onStart fired...");
                super.onStart();

                mRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });
        }
}
