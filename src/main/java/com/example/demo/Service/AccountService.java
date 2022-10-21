package com.example.demo.Service;

import com.example.demo.Dao.RegisterDao;
import com.example.demo.DemoApplication;
import com.example.demo.EmailCodeUtils.EmailCodeProcess;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Service.ServiceImpl.AccountServiceImpl;
import com.example.demo.Utils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class AccountService implements AccountServiceImpl {
    private RegisterDao registerDao;
    private EmailCodeProcess process;

    @Autowired
    public void setRegisterDao(RegisterDao dao)
    {
        this.registerDao = dao;
    }

    @Autowired
    public void setProcess(EmailCodeProcess process)
    {
        this.process = process;
    }

    @Override
    public AccountOperReturn QueryAccount(AccountEntity account) {
        return null;
    }

    @Override
    public AccountOperReturn AddAccount(AccountEntity account) {
        if(account.getUsername().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少用户名字段!");
        }

        if(account.getEmail().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少邮箱字段!");
        }

        if(account.getPassword().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少密码字段!");
        }

        try {
            if(registerDao.QueryPlayerAcountByUsername(account.getUsername()) == null) {
                if(registerDao.QueryPlayerAcountByEmail(account.getEmail()) == null) {
                    String EncryptedPassword = Utils.GeneratorAuthmeEncryptPassword(account.getPassword());
                    registerDao.AddPlayerAcount(account.getUsername(), account.getUsername(), EncryptedPassword, account.getEmail());
                    MqttMessage msg = new MqttMessage();
                    msg.setPayload(("[服务器注册提醒]\n游戏ID:" + account.getUsername() + "\n白名单申请:").getBytes(StandardCharsets.UTF_8));
                    DemoApplication.client.publish("Server/RegisterCheck", msg);
                    return new AccountOperReturn(0, "注册成功！");
                }
                else
                {
                    return new AccountOperReturn(-1,"邮箱已被注册！");
                }
            }
            else {
                return new AccountOperReturn(-1,"账户已存在！");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return new AccountOperReturn(-1,e.getMessage());
        }
    }

    @Override
    public AccountOperReturn DelAccount(AccountEntity account) {
        return null;
    }

    @Override
    public AccountOperReturn UpdateAccount(AccountEntity account) {
        return null;
    }

    @Override
    public AccountOperReturn LoginAccount(AccountEntity account) {
        AccountEntity result = registerDao.QueryPlayerAcountByUsername(account.getUsername());
        if(result == null)
        {
            return new AccountOperReturn(-1,"用户名不存在!");
        }
        else
        {
            String password = result.getPassword();
            if(password.equalsIgnoreCase("$SHA$" + password.split("\\$")[2] + "$" + Utils.getSHA256StrJava(Utils.getSHA256StrJava(account.getPassword())+password.split("\\$")[2])))
            {
                return new AccountOperReturn(0,"登录成功!");
            }
            else
            {
                return new AccountOperReturn(-1,"密码不一致!");
            }
        }
    }
}
