package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppVisitCount extends AppCompatActivity {
    String Filename = "MyFile";
    TextView textView;
    static int Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_visit_count);

        textView = findViewById(R.id.appVisitCountTxtView);

//        saveToFile()
        Count++;

        String readData = String.valueOf(Count);

        try {
            FileOutputStream outputStream = openFileOutput(Filename, Context.MODE_PRIVATE);
            outputStream.write(readData.getBytes());
            outputStream.close();
//            textView.setText("");
            Toast.makeText(this, "Count Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        readFromFile
        String ReadFileData = "";
        try {
            FileInputStream inputStream = openFileInput(Filename);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String currentData = bufferedReader.readLine();
            if (currentData == null) {
                Toast.makeText(this, "File Empty", Toast.LENGTH_SHORT).show();
            }
            else {
                while (currentData != null) {
                    ReadFileData = ReadFileData + currentData;
                    currentData = bufferedReader.readLine();
                }
                bufferedReader.close();
                streamReader.close();
                inputStream.close();
                Toast.makeText(this, "Count Displayed", Toast.LENGTH_SHORT).show();
            }
            textView.setText(ReadFileData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}