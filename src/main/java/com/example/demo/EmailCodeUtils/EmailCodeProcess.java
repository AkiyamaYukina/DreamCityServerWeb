package com.example.demo.EmailCodeUtils;

import com.example.demo.Entity.AccountEntity;
import com.example.demo.Entity.MailCodeEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Utils;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Component
public class EmailCodeProcess {
    private long timeout = 900 * 1000L;
    private Thread MailCodeQueueDefendThread;
    private HashMap<String, MailCodeEntity> MailCodeQueue = new HashMap<>();

    public EmailCodeProcess()
    {
        MailCodeQueueDefendThread = new Thread(()->
        {
            while (true)
            {
                try {
                    Iterator<Map.Entry<String,MailCodeEntity>> i = MailCodeQueue.entrySet().iterator();
                    while (i.hasNext())
                    {
                        Map.Entry<String,MailCodeEntity> key = i.next();

                        if( (key.getValue().getTimestamp() + timeout ) < System.currentTimeMillis())
                        {
                            i.remove();
                        }
                    }
                    Thread.sleep(1000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        MailCodeQueueDefendThread.start();
    }

    public boolean SendMailCode(String TargetMail,String content) {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", "smtp.qq.com");
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(properties);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipients(Message.RecipientType.TO, "2152410021@qq.com");//???????????????
            mimeMessage.setFrom(new InternetAddress("1250153995@qq.com"));

            mimeMessage.setSubject("[????????????] ????????????????????????");//????????????
            mimeMessage.setContent(content, "text/html;charset=utf-8");//??????

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", "1250153995@qq.com", "zwhxkaqzjwlehhbb");
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());//??????????????????????????????????????????
            transport.close();
            return true;
        }catch (MessagingException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public AccountOperReturn AddMailCodeSendNode(AccountEntity entity)
    {
        String mailCode = Utils.GeneratorRandomMailCode(6);
        if(SendMailCode(entity.getEmail(),"??????????????????: "+mailCode+" ,15????????????????????????????????????????????????????????????????????????"))
        {
            AccountOperReturn reuslt = new AccountOperReturn(0,"????????????????????????");
            MailCodeQueue.put(entity.getEmail(),new MailCodeEntity(entity,mailCode,System.currentTimeMillis()));
            return reuslt;
        }
        else
        {
            return new AccountOperReturn(-1,"????????????????????????????????????");
        }
    }

    public int getMailCodeQueueSize()
    {
        return MailCodeQueue.size();
    }
}
