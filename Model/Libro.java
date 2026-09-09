package Model;

import javax.swing.*;

public class Libro {
    private String titulo, autor;
    private int isbn;
    public Libro(String titulo,String autor, int isbn) {
        setTitulo(JOptionPane.showInputDialog("Ingrese el nombre del libro: "));
       setAutor(JOptionPane.showInputDialog("Ingrese el autor del libro: "));
       setIsbn(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el isbn del libro: ")));
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {

    }
}
