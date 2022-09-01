package ar.edu.utn.frba.dds.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntidadPersistente {
    @Id
    @GeneratedValue
    private Integer id;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
