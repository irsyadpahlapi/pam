package webpage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//import com.mobile.pos.DashboardActivity;
//import com.mobile.pos.MainActivity;
import com.example.lupalagi.taxi.Konfirmasi;
import com.example.lupalagi.taxi.Masuk;
import com.example.lupalagi.taxi.Masuk;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAMA = "MobilePos";
    private static final String IS_LOGIN = "isLogin";
    public static final String USERNAME = "username";
//    public static final String PASSWORD = "password";


    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAMA, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createSession(String username){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, username);
//        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getUser(){
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, preferences.getString(USERNAME, null));
//        user.put(PASSWORD, preferences.getString(PASSWORD, null));
        return user;
    }

    public void cekLogin(){
            Intent intent = new Intent(context, Konfirmasi.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, Masuk.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private boolean islogin(){
        return preferences.getBoolean(IS_LOGIN, false);
    }
}
