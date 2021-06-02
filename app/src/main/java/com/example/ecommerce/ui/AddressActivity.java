package com.example.ecommerce.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.AddressAdapter;
import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.NewProducts;
import com.example.ecommerce.models.PopularProducts;
import com.example.ecommerce.models.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectAddress {
    RecyclerView recyclerView;
    private List<Address> addressList;
    private AddressAdapter addressAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    Button addAddress, paymentBtn;
    Toolbar toolbar;
    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getData from detailed activity
        Object object = getIntent().getSerializableExtra("item");
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        addAddress = findViewById(R.id.add_address_btn);
        paymentBtn = findViewById(R.id.payment_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressList, this);
        recyclerView.setAdapter(addressAdapter);

        firebaseFirestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Address address = documentSnapshot.toObject(Address.class);
                    addressList.add(address);
                    addressAdapter.notifyDataSetChanged();
                }}
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = 0.0;
                if (object instanceof NewProducts) {
                    NewProducts newProducts = (NewProducts) object;
                    amount = newProducts.getPrice();
                }
                if (object instanceof PopularProducts) {
                    PopularProducts popularProducts = (PopularProducts) object;
                    amount = popularProducts.getPrice();
                }

                if (object instanceof ShowAll) {
                    ShowAll showAll = (ShowAll) object;
                    amount = showAll.getPrice();
                }

                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);

            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}