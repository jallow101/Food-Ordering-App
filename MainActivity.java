package com.example.reds0n.foodorderclient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private EditText email,password,repassword,name;
   // private EditText email,password,repassword,name;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();// removes app title bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);

        name  = (EditText) findViewById(R.id.nameid);
        email = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.passwordid);
//        repassword = (EditText) findViewById(R.id.repasswordid);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();


    }


    public void signUpButtonClicked(View view){

        if (!TextUtils.isEmpty(email.getText().toString())&&!TextUtils.isEmpty(password.getText().toString())&& !TextUtils.isEmpty(name.getText().toString())){


            Toasty.error(MainActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();

        }

        else {
            final ProgressDialog spinner = new ProgressDialog(MainActivity.this);
            spinner.setMessage("Please wait...");
            spinner.show();


            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //check if already user phone
                    if (dataSnapshot.child(email.getText().toString()).exists()) {
                        spinner.dismiss();


                        Toasty.warning(MainActivity.this,"Sorry User registered",Toast.LENGTH_SHORT, true).show();
                    } else {

                        spinner.dismiss();
                        users User = new users(name.getText().toString(), password.getText().toString(), email.getText().toString());
                        mDatabase.child(email.getText().toString()).setValue(User);

                        Toast.makeText(MainActivity.this, "Signup Succesful !", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(MainActivity.this, Login.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }







    }





   public void loginClicked( View view){

       Intent startloging = new Intent(MainActivity.this,Login.class);
       startActivity(startloging);
   }




}
