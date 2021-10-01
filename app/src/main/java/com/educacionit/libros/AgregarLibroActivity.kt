package com.educacionit.libros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.educacionit.libros.HomeActivity.LIBRO;

public class AgregarLibroActivity extends AppCompatActivity {
    private EditText etNombreLibro;
    private EditText etAutor;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_libro);

        setupUI();
    }

    private void setupUI() {
        etNombreLibro = findViewById(R.id.etNombreLibro);
        etAutor = findViewById(R.id.etAutor);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarLibro();
            }
        });
    }

    private void guardarLibro() {
        if (datosValidos()) {
            Libro libro = new Libro();
            libro.setNombre(etNombreLibro.getText().toString());
            libro.setAutor(etAutor.getText().toString());
            setResult(
                    Activity.RESULT_OK, new Intent().putExtra(LIBRO, libro)
            );
            finish();
        } else {
            Toast.makeText(AgregarLibroActivity.this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean datosValidos() {
        boolean datosValidos = true;
        if (etNombreLibro.getText().toString().isEmpty() || etAutor.getText().toString().isEmpty()) {
            datosValidos = false;
        }
        return datosValidos;
    }
}