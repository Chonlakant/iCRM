package software.is.com.myapplication.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import software.is.com.myapplication.Base64;
import software.is.com.myapplication.Data;
import software.is.com.myapplication.R;
import software.is.com.myapplication.adapter.CommentAdapter;

public class CommentHistory extends AppCompatActivity {

    public static final String URL =
            "http://todayissoftware.com/i_community/service_webboard/list_posts.php";
    private ListView commentListView;
    private CommentAdapter mCommentAdapter;


    Button bt_addComment;
    EditText content;
    String user,comment_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_history);

        bt_addComment = (Button) findViewById(R.id.bt_addComment);
        commentListView = (ListView)findViewById(R.id.all_comment);
        content = (EditText)findViewById(R.id.et_commentContent);

        bt_addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DO POST COMMENT", Toast.LENGTH_LONG).show();

                comment_content = content.getText().toString();

                if (comment_content.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                } else {

                    //new postComment().execute();
                    //new getComment().execute(URL);

                    content.setText(null);
                }
            }
        });

        //new getComment().execute(URL);

    }

    // Method GetComment
    private class getComment extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls) {
            String result = "";
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            // Dismiss ProgressBar
            showData(jsonString);
        }

        private void showData(String jsonString) {

            Gson gson = new Gson();
            Data data = gson.fromJson(jsonString, Data.class);


            StringBuilder builder = new StringBuilder();
            builder.setLength(0);

            List<Data.PostEntity> models = data.getPost();

            mCommentAdapter = new CommentAdapter(getApplicationContext(), models);
            commentListView.setAdapter(mCommentAdapter);
        }
    }


    // Method Post Comment
    private class postComment extends AsyncTask <Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("create_by", user));
            nameValuePairs.add(new BasicNameValuePair("comment_content", comment_content));
            nameValuePairs.add(new BasicNameValuePair("create_date", getTime()));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);
                Log.e("response", st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";
        }
    }

    private String getTime() {
        String time = String.valueOf(System.currentTimeMillis());
        return time;
    }
}
