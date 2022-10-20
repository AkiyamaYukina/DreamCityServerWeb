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
            mimeMessage.addRecipients(Message.RecipientType.TO, "2152410021@qq.com");//设置收信人
            mimeMessage.setFrom(new InternetAddress("1250153995@qq.com"));

            mimeMessage.setSubject("[梦想之都] 服务器注册验证码");//邮件主题
            mimeMessage.setContent(content, "text/html;charset=utf-8");//正文

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", "1250153995@qq.com", "zwhxkaqzjwlehhbb");
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());//发送邮件，第二个参数为收件人
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
        if(SendMailCode(entity.getEmail(),"您的验证码是: "+mailCode+" ,15分钟内有效，请尽快验证，若非您本人操作，请忽略。"))
        {
            AccountOperReturn reuslt = new AccountOperReturn(0,"验证码发送成功！");
            MailCodeQueue.put(entity.getEmail(),new MailCodeEntity(entity,mailCode,System.currentTimeMillis()));
            return reuslt;
        }
        else
        {
            return new AccountOperReturn(-1,"验证码发送失败，请重试！");
        }
    }

    public int getMailCodeQueueSize()
    {
        return MailCodeQueue.size();
    }
}
