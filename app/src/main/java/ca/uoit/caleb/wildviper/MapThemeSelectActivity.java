package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MapThemeSelectActivity extends Activity implements View.OnClickListener {

    MapStyleDBHelper mMapStyleDBHelper = new MapStyleDBHelper(this);

    private ArrayList<String> mStyles;
    private String mCurrentStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_theme_select);
        setTitle("Select a Theme");
        mStyles = mMapStyleDBHelper.getAllStyles();
        mCurrentStyle = mMapStyleDBHelper.getSelectedStyleName();
        setRadioButtons();
    }

    private void setRadioButtons() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.style_radio_select);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < mStyles.size(); i++) {
            String style = mStyles.get(i);
            RadioButton styleRadio = new RadioButton(this);
            if (mCurrentStyle.equals(style)) {
                styleRadio.setChecked(true);
            }
            styleRadio.setId(i);
            styleRadio.setText(style);
            styleRadio.setOnClickListener(this);
            radioGroup.addView(styleRadio);
        }
    }

    @Override
    public void onClick(View view) {
        RadioButton btn = (RadioButton) view;
        String style = btn.getText().toString();
        mMapStyleDBHelper.setSelectedTheme(style);
        finish();
    }
}
