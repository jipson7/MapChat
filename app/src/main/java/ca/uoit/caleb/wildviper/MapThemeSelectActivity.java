package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class MapThemeSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_theme_select);

        int currentTheme = MapData.getMapStyle(this);
        RadioButton currentSelected = null;
        switch(currentTheme) {
            case R.raw.map_style_day:
                currentSelected = (RadioButton) findViewById(R.id.radio_theme_day);
                break;
            case R.raw.map_style_night:
                currentSelected = (RadioButton) findViewById(R.id.radio_theme_night);
                break;
        }

        if (currentSelected != null) {
            currentSelected.setChecked(true);
        }
    }

    public void onThemeSelected(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_theme_day:
                if (checked)
                    returnTheme("DAY");
                    break;
            case R.id.radio_theme_night:
                if (checked)
                    returnTheme("NIGHT");
                    break;
        }
    }

    private void returnTheme(String theme) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("mapStyle", theme);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
