package lib;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

public class UserFunctions {

    private JSONParser jsonParser;

    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    public JSONObject loginUser(String email, String password) {
        //Stub
    }
    
    public JSONObject registerUser(String name, String email, String password) {
        //Stub
    }

    public String getUserName(Context context) {
        //Stub
    }

    public String getAdminFlag(Context context) {
        //Stub
    }

    public JSONObject createJob()
    {
        //Stub
    }

    public String getUserID(Context context)
    {
        //Stub
    }

    public JSONObject getAssignedJobs(String employeeID)
    {
        //Stub
    }

    public JSONObject getUnassignedJobs()
    {
        //Stub
    }

    public JSONObject createJob(String jobName, String contactName, String contactPhone, String address, String scope, String date)
    {
        //Stub
    }

    public JSONObject deleteJob(String jobID)
    {
        //Stub
    }

    public JSONObject assignJob(String jobID, String employeeID)
    {
        //Stub
    }

    public JSONObject getUnassignedEmployees(String jobID)
    {
        //Stub
    }

    public JSONObject toggleClocked(String jobID, String employeeID, String toggleBit)
    {
        //Stub
    }

    public boolean isUserLoggedIn(Context context){
        //Stub
    }

    public boolean logoutUser(Context context){
        //Stub
    }

}

