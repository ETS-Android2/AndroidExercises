package com.example.transmilenioestaciones.ui.listObjects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transmilenioestaciones.R;

public class ViewHolderBus extends RecyclerView .ViewHolder{
    private TextView textViewName, textViewDestiny;

    ViewHolderBus(@NonNull View itemView){
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDestiny = itemView.findViewById(R.id.textViewDestiny);
    }

    TextView getTextViewName(){
        return textViewName;
    }

    TextView getTextViewDestiny(){
        return textViewDestiny;
    }
}
