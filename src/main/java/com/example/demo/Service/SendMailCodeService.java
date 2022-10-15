package com.example.demo.Service;

import com.example.demo.EmailCodeUtils.EmailCodeProcess;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;
import com.example.demo.Service.ServiceImpl.SendMailCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMailCodeService implements SendMailCodeServiceImpl {

    private EmailCodeProcess process;

    @Autowired
    public void setEmailCodeProcess(EmailCodeProcess process)
    {
        this.process = process;
    }

    @Override
    public AccountOperReturn SendMailCode(AccountEntity account) {

        if(account.getEmail().isEmpty())
        {
            return new AccountOperReturn(-1,"缺少邮箱字段!");
        }

        return process.AddMailCodeSendNode(account);
    }
}
