package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener  {
    Button btnCargarMensajes;
    Button btnEnviarMensaje;
    ListView mensajesRecibidos;
    EditText mensaje;
    EditText destino;
    String userOrigen;
    databaseManager manager;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        btnCargarMensajes = (Button)findViewById(R.id.cargarMensajes);
        btnEnviarMensaje = (Button)findViewById(R.id.btnChat);
        mensajesRecibidos = (ListView) findViewById(R.id.MensajeRecibido);
        mensaje = (EditText) findViewById(R.id.MensajeChat);
        destino = (EditText) findViewById(R.id.DestinoChat);
        btnCargarMensajes.setOnClickListener(this);
        btnEnviarMensaje.setOnClickListener(this);
        mensajesRecibidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Toast.makeText(getBaseContext(),"Mantener presionado para eliminar mensaje",Toast.LENGTH_SHORT).show();
            }
        });
        mensajesRecibidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                Cursor c =(Cursor) mensajesRecibidos.getItemAtPosition(index);
                manager.eliminarMensajePorID(c.getString(0));
                cargarMensaje();
                return true;
            }
        });

    }
    public void onClick(View v){
        if(v.getId() == R.id.btnChat) {
            manager = new databaseManager(this);
            Cursor c = manager.cargarCursorUsuarioLogeo();
            c.moveToFirst();
            userOrigen = c.getString(0);
            enviarMensaje(userOrigen, destino.getText().toString(),mensaje.getText().toString(),"hoy","0");
            Toast.makeText(this,userOrigen,Toast.LENGTH_LONG).show();
        }
        else if ( v.getId() == R.id.cargarMensajes){
            cargarMensaje();
        }
    }
    public void enviarMensaje(String userOrigen, String userDestino, String texto, String fecha, String id ){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://udea-chat-kemquiros.c9users.io:8080/api/mensajes/enviar";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("usuarioOrigen",userOrigen);
        params.put("usuarioDestino",userDestino);
        params.put("message",texto);
        params.put("id",id);
        params.put("fecha",fecha);
        manager.añadirMensaje("Dios","hola hijos mios");
        JsonObjectRequest req;
        req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        queue.add(req);
    }
    public void cargarMensaje(){
        manager = new databaseManager(this);
        Cursor c = manager.cargarCursosMensajes();
        if(c==null){
            Toast.makeText(this,"No hay ningun mensaje para cargar",Toast.LENGTH_SHORT).show();
        }
        else {
            String from[] = new String[]{manager.cn_id, manager.cn_mensaje, manager.cn_userEnvia};
            int to[] = new int[]{R.id.idBuzon, R.id.mensajeBuzon, R.id.destinatarioBuzon};
            adapter = new SimpleCursorAdapter(this, R.layout.buzon, c, from, to);
            mensajesRecibidos.setAdapter(adapter);
        }
    }
}
