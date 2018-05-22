package com.mybudgetapp.emilg.mybudgetapp.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybudgetapp.emilg.mybudgetapp.Items.BudgetItem;
import com.mybudgetapp.emilg.mybudgetapp.R;

import java.util.List;

public class RecycleBudgetAdapter extends RecyclerView.Adapter<RecycleBudgetAdapter.ViewHolder> {
    private final List<BudgetItem> listitem;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public final TextView nameview;
        public final TextView typeview;
        public final TextView countview;
        public final TextView idview;
        public ViewHolder(View view) {
            super(view);
            nameview =  view.findViewById(R.id.budget_item_name);
            typeview =  view.findViewById(R.id.budget_type_item_name);
            countview =  view.findViewById(R.id.budget_count_item);
            idview = view.findViewById(R.id.budget_item_id);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }

    }

    public RecycleBudgetAdapter(List<BudgetItem> items) {
        listitem = items;
    }

    @Override
    public void onBindViewHolder(RecycleBudgetAdapter.ViewHolder holder, int position) {

        BudgetItem budgetItem = listitem.get(position);
        holder.nameview.setText(budgetItem.getBudgetname());
        holder.typeview.setText(budgetItem.getBudgettype());
        holder.countview.setText(budgetItem.getBudgetcount().toString());
        if(budgetItem.getBudgetcount()>0)
        {
            holder.countview.setTextColor(Color.parseColor("#00ff00"));
        }
        else
        {
            holder.countview.setTextColor(Color.parseColor("#ff0000"));
        }

        holder.idview.setText(String.valueOf(budgetItem.getId()));
    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }

    @NonNull
    @Override
    public RecycleBudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_recycler_item,parent,false);
        return new RecycleBudgetAdapter.ViewHolder(view);
    }
}
