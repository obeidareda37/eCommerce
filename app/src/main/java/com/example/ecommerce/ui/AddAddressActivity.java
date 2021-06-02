package com.example.ecommerce.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    EditText name , address , city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.add_address_toolbar);
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

        name =findViewById(R.id.ad_name);
        address =findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        phoneNumber=findViewById(R.id.ad_phone);
        postalCode =findViewById(R.id.ad_code);
        addAddressBtn =findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userAddress = address.getText().toString();
                String userCity = city.getText().toString();
                String userPhoneNumber = phoneNumber.getText().toString();
                String userPostalCode = postalCode.getText().toString();

                String final_Address ="";

                if (!userName.isEmpty()){
                    final_Address+=userName+", ";
                }

                if (!userAddress.isEmpty()){
                    final_Address+=userAddress+", ";
                }

                if (!userCity.isEmpty()){
                    final_Address+=userCity+", ";
                }
                if (!userPhoneNumber.isEmpty()){
                    final_Address+=userPhoneNumber+", ";
                }
                if (!userPostalCode.isEmpty()){
                    final_Address+=userPostalCode+". ";
                }

                if (!userName.isEmpty() &&!userAddress.isEmpty() && !userCity.isEmpty()
                        && !userPhoneNumber.isEmpty() && !userPostalCode.isEmpty()){

                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_Address);
                    firebaseFirestore.collection("CurrentUser").document(auth.getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                                finish();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}