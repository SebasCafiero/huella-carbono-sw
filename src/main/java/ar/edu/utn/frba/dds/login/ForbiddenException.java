package ar.edu.utn.frba.dds.login;

public class ForbiddenException extends Throwable{

    private Integer codigo;

    public ForbiddenException() {
        this.codigo = 403;
    }

    public String getMessage() {
        return codigo + " Forbidden";
    }

    public Integer getCodigo(){ return 401; }
}
