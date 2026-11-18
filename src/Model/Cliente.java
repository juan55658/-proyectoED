package Model;

import javax.swing.*;

public class Cliente extends Persona implements crearEditar {
    private boolean esEstudiante;

    public Cliente(int id) {
        super(id);
        setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la persona: "));
        setDocumento(JOptionPane.showInputDialog("Ingrese el numero del documento: "));
        setEdad(Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad: ")));
        setTelefono(JOptionPane.showInputDialog("Ingrese el numero de telefono: "));
        setEmail(JOptionPane.showInputDialog("Ingrese su correo: "));
        setEsEstudiante(JOptionPane.showInputDialog("Es estudiante? (si/no): "));
    }

    // este constructor no abre ventanas, lo usa la interfaz grafica
    public Cliente(int id, String nombre, String documento, int edad, String telefono, String email, boolean esEstudiante) {
        super(id);
        setNombre(nombre);
        setDocumento(documento);
        setEdad(edad);
        setTelefono(telefono);
        setEmail(email);
        this.esEstudiante = esEstudiante;
    }

    public String MostrarInformacion() {
        String texto = "Id: " + getId() + "\nNombre: " + getNombre() + "\nDocumento: " + getDocumento() + "\nEdad: " + getEdad() + "\nTelefono: " + getTelefono() + "\nEmail: " + getEmail() + "\nEs estudiante?: " + getEsEstudiante();
        return texto;
    }
    public void EditarInformacion() {
        setNombre(JOptionPane.showInputDialog("Ingrese el nuevo nombre de la persona: "));
        setDocumento(JOptionPane.showInputDialog("Ingrese el nuevo numero del documento: "));
        setEdad(Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva edad: ")));
        setTelefono(JOptionPane.showInputDialog("Ingrese el nuevo numero de telefono: "));
        setEmail(JOptionPane.showInputDialog("Ingrese el nuevo correo: "));
        setEsEstudiante(JOptionPane.showInputDialog("Es estudiante? (si/no): "));
    }

    public String getEsEstudiante() {
        if (this.esEstudiante == true) {
            return "Si";
        }
        return "No";
    }

    public boolean isEstudiante() { return esEstudiante; }

    public void setEsEstudiante(boolean esEstudiante) { this.esEstudiante = esEstudiante; }

    public void setEsEstudiante(String verificacion) {
        if (verificacion.equalsIgnoreCase("si")) {
            this.esEstudiante = true;
        } else if (verificacion.equalsIgnoreCase("no")) {
            this.esEstudiante = false;
        } else {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }


}
