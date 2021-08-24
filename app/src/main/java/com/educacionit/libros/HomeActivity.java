package com.educacionit.libros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String LIBRO = "Libro";
    private Toolbar toolbar;
    private RecyclerView rvLibros;
    private LibrosAdapter adapter;
    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Libro nuevoLibro = (Libro) result.getData().getSerializableExtra(LIBRO);
                        if (nuevoLibro != null) {
                            agregarNuevoLibroAdapter(nuevoLibro);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();
        saludarUsuario();
        setupAdapter();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis Libros");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_agregar) {
            goToAgregarLibro();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToAgregarLibro() {
        startForResult.launch(
                new Intent(HomeActivity.this, AgregarLibroActivity.class)
        );
    }

    private void setupAdapter() {
        rvLibros = findViewById(R.id.rvLibros);
        adapter = new LibrosAdapter(new LibrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Libro libro) {
                Toast.makeText(HomeActivity.this, libro.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
        rvLibros.setAdapter(adapter);
        adapter.setLibros(getLibros());
    }

    private List<Libro> getLibros() {
        return new ArrayList<Libro>() {{
            add(new Libro(1, "Harry Potter", "J.K. Rowling"));
            add(new Libro(2, "Game of Thrones", "George Martin"));
            add(new Libro(3, "Maze Runner", "James Dashner"));
        }};
    }

    private void agregarNuevoLibroAdapter(Libro nuevoLibro) {
        List<Libro> libros = adapter.getLibros();
        libros.add(nuevoLibro);
        adapter.setLibros(libros);
    }

    private void saludarUsuario() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String usuario = bundle.getString("USUARIO");
            Toast.makeText(HomeActivity.this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
        }
    }
}