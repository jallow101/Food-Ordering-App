package com.example.reds0n.foodorderclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView mMenulist;
    private DatabaseReference mDatabase, userDatabase;
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseRecyclerAdapter<category,Menu.CategoryViewHolder> FBRA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        //init firebase and recycleview
        mMenulist = (RecyclerView) findViewById(R.id.recycle_menu);
        mMenulist.setHasFixedSize(true);
        mMenulist.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("category");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cart = new Intent(Menu.this,Cart.class);
                startActivity(cart);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /// Starts Notification service
        Intent service = new Intent(Menu.this,ListenOrder.class);
        startService(service);
    }


    protected void onStart(){
        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
        FBRA = new FirebaseRecyclerAdapter<category, CategoryViewHolder>(
                category.class,
                R.layout.menu_item,
                Menu.CategoryViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, category model, final int position) {

                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final category clickname = model;
               viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                      //category id to new intent
                       Intent foodlist = new Intent(Menu.this,food_list.class);

                       //extra key for matching

                       foodlist.putExtra("CategoryId",FBRA.getRef(position).getKey());
                       startActivity(foodlist);
                   }

               });


            }
        };

        mMenulist.setAdapter(FBRA);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Orders) {
            // Handle the orders action
            Intent orders = new Intent(Menu.this,OrderStatus.class);
            startActivity(orders);

        } else if (id == R.id.nav_Cart) {

            Intent cart = new Intent(Menu.this,Cart.class);
            startActivity(cart);

        } else if (id == R.id.nav_fpassword) {

            showChangePasswordDialog();

        } else if (id == R.id.nav_logout) {

            Intent signin = new Intent(Menu.this,Login.class);
            signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(signin);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChangePasswordDialog() {

        AlertDialog.Builder  alert =  new AlertDialog.Builder(Menu.this);
        alert.setTitle("Change Password!!");
        alert.setMessage("Please fill all !!!!!!!!");

        LayoutInflater inflater = LayoutInflater.from(this);
        View layoutpass = inflater.inflate(R.layout.change_password,null);

        final MaterialEditText password = (MaterialEditText)layoutpass.findViewById(R.id.mypassword);
        final MaterialEditText new_password = (MaterialEditText)layoutpass.findViewById(R.id.newpassword);
        final MaterialEditText renew_password = (MaterialEditText)layoutpass.findViewById(R.id.renewpassword);

        alert.setView(layoutpass);
        alert.setIcon(R.drawable.ic_vpn_key_black_24dp);

        alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final android.app.AlertDialog loadingDilaog = new SpotsDialog(Menu.this);
                loadingDilaog.show();


                if (password.getText().toString().equals(Common.currentuser.getPassword())){


                    ///change to new password



                    if (new_password.getText().toString().equals(renew_password.getText().toString())){

                        Map<String,Object> updatepassword = new HashMap<>();
                        updatepassword.put("password",new_password.getText().toString());

                        userDatabase.child(Common.currentuser.getPhone()).
                                updateChildren(updatepassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingDilaog.dismiss();
                                        Toast.makeText(Menu.this, "Password Changed !!!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDilaog.dismiss();
                                        Toast.makeText(Menu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });








                    }
                    else {

                        loadingDilaog.dismiss();
                        Toast.makeText(Menu.this, "Passwords didn't match!!", Toast.LENGTH_SHORT).show();
                    }





                }



            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(Menu.this, "Bye Bye!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public  CategoryViewHolder(View itemView){
            super(itemView);
            mView=itemView;

        }

        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.menu_text);
            food_name.setText(name);


        }



        public void setImage(Context ctx, String image ){

            ImageView food_image = (ImageView) mView.findViewById(R.id.menu_image);
            Picasso.with(ctx).load(image).into(food_image);



        }


    }
}
