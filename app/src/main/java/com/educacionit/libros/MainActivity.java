package com.educacionit.libros;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String USUARIO = "USUARIO";
    private EditText etUsuario;
    private EditText etContrasenia;
    private Button btnIniciarSesion;
    private TextView txtTerminosYCondiciones;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);
        setupUI();
    }

    private void setupUI() {
        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContraseña);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtTerminosYCondiciones = findViewById(R.id.txtTerminosYCondiciones);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String contrasenia = etContrasenia.getText().toString();
                if (usuario.isEmpty() || contrasenia.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completar datos", Toast.LENGTH_SHORT).show();
                } else {
                    guardarSharedPref(usuario);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("USUARIO", usuario);
                    startActivity(intent);
                    finish();
                }
            }
        });
        txtTerminosYCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this, TermsAndConditionsActivity.class)
                );
            }
        });

        cargarSharedPref();
    }

    private void cargarSharedPref() {
        String usuario = preferences.getString(USUARIO, "");
        etUsuario.setText(usuario);
    }

    private void guardarSharedPref(String usuario) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USUARIO, usuario);
        editor.apply();
    }
}