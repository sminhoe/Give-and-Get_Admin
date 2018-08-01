package com.example.user8.myapp_admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<foodmodel> result;
    private foodadapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Button btn_addfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        btn_addfood = (Button)findViewById(R.id.btn_addfood);


        database = FirebaseDatabase.getInstance();
        ref = database.getReference("FoodList");

        result = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.food_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(lim);

        adapter = new foodadapter(result);
        recyclerView.setAdapter(adapter);

        updateList();

        btn_addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this, AddFoodActivity.class));
            }
        });
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case 0:
                if(adapter.curPos>-1){
                    removeFood(adapter.curPos);
                }
                break;

            case 1:
                if(adapter.curPos>-1){
                    changeFood(adapter.curPos);
                }
                break;

        }
        return super.onContextItemSelected(item);
    }

    private int getItemIndex(foodmodel food){
        int index = -1;
        for(int i = 0; i < result.size();i++)
        {
            if (result.get(i).f_key.equals(food.f_key))
            {
                index = i;
                break;
            }

        }
        return index;
    }

    private void updateList() {
        ref.orderByChild("f_status").equalTo("AVAILABLE").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(foodmodel.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                foodmodel model = dataSnapshot.getValue(foodmodel.class);

                int index = getItemIndex(model);

                result.set(index,model);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                foodmodel model = dataSnapshot.getValue(foodmodel.class);

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

    private void removeFood(int pos){

       foodmodel food = result.get(pos);
        food.f_status="UNAVAILABLE";
        food.f_name="----";

        Map<String,Object> foodValue = food.toMap();
        Map<String,Object> newFood = new HashMap<>();

        newFood.put(food.f_key,foodValue);

        ref.updateChildren(newFood);
    }

    private void changeFood(int pos){
            closeContextMenu();
    }
}
