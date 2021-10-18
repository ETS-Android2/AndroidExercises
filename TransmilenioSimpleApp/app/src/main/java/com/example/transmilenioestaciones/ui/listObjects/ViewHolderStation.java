package com.example.transmilenioestaciones.ui.listObjects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmilenioestaciones.R;

public class ViewHolderStation extends RecyclerView .ViewHolder{
    private TextView textViewName;

    ViewHolderStation(@NonNull View itemView){
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
    }

    TextView getTextViewName(){
        return textViewName;
    }
}
