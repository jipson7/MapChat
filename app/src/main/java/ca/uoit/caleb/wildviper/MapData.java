package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by caleb on 2016-11-15.
 */

public class MapData {


    //Preferences Name
    private static final String MAP_PREFERENCES = "MAP_PREFERENCES";

    private static final String MAP_STYLE_KEY = "mapStyle";


    public static void setMapStyle(String mapStyle, Context context) {
        SharedPreferences.Editor editor
                = context.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(MAP_STYLE_KEY, mapStyle);
        editor.commit();
    }

    public static int getMapStyle(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE);
        String mapStyle =  prefs.getString(MAP_STYLE_KEY, "");

        switch (mapStyle) {
            case "DAY": {
                return R.raw.map_style_day;
            }
            case "NIGHT": {
                return R.raw.map_style_night;
            }
            default:
                return R.raw.map_style_day;
        }
    }

}
