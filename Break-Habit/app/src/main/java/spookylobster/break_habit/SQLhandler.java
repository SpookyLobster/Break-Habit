package spookylobster.break_habit;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.lang.reflect.Method;

        import static android.R.attr.id;
        import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chris on 2017/1/28.
 */

public class SQLhandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "SQL.db";

    public SQLhandler(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists setting");
        db.execSQL("create table setting "+
                "(method varchar(100) primary key, number integer)");
        //set default value for settings
        db.execSQL("insert into setting (method, number) values(\"SetShake\",25)");
        db.execSQL("insert into setting (method, number) values(\"SetPushup\",10)");
        db.execSQL("insert into setting (method, number) values(\"SetTime\",5000)");
        db.execSQL("insert into setting (method, number) values(\"SetStep\",50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS setting");
        //make the table
        onCreate(db);
    }

    // inserting userinput to databaae
    public void insertSetting(int number ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number",number);
        db.insert("setting", null, contentValues);

    }
    public void updateSetting(int number, String method){
        //db.update("setting", contentValues, "_id="+id,null);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE setting SET number="+number+" where method=\""+method+"\"");
        db.close();
    }

    //Getting the userinput number
    public int getData(String method) {
        SQLiteDatabase db = this.getReadableDatabase();
        //System.out.print(method);
        Cursor res;
        //if (method.equals("SetPush")) {
            //res = db.rawQuery("select * from setting where method = \'SetPush\'", null);
        //} else if (method.equals("SetShake")){
        //res = db.rawQuery("select * from setting where method = \"SetShake\"", null);
        //} else {
 //           res = null;
       // }
        res = db.rawQuery("select * from setting where method = \"" + method + "\"", null);

        //Cursor res = db.rawQuery("select * from setting where method = \"SetPush\"", null);
        //res = db.rawQuery("select * from setting", null);
        if(res != null) {
            res.moveToFirst();
            //String i = res.getString(0) + res.getInt(1);
            //res.moveToNext();
            //String j = res.getString(0) + res.getInt(1);
            //return (i + " | " + j);
            return res.getInt(1);
        }
        return -1;
    }
}
