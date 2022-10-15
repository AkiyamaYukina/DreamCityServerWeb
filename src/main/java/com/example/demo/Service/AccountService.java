package com.example.demo.Service;

import com.example.demo.Dao.RegisterDao;
import com.example.demo.EmailCodeUtils.EmailCodeProcess;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Service.ServiceImpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if(registerDao.QueryPlayerAcount(account.getUsername()) == null) {
                registerDao.AddPlayerAcount(account.getUsername(), account.getUsername(), account.getPassword(), account.getEmail());
                return new AccountOperReturn(0,"注册成功！");
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
}
