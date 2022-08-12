package ar.edu.utn.frba.dds.entities.mediciones;

public class ArchivoSinExtensionException extends Throwable {

    private String nombreArchivo;
    public ArchivoSinExtensionException(String nombre) {
        super(nombre);
        this.nombreArchivo = nombre;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getMessage() {
        return "Nombre de archivo inválido: " + nombreArchivo;
    }

}
