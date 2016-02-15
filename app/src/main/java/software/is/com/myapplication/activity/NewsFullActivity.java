package software.is.com.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import software.is.com.myapplication.R;


public class NewsFullActivity extends AppCompatActivity {
    ImageView imag_content;
    TextView title, txt_content;
    String titleNews;
    String detail;
    int type;
    String imageUrl;
    private Toolbar toolbar;
    String url ;
    String code;
    String vender;
    private AQuery aq;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imag_content = (ImageView) findViewById(R.id.imag_content);
        title = (TextView) findViewById(R.id.title);
        txt_content = (TextView) findViewById(R.id.txt_content);
        button = (Button) findViewById(R.id.button);
        imag_content.setVisibility(View.GONE);
        aq = new AQuery(getApplicationContext());
//        titleNews = getIntent().getStringExtra("title");
//        detail = getIntent().getStringExtra("detail");
        type = getIntent().getIntExtra("type", 0);
//        imageUrl = getIntent().getStringExtra("image");
        code = getIntent().getStringExtra("code");
        vender = getIntent().getStringExtra("vender");



        url = "http://todayissoftware.com/i_community/service/news_details.php?id="+code+"&user="+vender;
       // url = "http://192.168.1.141/i_community/service/news_details.php?id="+code+"&user="+vender;
        Log.e("url", url+"");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("รายละเอียด");
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);

        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MoreActivity.class);
                startActivity(i);
            }
        });
        aq.ajax(url, JSONObject.class, this, "jsonCallback");

    }
    public void jsonCallback(String url, JSONObject json, AjaxStatus status){
        if(json != null){
            try {
                JSONArray ja = json.getJSONArray("post");
                for(int i = 0 ; i < ja.length();i++){
                    Log.e("ddddd",ja.get(i)+"");
                    JSONObject jo = ja.getJSONObject(i);
                    code = jo.optString("code");
                    imageUrl = jo.optString("file_img");
                    type = jo.optInt("status_img");
                    titleNews = jo.optString("title");
                    detail = jo.optString("details");
                    if (type == 0) {
                        title.setText(titleNews);
                        txt_content.setText(detail);
                        imag_content.setVisibility(View.GONE);
                    }if(type ==1 ) {
                        imag_content.setVisibility(View.VISIBLE);
                        title.setText(titleNews);
                        txt_content.setText(detail);
                        Picasso.with(getApplicationContext())
                                .load(imageUrl)
                                .into(imag_content);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}