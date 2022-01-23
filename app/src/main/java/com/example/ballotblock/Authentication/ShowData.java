package com.example.ballotblock.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ballotblock.R;

import java.util.ArrayList;

public class ShowData extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        listView = findViewById(R.id.showDataListView);

        SharedPreferences sharedPreferences = getSharedPreferences("MyFile",0);

        String newEmail = sharedPreferences.getString("K1","");
        String newPass = sharedPreferences.getString("K2","");

        String fName = sharedPreferences.getString("K3","");
        String lName = sharedPreferences.getString("K4","");
        String cnic = sharedPreferences.getString("K5","");
        String dob = sharedPreferences.getString("K6","");
        String address = sharedPreferences.getString("K7","");
        String encoded = sharedPreferences.getString("K8","");

        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        ImageView image = (ImageView)this.findViewById(R.id.showDataImgView);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(newEmail);
        arrayList.add(newPass);
        arrayList.add(fName);
        arrayList.add(lName);
        arrayList.add(cnic);
        arrayList.add(dob);
        arrayList.add(address);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                Get the current item for listView
                View view = super.getView(position, convertView, parent);
                if(position %2 == 1) {
                    view.setBackgroundColor(getResources().getColor(
                            android.R.color.holo_green_light
                    ));
                }
                else {
                    view.setBackgroundColor(getResources().getColor(
                            android.R.color.holo_green_dark
                    ));
                }
                return view;
            }
        };

        listView.setAdapter(myAdapter);
        image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
    }

}