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
import android.widget.TextView;

import com.example.loginwithsignup.Common.Helper;
import com.example.loginwithsignup.DBHelper.DBConnect;
import com.example.loginwithsignup.DBHelper.DBHandler;
import com.example.loginwithsignup.MainActivity;
import com.example.loginwithsignup.R;

public class HomeActivity extends AppCompatActivity {

    private TextView txtUserID;
    private SharedPreferences sp;
    private EditText editUserID, editUserName, editUserEmail, editUserPassword, editUser_ID;
    private Button btnUpdate, btnDelete;

    private DBHandler dbHandler;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);

        txtUserID = (TextView) findViewById(R.id.txtUserID);
        txtUserID.setText(sp.getString(DBConnect.C_USERID, ""));

        editUserID = (EditText) findViewById(R.id.editUserID);
        editUserName = (EditText) findViewById(R.id.editUserName);
        editUserEmail = (EditText) findViewById(R.id.editUserEmail);
        editUserPassword = (EditText) findViewById(R.id.editUserPassword);
        editUser_ID = (EditText) findViewById(R.id.editUser_ID);

        editUserID.setText(sp.getString(DBConnect.C_USERID, ""));
        editUserName.setText(sp.getString(DBConnect.C_USERNA, ""));
        editUserEmail.setText(sp.getString(DBConnect.C_UEMAIL, ""));
        editUserPassword.setText(sp.getString(DBConnect.C_PASSWD, ""));

        dbHandler = new DBHandler(getApplicationContext());
        helper = new Helper(getApplicationContext(), sp);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = editUserID.getText().toString().trim();
                String uname = editUserName.getText().toString();
                String uemail = editUserEmail.getText().toString().trim();
                String upasswd = editUserPassword.getText().toString();

                //Basic Validations
                if (uname.isEmpty()) {
                    helper.altMsg("Please Enter User Name");
                } else if (uemail.isEmpty()) {
                    helper.altMsg("Please Enter Email");
                } else if (!helper.emailValidate(uemail)) {
                    helper.altMsg("Please Enter Valid Email");
                } else if (upasswd.isEmpty()) {
                    helper.altMsg("Please Enter Password");
                } else {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(DBConnect.C_USERNA, uname);
                    updateValues.put(DBConnect.C_UEMAIL, uemail);
                    updateValues.put(DBConnect.C_PASSWD, upasswd);

                    if (dbHandler.updateUser(updateValues, uid)) {
                        helper.altMsg("User Update Successful");

                        helper.putSP(DBConnect.C_USERNA, uname, true);
                        helper.putSP(DBConnect.C_UEMAIL, uemail, true);
                        helper.putSP(DBConnect.C_PASSWD, upasswd, true);

                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();

                    } else {
                        helper.altMsg("Error Update");
                    }
                }

            }
        });

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = editUser_ID.getText().toString().trim();
                if (userid.isEmpty()) {
                    helper.altMsg("Please Enter User ID");
                } else {
                    if (dbHandler.deleteUser(userid)) {
                        helper.altMsg("User Delete Successful");

                        helper.putSP(DBConnect.C_USERID, "", false);
                        helper.putSP(DBConnect.C_USERNA, "", false);
                        helper.putSP(DBConnect.C_UEMAIL, "", false);
                        helper.putSP(DBConnect.C_PASSWD, "", false);

                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();
                    }
                }

            }
        });
    }
}