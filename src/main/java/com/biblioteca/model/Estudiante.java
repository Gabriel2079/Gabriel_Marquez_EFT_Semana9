package com.biblioteca.model;

public class Estudiante extends Persona {
    private String curso;

    public Estudiante(int id, String nombre, String rut, String curso, String correo) {
        super(id, nombre, rut, correo);
        this.curso = curso;
    }

    @Override
    public String getTipoPerfil() {
        return "Estudiante - Curso: " + curso;
    }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}