package com.biblioteca.model;

public class Usuario extends Persona {
    private String password;
    private String rol; // "BIBLIOTECARIO" o "ESTUDIANTE"

    public Usuario(int id, String nombre, String rut, String correo, String password, String rol) {
        super(id, nombre, rut, correo);
        this.password = password;
        this.rol = rol;
    }

    @Override
    public String getTipoPerfil() { return "Usuario del Sistema: " + rol; }

    public String getPassword() { return password; }
    public String getRol() { return rol; }
}