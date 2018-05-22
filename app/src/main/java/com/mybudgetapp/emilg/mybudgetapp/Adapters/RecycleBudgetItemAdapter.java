package com.mybudgetapp.emilg.mybudgetapp.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybudgetapp.emilg.mybudgetapp.Items.BudgetSubItem;
import com.mybudgetapp.emilg.mybudgetapp.R;

import java.util.List;

public class RecycleBudgetItemAdapter extends RecyclerView.Adapter<RecycleBudgetItemAdapter.ViewHolder> {
private final List<BudgetSubItem> listitem;

public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public final TextView nameview;
    public final TextView valueview;
    public final TextView idview;
    public ViewHolder(View view) {
        super(view);
        nameview =  view.findViewById(R.id.budgetitem_name_item);
        valueview =  view.findViewById(R.id.budgetitem_value_item);
        idview = view.findViewById(R.id.budgetitem_item_id);
        view.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

    }

}

    public RecycleBudgetItemAdapter(List<BudgetSubItem> items) {
        listitem = items;
    }

    @Override
    public void onBindViewHolder(RecycleBudgetItemAdapter.ViewHolder holder, int position) {

        BudgetSubItem budgetSubItem = listitem.get(position);
        holder.nameview.setText(budgetSubItem.getName());
        holder.valueview.setText(budgetSubItem.getValue().toString());

        holder.idview.setText(String.valueOf(budgetSubItem.getId()));
        if(budgetSubItem.getType())
        {
            holder.valueview.setTextColor(Color.parseColor("#00ff00"));
        }
        else
        {
            holder.valueview.setTextColor(Color.parseColor("#ff0000"));
        }
    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }

    @NonNull
    @Override
    public RecycleBudgetItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budgetitem_recycler_item,parent,false);
        return new RecycleBudgetItemAdapter.ViewHolder(view);
    }
}
