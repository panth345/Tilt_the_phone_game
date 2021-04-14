package com.example.tilt_the_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateprofileMainActivity extends AppCompatActivity {
Button button_Edit;
    EditText etEmail,etAddress,etPassword,etConPassword,etUsername;
    String password,conpassword,Email,Address, name, id;
    DatabaseHelper db = new DatabaseHelper(this);
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile_main);
        getSupportActionBar().hide();
        button_Edit = findViewById(R.id.appCompatButtonRegister);
        etEmail = (EditText) findViewById(R.id.et_Email);
        etAddress = (EditText) findViewById(R.id.et_Address);
        etPassword = (EditText) findViewById(R.id.et_Password);
        etConPassword = (EditText) findViewById(R.id.et_ConfPassword);
        etUsername = (EditText) findViewById(R.id.et_Name);
        String emailfromIntent = getIntent().getStringExtra("EMAIL");
        etEmail.setText(emailfromIntent);
        Intent i = getIntent();
       // String email = i.getStringExtra("email");
       // etEmail.setText(email);
       // String address = i.getStringExtra("address");
       // etAddress.setText(address);
       // String username = i.getStringExtra("username");
      //  etUsername.setText(username);

        Cursor result = db.fetch(emailfromIntent);

        while(result.moveToNext()){
            etUsername.setText(result.getString(1));
            etAddress.setText(result.getString(4));
            etEmail.setText(result.getString(2));
            etPassword.setText(result.getString(3));
            id = result.getString(0);
        }
        button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = etPassword.getText().toString();
                conpassword = etConPassword.getText().toString();
                Email = etEmail.getText().toString();
                Address = etAddress.getText().toString();
                name = etUsername.getText().toString();


                if (!(password.equals(conpassword))) {
                    Toast.makeText(getBaseContext(), "Passwords are not matching", Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                    etConPassword.setText("");
                    etEmail.setText("");
                    etAddress.setText("");
                } else if (etPassword.length() == 0 || etConPassword.length() == 0 || etEmail.length() == 0 || etAddress.length() == 0) {
                    etPassword.setError("Please complete all information");
                    etConPassword.setError("Please complete all information");
                    etEmail.setError("Please complete all information");
                    etAddress.setError("Please complete all information");
                } else if (etPassword.length() < 2) {
                    etPassword.requestFocus();
                    etPassword.setError("Password at least 6 characters");
                    etPassword.setText("");
                    etConPassword.setText("");
                    etEmail.setText("");
                    etAddress.setText("");
                } else {
                   /* user.setName(etUsername.getText().toString().trim());
                    user.setEmail(etEmail.getText().toString().trim());
                    user.setAddress(etAddress.getText().toString());
                    user.setPassword(etPassword.getText().toString().trim());*/

                    db.update(id, name, Email,password, Address);
                    Log.d("values", String.valueOf(user));
                   // databaseHelper.addUser(user);


                   // boolean isUpdate = db.updateEmployee(user);

                        Toast.makeText(getBaseContext(), "Update Success", Toast.LENGTH_LONG).show();


                }


            }
        });
    }
}