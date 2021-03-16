package com.fibo.smartfarmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.listeners.RecyclerListener;
import com.fibo.smartfarmer.models.Step;
import com.fibo.smartfarmer.utils.Constants;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private Context context;
    private List<Step> stepList;
    private RecyclerListener listener;

    public StepAdapter(Context context, List<Step> stepList, RecyclerListener listener) {
        this.context = context;
        this.stepList = stepList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view= LayoutInflater.from(context).inflate(R.layout.steps_item,parent,false);
ViewHolder viewHolder=new ViewHolder(view);
view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        listener.onClick(v,viewHolder.getAdapterPosition());
    }
});
return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
        Step step=stepList.get(position);
        int currentStep=position+1;
        holder.nameView.setText(step.getStepName());
        holder.headerView.setText(String.format(Locale.getDefault(),"Step %d of 4", currentStep));
        holder.optionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOptions(v,position);
            }
        });
        holder.tipsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTips(v,position);
            }
        });
        switch (currentStep){
            case 1:holder.headerView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.numeric_1_circle),null,null,null);
            break;
            case 2:holder.headerView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.numeric_2_circle),null,null,null);
                break;
            case 3:holder.headerView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.numeric_3_circle),null,null,null);
                break;
            case 4:holder.headerView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.numeric_4_circle),null,null,null);
                break;
            default:
        }

        if (step.getStepStatus().equals(Constants.STATUS_DONE)){
            holder.donePendingView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_check_circle_24));
        }

        if (step.getStepStatus().equals(Constants.STATUS_DONE) && step.getStepName().equals(Constants.CLEARANCE_STAGE)){
            addNewStep(step.getSeasonId(),Constants.PLANTING_STAGE);
        }

        if (step.getStepStatus().equals(Constants.STATUS_DONE) && step.getStepName().equals(Constants.PLANTING_STAGE)){
            addNewStep(step.getSeasonId(),Constants.WEEDING_STAGE);
        }

        if (step.getStepStatus().equals(Constants.STATUS_DONE) && step.getStepName().equals(Constants.WEEDING_STAGE)){
            addNewStep(step.getSeasonId(),Constants.HARVESTING_STAGE);
        }






    }

    private void addNewStep(int seasonId, String stage) {
        DbManager manager=new DbManager(context).open();
        manager.insertStep(new Step(seasonId,Constants.STATUS_PENDING,stage));
        manager.closeDb();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView nameView,headerView;
        CircleImageView donePendingView,optionView,tipsView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView=itemView.findViewById(R.id.clearance);
            headerView=itemView.findViewById(R.id.step1);
            donePendingView=itemView.findViewById(R.id.donePending);
            optionView=itemView.findViewById(R.id.optionView);
            tipsView=itemView.findViewById(R.id.tips);

        }
    }
}
