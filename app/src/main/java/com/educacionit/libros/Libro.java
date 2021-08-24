package com.educacionit.libros;

import java.io.Serializable;

public class Libro implements Serializable {

    private Integer id;
    private String nombre;
    private String autor;

    public Libro() {
    }

    public Libro(Integer id, String nombre, String autor) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
