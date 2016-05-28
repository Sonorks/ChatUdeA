package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sonorks on 23/05/16.
 */
public class dbHelperUsuarios extends SQLiteOpenHelper {

    public dbHelperUsuarios(Context context) {
        super(context, db_name, null, db_scheme_version);
    }

    private static final String db_name= "usuarios";
    private static int db_scheme_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(databaseManager.CREATE_TABLE_USUARIOS);
        Log.d("test","creada la tabla de usuarios");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
