package com.example.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewholder> {
    private Context mContext;
    private List<SHItems> mItems;

    public ListAdapter(List<SHItems> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false); //false damit er nicht sofort kreiert
        return new ItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewholder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    public void swapData(List<SHItems> items) {
        mItems = items;
        notifyDataSetChanged();

    }

    class ItemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private TextView tvState;
        private LinearLayout llItem;

        public ItemViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvState = itemView.findViewById(R.id.tv_state);
            llItem = itemView.findViewById(R.id.ll_item);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            tvName.setText(mItems.get(position).getName());
            tvState.setText(mItems.get(position).getState());
            llItem.setBackgroundResource(mItems.get(position).getColor());
        }

        @Override
        public void onClick(View v) {
            mItems.get(getAdapterPosition()).postCommad("TOGGLE");
        }
    }
}
