package com.example.transmilenioestaciones.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    // ugly way to load the data
    // {[name,origin,destiny]}
    String busName [] = {"A51","A52","A74"};
    // {[name,place,backbone]}
    String busStation [][] = {{"Calle 106","Calle 106","Autonorte"}};
    //--------------------

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int myIndex;

    //private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        myIndex = index;
        //pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_names, container, false);
        int index = 1;

        DataBusStation dataBusStation = new DataBusStation(this.getContext());

        switch (myIndex) {
            case 1:
                final RecyclerViewAdapterBus recyclerViewAdapterBus = new RecyclerViewAdapterBus(new ClickRecyclerViewInterfaceBus() {
                    @Override
                    public void onClick(View v, Bus bus) {
                        StationFragment nextFragment = new StationFragment(bus.getStations(),bus.getName(), dataBusStation);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                        fragmentTransaction.add(android.R.id.content,nextFragment); //Over all the container
                        fragmentTransaction.addToBackStack(null);

                        fragmentTransaction.commit();
                    }
                });
                RecyclerView bus = root.findViewById(R.id.recyclerViewNames);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
                bus.setLayoutManager(linearLayoutManager);
                bus.addItemDecoration(new DividerItemDecoration(root.getContext(), LinearLayoutManager.VERTICAL));
                bus.setAdapter(recyclerViewAdapterBus);

                ArrayList<Bus> busArrayList = dataBusStation.getBusArray("[1] TRONCAL");
                Log.d("PlaceholderFrag","Size busArrayList " + Integer.toString(busArrayList.size()));
                for(int i = 0; i < busArrayList.size(); i++){
                    recyclerViewAdapterBus.addBus(busArrayList.get(i));
                }
                recyclerViewAdapterBus.setBusListCopy(); // Create a copy to do the filter on the search item

                SearchView searchViewBus = root.findViewById(R.id.search_element);
                searchViewBus.setQueryHint(root.getResources().getString(R.string.hint_search_bus));
                searchViewBus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        recyclerViewAdapterBus.filter(query);
                        return true;
                        //return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        recyclerViewAdapterBus.filter(newText);
                        return true;
                        //return false;
                    }
                });

                break;
            case 2:
                final RecyclerViewAdapterStation recyclerViewAdapterStation = new RecyclerViewAdapterStation(new ClickRecyclerViewInterfaceStation() {
                    @Override
                    public void onClick(View v, Station station) {

                        BusFragment nextFragment = new BusFragment(station.getBuses(), station.getName(), dataBusStation);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                        fragmentTransaction.add(android.R.id.content,nextFragment); //Over all the container
                        fragmentTransaction.addToBackStack(null);

                        fragmentTransaction.commit();
                    }
                });
                RecyclerView station = root.findViewById(R.id.recyclerViewNames);
                LinearLayoutManager linearLayoutManagerStation = new LinearLayoutManager(root.getContext());
                station.setLayoutManager(linearLayoutManagerStation);
                station.addItemDecoration(new DividerItemDecoration(root.getContext(),LinearLayoutManager.VERTICAL));
                station.setAdapter(recyclerViewAdapterStation);

                Station[] stations = dataBusStation.getStations("[1] TRONCAL");
                for(int i = 0; i <stations.length; i++){
                    recyclerViewAdapterStation.addStation(stations[i]);
                }

                recyclerViewAdapterStation.setStationListCopy();

                SearchView searchViewStation = root.findViewById(R.id.search_element);
                searchViewStation.setQueryHint(root.getResources().getString(R.string.hint_search_station));
                searchViewStation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        recyclerViewAdapterStation.filter(query);
                        return true;
                        //return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        recyclerViewAdapterStation.filter(newText);
                        return true;
                        //return false;
                    }
                });

                break;
        }

        return root;
    }
}