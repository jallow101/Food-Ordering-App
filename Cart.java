package com.example.reds0n.foodorderclient;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {



    private RecyclerView recyclerView;

    private TextView totalPrice;
    private ImageView imagephoto;

    private DatabaseReference mDatabase;
//    FirebaseRecyclerAdapter<orders,CartViewHolder> FBRA; not used

    users User;
    private Button placeOrder;

    List<orders> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //// setting recycle view

        recyclerView = (RecyclerView) findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orders");




        totalPrice = (TextView) findViewById(R.id.Total);
        placeOrder = (Button) findViewById(R.id.cartbtn);
        imagephoto = (ImageView) findViewById(R.id.imagephoto) ;

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cart.size()>0) {

                    showAlertDialog();
                }
                else

                    Toasty.error(Cart.this,"Cart Is Empty",Toast.LENGTH_SHORT).show();


            }
        });



        loadOrders();

    }

    private void showAlertDialog() {



        // alert dialog for address...
        AlertDialog.Builder  alert =  new AlertDialog.Builder(Cart.this);
        alert.setTitle("One more step!!");
        alert.setMessage("Enter your Address or Table number");
        final EditText address = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
         address.setLayoutParams(lp);
         alert.setView(address);// adds edittext to alerdialog
         alert.setIcon(R.drawable.ic_shopping_cart_black_24dp);

         alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {


               users User = new users();
               Order request = new Order(

                       Common.currentuser.getName(),
                       Common.currentuser.getPhone(),
                       address.getText().toString(),
                       totalPrice.getText().toString(),
                       cart


               );

               /// going to firebase

                 String time = String.valueOf(System.currentTimeMillis());
                 mDatabase.child(time).setValue(request);




                //delete cart
                 new Database(getBaseContext()).cleanCart();
                 Toast.makeText(Cart.this, "Thanks.. Order Placed :)", Toast.LENGTH_SHORT).show();
                 finish();

             }
         });

         alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

            dialog.dismiss();
             }
         });
                 alert.show();

    }


    private void loadOrders() {

          cart =new Database(this).getCarts();
          adapter = new CartAdapter(cart,this);
         recyclerView.setAdapter(adapter);
             adapter.notifyDataSetChanged();

        ///calculations

          int total = 0;
          for(orders Orders: cart){
            total += Integer.parseInt(Orders.getPrice())*Integer.parseInt(Orders.getQuantity());




            Locale locale = new Locale("en","US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            totalPrice.setText(fmt.format(total));


          }



    }


   public static class CartViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView cart_name,cart_price;
        private ImageView cartimage,imagephoto;

        View mView;
        public  CartViewHolder(View itemView){
            super(itemView);
            mView=itemView;

            cart_name = (TextView) mView.findViewById(R.id.item_cart_name);
            cart_price = (TextView)  mView.findViewById(R.id.item_cart_price);
            cartimage = (ImageView)  mView.findViewById(R.id.item_cart_count) ;
            imagephoto = (ImageView)  mView.findViewById(R.id.imagephoto);

            mView.setOnCreateContextMenuListener(this);


        }

        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.item_cart_name);
            food_name.setText(name);


        }
//
//
//        public void setPrice(String price){
//            TextView food_name = (TextView) mView.findViewById(R.id.item_cart_price);
//            food_name.setText(price);
//
//
//        }
//
//
//
//
        public void setImage(Context ctx, String image ){

            ImageView food_image = (ImageView) mView.findViewById(R.id.imagephoto);
            Picasso.with(ctx).load(image).into(food_image);



        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            menu.setHeaderTitle("Select Action : ");
            menu.add(0,0,getAdapterPosition(),Common.DELETE);
            menu.add(0,0,getAdapterPosition(),Common.UPDATE);

        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.DELETE))

            deleteCart(item.getOrder());


        return true;
    }

    private void deleteCart(int position) {

        // removes item at position in List<orders>
        cart.remove(position);

        //clean cart
        new Database(this).cleanCart();
            for (orders addorders : cart)
                new Database(this).addToCart(addorders);

            loadOrders();


    }

    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{
        private  List<orders> listData = new ArrayList<>();
        private Context context;


        public CartAdapter( List<orders> listData, Context context) {
            this.context = context;
            this.listData = listData;
        }

        @Override
        public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View mView  = inflater.inflate(R.layout.cart_layout,parent,false);

            return new CartViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(CartViewHolder holder, int position) {


            Picasso.with(getBaseContext()).load(listData.get(position).getImage()).into(holder.imagephoto);

            TextDrawable drawable = TextDrawable.builder().buildRound(""+ listData.get(position).getQuantity(), Color.GREEN);
            holder.cartimage.setImageDrawable(drawable);

            Locale locale = new Locale("en","US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            // Try to see if total will automatically change to 0 when cart is empty
//            if (cart.size()<1){
//                holder.cart_price.setText(fmt.format(0));
//            }
//            else {
//
//                int price = (Integer.parseInt(listData.get(position).getPrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
//                holder.cart_price.setText(fmt.format(price));
//            }

            int price = (Integer.parseInt(listData.get(position).getPrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
            holder.cart_price.setText(fmt.format(price));
            holder.cart_name.setText(listData.get(position).getProductname());



        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }


}


