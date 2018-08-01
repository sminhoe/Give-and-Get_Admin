package com.example.user8.myapp_admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<usermodel> result;
    private useradapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        result = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.user_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(lim);

        adapter = new useradapter(result);
        recyclerView.setAdapter(adapter);

        updateList();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 0:
                removeUser(item.getItemId());
                break;

            case 1:
                changeUser(item.getItemId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private int getItemIndex(usermodel user){
        int index = -1;
        for(int i = 0; i < result.size();i++)
        {
            if (result.get(i).key.equals(user.key))
            {
                index = i;
                break;
            }

        }
        return index;
    }

    private void updateList() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(usermodel.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                usermodel model = dataSnapshot.getValue(usermodel.class);

                int index = getItemIndex(model);

                result.set(index,model);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                usermodel model = dataSnapshot.getValue(usermodel.class);

                int index = getItemIndex(model);

                result.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void removeUser(int pos){
        ref.child(result.get(pos).key).removeValue();
    }

    private void changeUser(int pos){
        usermodel user = result.get(pos);
        user.u_username="nono";

        Map<String,Object> userValue = user.toMap();
        Map<String,Object> newUser = new HashMap<>();

        newUser.put(user.key,userValue);

        ref.updateChildren(newUser);
    }
}
