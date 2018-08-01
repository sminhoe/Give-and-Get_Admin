package com.example.user8.myapp_admin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.foodviewholder>{

    private List<Food> list;

    public int curPos=-1;

    public RequestAdapter(List<Food> list) {
        this.list = list;
    }


    @Override
    public RequestAdapter.foodviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.view_request,parent,false);
        final RequestAdapter.foodviewholder vHolder = new RequestAdapter.foodviewholder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(final RequestAdapter.foodviewholder holder, final int position) {
        Food food = list.get(position);

        holder.txtname.setText(food.u_food);
        holder.txtdate.setText(food.u_date);
        holder.txttime.setText(food.u_time);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Food");
        ref.child(food.key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String check = (String) dataSnapshot.child("status").getValue();
                if (check.equals("ACCEPT"))
                {
                    holder.txtPending.setVisibility(View.INVISIBLE);
                    String txtowner = (String) dataSnapshot.child("uid").getValue();
                    String txtacceptor = (String) dataSnapshot.child("uid_request").getValue();

                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users");
                    ref2.child(txtowner).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = (String) dataSnapshot.child("u_username").getValue();
                            holder.txtOwner.setText(name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    ref2.child(txtacceptor).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = (String) dataSnapshot.child("u_username").getValue();
                            holder.txtAccept.setText(name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                else
                {
                    String txtowner = (String) dataSnapshot.child("uid").getValue();

                    holder.txtAccept.setText("NO ACCEPTOR");
                    holder.txtPending.setVisibility(View.VISIBLE);
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users");
                    ref3.child(txtowner).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = (String) dataSnapshot.child("u_username").getValue();
                            holder.txtOwner.setText(name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class foodviewholder extends RecyclerView.ViewHolder {

        TextView txtname,txtdate,txttime,txtAccept, txtOwner, txtPending;

        public foodviewholder(View itemView) {
            super(itemView);
            txtname = (TextView)itemView.findViewById(R.id.text_food);
            txtdate = (TextView)itemView.findViewById(R.id.text_date);
            txttime = (TextView)itemView.findViewById(R.id.text_time);
            txtAccept = (TextView)itemView.findViewById(R.id.txtuidR);
            txtOwner = (TextView)itemView.findViewById(R.id.txtuid);
            txtPending = (TextView)itemView.findViewById(R.id.txtPending);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPos=getAdapterPosition();
                }});
        }

    }
}
