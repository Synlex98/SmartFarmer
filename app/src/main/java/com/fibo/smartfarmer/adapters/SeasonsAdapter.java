package com.fibo.smartfarmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.listeners.RecyclerListener;
import com.fibo.smartfarmer.models.Season;

import java.util.List;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {
    private Context context;
    private List<Season> seasonList;
    private RecyclerListener listener;

    public SeasonsAdapter(Context context, List<Season> seasonList, RecyclerListener listener) {
        this.context = context;
        this.seasonList = seasonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.season_recycler_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
listener.onClick(view,holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Season season=seasonList.get(position);
holder.currentView.setText(season.getCurrentStage());
holder.idView.setText(String.valueOf(season.getSeasonId()));
holder.stockView.setText(season.getStock());
holder.sizeView.setText(season.getFarmSize());
holder.nFarmsView.setText(season.getNoOfFarms());


    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
TextView nFarmsView,sizeView,stockView,idView,currentView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nFarmsView=itemView.findViewById(R.id.nFarmsValue);
            sizeView=itemView.findViewById(R.id.sizeValue);
            stockView=itemView.findViewById(R.id.stockValue);
            idView=itemView.findViewById(R.id.seasonId);
            currentView=itemView.findViewById(R.id.stageValue);
        }
    }
}
