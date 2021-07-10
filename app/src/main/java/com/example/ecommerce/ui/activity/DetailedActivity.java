package com.example.ecommerce.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.models.NewProducts;
import com.example.ecommerce.models.PopularProducts;
import com.example.ecommerce.models.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg, addItems, removeItems;
    TextView name, description, price, quantity;
    TextView rating;
    RatingBar ratingBar;
    Button addToCart, buyNow;

    int totalQuantity = 1;
    int totalPrice = 0;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    NewProducts newProducts = null;

    PopularProducts popularProducts = null;

    ShowAll showAll = null;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detailed");

        if (object instanceof NewProducts) {
            newProducts = (NewProducts) object;
        } else if (object instanceof PopularProducts) {
            popularProducts = (PopularProducts) object;
        } else if (object instanceof ShowAll) {
            showAll = (ShowAll) object;
        }
        detailedImg = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        rating = findViewById(R.id.my_rating);
        ratingBar = findViewById(R.id.rating);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        quantity = findViewById(R.id.quantity);

        //NewProducts

        if (newProducts != null) {
            Glide.with(getApplicationContext()).load(newProducts.getImg_url()).into(detailedImg);
            name.setText(newProducts.getName());
            rating.setText(newProducts.getRating());
            ratingBar.setRating(Float.parseFloat(newProducts.getRating()));
            description.setText(newProducts.getDescription());
            price.setText(String.valueOf(newProducts.getPrice()));
            totalPrice = newProducts.getPrice() * totalQuantity;

        }
        //Popular
        if (popularProducts != null) {
            Glide.with(getApplicationContext()).load(popularProducts.getImg_url()).into(detailedImg);
            name.setText(popularProducts.getName());
            rating.setText(popularProducts.getRating());
            ratingBar.setRating(Float.parseFloat(popularProducts.getRating()));
            description.setText(popularProducts.getDescription());
            price.setText(String.valueOf(popularProducts.getPrice()));

            totalPrice = popularProducts.getPrice() * totalQuantity;

        }

        //show All
        if (showAll != null) {
            Glide.with(getApplicationContext()).load(showAll.getImg_url()).into(detailedImg);
            name.setText(showAll.getName());
            rating.setText(showAll.getRating());
            ratingBar.setRating(Float.parseFloat(showAll.getRating()));
            description.setText(showAll.getDescription());
            price.setText(String.valueOf(showAll.getPrice()));

            totalPrice = showAll.getPrice() * totalQuantity;

        }

        // BuyNow
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(DetailedActivity.this,AddressActivity.class);

                 if (newProducts != null){
                     intent.putExtra("item",newProducts);
                 }
                 if (popularProducts != null){
                     intent.putExtra("item",popularProducts);

                 }
                 if (showAll != null){
                     intent.putExtra("item",showAll);
                 }

                 startActivity(intent);
            }
        });
        //Add To Cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProducts != null){
                        totalPrice = newProducts.getPrice() * totalQuantity;

                    }
                    if (popularProducts != null){
                        totalPrice = popularProducts.getPrice() * totalQuantity;

                    }

                    if (showAll != null){
                        totalPrice = showAll.getPrice() * totalQuantity;

                    }
                }

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addToCart() {

        String saveCurrentTime, saveCurrentDate;
        Calendar calendarFroDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendarFroDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarFroDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);


        firebaseFirestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}