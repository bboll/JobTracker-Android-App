package com.brianboll.jobtracker;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import lib.UserFunctions;

import java.util.ArrayList;
import java.util.List;

public class JobList extends AppCompatActivity {


        private String KEY_SUCCESS = "success";
        private JobListAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_joblist);

            //No status bar, list will fill screen
            int statusbar_height = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusbar_height = getResources().getDimensionPixelSize(resourceId);
            }

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            int screen_height = size.y + statusbar_height;

            ListView listView = (ListView) findViewById(R.id.jobListView);
            UserFunctions userFunction = new UserFunctions();

            //Get current user's id from SQLite DB
            final String userid = userFunction.getUserID(getApplicationContext());

            final List<Job> jobList = new ArrayList<Job>();

            try {
                //Returns JSON array of objects
                JSONObject json = userFunction.getAssignedJobs(userid);

                if (json.getString(KEY_SUCCESS) != null) {

                    //Deserialize and compile jobs into an array
                    JSONObject json_jobs = json.getJSONObject("jobs");

                    int totaljobCount = Integer.parseInt(json.getString("count"));
                    for (int i = 1; i <= totaljobCount; ++i) {
                        try {
                            JSONObject json_tmp_job = json_jobs.getJSONObject("job" + i);
                            if (json_tmp_job.getString("jobID") != "null") {
                                Job tmp_job = new Job();
                                tmp_job.ID = Integer.parseInt(json_tmp_job.getString("jobID"));
                                tmp_job.jobName = json_tmp_job.getString("jobName");
                                tmp_job.contactName = json_tmp_job.getString("contactName");
                                tmp_job.contactPhone = json_tmp_job.getString("contactPhone");
                                tmp_job.address = json_tmp_job.getString("jobAddress");
                                tmp_job.date = json_tmp_job.getString("jobDate");
                                tmp_job.scope = json_tmp_job.getString("jobScope");
                                tmp_job.time_in = json_tmp_job.getString("timeIn");
                                tmp_job.time_out = json_tmp_job.getString("timeOut");

                                jobList.add(tmp_job);
                            }
                        } catch (JSONException e) {
                                //Don't want entire list to fail if one job entry can't be parsed for some reason
                                //jobList.add(new Job());

                                //Throwable.printStackTrace for testing only
                                e.printStackTrace();
                        }
                    }

                    adapter = new JobListAdapter(this, jobList, screen_height);
                    listView = (ListView) findViewById(R.id.jobListView);
                    listView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                //TODO: Implement fallback or user dialog for failed connection to server
                //Throwable.printStackTrace for testing only
                e.printStackTrace();
            }

            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String jobID = String.valueOf(jobList.get(position).ID);
                    String jobName = jobList.get(position).jobName;
                    String contactName = jobList.get(position).contactName;
                    String contactPhone = jobList.get(position).contactPhone;
                    String jobAddress = jobList.get(position).address;
                    String jobDate = jobList.get(position).date;
                    String jobScope = jobList.get(position).scope;
                    String timeIn = jobList.get(position).time_in;
                    String timeOut = jobList.get(position).time_out;
                    Intent intent = new Intent(getBaseContext(), JobActivity.class);

                    //TODO: Bundle extras and unpack in job activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("assigned", "true");
                    intent.putExtra("jobID", jobID);
                    intent.putExtra("jobName", jobName);
                    intent.putExtra("contactName", contactName);
                    intent.putExtra("contactPhone", contactPhone);
                    intent.putExtra("jobAddress", jobAddress);
                    intent.putExtra("jobDate", jobDate);
                    intent.putExtra("jobScope", jobScope);
                    intent.putExtra("timeIn", timeIn);
                    intent.putExtra("timeOut", timeOut);
                    startActivity(intent);
                }
            });

        }

        @Override
        protected void onResume() {
            super.onResume();
            adapter.notifyDataSetChanged();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.initial, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            UserFunctions userFunction = new UserFunctions();

            // Handle item selection
            switch (item.getItemId()) {
                case R.id.action_logout:
                    userFunction.logoutUser(getApplicationContext());

                    // User is not logged in show login screen
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);

                    // Closing dashboard screen
                    finish();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }
}
