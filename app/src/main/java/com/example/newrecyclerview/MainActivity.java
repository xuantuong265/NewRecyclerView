package com.example.newrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    EditText edtChoseBirthday, edtAddress, edtPhoneNumber,
            edtLastName,edtfistName;
    RadioButton rdMale, rdFemale;
    RadioGroup radioGroup;
    Button btnSave, btnUpload, btnCamera;
    ImageView imgAvt;
    private RecyclerView recyclerView;
    ArrayList<Students> studentsArrayList;
    int REQUEST_CODE_IMAGE = 1;
    DatabaseReference mData;
    AlertDialog alertDialog;
    StorageReference storageReference;

    public String url;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private int PICK_IMAGE_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){

        // dilog
        mData = FirebaseDatabase.getInstance().getReference();
        alertDialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();
        storageReference = FirebaseStorage.getInstance().getReference("image_upload");

        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnCamera = (Button) findViewById(R.id.btnCamera);
        imgAvt = (ImageView) findViewById(R.id.imgAvt);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtfistName = (EditText) findViewById(R.id.edtfistName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_character);
        rdFemale = (RadioButton) findViewById(R.id.radioButton_female);
        rdMale = (RadioButton) findViewById(R.id.radioButton_male);
        edtChoseBirthday = (EditText) findViewById(R.id.edtChoseBirthday);
        edtChoseBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseBirrthday();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_CODE);
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE){
            alertDialog.show();

            UploadTask uploadTask = storageReference.putFile(data.getData());
            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Upload fail...", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(MainActivity.this, "Đợi 1 lát <3", Toast.LENGTH_SHORT).show();
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    url = task.getResult().toString().substring
                            (0,task.getResult().toString().indexOf("&token"));
                    Picasso.get().load(url).into(imgAvt);
                    alertDialog.dismiss();

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // tạo node trong firebase
                            Students students = new Students();
                            students.setEdtfistName(edtfistName.getText().toString());
                            students.setEdtLastName(edtLastName.getText().toString());
                            students.setEdtAddress(edtAddress.getText().toString());
                            students.setEdtChoseBirthday(edtChoseBirthday.getText().toString());
                            students.setEdtPhoneNumber(edtPhoneNumber.getText().toString());

                            if (rdFemale.isChecked()){
                                students.setRadioButton(rdFemale.getText().toString());
                            }
                            if (rdMale.isChecked()){
                                students.setRadioButton(rdMale.getText().toString());
                            }
                            students.setLinkAvt(url);
                            mData.child("Students").push().setValue(students, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        Toast.makeText(MainActivity.this, "Lưu dữ liệu thành công <3", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Lỗi game....", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Intent intent =  new Intent(MainActivity.this, ResultActivity.class);
                            startActivity(intent);

                        }
                    });
                }
            });

        }


    }



    private void choseBirrthday (){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtChoseBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }



}
