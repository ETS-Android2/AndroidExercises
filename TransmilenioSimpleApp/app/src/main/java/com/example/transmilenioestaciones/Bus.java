package com.example.transmilenioestaciones;

import com.example.transmilenioestaciones.ui.main.StationFragment;

import java.util.ArrayList;

public class Bus implements Comparable<Bus> {
    private String name;
    private String route;
    private String destiny;
    //private Station[] stations;
    private ArrayList<String> stations;

    public Bus(String name, String destiny, String route){
        this.name = name;
        this.destiny = destiny;
        this.route = route;
    }

    public Bus(String name, String destiny, String route, ArrayList<String> stations){
        this.name = name;
        this.destiny = destiny;
        this.route = route;
        this.stations = stations;
    }

    public String getName(){
        return name;
    }

    public String getRoute(){
        return route;
    }

    public String getDestiny(){
        return destiny;
    }

    /*public Station[] getStations(){
        return stations;
    }*/

    public ArrayList<String> getStations(){
        return stations;
    }

    /*public void setStations(Station[] stations){
        this.stations = stations;
    }*/
    public void setStations(ArrayList<String> stations){
        this.stations = stations;
    }

    @Override
    public int compareTo(Bus bus){
        return this.getName().compareTo(bus.getName());
    }

}
