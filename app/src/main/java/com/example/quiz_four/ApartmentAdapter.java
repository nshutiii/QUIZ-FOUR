package com.example.quiz_four;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ApartmentAdapter extends BaseAdapter {
    private Context context;
    private List<Apartment> apartmentList;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public interface OnEditClickListener {
        void onEditClick(Apartment apartment);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Apartment apartment);
    }

    public ApartmentAdapter(Context context, List<Apartment> apartmentList) {
        this.context = context;
        this.apartmentList = apartmentList;
    }

    public void setEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    @Override
    public int getCount() {
        return apartmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return apartmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return apartmentList.get(position).getApartmentId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_apartment, parent, false);
            holder = new ViewHolder();
            holder.tvUnitNumber = convertView.findViewById(R.id.tvUnitNumber);
            holder.tvSquareFootage = convertView.findViewById(R.id.tvSquareFootage);
            holder.tvDailyRent = convertView.findViewById(R.id.tvDailyRent);
            holder.tvIsAirBnb = convertView.findViewById(R.id.tvIsAirBnb);
            holder.tvAllowsPets = convertView.findViewById(R.id.tvAllowsPets);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Apartment apartment = apartmentList.get(position);
        
        holder.tvUnitNumber.setText("Unit No: " + apartment.getUnitNumber());
        holder.tvSquareFootage.setText("Square Footage: " + apartment.getSquareFootage() + " sq ft");
        
        // Calculate daily rent (assuming rentAmount is monthly, divide by 30)
        double dailyRent = apartment.getRentAmount() / 30.0;
        holder.tvDailyRent.setText("Daily Rent: $" + String.format("%.2f", dailyRent));
        
        holder.tvIsAirBnb.setText("Is AirBnB: " + (apartment.getIsAirBnb() ? "Yes" : "No"));
        holder.tvAllowsPets.setText("Allows Pets: " + (apartment.getAllowsPets() ? "Yes" : "No"));

        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(apartment);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(apartment);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvUnitNumber;
        TextView tvSquareFootage;
        TextView tvDailyRent;
        TextView tvIsAirBnb;
        TextView tvAllowsPets;
        Button btnEdit;
        Button btnDelete;
    }

    public void updateList(List<Apartment> newList) {
        this.apartmentList = newList;
        notifyDataSetChanged();
    }
}
