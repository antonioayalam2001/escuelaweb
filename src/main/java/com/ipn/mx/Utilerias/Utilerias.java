package com.ipn.mx.Utilerias;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Utilerias {

    public void enviarMail(String correoDestinatario, String asunto, String textoCorreo){
        //Propiedades de la conexion
        try{
            Properties props = new Properties();
            props.setProperty("mail.smtp.host","smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable","true");
            props.setProperty("mail.smtp.port","587");

            Session session = Session.getDefaultInstance(props);

            //Inicizalizar la sesión;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("antonioayalam2001@gmail.com"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(correoDestinatario));
            message.addRecipient(Message.RecipientType.BCC,new InternetAddress(correoDestinatario));
            //CC a quien se le envia una copia oculta
            //BCC a quien se le envia una copia oculta

            message.setSubject(asunto);
            message.setText(textoCorreo);
            //Envio mensaje
            Transport transporte = session.getTransport("smtp");
            transporte.connect("antonioayalam2001@gmail.com","ixyuapsagpqkuejs");
            transporte.sendMessage(message, message.getAllRecipients());
            //Cierre
            transporte.close();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        https://support.google.com/accounts/answer/185833?visit_id=637867741267330913-2603975152&p=InvalidSecondFactor&rd=1
//        The link above show us how to set up out account so we can send email from there
        String correoDestinatario = "angeldeldiablo01@gmail.com";
        String asunto = "Registro";
        String textoCorreo = "Realizando una prueba";

        Utilerias uti = new Utilerias();
        uti.enviarMail(correoDestinatario,asunto,textoCorreo);
    }

}
