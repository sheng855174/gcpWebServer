package sheng.gcp.web.server.common;

import lombok.extern.slf4j.Slf4j;
import sheng.gcp.web.server.config.Config;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


@Slf4j
public class SmtpGmail {

    public static void send(String receiver, String title, String msg) throws Exception {
        log.info("發送mail開始..." + "收件人 : " + receiver + ", 主旨 : " + title);

        Properties props = getProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.email_username, Config.email_password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Config.email_username));
        // 收件者
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(title);
        message.setText(msg);

        Transport transport = session.getTransport("smtp");
        transport.connect(Config.email_host, Config.email_port, Config.email_username, Config.email_password);

        Transport.send(message);

        log.info(receiver + " 寄送email結束");

    }

    public static Properties getProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.host", Config.email_host);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.port", Config.email_port);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return props;
    }
/*
    public static void main(String[] argv){
        // 要先去開啟存取權限 https://myaccount.google.com/lesssecureapps
        try {
            send("sheng855174@gmail.com", "情侶契約書密碼重置", "密碼 : flsjfslajl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

}
