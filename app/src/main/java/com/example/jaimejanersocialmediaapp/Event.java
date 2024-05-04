package com.example.jaimejanersocialmediaapp;

public class Event {
    private String titulo, descripcion, duenio;

    public Event() {
    }

    public Event(String titulo, String descripcion, String duenio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.duenio = duenio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDuenio() { return duenio; }

    public void setDuenio(String duenio) { this.duenio = duenio; }
}
