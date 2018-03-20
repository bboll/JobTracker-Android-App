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
        JSONObject jObj;
        
        return jObj;
    }
    
    public JSONObject registerUser(String name, String email, String password) {
        //Stub
        JSONObject jObj;
        
        return jObj;
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
        JSONObject jObj;
        
        return jObj;
    }

    public String getUserID(Context context)
    {
        //Stub
        return String str;
    }

    public JSONObject getAssignedJobs(String employeeID)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject getUnassignedJobs()
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject createJob(String jobName, String contactName, String contactPhone, String address, String scope, String date)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject deleteJob(String jobID)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject assignJob(String jobID, String employeeID)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject getUnassignedEmployees(String jobID)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
    }

    public JSONObject toggleClocked(String jobID, String employeeID, String toggleBit)
    {
        //Stub
        JSONObject jObj;
        
        return jObj;
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

