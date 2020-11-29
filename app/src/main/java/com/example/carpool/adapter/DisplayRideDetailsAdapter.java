package com.example.carpool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.R;
import com.example.carpool.model.RideDetails;

import java.util.LinkedList;

public class DisplayRideDetailsAdapter extends RecyclerView.Adapter<DisplayRideDetailsAdapter.ViewHolder> {

    private Context context;
    private LinkedList<RideDetails> listOfRideDetails;

    public DisplayRideDetailsAdapter(Context context, LinkedList<RideDetails> listOfRideDetails) {
        this.context = context;
        this.listOfRideDetails = listOfRideDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_display_ride_details_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RideDetails rideDetails = listOfRideDetails.get(position);
        holder.tvCarNumber.setText(rideDetails.getCarNumber());
        holder.tvFrom.setText(rideDetails.getFrom());
        holder.tvTo.setText(rideDetails.getTo());
    }

    @Override
    public int getItemCount() {
        return listOfRideDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCarNumber, tvFrom, tvTo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTo = itemView.findViewById(R.id.tvTo);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvCarNumber = itemView.findViewById(R.id.tvCarNumber);
        }
    }
}
