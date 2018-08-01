package com.example.user8.myapp_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddFoodActivity extends AppCompatActivity {

    private EditText inputNewfood;
    private Button btnAdd;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding a new food...");

        inputNewfood = (EditText) findViewById(R.id.edt_newfood);
        btnAdd = (Button)findViewById(R.id.btn_submitnewfood);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newfood = inputNewfood.getText().toString().trim();

                if (!validateForm())
                {
                    return;
                }

                progressDialog.show();

                //checkfoodname
                Query usernameQuery = FirebaseDatabase.getInstance().getReference("FoodList").orderByChild("f_name").equalTo(newfood);
                usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getChildrenCount() > 0)
                        {
                            progressDialog.cancel();
                            Toast.makeText(AddFoodActivity.this,  "This food is in the list.Please add another food..",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FoodList");
                            String key = ref.push().getKey();
                            foodmodel myFood = new foodmodel(newfood,"AVAILABLE",key);
                            ref.child(key).setValue(myFood).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(AddFoodActivity.this,  getString(R.string.addfood_success),Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), FoodActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(AddFoodActivity.this,  getString(R.string.addfood_fail),Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), AddFoodActivity.class));
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private boolean validateForm()
    {
        boolean valid = true;

        String addfood = inputNewfood.getText().toString();
        if (TextUtils.isEmpty(addfood))
        {
            inputNewfood.setError(getString(R.string.required_msg));
            valid = false;
        }

        return valid;
    }
}
