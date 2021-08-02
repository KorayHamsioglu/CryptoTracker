package com.example.cryptotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CardViewTasarim> {
    private Context context;
    private List<String> isimler;
    private  List<Double> fiyatlar;

    public Adapter(Context context, List<String> isimler, List<Double> fiyatlar) {
        this.context =  context;
        this.isimler =isimler;
        this.fiyatlar=fiyatlar;
    }
    public void filterList(ArrayList<String> list){
        isimler=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewTasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim,parent,false);

        return new CardViewTasarim(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarim holder, int position) {
        String islem=isimler.get(position);
        double fiyat=fiyatlar.get(position);
        holder.textView2.setText(islem);
        String s=String.valueOf(fiyat);
        holder.textView3.setText(s);


    }

    @Override
    public int getItemCount() {
        return isimler.size();
    }

    public class CardViewTasarim extends RecyclerView.ViewHolder{
        public TextView textView3;
        public TextView textView2;
        public CardView cardView;

        public CardViewTasarim(View view){
            super(view);
            textView3=view.findViewById(R.id.textView3);
            textView2=view.findViewById(R.id.textView2);
            cardView=view.findViewById(R.id.CardView);

        }
    }
}
