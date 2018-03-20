package com.brianboll.jobtracker;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
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

public class AdminJobList extends AppCompatActivity {
    private String KEY_SUCCESS = "success";
    private Menu activity_menu;
	private JobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminjoblist);
		
		int statusbar_height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusbar_height = getResources().getDimensionPixelSize(resourceId);
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screen_height = size.y;

        ListView listView = (ListView) findViewById(R.id.adminJobListView);

        UserFunctions userFunction = new UserFunctions();

        final List<Job> jobList = new ArrayList<Job>();

        try {
            //Grab list of employees and their ids
            JSONObject json = userFunction.getUnassignedJobs();

            if (json.getString(KEY_SUCCESS) != null) {
				
                //Deserialize JSON and compile a list of "job objects"
                JSONObject json_jobs = json.getJSONObject("jobs");

                int totaljobCount = Integer.parseInt(json.getString("count"));

                for (int i = 1; i <= totaljobCount; ++i) {
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

                            jobList.add(tmp_job);
						}
                }
            }
        } catch (JSONException e) {
			//TODO: Devise recover strategy
            //Throwable.printStackTrace for testing only
            e.printStackTrace();
        }

        adapter = new JobListAdapter(this, jobList, screen_height);
        listView = (ListView) findViewById(R.id.adminJobListView);
        listView.setAdapter(adapter);

		//If JSON parse fails, list will be empty
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jobID = String.valueOf(jobList.get(position).ID);
                String jobName = jobList.get(position).jobName;
                String contactName = jobList.get(position).contactName;
                String contactPhone = jobList.get(position).contactPhone;
                String jobAddress = jobList.get(position).address;
                String jobDate = jobList.get(position).date;
                String jobScope = jobList.get(position).scope;
                String timeIn = "";
                String timeOut = "";
                Intent intent = new Intent(getBaseContext(), JobActivity.class);

                //Clear all layers and open up job activity
                //Helps performance to close game list and allows a fresh list request on back button
				//TODO: Bundle these extras, pass bundle
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("assigned", "false");
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

        activity_menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UserFunctions userFunction = new UserFunctions();

        // Handle item selection
        switch (item.getItemId()) {
			
			//Logout action
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
