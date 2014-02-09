package org.tango.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Created by tango on 14-2-9.
 */
public class Mailer {

    // 发送邮件的服务器的IP和端口
    private String smtpServer;
    private String smtpServerPort = "25";
    private String pop3Server;
    private String pop3ServerPort = "110";
    // 是否需要身份验证
    private boolean validate = false;

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        if (smtpServer != null) {
            p.put("mail.smtp.host", this.smtpServer);
            p.put("mail.smtp.port", this.smtpServerPort);
        }
        if (pop3Server != null) {
            p.put("mail.pop3.host", this.pop3Server);
            p.put("mail.pop3.port", this.pop3ServerPort);
        }
        p.put("mail.smtp.auth", this.isValidate() ? "true" : "false");
        if ("smtp.gmail.com".equals(this.smtpServer)) {
            p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.setProperty("mail.smtp.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.port", "465");
            p.setProperty("mail.smtp.socketFactory.port", "465");
        }
        return p;
    }

    private Authenticator getAuthenticator(final Mail mail) {
        if (this.isValidate()) {
            return new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail.getUserName(), mail.getPassword());
                }
            };
        }
        return null;
    }

    public void send(Mail mail) throws MessagingException {
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(this.getProperties(), this.getAuthenticator(mail));
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mail.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mail.getToAddress());
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mail.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        String mailContent = mail.getContent();
        if (!mail.hasAttachFile()) {
            // 设置邮件消息的主要内容
            mailMessage.setText(mailContent);
        }else {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailContent);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            //
            File[] attachFiles = mail.getAttachFiles();
            for (int i = 0; i < attachFiles.length; i++) {
                File file = attachFiles[i];
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                multipart.addBodyPart(messageBodyPart);
            }
            mailMessage.setContent(multipart);
        }
        // 发送邮件
        Transport.send(mailMessage);
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpServerPort() {
        return smtpServerPort;
    }

    public void setSmtpServerPort(String smtpServerPort) {
        this.smtpServerPort = smtpServerPort;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public void setPop3Server(String pop3Server) {
        this.pop3Server = pop3Server;
    }

    public String getPop3ServerPort() {
        return pop3ServerPort;
    }

    public void setPop3ServerPort(String pop3ServerPort) {
        this.pop3ServerPort = pop3ServerPort;
    }

    public boolean isValidate() {
        return this.validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

}
