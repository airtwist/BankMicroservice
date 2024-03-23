package org.mtroyanov.service;

import org.mtroyanov.dto.CreateAccountDto;

public interface AccountService {

    void createAccount(CreateAccountDto createAccountDto);
}
