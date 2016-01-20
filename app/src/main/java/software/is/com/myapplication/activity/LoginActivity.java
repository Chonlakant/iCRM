package software.is.com.myapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import software.is.com.myapplication.AlertDialogManager;
import software.is.com.myapplication.Base64;
import software.is.com.myapplication.ConnectionDetector;
import software.is.com.myapplication.MainActivity;
import software.is.com.myapplication.R;

import static software.is.com.myapplication.CommonUtilities.SENDER_ID;
import static software.is.com.myapplication.CommonUtilities.SERVER_URL;


public class LoginActivity extends Activity {
    Button btn_login;
    TextView link_signup;

    AlertDialogManager alert = new AlertDialogManager();

    // Internet detector
    ConnectionDetector cd;

    // UI elements
    EditText txtName;
    EditText txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        link_signup = (TextView) findViewById(R.id.link_signup);
        txtName = (EditText) findViewById(R.id.input_email);
        txtEmail = (EditText) findViewById(R.id.input_password);


        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(LoginActivity.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return;
        }


        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();

                // Check if user filled the form
                if (name.trim().length() > 0 && email.trim().length() > 0) {
                    // Launch Main Activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    // Registering user on our server
                    // Sending registraiton details to MainActivity
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    startActivity(i);
                    finish();
                } else {
                    // user doen't filled that data
                    // ask him to fill the form
                    alert.showAlertDialog(LoginActivity.this, "Registration Error!", "Please enter your details", false);
                }
            }
        });
        link_signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}