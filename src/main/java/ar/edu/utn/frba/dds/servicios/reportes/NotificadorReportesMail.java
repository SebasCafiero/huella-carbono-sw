package ar.edu.utn.frba.dds.servicios.reportes;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.mediciones.Reporte;
import ar.edu.utn.frba.dds.personas.AgenteSectorial;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class NotificadorReportesMail implements NotificadorReportes {
    private Reporte reporte;
    private AgenteSectorial agente;

    @Override
    public void notificarReporte() {
        String asunto = "Reporte periódico de consumo de Huella de Carbono";

        String saludo = "Hola:\nSe informan por este medio los resultados del informe periódico "
                + "respecto del consumo de Huella de Carbono.";
        String ubicacion = "Este informe se refiere al sector geográfico de "
                + this.reporte.getArea().getNombre() + ".";
        String despedida = "Muchas gracias.";

        String consumo = "";
        for(Organizacion organizacion : this.reporte.getHcOrganizaciones().keySet()) {
            consumo += "El consumo correspondiente a la organización "
                    + organizacion.getRazonSocial() + " es "
                    + this.reporte.getHcOrganizaciones().get(organizacion) + ". Esto corresponde al "
                    + 100 * this.reporte.getHcOrganizaciones().get(organizacion) / this.reporte.getHcTotal()
                    + "% de todo el sector.\n";
        }
        consumo += "\nFinalmente, el consumo total del sector es " + this.reporte.getHcTotal().toString() + ".";

        String cuerpo = saludo + "\n" + ubicacion + "\n" + consumo + "\n" + despedida;

        System.out.println(reporte);
        System.out.println(reporte.getOrganizaciones().toString() + reporte.getOrganizaciones().size());

        List<String> destinatarios = this.reporte.getOrganizaciones().stream()
                .flatMap(org -> org.getContactosMail().stream()).collect(Collectors.toList());

        System.out.println(destinatarios);

        String remitente = agente.getContactoMail().getDireccionEMail();
        String contra = agente.getContactoMail().getPasssword();

        System.out.println("Remitente: " + remitente + ". Contra:" + contra);

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

            for(String destinatario : destinatarios) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
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

    @Override
    public NotificadorReportes setInformacionReporte(Reporte reporte) {
        this.reporte = reporte;
        return this;
    }

    @Override
    public NotificadorReportes setAgente(AgenteSectorial agente) {
        this.agente = agente;
        return this;
    }
}
