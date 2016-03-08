package software.is.com.myapplication.activity;

import android.app.Activity;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import software.is.com.myapplication.AlertDialogManager;
import software.is.com.myapplication.Base64;
import software.is.com.myapplication.ConnectionDetector;
import software.is.com.myapplication.IcrmApp;
import software.is.com.myapplication.MainActivity;
import software.is.com.myapplication.PrefManager;
import software.is.com.myapplication.R;

import static software.is.com.myapplication.CommonUtilities.SENDER_ID;
import static software.is.com.myapplication.CommonUtilities.SERVER_URL;


public class LoginActivity extends Activity {
    Button btn_login;
    TextView link_signup;
    private AQuery aq;
    AlertDialogManager alert = new AlertDialogManager();

    // Internet detector
    ConnectionDetector cd;

    // UI elements
    EditText txtEmail;
    EditText txtPass;
    EditText txtVender;
    PrefManager prefManager;
    String regId;
    Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager = IcrmApp.getPrefManager();
        btn_login = (Button) findViewById(R.id.btn_login);
        link_signup = (TextView) findViewById(R.id.link_signup);
        txtEmail = (EditText) findViewById(R.id.input_email);
        txtPass = (EditText) findViewById(R.id.input_password);
        aq = new AQuery(getApplicationContext());
        cd = new ConnectionDetector(getApplicationContext());
        regId = prefManager.token().getOr("");
        Log.e("vvvvvvvv", regId + "");

        loadingDialog = new Dialog(LoginActivity.this, R.style.FullHeightDialog);
        loadingDialog.setContentView(R.layout.dialog_loading);

        if(prefManager.isLogin().getOr(false) != false){
            Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
        }else{

        }
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(LoginActivity.this, "Internet Connection Error","Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(LoginActivity.this, "Configuration Error!", "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return;
        }


        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                onLoginButtonClick();
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


    private void onLoginButtonClick() {
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();
        Log.e("qqq", pass);
        Log.e("www", pass);
        Log.e("ttt", regId);
        loadingDialog.show();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "กรุณาใส่พาสเวิร์ด", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://todayissoftware.com/i_community/login.php";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", pass);
        params.put("regId", regId);


        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        Log.e("qqqq",json.getString("username"));
        Log.e("ddd", success + "");
        if (success == 0) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "กรอก pass หรือ Password ผิด", Toast.LENGTH_SHORT).show();
        }
        if (success == 1) {
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "เข้าสู่รับบสำเร็จ", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();
            prefManager.isLogin().put(true);
            prefManager.userName().put(json.getString("username"));
            prefManager.vendeName().put(json.getString("vendor"));
            prefManager.commit();

        }
    }

}