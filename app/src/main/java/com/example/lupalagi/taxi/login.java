package com.example.lupalagi.taxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by LUPA LAGI on 16/12/2015.
 */
public class login extends AppCompatActivity {
    Button kirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        kirim = (Button) findViewById(R.id.submit);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Konfirmasi.class);

                startActivity(i);

            }
        });
    }
}
