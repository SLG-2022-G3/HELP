package com.slg.G3.sos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slg.G3.sos.ContactModel;
import com.slg.G3.sos.R;

import java.util.ArrayList;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.ViewHolder> {
    Context context;
    ArrayList<ContactModel> contactModelArrayList;

    public DatabaseAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {
        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContactName, tvContactNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvContactNumber = itemView.findViewById(R.id.tvContactNumber);

        }
    }

    @NonNull
    @Override
    public DatabaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel model = contactModelArrayList.get(position);
        holder.tvContactName.setText(model.getName());
        holder.tvContactNumber.setText(model.getPhoneNo());


    }


    @Override
    public int getItemCount() {
        return contactModelArrayList.size();
    }
}
