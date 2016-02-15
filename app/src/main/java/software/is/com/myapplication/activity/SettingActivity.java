package software.is.com.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;

import software.is.com.myapplication.IcrmApp;
import software.is.com.myapplication.MainActivity;
import software.is.com.myapplication.PrefManager;
import software.is.com.myapplication.R;

public class SettingActivity extends Activity {

    Button save;
    PrefManager prefManager;
    EditText editText;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = IcrmApp.getPrefManager();
        setContentView(R.layout.activity_color_picker);
        save = (Button) findViewById(R.id.save);
        editText = (EditText) findViewById(R.id.editText);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editText.getText().toString();
                prefManager.title().put(title);
                prefManager.commit();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}
