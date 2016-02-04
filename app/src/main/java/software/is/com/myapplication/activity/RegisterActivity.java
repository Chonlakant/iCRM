package software.is.com.myapplication.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import software.is.com.myapplication.AlertDialogManager;
import software.is.com.myapplication.ConnectionDetector;
import software.is.com.myapplication.IcrmApp;
import software.is.com.myapplication.MainActivity;
import software.is.com.myapplication.PrefManager;
import software.is.com.myapplication.R;
import software.is.com.myapplication.ServerUtilities;
import software.is.com.myapplication.WakeLocker;

import static software.is.com.myapplication.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static software.is.com.myapplication.CommonUtilities.EXTRA_MESSAGE;
import static software.is.com.myapplication.CommonUtilities.SENDER_ID;

public class RegisterActivity extends Activity {
    Button btn_signup;

    PrefManager pref;
    EditText input_name;
    EditText input_email;
    EditText input_password;
    EditText input_invite;

    String username;
    String email;
    String password;
    String invite;

    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connection detector
    ConnectionDetector cd;
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        input_name = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_invite = (EditText) findViewById(R.id.input_invite);
        pref = IcrmApp.getPrefManager();
        btn_signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfile();

            }
        });
    }

    private void uploadProfile() {

        email = input_email.getText().toString();
        username = input_name.getText().toString();
        password = input_password.getText().toString();
        invite = input_invite.getText().toString();


        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(RegisterActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);


        registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        regId = GCMRegistrar.getRegistrationId(this);

        Log.e("aaaa", regId + "");

        pref.email().put(email);
        pref.passWord().put(password);
        pref.name().put(username);
        pref.inVite().put(invite);
        pref.token().put(regId);
        pref.isLogin().put(true);
        pref.commit();

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                Toast.makeText(getApplicationContext(), "เข้าสู่ระบบ", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                Toast.makeText(getApplicationContext(), "กรุณาต่ออินเทอร์เน็ต", Toast.LENGTH_SHORT).show();
            }

        }

        final Context context = this;
        mRegisterTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                // Register on our server
                // On server creates a new user
                ServerUtilities.register(context, username, email, regId);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                mRegisterTask = null;
            }

        };
        mRegisterTask.execute(null, null, null);


        Charset chars = Charset.forName("UTF-8");
        String url = "http://192.168.1.141/i_community/register_gcm.php";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("regId", regId);


        AQuery aq = new AQuery(getApplication());
        aq.ajax(url, params, JSONObject.class, this, "registerCb");
    }

    public void registerCb(String url, JSONObject json, AjaxStatus status)
            throws JSONException {
        Log.e("return", json.toString(4));

        int success = json.getInt("success");
        Log.e("ddd", success + "");
        if (success == 0) {
//            Toast.makeText(getApplicationContext(), "กรอก pass หรือ Password ผิด", Toast.LENGTH_SHORT).show();
        }
        if (success == 1) {
            Toast.makeText(getApplicationContext(), "เข้าสู่รับบสำเร็จ", Toast.LENGTH_SHORT).show();
            Intent intentMain = new Intent(getApplicationContext().getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();
            pref.isLogin().put(true);
            pref.commit();

        }


    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);

            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message

            //Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();


            // Releasing wake lock
            WakeLocker.release();
        }
    };


    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("Receiver Error", e.getMessage());
        }
        super.onDestroy();
    }
}