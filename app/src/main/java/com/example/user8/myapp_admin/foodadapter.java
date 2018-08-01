package com.example.user8.myapp_admin;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class foodadapter extends RecyclerView.Adapter<foodadapter.foodviewholder>{

    private List<foodmodel>list;

    public int curPos=-1;

    public foodadapter(List<foodmodel> list) {
        this.list = list;
    }

    @Override
    public foodviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.view_item,parent,false);
        final foodviewholder vHolder = new foodviewholder(v);

//        vHolder.btnRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Text Click" + String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
//            }
//        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final foodviewholder holder, final int position) {
        foodmodel food = list.get(position);

        holder.txtname.setText(food.f_name);
        holder.txtstatus.setText(food.f_status);

//        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//                contextMenu.setHeaderTitle("Select choice.");
//                contextMenu.add(holder.getAdapterPosition(),0,0,"Delete");
//                contextMenu.add(holder.getAdapterPosition(),1,0,"Cancel");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public class foodviewholder extends RecyclerView.ViewHolder{
//
//        TextView txtname,txtstatus;
//        Button btnRequest;
//
//        public foodviewholder(View itemView) {
//            super(itemView);
//            txtname = (TextView)itemView.findViewById(R.id.text_food);
//            txtstatus = (TextView)itemView.findViewById(R.id.text_status);
//        }
//    }

    public class foodviewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView txtname,txtstatus;

        public foodviewholder(View itemView) {
            super(itemView);
            txtname = (TextView)itemView.findViewById(R.id.text_food);
            txtstatus = (TextView)itemView.findViewById(R.id.text_status);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPos=getAdapterPosition();
                }});
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            curPos=getAdapterPosition();
            menu.add(0, 0, 0, "Delete");
            menu.add(0, 1, 0, "Cancel");
        }
    }
}
