package com.example.finalproject1;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject1.Task;
import com.example.finalproject1.adapter.TaskListAdapter;
import com.example.finalproject1.database.SQLiteDatabaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Task> tasks;
    public SQLiteDatabaseHandler db;
    public Activity activity;
    public ListView listView;
    public TaskListAdapter customTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        db = new SQLiteDatabaseHandler(this);
        listView = findViewById(R.id.list);
        tasks = (ArrayList) db.getAllTask();

        for(Task task : tasks){
            String log = "Name: " + task.getTask();
            Log.d("Name: ", log);
        }

        TaskListAdapter customTaskList = new TaskListAdapter(this, tasks, db);
        listView.setAdapter(customTaskList);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addaction){
            addPopup();
        }
        return true;
    }

    public void addPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_todolist, null);
        dialogBuilder.setView(layout);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final EditText taskEdit = (EditText) layout.findViewById(R.id.editTextTask);
        Button save = (Button) layout.findViewById(R.id.save_popup);
        Button dismiss = (Button) layout.findViewById(R.id.dismiss);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskStr = taskEdit.getText().toString();
                Task task = new Task(taskStr);
                db.addTask(task);
                if(customTaskList == null) {
                    customTaskList = new TaskListAdapter(activity, tasks, db);
                    listView.setAdapter(customTaskList);
                }
                customTaskList.task = (ArrayList) db.getAllTask();
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

                for(Task task1 : tasks){
                    String log = "Name: " + task1.getTask();
                    Log.d("Name: ", log);
                }
                alertDialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}