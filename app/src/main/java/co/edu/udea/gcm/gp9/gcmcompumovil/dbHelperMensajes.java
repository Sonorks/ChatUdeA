package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sonorks on 23/05/16.
 */
public class dbHelperMensajes extends SQLiteOpenHelper {
    public dbHelperMensajes(Context context) {
        super(context, db_name, null, db_scheme_version);
    }

    private static final String db_name= "mensajes";
    private static int db_scheme_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(databaseManager.CREATE_TABLE_MENSAJE);
        Log.d("test","creada la tabla de mensajes");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
