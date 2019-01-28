package com.example.roomexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roomexample.database.Dog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogsViewHolder> {
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private List<Dog> dogs;

    public DogAdapter(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_adapter_item, parent, false);
        return new DogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewHolder holder, final int position) {
        holder.bind(dogs.get(position));
     }

    @Override
    public int getItemCount() {
        return dogs.size();
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface ItemClickListener {
        void onClick(int id);
    }

    public interface ItemLongClickListener {
        void onLongClick(int id);
    }

    class DogsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        final TextView tvDogInfo;
        final Context context;

        DogsViewHolder(View itemView) {
            super(itemView);
            tvDogInfo = itemView.findViewById(R.id.tvDogInfo);
            context = tvDogInfo.getContext();
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener((this));
        }

        void bind(final Dog dog) {
            tvDogInfo.setText("ID: " + dog.get_id()
                    + " Name: " + dog.getName());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onClick(dogs.get(position).get_id());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                itemLongClickListener.onLongClick(dogs.get(position).get_id());
            }
            return false;
        }
    }
}
