package com.educacionit.libros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasenia;
    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContraseña);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String contrasenia = etContrasenia.getText().toString();
                if (usuario.isEmpty() || contrasenia.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completar datos", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("USUARIO", usuario);
                    startActivity(intent);
                }
            }
        });
    }
}