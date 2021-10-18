package com.example.transmilenioestaciones.ui.main;

import android.os.Bundle;
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
import com.example.transmilenioestaciones.ui.listObjects.ClickRecyclerViewInterfaceBus;
import com.example.transmilenioestaciones.ui.listObjects.RecyclerViewAdapterBus;

import java.util.ArrayList;

public class BusFragment extends Fragment {
    ArrayList<String> buses;
    DataBusStation dataBusStation;
    String title;

    public BusFragment(ArrayList<String> buses, String title, DataBusStation dataBusStation){
        this.buses = buses;
        this.dataBusStation = dataBusStation;
        this.title = title;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_names_float, container, false);

        RecyclerViewAdapterBus recyclerViewAdapterBus = new RecyclerViewAdapterBus(new ClickRecyclerViewInterfaceBus() {
            @Override
            public void onClick(View v, Bus bus) {

                StationFragment nextFragment = new StationFragment(bus.getStations(),bus.getName(), dataBusStation);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.add(android.R.id.content,nextFragment); //Over all the container
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        TextView title = root.findViewById(R.id.textViewTitle);
        String full_title = root.getResources().getString(R.string.frag_station) + " - " + this.title;
        title.setText(full_title);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewNames);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapterBus);

        for(int i = 0; i < buses.size(); i++){
            recyclerViewAdapterBus.addBus(dataBusStation.findBus(buses.get(i)));
        }

        return root;
    }
}
