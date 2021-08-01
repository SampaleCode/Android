package com.example.loginwithsignup.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Helper {
    private Context mContext;
    private SharedPreferences mSp;
    private SharedPreferences.Editor spe;

    public Helper(Context context, SharedPreferences sp) {
        this.mContext = context;
        this.mSp = sp;
    }

    public void altMsg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public boolean emailValidate(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public void putSP(String key, String value, boolean add) {
        spe = mSp.edit();

        if (add) {//Add to shared Preference
            spe.putString(key, value);
        } else {//Remove from the shared Preference
            spe.remove(key);
        }
        spe.commit();
    }

}
