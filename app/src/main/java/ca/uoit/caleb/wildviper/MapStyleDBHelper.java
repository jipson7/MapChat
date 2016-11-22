package ca.uoit.caleb.wildviper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by caleb on 2016-11-20.
 */

public class MapStyleDBHelper extends SQLiteOpenHelper {

    private static final String FILENAME = "map_styles.db";

    private static final int VERSION = 1;

    private static final String TABLE_NAME = "MapStyles";

    private static final String CREATE_STATEMENT = "" +
            "CREATE TABLE " + TABLE_NAME + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "styleName VARCHAR(100) NOT NULL," +
            "styleJson TEXT NOT NULL," +
            "isSelected INTEGER NOT NULL DEFAULT 0);";

    private static final String DROP_STATEMENT = "DROP TABLE " + TABLE_NAME + ";";
    private final Context context;

    public MapStyleDBHelper(Context context) {
        super(context, FILENAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
        /**
         * Populate with styles from resource files
         */
        String dayStyle = context.getResources().getString(R.string.map_style_day);
        addMapStyle(db, "Day", dayStyle, 1);
        String nightStyle = context.getResources().getString(R.string.map_style_night);
        addMapStyle(db, "Night", nightStyle, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        onCreate(db);
    }

    private void addMapStyle(SQLiteDatabase db, String styleName, String styleJson, int isSelected) {
        ContentValues values = new ContentValues();
        values.put("styleName", styleName);
        values.put("styleJson", styleJson);
        values.put("isSelected", isSelected);
        db.insert(TABLE_NAME, null, values);
    }

    public String getSelectedStyleJson() {
        SQLiteDatabase db = getReadableDatabase();
        String styleJson = "";
        Cursor cursor = db.rawQuery("SELECT styleJson FROM " + TABLE_NAME + " WHERE isSelected=?", new String[] {1 + ""});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            styleJson = cursor.getString(cursor.getColumnIndex("styleJson"));
        }

        return styleJson;
    }

    public String getSelectedStyleName() {
        SQLiteDatabase db = getReadableDatabase();
        String styleName = "";
        Cursor cursor = db.rawQuery("SELECT styleName FROM " + TABLE_NAME + " WHERE isSelected=?", new String[] {1 + ""});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            styleName = cursor.getString(cursor.getColumnIndex("styleName"));
        }

        return styleName;
    }

    public ArrayList<String> getAllStyles() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> styles = new ArrayList<>();

        String fieldName = "styleName";

        String[] columns = new String[] {fieldName};

        Cursor cursor = db.query(TABLE_NAME, columns, "", new String[] {}, "", "", fieldName);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String style = cursor.getString(cursor.getColumnIndex(fieldName));
            styles.add(style);
            cursor.moveToNext();
        }

        return styles;
    }

    public void setSelectedTheme(String styleName) {
        SQLiteDatabase db = getWritableDatabase();
        String selectedColumn = "isSelected";

        /**
         * Zero out all selected
         */
        ContentValues args = new ContentValues();
        args.put(selectedColumn, 0);
        db.update(TABLE_NAME, args, null, null);

        /**
         * Select new theme
         */
        String where = "styleName=?";
        ContentValues args2 = new ContentValues();
        args2.put(selectedColumn, 1);
        db.update(TABLE_NAME, args2, where, new String[]{styleName});
    }
}
