package ar.edu.utn.frba.dds.servicios.reportes;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class NotificadorReportesMail implements NotificadorReportes {
    private ContactoMail contactoSistema;

    @Override
    public void notificarReporte(AgenteSectorial agente, ReporteAgente reporte) {
        String asunto = "ReporteAgente periódico de consumo de Huella de Carbono";

        String saludo = "Hola:\nSe informan por este medio los resultados del informe periódico "
                + "respecto del consumo de Huella de Carbono.";
        String ubicacion = "Este informe corresponde al sector geográfico de "
                + reporte.getArea().getNombre() + ".";
        String despedida = "Muchas gracias.";

        String consumo = "";
        for(Organizacion organizacion : reporte.getHcOrganizaciones().keySet()) {
            consumo += "El consumo correspondiente a la organización "
                    + organizacion.getRazonSocial() + " es "
                    + reporte.getHcOrganizaciones().get(organizacion) + ". Esto corresponde al "
                    + 100 * reporte.getHcOrganizaciones().get(organizacion) / reporte.getHcTotal()
                    + "% de todo el sector.\n";
        }
        consumo += "\nFinalmente, el consumo total del sector territorial es " + reporte.getHcTotal().toString() + ".";

        String cuerpo = saludo + "\n" + ubicacion + "\n" + consumo + "\n" + despedida;

        System.out.println(reporte);
        System.out.println(reporte.getOrganizaciones().toString() + reporte.getOrganizaciones().size());

        List<ContactoMail> destinatarios = reporte.getOrganizaciones().stream()
                .flatMap(org -> org.getContactosMail().stream()).collect(Collectors.toList());

        System.out.println(destinatarios);

        String remitente = agente.getMail().getDireccion();
        String contra = agente.getMail().getPassword();

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", contra);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));

            for(ContactoMail destinatario : destinatarios) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario.getDireccion()));
            }

            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, contra);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

    private ContactoMail getContactoSistema() {
        if(this.contactoSistema != null)
            return this.contactoSistema;

        ContactoMail contacto;
        try {
            Properties propiedades = new Properties();
            FileReader file = new FileReader("resources/aplication.properties");
            propiedades.load(file);
            String direccion = propiedades.getProperty("contacto.mail.direccion");
            String password = propiedades.getProperty("contacto.mail.password");
            file.close();
            contacto = new ContactoMail(direccion, password);
        } catch (IOException e) {
            throw new RuntimeException("El archivo properties no existe");
        }
        return contacto;
    }
}
