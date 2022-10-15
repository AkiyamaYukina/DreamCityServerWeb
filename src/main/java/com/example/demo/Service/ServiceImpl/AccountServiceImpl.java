package com.example.demo.Service.ServiceImpl;

import com.example.demo.Entity.AccountEntity;
import com.example.demo.Error.AccountOperReturn;

public interface AccountServiceImpl {

    AccountOperReturn QueryAccount(AccountEntity account);
    AccountOperReturn AddAccount(AccountEntity account);
    AccountOperReturn DelAccount(AccountEntity account);
    AccountOperReturn UpdateAccount(AccountEntity account);
}
