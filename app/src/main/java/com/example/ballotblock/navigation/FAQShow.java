package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.ballotblock.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FAQShow extends AppCompatActivity {
    EditText faqAns;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqshow);

        faqAns = findViewById(R.id.faqAnsEdtTxt);

        Intent intent = getIntent();
        link = intent.getStringExtra("link");
    }

    public void FAQAns(View view) {
        DownloadWebContent obj = new DownloadWebContent();
        try {
//        DownloadWebContent function doInBackground returns String
            String CompleteWebContent = obj.execute(link).get();
            Log.d("TAG", "Back in Main...");
            faqAns.setText(CompleteWebContent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class DownloadWebContent extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.d("TAG", "DoInBackground in Progress");
            try {
//                get url from first index
                URL url = new URL(strings[0]);
//                now connection with web
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
//                    now we will have strings returning, so we will need inputStreamReader.
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    int data = reader.read();
                    String webContent = "";
                    while (data != -1) {
//                        it will read ch by ch
                        char ch = (char) data;
                        webContent += ch;
//                        if -1 then loop terminates
                        data = reader.read();
                    }
                    return webContent;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}