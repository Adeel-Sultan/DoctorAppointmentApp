package com.example.adeelsultan.phcproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class LoginAsPatient extends AppCompatActivity {

    Button button1, button2, login;
    EditText email, password;

    ProgressDialog progressDialog;
    FirebaseAuth auth;

    boolean person_Found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_patient);

        button1 = findViewById(R.id.iamdoctor);
        button2 = findViewById(R.id.pat_signupbutton);
        login = findViewById(R.id.pat_loginbutton);
        email = findViewById(R.id.pat_loginemail);
        password = findViewById(R.id.pat_loginpassword);

        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        person_Found = false;

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAsPatient.this, LoginAsDoctor.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAsPatient.this, SignupAsPatient.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckValidation();
            }
        });
    }

    private void CheckValidation() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)){
            email.setError("Please Enter Email");
        }
        else if (!(Patterns.EMAIL_ADDRESS.matcher(Email).matches())){
            email.setError("Please Enter ValidEmail");
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Please Enter Password");
        }
        else {
            PerformAuthentication(Email, Password);
        }
    }

    private void PerformAuthentication(final String email1, final String password1) {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                            reference.child("PatientTable").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        String Email_Fetched=snapshot.child("email").getValue().toString();
                                        String Password_Fetched=snapshot.child("password").getValue().toString();
                                        if(Email_Fetched.equals(email1)&&Password_Fetched.equals(password1)){
                                            startActivity(new Intent(LoginAsPatient.this, DashboardActivity.class));
                                            person_Found=true;
                                            finish();
                                            break;
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(LoginAsPatient.this, "Database Error", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginAsPatient.this, "Not Authenticated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
