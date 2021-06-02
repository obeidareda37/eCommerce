package com.example.ecommerce.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    Button signUp;
    TextView signIn;
    EditText name, email, password;

    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            finish();
        }

        name = findViewById(R.id.Registration_ed_name);
        email = findViewById(R.id.Registration_ed_email);
        password = findViewById(R.id.Registration_ed_password);

        signUp = findViewById(R.id.Registration_btn_signUp);
        signIn = findViewById(R.id.Registration_text_signIn);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime= sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();
            Intent intent = new Intent(RegistrationActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getApplicationContext(), R.string.validate_name, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(getApplicationContext(), R.string.validat_email, Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(getApplicationContext(), R.string.validate_passwoed, Toast.LENGTH_SHORT).show();
                    return;

                }

                if (userPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.password_length, Toast.LENGTH_SHORT).show();
                    return;

                }

                auth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), R.string.succrssfully_registration, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.registraion_failed + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });


    }
}