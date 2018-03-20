package com.brianboll.jobtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import lib.UserFunctions;

public class CreateJobActivity extends AppCompatActivity {
    private String KEY_SUCCESS = "success";

    TextView jobName, contactName, contactPhone, addressText, dateText, scopeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createjob);

        jobName = (TextView) findViewById(R.id.textJobName);
        contactName = (TextView) findViewById(R.id.textContactName);
        contactPhone = (TextView) findViewById(R.id.textPhone);
        addressText = (TextView) findViewById(R.id.textAddress);
        dateText = (TextView) findViewById(R.id.textDate);
        scopeText = (TextView) findViewById(R.id.textScope);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.createjob, menu);
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

            case R.id.action_submitjob:
                UserFunctions userFunctions = new UserFunctions();

                String job_name = jobName.getText().toString();
                String contact_name = contactName.getText().toString();
                String contact_phone = contactPhone.getText().toString();
                String address = addressText.getText().toString();
                String date = dateText.getText().toString();
                String scope = scopeText.getText().toString();

                JSONObject json = userFunctions.createJob(job_name, contact_name, contact_phone, address, scope, date);

                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        Intent dashboard = new Intent(getApplicationContext(), InitialActivity.class);
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);
                    }
                } catch (JSONException e) {
                    //Throwable.printStackTrace for testing only
                    e.printStackTrace();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
