package com.example.finalproject1.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject1.R;
import com.example.finalproject1.Task;
import com.example.finalproject1.database.SQLiteDatabaseHandler;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter{
    private Activity context;
    public ArrayList<Task> task;
    public SQLiteDatabaseHandler db;
    BaseAdapter ba;

    public TaskListAdapter(Activity context, ArrayList<Task> task, SQLiteDatabaseHandler db) {
        this.context = context;
        this.task = task;
        this.db = db;
    }

    public static class ViewHolder {
        TextView textViewId;
        Button deleteButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.list_item, null, true);

            vh.textViewId = (TextView) row.findViewById(R.id.text);
            vh.deleteButton = (Button) row.findViewById(R.id.delete);

            //store the holder with the view
            row.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewId.setText("" + task.get(position).getTask());
        final int positionPopup = position;


        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Last Index", "" + positionPopup);
                //integer index
                db.deleteTask(task.get(positionPopup));

                task = (ArrayList) db.getAllTask();
                Log.d("Country Size", "" + task.size());
                notifyDataSetChanged();
            }
        });
        return row;
    }
    @Override
    public int getCount() {
        return task.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
