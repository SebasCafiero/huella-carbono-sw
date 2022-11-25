package ar.edu.utn.frba.dds.interfaces.input.json;

public class MiembroIdentityRequest {
    private String tipoDocumento;
    private Integer documento;

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }
}
