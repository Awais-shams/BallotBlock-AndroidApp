package com.example.ballotblock.Authentication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.RestAPI.RegisterVoterModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register3 extends AppCompatActivity {
    Toolbar toolbar;
    public Bitmap bitmap;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    MyRetrofitInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.uploadImg);

        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);

        sharedPreferences = getSharedPreferences("MyFile",0);
    }

    public void browseImage(View view) {
        Request_Permission();
    }

    public void Request_Permission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent,101);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
            try
            {
                // getContentResolver fun() of Activity Class and return object of content resolver type
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                if (bitmap != null)
                {
                    bitmap.recycle();
                }
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void Finish(View view) {
        if(bitmap==null)
        {
            Toast.makeText(Register3.this, "ID Proof Image is Required!", Toast.LENGTH_LONG).show();
            return;
        }

        createUser();
    }

    public void createUser() {
        String newEmail = sharedPreferences.getString("K1","");
        String newPass = sharedPreferences.getString("K2","");

        String fName = sharedPreferences.getString("K3","");
        String lName = sharedPreferences.getString("K4","");
        String cnic = sharedPreferences.getString("K5","");
        String dob = sharedPreferences.getString("K6","");
        String address = sharedPreferences.getString("K7","");

//        RegisterVoterModel cred = new RegisterVoterModel(fName, lName, newEmail, newPass, dob, cnic, address);
//        apiInterface.registerVoter(cred);
//        Call<RegisterVoterModel> myPost = apiInterface.registerVoter(cred);
//        myPost.enqueue(new Callback<RegisterVoterModel>() {
//            @Override
//            public void onResponse(Call<RegisterVoterModel> call, Response<RegisterVoterModel> response) {
//                if (response.isSuccessful()) {
//                    if(response.body() != null) {
//                        Toast.makeText(Register3.this, "Voter Registered...", Toast.LENGTH_SHORT).show();
//                        Intent intent3 = new Intent(Register3.this, LoginScreen.class);
//                        startActivity(intent3);
//                    }
//                    else {
//                        Toast.makeText(Register3.this, "Cannot Register Voter. API Response body is null", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(Register3.this, "Problem in Voter Credentials format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<RegisterVoterModel> call, Throwable t) {
//                Toast.makeText(Register3.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}