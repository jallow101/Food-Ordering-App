package com.example.reds0n.foodorderclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OrderStatus extends AppCompatActivity {

    private ImageView imagephoto;
    private  RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<Order,OrderStatus.orderViewHolder> FBRA;

    Order myorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recyclerView = (RecyclerView) findViewById(R.id.orderlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orders");

        imagephoto = (ImageView) findViewById(R.id.imagephoto);


        loadOrder(Common.currentuser.getPhone());




    }


    private void loadOrder(String phone) {

      FBRA = new FirebaseRecyclerAdapter<Order, orderViewHolder>(
              Order.class,
              R.layout.order_layout,
              orderViewHolder.class,
              mDatabase.orderByChild("phone").equalTo(phone)
      ) {
          @Override
          protected void populateViewHolder(orderViewHolder viewHolder, Order model, int position) {





              viewHolder.order_id.setText(FBRA.getRef(position).getKey());
              viewHolder.order_address.setText(model.getAddress());
              viewHolder.order_number.setText(model.getPhone());
              viewHolder.order_status.setText(Common.convertCodeToStatus(model.getStatus()));

              myorder=model;

          }
      };

      recyclerView.setAdapter(FBRA);






    }

    private String convertstatus(String status) {

        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "Taken";
        else
            return "Done";
    }


    public static class orderViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener{

        private TextView order_id,order_number,order_status,order_address;
        private ImageView imagephoto;


        View mView;
        public orderViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            mView.setOnCreateContextMenuListener(this);

            order_id = (TextView) mView.findViewById(R.id.orderid);
            order_number = (TextView) mView.findViewById(R.id.orderphone);
            order_address = (TextView) mView.findViewById(R.id.orderaddress);
            order_status = (TextView) mView.findViewById(R.id.orderstatus);
            imagephoto = (ImageView) mView.findViewById(R.id.imagephoto);



        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action : ");
            menu.add(0,0,getAdapterPosition(),Common.DELETE);
        }




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.DELETE)){

            deleteOrder(FBRA.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {

        if(myorder.getStatus()=="Placed"|myorder.getStatus()=="Taken")
        mDatabase.child(key).removeValue();
        else
            Toast.makeText(this, "You cant delete Order", Toast.LENGTH_SHORT).show();
    }
}
