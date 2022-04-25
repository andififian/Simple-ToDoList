package com.example.finalproject1;

public class Task {
    int id;
    String task;

    public Task(){
        super();
    }
    public Task(String task){
        super();
        this.task=task;
    }

    //contructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
