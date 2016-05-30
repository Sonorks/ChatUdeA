package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class RegistroActivity extends AppCompatActivity {
    EditText usuario;
    EditText ID;
    EditText facultad;
    EditText semestre;
    String token;
    RegistrationIntentService mRegistrationIntentService;
    Button btnRegistro;
    String user;
    databaseManager manager;
    public RegistroActivity() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro);
        usuario = (EditText) findViewById(R.id.UsuarioRegistro);
        ID = (EditText) findViewById(R.id.IDRegistro);
        facultad = (EditText) findViewById(R.id.FacultadRegistro);
        semestre = (EditText) findViewById(R.id.SemestreRegistro);
        btnRegistro = (Button) findViewById(R.id.botonRegistro);
        Token token1 = null;
        token1 = token1.getInstance();
        token = token1.getToken();
        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (v.getId()== R.id.botonRegistro){
                    user= usuario.getText().toString();
                    enviarRegistro(usuario.getText().toString(),ID.getText().toString(),facultad.getText().toString(),semestre.getText().toString(),token);
                    manager.insertarLogeo(usuario.getText().toString());
                    Intent i = new Intent(getBaseContext(),ChatActivity.class);
                    startActivity(i);
                    //System.exit(0);
                }
            }
        });
    }
    public void enviarRegistro(String usuario, String ID, String facultad, String semestre, String token){
        manager = new databaseManager(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://udea-chat-kemquiros.c9users.io:8080/api/registros";
        manager.añadirUsuario(usuario,facultad,semestre);
        manager.insertarLogeo(usuario);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("nombre",usuario);
        params.put("token",token);
        params.put("facultad",facultad);
        params.put("semestre",semestre);
        params.put("id",ID);
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
        Log.d("test","Enviado registro de usuario con token"+token);
    }

}
