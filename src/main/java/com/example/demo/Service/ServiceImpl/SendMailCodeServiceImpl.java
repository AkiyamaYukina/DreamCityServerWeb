package com.example.demo.Service.ServiceImpl;

import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;

public interface SendMailCodeServiceImpl {

    AccountOperReturn SendMailCode(AccountEntity entity);
}
