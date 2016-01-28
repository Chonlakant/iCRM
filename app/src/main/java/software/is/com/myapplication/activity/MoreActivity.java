package software.is.com.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import software.is.com.myapplication.MainActivity;
import software.is.com.myapplication.R;

public class MoreActivity extends Activity {
    WebView webWiew1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);
        webWiew1 = (WebView) findViewById(R.id.yourwebview);

        webWiew1.loadUrl("http://mn-community.com/demo/phpmailer/form-demo.php");
        webWiew1.getSettings().setJavaScriptEnabled(true);
        webWiew1.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }
        });

    }
}