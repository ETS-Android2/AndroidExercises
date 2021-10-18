package com.example.transmilenioestaciones.ui.listObjects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmilenioestaciones.Bus;
import com.example.transmilenioestaciones.R;
import com.example.transmilenioestaciones.Station;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterStation extends RecyclerView.Adapter<ViewHolderStation> {
    private List<Station> stationList,stationListCopy;
    private ClickRecyclerViewInterfaceStation clickRecyclerViewInterfaceStation;

    public RecyclerViewAdapterStation(List<Station> stationList){
        this.stationList = stationList;
    }

    public RecyclerViewAdapterStation(ClickRecyclerViewInterfaceStation clickRecyclerViewInterfaceStation){
        this.clickRecyclerViewInterfaceStation = clickRecyclerViewInterfaceStation;
        this.stationList = new ArrayList<>();
    }

    public List<Station> getStationList(){
        return stationList;
    }

    public void addStation(Station station){
        this.stationList.add(station);
        this.notifyItemInserted(this.stationList.size()-1);
    }

    public void setBusList(List<Station> stationList){
        this.stationList = stationList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderStation onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_name, parent, false);
        final ViewHolderStation viewHolderStation = new ViewHolderStation(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecyclerViewInterfaceStation.onClick(v, stationList.get(viewHolderStation.getAdapterPosition()));
            }
        });
        return viewHolderStation;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStation viewHolderStation, int position){
        Station station = this.stationList.get(position);
        viewHolderStation.getTextViewName().setText(station.getName());
        //viewHolderStation.getTextViewDestiny().setText(station.getDestiny());
    }

    @Override
    public int getItemCount(){
        return this.stationList.size();
    }

    public void setStationListCopy(){
        this.stationListCopy = new ArrayList<>(stationList);
    }

    // Based on https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
    public void filter(String text){
        //List<Bus> busListCopy = new ArrayList<>(busList);
        stationList.clear();
        if(text.isEmpty()) {
            stationList.addAll(stationListCopy);
        }
        else{
            text = text.toLowerCase();
            for(int i = 0; i < stationListCopy.size(); i++){
                if(stationListCopy.get(i).getName().toLowerCase().contains(text)){
                    stationList.add(stationListCopy.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
