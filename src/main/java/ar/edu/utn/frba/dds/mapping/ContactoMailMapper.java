package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.mihuella.dto.ContactoMailJSONDTO;

public class ContactoMailMapper {
    public static ContactoMail toEntity(ContactoMailJSONDTO contactoMailDTO) {
        ContactoMail contactoMail = new ContactoMail(
                contactoMailDTO.direccionesEMail,
                contactoMailDTO.password
        );
        return contactoMail;
    }
}