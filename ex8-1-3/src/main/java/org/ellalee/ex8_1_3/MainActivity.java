package org.ellalee.ex8_1_3;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText input01;
    TextView txtMsg;

    public static String defaultUrl = "http://m.naver.com";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input01 = findViewById(R.id.input01);
        input01.setText(defaultUrl);

        txtMsg = findViewById(R.id.txtMsg);

        Button requestBtn = findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlstr = input01.getText().toString();

                ConnectThread thread = new ConnectThread(urlstr);
                thread.start();
            }
        });
    }
    class ConnectThread extends Thread{
        String urlStr;
        public ConnectThread(String inStr){
            urlStr = inStr;
        }
        public void run(){
            txtMsg.setText(output);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private  String request(String urlStr){
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int resCode = conn.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while (true){
                    line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        }catch (Exception e){
            Log.e("SampleHTTP","Exception in processing response.",e);
            e.printStackTrace();
        }
        return output.toString();
    }
}