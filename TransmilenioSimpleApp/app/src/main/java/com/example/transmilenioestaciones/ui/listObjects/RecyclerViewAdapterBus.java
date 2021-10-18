package com.example.transmilenioestaciones.ui.listObjects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmilenioestaciones.Bus;
import com.example.transmilenioestaciones.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterBus extends RecyclerView.Adapter<ViewHolderBus> {
    private List<Bus> busList, busListCopy;
    private ClickRecyclerViewInterfaceBus clickRecyclerViewInterfaceBus;

    public RecyclerViewAdapterBus(List<Bus> busList){
        this.busList = busList;
    }

    public RecyclerViewAdapterBus(ClickRecyclerViewInterfaceBus clickRecyclerViewInterfaceBus){
        this.clickRecyclerViewInterfaceBus = clickRecyclerViewInterfaceBus;
        this.busList = new ArrayList<>();
    }

    public List<Bus> getBusList(){
        return busList;
    }

    public void addBus(Bus bus){
        this.busList.add(bus);
        this.notifyItemInserted(this.busList.size()-1);
    }

    public void setBusList(List<Bus> busList){
        this.busList = busList;
        this.notifyDataSetChanged();
    }

    public void setBusListCopy(){
        this.busListCopy = new ArrayList<>(busList);
    }

    @NonNull
    @Override
    public ViewHolderBus onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detailed_name, parent, false);
        final ViewHolderBus viewHolderBus = new ViewHolderBus(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecyclerViewInterfaceBus.onClick(v, busList.get(viewHolderBus.getAdapterPosition()));
            }
        });
        return viewHolderBus;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBus viewHolderBus, int position){
        Bus bus = this.busList.get(position);
        viewHolderBus.getTextViewName().setText(bus.getName());
        viewHolderBus.getTextViewDestiny().setText(bus.getDestiny());
    }

    @Override
    public int getItemCount(){
        return this.busList.size();
    }

    // Based on https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
    public void filter(String text){
        //List<Bus> busListCopy = new ArrayList<>(busList);
        busList.clear();
        if(text.isEmpty()) {
            busList.addAll(busListCopy);
        }
        else{
            text = text.toLowerCase();
            for(int i = 0; i < busListCopy.size(); i++){
                if(busListCopy.get(i).getName().toLowerCase().contains(text)){
                    busList.add(busListCopy.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
