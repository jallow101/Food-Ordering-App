package com.example.reds0n.foodorderclient;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description,food_price1;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    FloatingActionButton pricebutton;
    ElegantNumberButton numberButton;
    FButton  fbuttoncart;

    Food myfood;
    String foodId="";
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("food");
        numberButton = (ElegantNumberButton) findViewById(R.id.numbers);
        pricebutton = (FloatingActionButton) findViewById(R.id.pricebutton);

        food_description= (TextView) findViewById(R.id.food_description);
        food_price= (TextView) findViewById(R.id.foodprice);
        food_price1= (TextView) findViewById(R.id.price1);
        food_name= (TextView) findViewById(R.id.foodname);
        food_image= (ImageView) findViewById(R.id.imageview);

        fbuttoncart = (FButton) findViewById(R.id.takecart);

        fbuttoncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Database(getBaseContext()).addToCart(new orders(
                        foodId,
                        myfood.getName(),
                        numberButton.getNumber(),
                        myfood.getPrice(),
                        myfood.getDiscount(),
                        myfood.getImage()
                        

                ));
                Toast.makeText(FoodDetail.this, "Added to Cart !!!!!!!!", Toast.LENGTH_SHORT).show();

            }
        });



        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);




        if(getIntent() != null){


            foodId = getIntent().getStringExtra("FoodId");


            if (!foodId.isEmpty() && foodId != null) {


                loadfood(foodId);


            }

        }

    }



    private void loadfood(String foodId) {

        mDatabase.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myfood = dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(myfood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(myfood.getName());
                food_price.setText(myfood.getPrice());
                food_name.setText(myfood.getName());
                food_price1.setText(myfood.getPrice());
                food_description.setText(myfood.getDescription());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}


