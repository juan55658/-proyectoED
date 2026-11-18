package Model;

import Estructuras.Cola;

import javax.swing.*;
import java.time.LocalDate;

public class Libro implements crearEditar {
    private String titulo, autor;
    private int isbn;
    private boolean alquilado;
    private LocalDate fechaDevolucion;
    private Cliente alquiladoA;
    // cada libro tiene su propia fila de espera
    private Cola<Cliente> filaEspera;

    public Libro() {
        setTitulo(JOptionPane.showInputDialog("Ingrese el nombre del libro: "));
        setAutor(JOptionPane.showInputDialog("Ingrese el autor del libro: "));
        setIsbn(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el isbn del libro: ")));
        this.alquilado = false;
        this.fechaDevolucion = null;
        this.alquiladoA = null;
        this.filaEspera = new Cola<>();
    }

    // este constructor no abre ventanas, sirve para hacer pruebas y para la interfaz grafica
    public Libro(String titulo, String autor, int isbn) {
        setTitulo(titulo);
        setAutor(autor);
        setIsbn(isbn);
        this.alquilado = false;
        this.fechaDevolucion = null;
        this.alquiladoA = null;
        this.filaEspera = new Cola<>();
    }

    public String MostrarInformacion() {
        String texto = "Isbn: " + getIsbn() + "\nTitulo: " + getTitulo() + "\nAutor: " + getAutor()
                + "\nAlquilado: " + (alquilado ? "Si" : "No");
        if (alquilado) {
            texto += "\nFecha de devolucion: " + fechaDevolucion;
            if (alquiladoA != null) {
                texto += "\nAlquilado por: " + alquiladoA.getNombre();
            }
        }
        texto += "\nPersonas en fila de espera: " + filaEspera.getTamano();
        return texto;
    }

    public void EditarInformacion() {
        setTitulo(JOptionPane.showInputDialog("Ingrese el nuevo nombre del libro: "));
        setAutor(JOptionPane.showInputDialog("Ingrese el nuevo autor del libro: "));
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
    public Cliente getAlquiladoA() { return alquiladoA; }
    public void setAlquiladoA(Cliente alquiladoA) { this.alquiladoA = alquiladoA; }
    public Cola<Cliente> getFilaEspera() { return filaEspera; }
}
