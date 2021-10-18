package com.example.transmilenioestaciones;

import java.util.ArrayList;

public class Station implements Comparable<Station>{
    private String name;
    //private Bus buses[];
    private ArrayList<String> buses;

    public Station(String name){
        this.name = name;
    }

    public Station(String name, String bus){
        this.name = name;
        buses = new ArrayList<String>();
        buses.add(bus);
    }
    //public Station(String name, Bus buses[]){
    public Station(String name, ArrayList<String> buses){
        this.name = name;
        this.buses = buses;
    }

    public String getName(){
        return name;
    }

    public void addBusString(String bus){
        buses.add(bus);
    }
    //public Bus[] getBuses(){
    public ArrayList<String> getBuses(){
        return buses;
    }

    //public void setBuses(Bus buses[]){
    public void setBuses(ArrayList<String> buses){
        this.buses = buses;
    }

    @Override
    public int compareTo(Station station){
        return this.getName().compareTo(station.getName());
    }
}
