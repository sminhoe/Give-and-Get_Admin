package com.example.user8.myapp_admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    private Button btn_user,btn_food,btn_request,btn_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_user = (Button)findViewById(R.id.btn_user);
        btn_food = (Button)findViewById(R.id.btn_food);
        btn_request = (Button)findViewById(R.id.btn_request);
        btn_signout = (Button)findViewById(R.id.btn_signout);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, UserActivity.class));
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, FoodActivity.class));
            }
        });

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, RequestActivity.class));
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                auth.signOut();
//                finish();
//                if (auth.getCurrentUser() != null) {
//                    startActivity(new Intent(Main2Activity.this, MainActivity.class));
//                }
                auth.signOut();
                        if (auth.getCurrentUser() == null) {
                            Toast.makeText(Main2Activity.this, getString(R.string.signout_msg), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                            finish();
                        }

            }
        });
    }
}
