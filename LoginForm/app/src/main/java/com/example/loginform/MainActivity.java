package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText editUserName, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = editUserName.getText().toString().trim();
                String paswd = editPassword.getText().toString();

                if (uname.equals("")) {
                    message("Please Enter User Name");
                } else if (paswd.equals("")) {
                    message("Please Enter Password");
                } else {
                    if (uname.equals("admin") && paswd.equals("admin")) {
                        message("Login Successfully");
                        //From this call Dashboard Or Main Menu

                    } else {
                        message("Login Fail");
                    }
                }

            }
        });

    }

    private void message(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}