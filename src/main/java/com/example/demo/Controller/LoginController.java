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
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/reg")
    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public AccountOperReturn onPlayerLogin(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            @RequestParam("email")String email,
            @RequestParam("VerifyCode")String verifycode,
            @RequestParam("whitelist")String whitelist,
            HttpServletRequest request
    )
    {
        AccountEntity entity = new AccountEntity(username, password, email);
        return accountService.AddAccount(entity);
    }

    @PostMapping("/sendMailCode")
    @RequestMapping(value = "/sendMailCode",method = RequestMethod.POST)
    public AccountOperReturn SendMailCode(
            @RequestParam("username") String username,
            @RequestParam("email") String useremail,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        AccountEntity entity = new AccountEntity(username,"",useremail);
        return sendMailCodeService.SendMailCode(entity);
    }

    @PostMapping("/login")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AccountOperReturn LoginOnWebSite(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpServletResponse response,
            HttpServletRequest request)
    {
        return null;

    }
}
