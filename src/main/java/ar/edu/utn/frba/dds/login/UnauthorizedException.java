package ar.edu.utn.frba.dds.login;

public class UnauthorizedException extends Throwable{

    private Integer codigo;

    public UnauthorizedException() {
        this.codigo = 401;
    }

    public String getMessage() {
        return codigo + " Unauthorized";
    }

    public Integer getCodigo(){ return 401; }

}
