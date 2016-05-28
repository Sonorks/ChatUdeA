package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sonorks on 23/05/16.
 */
public class databaseManager {
    public static final String tableNameMensajes = "mensajes";
    public static final String tableNameUsuarios = "usuarios";
    public static final String tableNameLogin = "login";
    public static final String cn_userLogin = "userLogin";
    public static final String cn_id = "_id";
    public static final String cn_userEnvia = "usuarioEnvia";
    public static final String cn_mensaje = "mensaje";
    public static final String cn_usuario = "origen";
    public static final String cn_facultad = "facultad";
    public static final String cn_semestre = "semestre";

    public static final String CREATE_TABLE_MENSAJE = "create table " + tableNameMensajes + " ("

            + cn_id + " integer primary key autoincrement,"

            + cn_userEnvia + " text not null,"

            + cn_mensaje + " text not null);";

    public static final String CREATE_TABLE_USUARIOS = "create table " + tableNameUsuarios + " ("

            + cn_id + " integer primary key autoincrement,"

            + cn_usuario + " text not null," + cn_facultad + " text not null,"

            + cn_semestre + " text not null);";

    public static final String CREATE_TABLE_LOGIN = "create table "+tableNameLogin+" ("
            +cn_userLogin+ " text not null);";

    private dbHelperUsuarios helperUsuarios;

    private dbHelperMensajes helperMensajes;

    private dbHelperLogin helperLogin;

    public static SQLiteDatabase dbLogin;

    public static SQLiteDatabase dbUsuarios;

    public static SQLiteDatabase dbMensajes;

    public databaseManager(Context context) { //el constructor
        helperUsuarios = new dbHelperUsuarios(context);
        helperMensajes = new dbHelperMensajes(context);
        helperLogin = new dbHelperLogin(context);
        dbUsuarios = helperUsuarios.getWritableDatabase();
        dbMensajes = helperMensajes.getWritableDatabase();
        dbLogin = helperLogin.getWritableDatabase();
    }
    private ContentValues generarContentValuesUsuarios(String usuario, String facultad, String semestre) {

        ContentValues valores = new ContentValues();

        valores.put(cn_usuario, usuario);

        valores.put(cn_facultad, facultad);

        valores.put(cn_semestre, semestre);

        return valores;

    }
    private ContentValues generarContentValuesMensajes(String usuarioEnvia, String mensaje) {

        ContentValues valores = new ContentValues();

        valores.put(cn_userEnvia, usuarioEnvia);

        valores.put(cn_mensaje, mensaje);

        return valores;
    }
    private ContentValues generarContentValuesLogin(String user){
        ContentValues valores = new ContentValues();
        valores.put(cn_userLogin,user);
        return valores;
    }
    public void insertarLogeo(String user) {
        dbLogin.insert(tableNameLogin, null, generarContentValuesLogin(user));
    }
    public void deslogear(String usuario) {
        System.out.println("Elimine a "+usuario);
        dbLogin.delete(tableNameLogin, cn_userLogin + "=?", new String[]{usuario});
    }
    public boolean buscarUsuario(String usuario){
        String[] columnas = new String[]{cn_id,cn_usuario, cn_facultad,cn_semestre};
        Cursor c = dbUsuarios.query(tableNameUsuarios,columnas,cn_usuario+"=?",new String[]{usuario},null,null,null);
        if(c.moveToFirst()){
            do{
                String n = c.getString(1);
                if(n.equals(usuario)){
                    return true;
                }
            }while(c.moveToNext());
            return false;
        }
        else return false;
    }
    public Cursor cargarCursorUsuarioLogeo() {
        String[] columnas2 = new String[]{cn_userLogin};
        return dbLogin.query(tableNameLogin, columnas2, null, null, null, null, null);
    }
    public void añadirUsuario(String usuario, String facultad, String semestre){
        Log.d("test","añadi usuario a la db");
        dbUsuarios.insert(tableNameUsuarios,null,generarContentValuesUsuarios(usuario,facultad,semestre));
    }
    public void añadirMensaje(String userEnvia, String mensaje){
        dbMensajes.insert(tableNameMensajes,null,generarContentValuesMensajes(userEnvia,mensaje));
        Log.d("test","añadi mensaje a la db");
    }
    public Cursor cargarCursorUsuarios(){
        String[] columnas = new String[]{cn_id,cn_usuario, cn_facultad,cn_semestre};
        return dbUsuarios.query(tableNameUsuarios, columnas, null, null, null, null, null);
    }
    public Cursor cargarCursosMensajes(){
        String[] columnas = new String[]{cn_id,cn_userEnvia, cn_mensaje};
        return dbMensajes.query(tableNameMensajes, columnas, null, null, null, null, null);
    }
    public void eliminarMensajePorID(String id){
        dbMensajes.delete(tableNameMensajes, cn_id +"=?",new String[]{id});
    }

}