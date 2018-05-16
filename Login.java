package com.example.reds0n.foodorderclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import info.hoang8f.widget.FButton;

import static com.example.reds0n.foodorderclient.R.layout.activity_login;

public class Login extends AppCompatActivity {
private EditText userEmail,userPassword;

private DatabaseReference mDatabase;
private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();// removes app title bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.emailid);
        userPassword = (EditText) findViewById(R.id.passwordid);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


    }


    public void signUpButtonClicked(View view){

        Intent signup = new Intent(Login.this,MainActivity.class);
        startActivity(signup);

    }

    public void loginButttonClicked(View view) {


        if (userPassword.getText().toString().isEmpty()|userEmail.getText().toString().isEmpty()){

            Toast.makeText(this, "Please fill all!!", Toast.LENGTH_SHORT).show();


        }

        else {

            final ProgressDialog spinner = new ProgressDialog(Login.this);
            spinner.setMessage("Please wait...");
            spinner.show();



            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.child(userEmail.getText().toString()).exists()) {
                        spinner.dismiss();

                        users User = dataSnapshot.child(userEmail.getText().toString()).getValue(users.class);



                        Common.currentuser = User;


                        mDatabase.removeEventListener(this);

                        if (User.getPassword().equals(userPassword.getText().toString())) {

                            Intent menuIntent = new Intent(Login.this, Menu.class);
                            startActivity(menuIntent);
                            finish();

                            Toasty.success(Login.this, "Signin Successful", Toast.LENGTH_SHORT, true).show();


//                            finish();

                        } else {

                            spinner.dismiss();
                            Toast.makeText(Login.this, "Signin Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        spinner.dismiss();
                        Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }










}
