import org.junit.Test;
import org.tango.mail.Mail;
import org.tango.mail.Mailer;

import javax.mail.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by tango on 14-2-9.
 */
public class MailTest {

    @Test
    public void testSend() throws MessagingException, IOException {
        Mailer mailer = new Mailer();
        mailer.setValidate(true);
        mailer.setSmtpServer("smtp.gmail.com");
        mailer.setSmtpServerPort("25");

        Mail mail = new Mail();
        mail.setUserName("org.java.tango@gmail.com");
        mail.setPassword("guangtiaowu3");
        mail.setFromAddress("org.java.tango@gmail.com");

        //
        mail.setToAddress("278826466@qq.com");
        mail.setSubject("first mail1");
        mail.setContent("hello world1");

        mail.setAttachFiles(new File[]{
                new File("pom.xml"),
                new File("tango-application.iml")
        });
        mailer.send(mail);
    }
}
