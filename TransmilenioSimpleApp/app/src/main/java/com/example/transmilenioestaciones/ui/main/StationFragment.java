package com.example.transmilenioestaciones.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmilenioestaciones.Bus;
import com.example.transmilenioestaciones.R;
import com.example.transmilenioestaciones.Station;
import com.example.transmilenioestaciones.ui.listObjects.ClickRecyclerViewInterfaceBus;
import com.example.transmilenioestaciones.ui.listObjects.ClickRecyclerViewInterfaceStation;
import com.example.transmilenioestaciones.ui.listObjects.RecyclerViewAdapterBus;
import com.example.transmilenioestaciones.ui.listObjects.RecyclerViewAdapterStation;

import java.util.ArrayList;

public class StationFragment extends Fragment {
    ArrayList<String> busStations;
    DataBusStation dataBusStation;
    String title;

    public StationFragment( ArrayList<String> busStations, String title, DataBusStation dataBusStation){
        this.busStations = busStations;
        this.dataBusStation = dataBusStation;
        this.title = title;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_main, container, false);
        //View root = inflater.inflate(R.layout.fragment_names, container, false);
        View root = inflater.inflate(R.layout.fragment_names_float, container, false);
        //DataBusStation dataBusStation = new DataBusStation(this.getContext());

        RecyclerViewAdapterStation recyclerViewAdapterStation = new RecyclerViewAdapterStation(new ClickRecyclerViewInterfaceStation() {
            @Override
            public void onClick(View v, Station station) {
                //Toast.makeText(root2.getContext(), "You clicked me!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(root.getContext(), "You clicked me!", Toast.LENGTH_SHORT).show();
                BusFragment nextFragment = new BusFragment(station.getBuses(), station.getName(), dataBusStation);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //fragmentTransaction.add(nextFragment,"Test");
                fragmentTransaction.add(android.R.id.content,nextFragment); //Over all the container
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.replace(root.getId(),nextFragment);
                fragmentTransaction.commit();
            }
        });
        //RecyclerView station2 = root2.findViewById(R.id.recyclerViewNames2);
        TextView title = root.findViewById(R.id.textViewTitle);
        String full_title = root.getResources().getString(R.string.frag_bus) + " - " + this.title;
        title.setText(full_title);
        RecyclerView station2 = root.findViewById(R.id.recyclerViewNames);
        //LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(root2.getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(root.getContext());
        station2.setLayoutManager(linearLayoutManager2);
        //station2.addItemDecoration(new DividerItemDecoration(root2.getContext(), LinearLayoutManager.VERTICAL));
        station2.addItemDecoration(new DividerItemDecoration(root.getContext(), LinearLayoutManager.VERTICAL));
        station2.setAdapter(recyclerViewAdapterStation);
        String stations = "";
        //ArrayList<String> busStations = bus.getStations();
        for(int i = 0; i < busStations.size(); i++){
            //recyclerViewAdapterStation.addStation(new Station(busStations.get(i)));
            recyclerViewAdapterStation.addStation(dataBusStation.findStation(busStations.get(i)));
        }

        return root;
    }

}
