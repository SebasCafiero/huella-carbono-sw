package ar.edu.utn.frba.dds.repositories.dataInicial.memory;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataAgenteSectorial {
    private static List<AgenteSectorial> agentesSectoriales = new ArrayList<>();

    public static <T> List<T> getList() {
        if (agentesSectoriales.size() == 0) {

            AgenteSectorial a1 = new AgenteSectorial();
            a1.setMail(new ContactoMail("mailDePrueba@gmail.com", "password"));
            a1.setTelefono(new ContactoTelefono("4281-8129"));

            AgenteSectorial a2 = new AgenteSectorial();
            a2.setMail(new ContactoMail("mailDePrueba2@gmail.com", "password2.0"));
            a2.setTelefono(new ContactoTelefono("4290-1234"));

            addAll(a1,a2);
        }
        return (List<T>) agentesSectoriales;
    }
    private static void addAll(AgenteSectorial ... agentesSectoriales) {Collections.addAll(DataAgenteSectorial.agentesSectoriales,agentesSectoriales);}
}
