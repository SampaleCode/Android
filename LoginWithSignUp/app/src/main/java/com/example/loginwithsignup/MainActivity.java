package com.example.loginwithsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginwithsignup.Common.Helper;
import com.example.loginwithsignup.DBHelper.DBConnect;
import com.example.loginwithsignup.DBHelper.DBHandler;
import com.example.loginwithsignup.Forms.HomeActivity;
import com.example.loginwithsignup.Forms.SignupActivity;
import com.example.loginwithsignup.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText editUserID, editPassword;
    private Button btnLogin, btnSignup;
    private Helper helper;
    private DBHandler dbHandler;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserID = (EditText) findViewById(R.id.editUserID);
        editPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);

        helper = new Helper(getApplicationContext(), sp);
        dbHandler = new DBHandler(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = editUserID.getText().toString().trim();
                String uPasswd = editPassword.getText().toString().trim();
                //Basic Validate
                if (uID.isEmpty()) {
                    helper.altMsg("Please Enter User ID");
                } else if (uPasswd.isEmpty()) {
                    helper.altMsg("Please Enter Password");
                } else {
                    //Valid User
                    JSONObject getObj = dbHandler.getUserData(uID, uPasswd);
                    if (getObj.length() > 0) {

                        //Redirect the Home Page with Cashing Data
                        try {
                            helper.putSP(DBConnect.C_USERID, getObj.get(DBConnect.C_USERID).toString(), true);
                            helper.putSP(DBConnect.C_USERNA, getObj.get(DBConnect.C_USERNA).toString(), true);
                            helper.putSP(DBConnect.C_UEMAIL, getObj.get(DBConnect.C_UEMAIL).toString(), true);
                            helper.putSP(DBConnect.C_PASSWD, getObj.get(DBConnect.C_PASSWD).toString(), true);

                            //Redirect to the Home Page
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        helper.altMsg("Error");
                    }
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirect to Signup Page

                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });


    }
}