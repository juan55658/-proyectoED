package Model;

import javax.swing.*;
import java.time.LocalDate;

public class Libro implements crearEditar {
    private String titulo, autor;
    private int isbn;
    private boolean alquilado;
    private LocalDate fechaDevolucion;

    public Libro() {
        setTitulo(JOptionPane.showInputDialog("Ingrese el nombre del libro: "));
        setAutor(JOptionPane.showInputDialog("Ingrese el autor del libro: "));
        setIsbn(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el isbn del libro: ")));
        this.alquilado = false;
        this.fechaDevolucion = null;
    }

    public String MostrarInformacion() {
        String texto = "Isbn: " + getIsbn() + "\nTitulo: " + getTitulo() + "\nAutor: " + getAutor()
                + "\nAlquilado: " + (alquilado ? "Si" : "No")
                + (alquilado ? "\nFecha de devolucion: " + fechaDevolucion : "");
        return texto;
    }

    public void EditarInformacion() {
        setTitulo(JOptionPane.showInputDialog("Ingrese el nuevo nombre del libro: "));
        setAutor(JOptionPane.showInputDialog("Ingrese el muevo autor del libro: "));
        setIsbn(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo isbn del libro: ")));
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }

    public boolean isAlquilado() { return alquilado; }
    public void setAlquilado(boolean alquilado) { this.alquilado = alquilado; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}