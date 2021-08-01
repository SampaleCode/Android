package com.example.loginwithsignup.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginwithsignup.Common.Helper;
import com.example.loginwithsignup.DBHelper.DBConnect;
import com.example.loginwithsignup.DBHelper.DBHandler;
import com.example.loginwithsignup.MainActivity;
import com.example.loginwithsignup.R;

public class SignupActivity extends AppCompatActivity {

    private EditText editUserID, editUserName, editUserEmail, editPassword, editCPassword;
    private Button btnSignup, btnBack;

    private Helper helper;
    private DBHandler dbHandler;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editUserID = (EditText) findViewById(R.id.editUserID);
        editUserName = (EditText) findViewById(R.id.editUserName);
        editUserEmail = (EditText) findViewById(R.id.editUserEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editCPassword = (EditText) findViewById(R.id.editCPassword);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnBack = (Button) findViewById(R.id.btnBack);

        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);

        helper = new Helper(getApplicationContext(), sp);
        dbHandler = new DBHandler(getApplicationContext());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = editUserID.getText().toString().trim();
                String uname = editUserName.getText().toString().trim();
                String uemail = editUserEmail.getText().toString().trim();
                String upasswd = editPassword.getText().toString().trim();
                String ucpasswd = editCPassword.getText().toString().trim();

                //Basic Validation

                if (uid.isEmpty()) {
                    helper.altMsg("Please Enter User ID");

                } else if (uname.isEmpty()) {
                    helper.altMsg("Please Enter User Name");

                } else if (uemail.isEmpty()) {
                    helper.altMsg("Please Enter User Email");

                } else if (upasswd.isEmpty()) {
                    helper.altMsg("Please Enter User Password");

                } else if (ucpasswd.isEmpty()) {
                    helper.altMsg("Please Enter Confirm Password");

                } else if (!upasswd.equals(ucpasswd)) {
                    helper.altMsg("Password Mismatch");

                } else {
                    // Validate User & login
                    if (!dbHandler.validateUserID(uid)) {

                        if (helper.emailValidate(uemail)) {
                            ContentValues values = new ContentValues();
                            values.put(DBConnect.C_USERID, uid);
                            values.put(DBConnect.C_USERNA, uname);
                            values.put(DBConnect.C_UEMAIL, uemail);
                            values.put(DBConnect.C_PASSWD, upasswd);

                            if (dbHandler.saveUser(values)) {
                                helper.altMsg("User Save Successfully");

                                //Clear Data Page
                                editUserID.setText("");
                                editUserName.setText("");
                                editUserEmail.setText("");
                                editPassword.setText("");
                                editCPassword.setText("");

                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();

                            } else {
                                helper.altMsg("Error Save");
                            }
                        } else {
                            helper.altMsg("Invalid Email");
                        }


                    } else {
                        helper.altMsg("User Id Already Exits...");
                    }

                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

    }
}