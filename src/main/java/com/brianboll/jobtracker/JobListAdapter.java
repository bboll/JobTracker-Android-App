package com.brianboll.jobtracker;

import android.app.Activity;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JobListAdapter extends ArrayAdapter<Job> {

    private final Activity context;
    private List<Job> jobIDs = new ArrayList<Job>();
    int avail_screen_height;

    public JobListAdapter(Activity context, List<Job> jobIDsNew, int screen_height) {
        super(context, R.layout.activity_joblist, jobIDsNew);

        avail_screen_height = screen_height;

        this.context=context;
        this.jobIDs = jobIDsNew;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.joblist, parent, false);
            viewHolder = new ViewHolder(position);
            viewHolder.name = (TextView) convertView.findViewById(R.id.Itemname);
            viewHolder.ID = (TextView) convertView.findViewById(R.id.ItemID);
            viewHolder.description = (TextView) convertView.findViewById(R.id.Description);
            viewHolder.description.setTextColor(Color.BLACK);
            viewHolder.date = (TextView) convertView.findViewById(R.id.iconText);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height = avail_screen_height / 5;
        convertView.setLayoutParams(params);

        //TODO: Use strings.xml and placeholders for localization
        viewHolder.ID.setText("ID: " + String.valueOf(jobIDs.get(position).ID));
        viewHolder.name.setText(jobIDs.get(position).jobName);
        viewHolder.description.setText(jobIDs.get(position).scope);
        viewHolder.date.setText(jobIDs.get(position).formatDate(jobIDs.get(position).date));

        return convertView;

    };

    private static class ViewHolder {

        TextView name, ID, description,date;

        private int position;

        public ViewHolder() {}

        public ViewHolder(int i) {

            position = i;
        }
    }
}

