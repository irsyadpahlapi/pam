package com.example.lupalagi.taxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import webpage.SessionManager;


public class Masuk extends Activity {
    private Button btnLogin;
    private EditText username, password;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUser();
//        final String mNama = user.get(SessionManager.USERNAME);
//        final String mKunci = user.get(SessionManager.PASSWORD);
        final String auth_token = user.get(SessionManager.USERNAME);
        if (auth_token != null) {
            sessionManager.cekLogin();
            finish();
        }
        btnLogin = (Button) findViewById(R.id.submit);
        //btnDaftar = (Button)findViewById(R.id.btnDaftar);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
/*        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = username.getText().toString();
                final String kunci = password.getText().toString();

                if (nama.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Email harus diisi.", Toast.LENGTH_SHORT).show();
                } else if (kunci.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Password harus diisi.", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog = ProgressDialog.show(Masuk.this, "", "Silahkan tunggu..", true);
                    progressDialog.setCancelable(true);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "http://192.168.43.229/webservice/login.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                sessionManager.createSession(nama);
                                Intent intent = new Intent(getApplicationContext(), Konfirmasi.class);
                                startActivity(intent);
                                finish();
                                JSONObject object = new JSONObject(response);
                                Boolean status = object.getBoolean("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Cek your conection", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    }) {
                        @Override
                        protected void onFinish() {
                            super.onFinish();
                            progressDialog.dismiss();
                        }
                        @Override
                        protected void deliverResponse(String response) {
                            super.deliverResponse(response);
                            progressDialog.show();
                        }
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", username.getText().toString());
                            params.put("password", password.getText().toString());
                            return params;
                        }
                    };
                    Volley.newRequestQueue(getApplicationContext()).add(postRequest);
                }
            }
        });
    }
}

/*    public void getErrors(String json){
        try {
            JSONObject object = new JSONObject(json);
            Boolean status = object.getBoolean("status");
            if (!status){
                String msg = object.getString("msg");
                JSONObject error = object.getJSONObject("error");
                String msgerror = "";
                if (error.has("email")){
                    JSONArray erromessage = error.getJSONArray("email");
                    for(int i=0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i)+"\n";

                    }
                }

                if (error.has("password")) {
                    JSONArray erromessage = error.getJSONArray("password");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i)+"\n";
                    }
                }
                Toast.makeText(getApplicationContext(),msg+"\n"+msgerror,Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
/*

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;


import util.resto.SessionManager;

public class MainActivity extends Activity {

    Button btnDaftar;
    Button btnLOgin;
    EditText username, password;
    SessionManager sessionManager;
    HashMap<String, String> user;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUser();
        final String mNama = user.get(SessionManager.USERNAME);
        final String mKunci = user.get(SessionManager.PASSWORD);
        if(mNama != null && mKunci != null){
            sessionManager.cekLogin();
            finish();
        }

        username = (EditText)findViewById(R.id.edtLoginUsername);
        password = (EditText)findViewById(R.id.edtLoginPassword);
        btnLOgin = (Button)findViewById(R.id.btnLogin);
        btnDaftar = (Button)findViewById(R.id.btnDaftar);
        progress = new ProgressDialog(this);
        btnLOgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Silakan Tunggu");
                StringRequest postRequest = new StringRequest(VoiceInteractor.Request.Method.POST, "http://app.idshare.id/api/user/login", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            Boolean status = object.getBoolean("status");
                            if (status){
                                String msg = object.getString("msg");
                                UtilsPreference.getInstance(getApplicationContext()).setBoolean("islogin",true);
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        String json;
                        if(response != null && response.data != null){
                            switch(response.statusCode){
                                case 401:
                                    json = new String(response.data);
                                    getErrors(json);
                                    //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                                    break;
                                case 402:
                                    json = new String(response.data);
                                    getErrors(json);
                                    //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                                    break;
                            }
                            //Additional cases
                        }
                        progress.dismiss();

                    }
                }){
                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        progress.dismiss();
                    }


                    @Override
                    protected void deliverResponse(String response) {
                        super.deliverResponse(response);
                        progress.show();
                    }

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<>();
                        // the POST parameters:
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(getApplicationContext()).add(postRequest);
            }
        });
*/
/*        btnLOgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = username.getText().toString();
                String kunci = password.getText().toString();

                if (nama.length() > 0 && kunci.length() > 0) {

                      Toast.makeText(getApplicationContext(), "Berhasil.", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Silahkan masukkan email dan password anda.", Toast.LENGTH_SHORT).show();
                }
            }
        });*//*




        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
*/
