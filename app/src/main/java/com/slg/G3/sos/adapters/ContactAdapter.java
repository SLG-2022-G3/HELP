//package com.slg.G3.sos.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.parse.ParseFile;
//import com.slg.G3.sos.DetailedContact;
//import com.slg.G3.sos.R;
//import com.slg.G3.sos.fragments.ContactsFragment;
//import com.slg.G3.sos.models.Contact;
//
//import java.util.List;
//
//public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
//
//    private Context context;
//    private List<Contact> contacts;
//
//
//    public ContactAdapter (ContactsFragment context, List<Contact> contacts){
//        this.context = context.getContext();
//        this.contacts = contacts;
//    }
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.contacts, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Contact contact = contacts.get(position);
//        holder.bind(contact);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return contacts.size();
//    }
//
//    // Clean all elements of the recycler
//    public void clear() {
//        contacts.clear();
//        notifyDataSetChanged();
//    }
//    // Add a list of items -- change to type used
//    public void addAll(List<Contact> contacts) {
//        contacts.addAll(contacts);
//        notifyDataSetChanged();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
////        private ImageView ivProfPic;
//        private TextView tvContactName;
//        private TextView tvContactNumber;
//        private ImageButton ibEditContact;
//        private ImageButton ibDeleteContact;
//        RelativeLayout rlContact;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
////            ivProfPic = itemView.findViewById(R.id.ivProfPic);
//            tvContactName = itemView.findViewById(R.id.tvContactName);
//            tvContactNumber = itemView.findViewById(R.id.tvContactNumber);
//            ibEditContact = itemView.findViewById(R.id.ibEditContact);
//            ibDeleteContact = itemView.findViewById(R.id.ibDeleteContact);
//            rlContact = itemView.findViewById(R.id.rlContact);
//
//            rlContact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailedContact.class);
//                    context.startActivity(intent);
//
//                }
//            });
//
//            ibEditContact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//
//
//            ibDeleteContact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//
//        }
//
//        public void bind(Contact contact) {
//            tvContactName.setText(contact.getName());
//            tvContactNumber.setText(contact.getNumber());
////
////            ParseFile image = contact.getImage();
////            if (image != null){
////                Glide.with(context).load(image.getUrl()).transform(new CircleCrop()).into(ivProfPic);
////            }
//        }
//
//
//    }
//
//}