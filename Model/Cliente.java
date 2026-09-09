package Model;

import javax.swing.*;

public class Cliente extends Persona {
    private boolean esEstudiante;

    public Cliente(int id,String nombre, String documento,
                   int edad, String telefono, String email,Boolean esEstudiante) {
        super(id);
        setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la persona: "));
        setDocumento(JOptionPane.showInputDialog("Ingrese el numero del documento: "));
        setEdad(Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad: ")));
        setTelefono(JOptionPane.showInputDialog("Ingrese el numero de telefono: "));
        setEmail(JOptionPane.showInputDialog("Ingrese su correo: "));
        setEsEstudiante(JOptionPane.showInputDialog("Es estudiante? (si/no): "));
    }

    public void MostrarInformacion() {
        JOptionPane.showMessageDialog(null, "Id: " + getId() + "\nNombre: " + getNombre() + "\nDocuemnto: " + getDocumento() + "\nEdad: " + getEdad() + "\nTelefono: " + getTelefono() + "\nEmail: " + getEmail() + "Es estudiante?: " + getEsEstudiante());
    }
    public void EditarInformacion() {

    }

    public String getEsEstudiante() {
        if (this.esEstudiante == true) {
            return "Si";
        }
        return "No";
    }

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
