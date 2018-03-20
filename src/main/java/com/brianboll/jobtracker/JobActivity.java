package com.brianboll.jobtracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lib.UserFunctions;

public class JobActivity extends Activity {
    private static String KEY_SUCCESS = "success";

    Button clockIn;
    Button clockOut;
    Button btnAssignJob;
    Button btnDeleteJob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        clockIn = (Button) findViewById(R.id.clockIn);
        clockOut = (Button) findViewById(R.id.clockOut);
        btnAssignJob = (Button) findViewById(R.id.btnAssignJob);
        btnDeleteJob = (Button) findViewById(R.id.btnDeleteJob);

        String adminFlag = "";
        UserFunctions userFunction = new UserFunctions();
        adminFlag = userFunction.getAdminFlag(getApplicationContext());

        //Determine height for listView display calculations
        int statusbar_height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusbar_height = getResources().getDimensionPixelSize(resourceId);
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_height = size.y + statusbar_height;

        Bundle extras = getIntent().getExtras();

        final String jobID = extras.getString("jobID");

        if(extras.getString("assigned").equals("true")) {
            if (extras.getString("timeIn").equals("")) {
                //Clocked in already. Hide clockIn button, make clockOut button visible
                clockIn.setVisibility(View.VISIBLE);
                clockOut.setVisibility(View.INVISIBLE);
            } else {
                //Not clocked in. Hide clockOut button, make clockIn button visible
                clockOut.setVisibility(View.VISIBLE);
                clockIn.setVisibility(View.INVISIBLE);
            }
        }

        if(adminFlag.equals("1"))
        {
            btnAssignJob.setEnabled(true);
            btnDeleteJob.setEnabled(true);

            btnAssignJob.setVisibility(View.VISIBLE);
            btnDeleteJob.setVisibility(View.VISIBLE);
        }

        List<JobDetail> jobdetails = new ArrayList<JobDetail>();

        if(extras == null) {
            //Set strings manually to null or some default value
        } else {
            jobdetails.add(new JobDetail("Job Name", extras.getString("jobName")));
            jobdetails.add(new JobDetail("Contact Name", extras.getString("contactName")));
            jobdetails.add(new JobDetail("Contact Phone", extras.getString("contactPhone")));
            jobdetails.add(new JobDetail("Address", extras.getString("jobAddress")));
            jobdetails.add(new JobDetail("Scope", extras.getString("jobScope")));
            jobdetails.add(new JobDetail("Date", extras.getString("jobDate")));
        }

        JobDetailsListAdapter adapter2 = new JobDetailsListAdapter(this, jobdetails, screen_height);
        ListView listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(adapter2);
        listView2.setScrollbarFadingEnabled(false);

        clockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockIn.setVisibility(View.INVISIBLE);
                clockOut.setVisibility(View.VISIBLE);

                UserFunctions userFunction = new UserFunctions();

                //Get current user's id
                final String employeeID = userFunction.getUserID(getApplicationContext());

                //Grab list of employees and their ids
                JSONObject json = userFunction.toggleClocked(jobID, employeeID, "1");

                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        //Create list of work entries from JSON
                        JSONObject json_jobs = json.getJSONObject("work");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        clockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When pressed, need to make the button invisible
                // Make clockIn button visible

                UserFunctions userFunction = new UserFunctions();

                //Get current user's id
                final String employeeID = userFunction.getUserID(getApplicationContext());

                //Grab list of employees and their ids
                JSONObject json = userFunction.toggleClocked(jobID, employeeID, "0");

                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        //Create list of employees from JSON
                        JSONObject json_jobs = json.getJSONObject("work");

                        Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("jobID", jobID);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //Spinner to assign employees
        btnAssignJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JobActivity.this);

                builder.setTitle("Add Employee To Job");

                UserFunctions userFunctions = new UserFunctions();
                JSONObject json = userFunctions.getUnassignedEmployees(jobID);

                final List<Employee> employeeList = new ArrayList<Employee>();
                final List<String> nameList = new ArrayList<String>();

                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        JSONObject json_employees = json.getJSONObject("employees");

                        int totalEmployeeCount = Integer.parseInt(json.getString("count"));

                        for (int i = 1; i <= totalEmployeeCount; ++i) {
                            try {
                                JSONObject json_tmp_employee = json_employees.getJSONObject("employee" + i);
                                if (json_tmp_employee.getString("ID") != "null") {
                                    Employee tmp_employee = new Employee();
                                    tmp_employee.ID = json_tmp_employee.getString("ID");
                                    tmp_employee.Name = json_tmp_employee.getString("Name");

                                    employeeList.add(tmp_employee);
                                    nameList.add(json_tmp_employee.getString("Name"));
                                }
                            } catch (JSONException e) {

                            }
                        }
                    }
                } catch (JSONException e) {
                    //Throwable.printStackTrace for testing only
                    e.printStackTrace();
                }

                final CharSequence[] types = nameList.toArray(new CharSequence[nameList.size()]);
                builder.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UserFunctions userFunctions = new UserFunctions();
                        userFunctions.assignJob(jobID, employeeList.get(which).ID);
                    }

                });

                builder.show();
            }
        });

        btnDeleteJob.setOnClickListener(new View.OnClickListener() {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            UserFunctions userFunctions = new UserFunctions();
                            JSONObject json = userFunctions.deleteJob(jobID);

                            finish();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JobActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }
}

