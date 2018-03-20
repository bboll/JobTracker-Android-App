package com.brianboll.jobtracker;

import android.os.StrictMode;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.TextView;

import lib.UserFunctions;

public class InitialActivity extends AppCompatActivity {
    UserFunctions userFunctions;
    TextView loggedInUser;
    Button btnUnassignedJobs, btnViewJobs, btnCreateJob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        //Helps when trying to handle network exception fatal error after Android 3.0
        //TODO: Consider AsyncTask or Thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Definitions of fonts have to be in a method
        TextView logoText = (TextView) findViewById(R.id.logoText);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue Regular.ttf");
        logoText.setTypeface(tf);

        /**
         * Dashboard Screen for the application
         * */
        // Check login status in SQLite database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){

            //If logged in and not an admin simply send to job list activity
            final String admin_flag = userFunctions.getAdminFlag(getApplicationContext());

            if(admin_flag.equals("0")) {
                Intent intent = new Intent(getApplicationContext(), JobList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                // Closing dashboard screen
                finish();
            }

            btnUnassignedJobs = (Button) findViewById(R.id.btnUnassignedJobs);
            btnViewJobs = (Button) findViewById(R.id.btnViewJobs);
            btnCreateJob = (Button) findViewById(R.id.btnCreateJob);

            // User already logged in show dashboard
            loggedInUser = (TextView) findViewById(R.id.loggedInAs);
            final String username = userFunctions.getUserName(getApplicationContext());
            loggedInUser.setText("Logged in as " + username);

            btnUnassignedJobs.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AdminJobList.class);
                    startActivity(intent);
                }
            });

            btnViewJobs.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), JobList.class);
                    startActivity(intent);
                }
            });

            btnCreateJob.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CreateJobActivity.class);
                    startActivity(intent);
                }
            });

        }else{
            // User is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);

            // Closing dashboard screen
            finish();
        }
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
