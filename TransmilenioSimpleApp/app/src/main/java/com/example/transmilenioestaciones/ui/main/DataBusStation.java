package com.example.transmilenioestaciones.ui.main;

import android.content.Context;
import android.util.Log;

import com.example.transmilenioestaciones.Bus;
import com.example.transmilenioestaciones.Station;
import com.example.transmilenioestaciones.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class DataBusStation {

    private ArrayList<String[]> data;
    private ArrayList<String[]> dataBackBone;
    private ArrayList<Bus> busesBBData;
    private ArrayList<Station> stationsBBData;

    DataBusStation(Context context){
        data = new ArrayList<>();
        readDataCSV(context);
        dataBackBone = setDataArray("[1] TRONCAL");
        busesBBData = getBusArray("[1] TRONCAL");
        stationsBBData = setStationsBBData("[1] TRONCAL");
    }

    private void readDataCSV(Context context){
        InputStream inputStream = context.getResources().openRawResource(R.raw.paradasrutastroncalesalimentadorastransmilenio);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null){
                String[] tokens = line.split(";");
                data.add(new String[]{tokens[2],tokens[3],tokens[4],tokens[5],tokens[6],tokens[7],tokens[8],tokens[9]});

            }
        }catch (IOException e) {
            Log.e("DataBusStation","Error " + line, e);
            e.printStackTrace();
        }
    }

    public ArrayList<Bus> buses(){
        ArrayList<Bus> buses = new ArrayList<Bus>();

        return buses;
    }

    private ArrayList<String[]> setDataArray(String type){
        ArrayList<String[]> dataArray = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            if(data.get(i)[6].equals(type)){
                dataArray.add(data.get(i));
            }
        }
        return dataArray;
    }

    public ArrayList<String[]> getData(){
        return data;
    }

    // Simple test
    public ArrayList<Bus> getBusArray(String type){
        ArrayList<Bus> busArrayList = new ArrayList<>();
        for(int i = 0; i < dataBackBone.size(); i++){

                if(dataBackBone.get(i)[4].equals("0")) { // First station
                    Bus bus = new Bus(dataBackBone.get(i)[1], dataBackBone.get(i)[2], dataBackBone.get(i)[0]);
                    bus.setStations(getBusStations(bus));
                    busArrayList.add(bus);
                }
        }

        Collections.sort(busArrayList);

        return busArrayList;
    }

    private ArrayList<Station> setStationsBBData(String type){
        ArrayList<Station> stationArrayList = new ArrayList<>();
        boolean found;
        for(int i = 0; i < dataBackBone.size(); i++){
            found = false;
            for(int j = 0; j < stationArrayList.size(); j++){
                if(stationArrayList.get(j).getName().equals(dataBackBone.get(i)[7])){
                    found = true;
                    stationArrayList.get(j).addBusString(dataBackBone.get(i)[1]); // Add bus to the station
                    break;
                }
            }
            if(!found){
                stationArrayList.add(new Station(dataBackBone.get(i)[7],dataBackBone.get(i)[1])); // Add name and first bus
            }
            //}
        }

        Collections.sort(stationArrayList);

        return stationArrayList;
    }

    public Station[] getStations(String type){
        Station[] stations = new Station[stationsBBData.size()];
        for(int i = 0; i < stationsBBData.size(); i++){
            stations[i] = stationsBBData.get(i);
        }
        return stations;
    }

    public ArrayList<String> getBusStations(Bus bus){
        ArrayList<String[]> arrayList = new ArrayList<>();
        // ugly way to sort the values
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for(int i = 0; i < dataBackBone.size(); i++){
            if(bus.getRoute().equals(dataBackBone.get(i)[0])){
                arrayList.add(new String[]{dataBackBone.get(i)[4],dataBackBone.get(i)[7]});
                integerArrayList.add(Integer.parseInt(dataBackBone.get(i)[4]));
            }
        }
        Collections.sort(integerArrayList); // Sort based on the position
        ArrayList<String> stringArrayList = new ArrayList<>();
        for(int i = 0; i < integerArrayList.size(); i++){
            for(int j = 0; j < arrayList.size(); j++){
                if(Integer.toString(integerArrayList.get(i)).equals(arrayList.get(j)[0])){ // Element has been found
                    stringArrayList.add(arrayList.get(j)[1]); // Add the string station to the array
                }
            }
        }
        return stringArrayList;
    }

    public Station findStation(String name){
        for(int i = 0; i < stationsBBData.size(); i++){
            if(stationsBBData.get(i).getName().equals(name)){
                return stationsBBData.get(i);
            }
        }
        return null;
    }

    public Bus findBus(String name){
        for(int i = 0; i < busesBBData.size(); i++){
            if(busesBBData.get(i).getName().equals(name)){
                return busesBBData.get(i);
            }
        }
        return null;
    }

}
