package Controller;

import Estructuras.ArbolLibros;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Model.Cliente;
import Model.Libro;

import java.time.LocalDate;
import java.util.ArrayList;

// Aqui esta la logica de la biblioteca SIN ventanas (nada de JOptionPane).
// La interfaz grafica solo pide los datos y llama estos metodos.
// Cuando algo sale mal se lanza una IllegalArgumentException con el mensaje para el usuario.
public class BibliotecaServicio {
    private ListaEnlazada<Cliente> clientes;
    private ArbolLibros libros;
    private Pila<String> historial;
    private int contadorId;

    public BibliotecaServicio() {
        clientes = new ListaEnlazada<>();
        libros = new ArbolLibros();
        historial = new Pila<>();
        contadorId = 0;
    }

    // ---------------- LIBROS ----------------

    public void agregarLibro(String titulo, String autor, int isbn) {
        validarLibro(titulo, autor, isbn);
        Libro libro = new Libro(titulo.trim(), autor.trim(), isbn);
        if (!libros.insertar(libro)) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN");
        }
    }

    public void editarLibro(Libro libro, String titulo, String autor, int isbnNuevo) {
        validarLibro(titulo, autor, isbnNuevo);
        int isbnViejo = libro.getIsbn();
        if (isbnNuevo != isbnViejo) {
            if (libros.buscar(isbnNuevo) != null) {
                throw new IllegalArgumentException("Ya existe otro libro con ese ISBN");
            }
            // si cambia el isbn hay que sacarlo del arbol y volver a meterlo para que siga ordenado
            libros.eliminar(isbnViejo);
            libro.setIsbn(isbnNuevo);
            libros.insertar(libro);
        }
        libro.setTitulo(titulo.trim());
        libro.setAutor(autor.trim());
    }

    public void eliminarLibro(Libro libro) {
        if (libro.isAlquilado()) {
            throw new IllegalArgumentException("No se puede eliminar un libro que está alquilado");
        }
        libros.eliminar(libro.getIsbn());
    }

    public ArrayList<Libro> getLibros() {
        return libros.aLista();
    }

    public int cantidadLibros() { return libros.contar(); }
    public int alturaArbol() { return libros.altura(); }

    private void validarLibro(String titulo, String autor, int isbn) {
        // se revisa en el mismo orden en que aparecen los campos en la ventana
        if (isbn <= 0) {
            throw new IllegalArgumentException("El ISBN debe ser un número positivo");
        }
        if (titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
    }

    // ---------------- CLIENTES ----------------

    public void agregarCliente(String nombre, String documento, int edad, String telefono, String email, boolean esEstudiante) {
        validarCliente(null, nombre, documento, edad, email);
        Cliente cliente = new Cliente(contadorId, nombre.trim(), documento.trim(), edad, telefono.trim(), email.trim(), esEstudiante);
        clientes.agregar(cliente);
        contadorId++;
    }

    public void editarCliente(Cliente cliente, String nombre, String documento, int edad, String telefono, String email, boolean esEstudiante) {
        validarCliente(cliente, nombre, documento, edad, email);
        cliente.setNombre(nombre.trim());
        cliente.setDocumento(documento.trim());
        cliente.setEdad(edad);
        cliente.setTelefono(telefono.trim());
        cliente.setEmail(email.trim());
        cliente.setEsEstudiante(esEstudiante);
    }

    public void eliminarCliente(Cliente cliente) {
        for (Libro libro : libros.aLista()) {
            if (libro.getAlquiladoA() == cliente) {
                throw new IllegalArgumentException("No se puede eliminar: el cliente tiene alquilado \"" + libro.getTitulo() + "\"");
            }
        }
        // lo sacamos de las filas de espera donde este
        for (Libro libro : libros.aLista()) {
            libro.getFilaEspera().eliminar(cliente);
        }
        clientes.eliminar(cliente);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes.aLista();
    }

    private void validarCliente(Cliente actual, String nombre, String documento, int edad, String email) {
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento no puede estar vacío");
        }
        if (edad < 1 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 1 y 120");
        }
        if (!email.trim().isEmpty() && !email.contains("@")) {
            throw new IllegalArgumentException("El correo no es válido (le falta el @)");
        }
        for (Cliente c : clientes.aLista()) {
            if (c != actual && c.getDocumento().equals(documento.trim())) {
                throw new IllegalArgumentException("Ya existe un cliente con ese documento");
            }
        }
    }

    // ---------------- PRESTAMOS ----------------

    // devuelve true si lo alquilo, false si el libro esta ocupado (la ventana ofrece la fila de espera)
    public boolean alquilar(Cliente cliente, Libro libro) {
        if (!cliente.isEstudiante()) {
            throw new IllegalArgumentException("Solo los estudiantes pueden alquilar libros");
        }
        if (libro.isAlquilado()) {
            if (libro.getAlquiladoA() == cliente) {
                throw new IllegalArgumentException("Este cliente ya tiene este libro alquilado");
            }
            return false;
        }
        prestar(libro, cliente);
        return true;
    }

    // mete al cliente en la cola del libro y devuelve su posicion
    public int unirseFila(Cliente cliente, Libro libro) {
        if (libro.getFilaEspera().contiene(cliente)) {
            throw new IllegalArgumentException("Este cliente ya está en la fila de espera");
        }
        libro.getFilaEspera().encolar(cliente);
        return libro.getFilaEspera().getTamano();
    }

    // devuelve el mensaje que se le muestra al usuario
    public String devolver(Libro libro) {
        if (!libro.isAlquilado()) {
            throw new IllegalArgumentException("Este libro no está alquilado");
        }

        LocalDate hoy = LocalDate.now();
        String mensaje = "Libro devuelto correctamente.";
        String registro = libro.getTitulo() + " (ISBN " + libro.getIsbn() + ") - devuelto por "
                + libro.getAlquiladoA().getNombre() + " el " + hoy;
        if (hoy.isAfter(libro.getFechaDevolucion())) {
            mensaje = "Devolución tardía. La fecha límite era " + libro.getFechaDevolucion() + ".";
            registro += " (tardía)";
        }
        historial.apilar(registro);

        libro.setAlquilado(false);
        libro.setFechaDevolucion(null);
        libro.setAlquiladoA(null);

        // el libro pasa al primero de la fila (si el que sigue ya no es estudiante se salta)
        Cliente siguiente = FilaEsperaV2.atenderSiguiente(libro);
        while (siguiente != null && !siguiente.isEstudiante()) {
            siguiente = FilaEsperaV2.atenderSiguiente(libro);
        }
        if (siguiente != null) {
            prestar(libro, siguiente);
            mensaje += "\nEl libro pasó a " + siguiente.getNombre() + ", que era el primero en la fila. Debe devolverlo el "
                    + libro.getFechaDevolucion() + ".";
        }
        return mensaje;
    }

    // la lista empieza por la devolucion mas reciente (el tope de la pila)
    public ArrayList<String> getHistorial() {
        return historial.aLista();
    }

    // deja el libro alquilado a un cliente por 15 dias
    private void prestar(Libro libro, Cliente cliente) {
        libro.setAlquilado(true);
        libro.setAlquiladoA(cliente);
        libro.setFechaDevolucion(LocalDate.now().plusDays(15));
    }
}
