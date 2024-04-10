package org.mtroyanov.service;

import org.mtroyanov.dto.CreateAccountDto;
import org.mtroyanov.entity.Account;

import java.util.List;

public interface AccountService {

    void createAccount(CreateAccountDto createAccountDto);

}
