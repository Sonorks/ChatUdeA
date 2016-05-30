package co.edu.udea.gcm.gp9.gcmcompumovil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{


    // UI references.
    private AutoCompleteTextView Usuario;
    private Button RegistrarseButton;
    private Button mSignInButton;
    databaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario = (AutoCompleteTextView) findViewById(R.id.usuarioLogin);
        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        RegistrarseButton = (Button) findViewById(R.id.registrarseLogin);
        manager = new databaseManager(this);
        mSignInButton.setOnClickListener(this);
        RegistrarseButton.setOnClickListener(this);
    }
    public void onClick(View v){
        if ( v.getId() == R.id.email_sign_in_button){
            String user = Usuario.getText().toString();
            Boolean concuerda = manager.buscarUsuario(user);
            if(concuerda){
                Intent i = new Intent(this,ChatActivity.class);
                startActivity(i);
                //System.exit(0);
            }
            else{
                Toast.makeText(this,"El usuario no se encuentra",Toast.LENGTH_SHORT).show();
            }

        }
        else if (v.getId() == R.id.registrarseLogin){
            Log.d("test","al boton de registro en el login");
            Intent i = new Intent(this,RegistroActivity.class);
            startActivity(i);
            //System.exit(0);
        }
    }


}

