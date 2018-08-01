package com.example.user8.myapp_admin;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class useradapter extends RecyclerView.Adapter<useradapter.userviewholder>{

    private List<usermodel>list;

    public useradapter(List<usermodel> list) {
        this.list = list;
    }

    @Override
    public userviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.view_user,parent,false);
        final userviewholder vHolder = new userviewholder(v);

//        vHolder.btnRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Text Click" + String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
//            }
//        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final userviewholder holder, final int position) {
        usermodel user = list.get(position);

        holder.txtname.setText(user.u_username);
        holder.txtemail.setText(user.u_email);

//        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//                contextMenu.setHeaderTitle("Select choice.");
//                contextMenu.add(holder.getAdapterPosition(),0,0,"Request");
//                contextMenu.add(holder.getAdapterPosition(),1,0,"Cancel");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class userviewholder extends RecyclerView.ViewHolder{

        TextView txtname,txtemail;
        Button btnRequest;

        public userviewholder(View itemView) {
            super(itemView);
            txtemail = (TextView)itemView.findViewById(R.id.text_email);
            txtname = (TextView)itemView.findViewById(R.id.text_username);
            btnRequest = (Button)itemView.findViewById(R.id.btn_request);
        }
    }
}
