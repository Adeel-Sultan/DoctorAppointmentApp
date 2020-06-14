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

import com.example.adeelsultan.phcproject.ComplaintsModel.ComplaintsModel;
import com.example.adeelsultan.phcproject.PatientsModel.PatientsModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ComplainActivity extends AppCompatActivity {

    Button image_choose_button, Complaint;
    EditText user, email, blood, cnic, phone, issues;
    RadioGroup gender;
    RadioButton genderbutton;

    Uri fileData = null;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);


        Complaint = findViewById(R.id.complain_button);
        image_choose_button = findViewById(R.id.complain_image);
        user = findViewById(R.id.complain_name);
        email = findViewById(R.id.complain_email);
        blood = findViewById(R.id.complain_blood);
        cnic = findViewById(R.id.complain_cnic);
        phone = findViewById(R.id.complain_phone);
        issues = findViewById(R.id.complain_issues);
        gender = findViewById(R.id.gender_group);

        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();



        Complaint.setOnClickListener(new View.OnClickListener() {
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
        String userBlood = blood.getText().toString().trim();
        String userCNIC = cnic.getText().toString().trim();
        String userPhone = phone.getText().toString().trim();
        String userIssue = issues.getText().toString().trim();
        String Status = "pending";

        if (TextUtils.isEmpty(userName)){
            user.setError("Please Enter your UserName here");
        }
        else if (TextUtils.isEmpty(userEmail)){
            email.setError("Please Enter your Email here");
        }
        else if (!(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())){
            email.setError("Please Enter your valid Email");
        }
        else if (TextUtils.isEmpty(userBlood)){
            blood.setError("Please Enter your Blood Group here");
        }
        else if (TextUtils.isEmpty(userCNIC)){
            cnic.setError("Please Enter your CNIC");
        }
        else if (TextUtils.isEmpty(userPhone)){
            phone.setError("Please Enter your Phone Number");
        }
        else if (TextUtils.isEmpty(userIssue)){
            issues.setError("Please Enter your Complaints/Issue");
        }
        else  if (fileData == null){
            Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
        }
        else {
            int selectedID = gender.getCheckedRadioButtonId();
            genderbutton = findViewById(selectedID);
            String userGender = genderbutton.getText().toString();

            SendImageinStorage(userName, userEmail, userBlood, userCNIC, userPhone, userIssue, userGender, Status, fileData);
        }
    }

    private void SendImageinStorage(final String userName, final String userEmail, final String userBlood, final String userCNIC, final String userPhone, final String userIssue, final String userGender, final String Status, Uri fileData) {

        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final StorageReference ref = FirebaseStorage.getInstance().getReference("Complaint's_Images/"+auth.getCurrentUser().getUid());
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
                if (task.isSuccessful()) {
                    Uri DownloadedURL = task.getResult();
                    InsertInRealTimeDataBase(userName, userEmail, userBlood, userCNIC, userPhone, userIssue, userGender, Status, DownloadedURL);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ComplainActivity.this, "URL not generated", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



    private void InsertInRealTimeDataBase(String userName, String userEmail, String userBlood, String userCNIC, String userPhone, String userIssue, String userGender, String Status, Uri downloadedURL) {

        ComplaintsModel value = new ComplaintsModel(userName, userEmail, userBlood, userCNIC, userPhone, userIssue, userGender, Status, downloadedURL.toString());
        FirebaseDatabase.getInstance().getReference("ComplaintsTable").child(auth.getCurrentUser().getUid()).setValue(value)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(ComplainActivity.this, "Complaints Submitted Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ComplainActivity.this, DashboardActivity.class));
                            finish();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(ComplainActivity.this, "Complaints Not Submitted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

