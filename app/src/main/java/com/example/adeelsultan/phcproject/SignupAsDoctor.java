package com.example.adeelsultan.phcproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.adeelsultan.phcproject.DoctorsModel.DoctorsModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.Random;

public class SignupAsDoctor extends AppCompatActivity {

    Button image_choose_button, Register, button1, button2;
    EditText user, email, password, confirmpassword, phone, address, specialty;
    RadioGroup gender;
    RadioButton genderbutton;

    Uri fileData = null;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_as_doctor);

        button1 = findViewById(R.id.doctorsignuppatientbutton);
        button2 = findViewById(R.id.doctorsignuploginbutton);
        image_choose_button = findViewById(R.id.imagebutton);
        Register = findViewById(R.id.doctorsignupregisterbutton);
        user = findViewById(R.id.reg_username);
        email = findViewById(R.id.reg_emailaddress);
        password = findViewById(R.id.reg_password);
        confirmpassword = findViewById(R.id.reg_confirmpassword);
        phone = findViewById(R.id.reg_phonenumber);
        address = findViewById(R.id.reg_address);
        specialty = findViewById(R.id.reg_specialty);
        gender = findViewById(R.id.gender_group);

        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupAsDoctor.this, SignupAsPatient.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupAsDoctor.this, LoginAsDoctor.class);
                startActivity(intent);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAllValidation();
            }
        });
        image_choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choose_Image();
            }
        });
    }

    private void Choose_Image() {
        Intent ImagePick = new Intent();
        ImagePick.setType("image/*");
        ImagePick.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ImagePick, 01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 01 && resultCode == RESULT_OK){
            fileData = data.getData();
        }
    }

    private void CheckAllValidation() {
        String userName = user.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassowrd = password.getText().toString().trim();
        String userConfirmPassword = confirmpassword.getText().toString().trim();
        String userPhone = phone.getText().toString().trim();
        String userAddress = address.getText().toString().trim();
        String userSpecialty = specialty.getText().toString().trim();

        rand=new Random();
        int num=rand.nextInt(5)+1;
        String d_rating=Integer.toString(num);

        if (TextUtils.isEmpty(userName)){
            user.setError("Please Enter your UserName here");
        }
        else if (TextUtils.isEmpty(userEmail)){
            email.setError("Please Enter your Email here");
        }
        else if (!(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())){
            email.setError("Please Enter your valid Email");
        }
        else if (TextUtils.isEmpty(userPassowrd)){
            password.setError("Please Enter your Password here");
        }
        else if (TextUtils.isEmpty(userConfirmPassword)){
            confirmpassword.setError("Please Re-Enter Password");
        }
        else if (!(userPassowrd.equals(userConfirmPassword))){
            Toast.makeText(this, "Password not Matched", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhone)){
            phone.setError("Please Enter your Phone Number");
        }
        else if (TextUtils.isEmpty(userAddress)){
            address.setError("Please Enter your Address");
        }
        else if (TextUtils.isEmpty(userSpecialty)){
            address.setError("Please Enter your Specialty");
        }
        else  if (fileData == null){
            Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
        }
        else {
            int selectedID = gender.getCheckedRadioButtonId();
            genderbutton = findViewById(selectedID);
            String userGender = genderbutton.getText().toString();

            InsertInDataBase(userName, userEmail, userPassowrd, userConfirmPassword, userPhone, userAddress, userSpecialty,d_rating, userGender, fileData);
        }
    }

    private void InsertInDataBase(final String userName, final String userEmail, final String userPassowrd, final String userConfirmPassword, final String userPhone, final String userAddress, final String userSpecialty,final String d_rating, final String userGender, final Uri fileData) {
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth.createUserWithEmailAndPassword(userEmail, userPassowrd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            SendImageinStorage(userName, userEmail, userPassowrd, userConfirmPassword, userPhone, userAddress, userSpecialty,d_rating, userGender, fileData);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupAsDoctor.this, "Authentication not complete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendImageinStorage(final String userName, final String userEmail, final String userPassowrd, final String userConfirmPassword, final String userPhone, final String userAddress, final String userSpecialty,final String d_rating, final String userGender, final Uri fileData) {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("Doctor's_Images/"+auth.getCurrentUser().getUid());
        ref.putFile(fileData).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                        throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri DownloadedURL = task.getResult();
                    InsertInRealTimeDataBase(userName, userEmail, userPassowrd, userConfirmPassword, userPhone, userAddress, userSpecialty,d_rating, userGender, DownloadedURL);
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(SignupAsDoctor.this, "URL not generated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void InsertInRealTimeDataBase(String userName, String userEmail, String userPassowrd, String userConfirmPassword, String userPhone, String userAddress, String userSpecialty, String d_rating, String userGender, Uri downloadedURL) {
        DoctorsModel values = new DoctorsModel(userName, userEmail, userPassowrd, userPhone, userAddress, userSpecialty,d_rating, userGender, downloadedURL.toString());

        FirebaseDatabase.getInstance().getReference("DoctorTable").child(auth.getCurrentUser().getUid()).setValue(values)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(SignupAsDoctor.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupAsDoctor.this, LoginAsDoctor.class));
                            finish();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupAsDoctor.this, "User not created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
