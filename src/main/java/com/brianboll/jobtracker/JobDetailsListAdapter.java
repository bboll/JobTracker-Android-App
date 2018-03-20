package com.brianboll.jobtracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsListAdapter extends ArrayAdapter<JobDetail> {

    private final Activity context;
    private List<JobDetail> current_job = new ArrayList<JobDetail>();
    int avail_screen_height;

    //Constructor
    public JobDetailsListAdapter(Activity context, List<JobDetail> job, int screen_height) {
        super(context, R.layout.activity_job, job);

        avail_screen_height = screen_height;

        this.context=context;
        this.current_job = job;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder viewHolder;

        //Recycle view if it exists
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.jobdetail, parent, false);
            viewHolder = new ViewHolder(position);
            viewHolder.label = (TextView) convertView.findViewById(R.id.detailLabel);
            viewHolder.detail = (TextView) convertView.findViewById(R.id.detail);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        params.height = avail_screen_height / 5;
        convertView.setLayoutParams(params);

        viewHolder.label.setText(current_job.get(position).label);

        String currentLabel = current_job.get(position).label;

        //Date label needs to be formatted, scope simply needs re-sized
        switch (currentLabel) {
            case "Date":
                viewHolder.detail.setText(current_job.get(position).formatDate(current_job.get(position).detail));
                break;

            case "Scope":
                viewHolder.detail.setTextSize(15);
                break;

            default:
                viewHolder.detail.setText(current_job.get(position).detail);
                break;
        }

        return convertView;

    };

    private static class ViewHolder {

        TextView label, detail;

        private int position;

        ViewHolder() {}

        ViewHolder(int i) {

            position = i;
        }
    }

}
