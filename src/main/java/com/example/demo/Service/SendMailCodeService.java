package com.example.demo.Service;

import com.example.demo.Dao.RegisterDao;
import com.example.demo.EmailCodeUtils.EmailCodeProcess;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Service.ServiceImpl.SendMailCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMailCodeService implements SendMailCodeServiceImpl {

    private EmailCodeProcess process;
    private RegisterDao dao;
    @Autowired
    public void setEmailCodeProcess(EmailCodeProcess process)
    {
        this.process = process;
    }

    @Autowired
    public void setRegisterDao(RegisterDao dao)
    {
        this.dao = dao;
    }
    @Override
    public AccountOperReturn SendMailCode(AccountEntity account) {

        if(account.getEmail().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少邮箱字段!");
        }

        if(account.getUsername().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少用户名字段!");
        }
        if(dao.QueryPlayerAcountByUsername(account.getUsername()) == null) {
            if(dao.QueryPlayerAcountByEmail(account.getEmail()) == null) {
                return process.AddMailCodeSendNode(account);
            }else
            {
                return new AccountOperReturn(-1,"邮箱已被注册！");
            }
        }
        else
        {
            return new AccountOperReturn(-1,"用户名已存在！");
        }
    }
}
