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
        return JSONObect jObj;
    }
    
    public JSONObject registerUser(String name, String email, String password) {
        //Stub
        return JSONObect jObj;
    }

    public String getUserName(Context context) {
        //Stub
        return String str;
    }

    public String getAdminFlag(Context context) {
        //Stub
        return String str;
    }

    public JSONObject createJob()
    {
        //Stub
        return JSONObect jObj;
    }

    public String getUserID(Context context)
    {
        //Stub
    }

    public JSONObject getAssignedJobs(String employeeID)
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject getUnassignedJobs()
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject createJob(String jobName, String contactName, String contactPhone, String address, String scope, String date)
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject deleteJob(String jobID)
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject assignJob(String jobID, String employeeID)
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject getUnassignedEmployees(String jobID)
    {
        //Stub
        return JSONObect jObj;
    }

    public JSONObject toggleClocked(String jobID, String employeeID, String toggleBit)
    {
        //Stub
        return JSONObect jObj;
    }

    public boolean isUserLoggedIn(Context context){
        //Stub
        return true;
    }

    public boolean logoutUser(Context context){
        //Stub
        return true;
    }

}

