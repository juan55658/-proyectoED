package Model;

public class Persona {
    protected String nombre, documento, telefono, email;
    protected int Id, edad;

    public Persona(int Id) {
        setId(Id + 1);
    }


    public int getId() {return Id;}
    public String getNombre() {return nombre;}
    public String getDocumento() {return documento;}
    public int getEdad() {return edad;}
    public String getTelefono() {return telefono;}
    public String getEmail() {return email;}

    public void setId(int Id) {this.Id = Id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setDocumento(String Documento) {this.documento = Documento;}
    public void setEdad(int Edad) {this.edad = Edad;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public void setEmail(String email) {this.email = email;}
}
