package com.example.myapp;

import java.io.Serializable;

public class Workout implements Serializable {
    private String id;
    private String name;
    private int resttime;
    private int repetitions;
    private int sets;
    private String TimeType;
    private int ViewType;


    public Workout(String name, int repetitions,int sets,int resttime,int ViewType){
        this.name = name;
        this.repetitions=repetitions;
        this.sets = sets;
        this.resttime = resttime;
        this.ViewType = ViewType;
    }
    public Workout(String name, int RestTime, String TimeType,int ViewType){
        this.name = name;
        this.resttime = RestTime;
        this.TimeType = TimeType;
        this.ViewType = ViewType;
    }
    public Workout(String id, String name, int repetitions,int sets,int resttime,int ViewType){
        this.id = id;
        this.name = name;
        this.repetitions=repetitions;
        this.sets = sets;
        this.resttime = resttime;
        this.ViewType = ViewType;
    }
    public Workout(String id,String name, int RestTime, String TimeType,int ViewType){
        this.id = id;
        this.name = name;
        this.resttime = RestTime;
        this.TimeType = TimeType;
        this.ViewType = ViewType;
    }
    public void setTimeType(String type) {
        this.TimeType = type;
    }
    public String getTimeType() {
        return this.TimeType;
    }
    public void setItemViewType(int type) {
        this.ViewType = type;
    }
    public int getItemViewType() {
        return this.ViewType;
    }
    public void setResttime(int rest) {
        this.resttime = rest;
    }
    public int getResttime() {
        return this.resttime;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }
    public int getSets() {
        return this.sets;
    }
    public void setRepetitions(int count) {
        this.repetitions = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRepetitions() {
        return repetitions;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}