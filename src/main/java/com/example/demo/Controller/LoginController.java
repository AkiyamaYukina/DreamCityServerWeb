package com.example.demo.Controller;

import com.example.demo.Dao.RegisterDao;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Entity.MailCodeEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.SendMailCodeService;
import com.example.demo.Utils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.example.demo.DemoApplication.client;

@RestController
public class LoginController {
    private AccountService accountService;
    private SendMailCodeService sendMailCodeService;

    @Autowired
    public void setAccountService(AccountService service)
    {
        this.accountService = service;
    }

    @Autowired
    public void setSendMailCodeService(SendMailCodeService service)
    {
        this.sendMailCodeService = service;
    }

    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public AccountOperReturn onPlayerLogin(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            @RequestParam("email")String email,
            @RequestParam("VerifyCode")String verifycode,
            @RequestParam("whitelist")String whitelist
    )
    {
        AccountEntity entity = new AccountEntity(username, password, email);
        return accountService.AddAccount(entity);
    }

    @RequestMapping(value = "/sendMailCode",method = RequestMethod.POST)
    public AccountOperReturn SendMailCode(@RequestParam("email") String useremail)
    {
        AccountEntity entity = new AccountEntity("","",useremail);
        return sendMailCodeService.SendMailCode(entity);
    }
}
