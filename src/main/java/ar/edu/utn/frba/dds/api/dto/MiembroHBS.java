package ar.edu.utn.frba.dds.api.dto;

public class MiembroHBS {
    private String nombre;
    private String apellido;
    private String tipoDeDocumento;
    private Integer nroDocumento;
    private Integer id;

    public MiembroHBS() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}


