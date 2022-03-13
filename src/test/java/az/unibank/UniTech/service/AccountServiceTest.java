package az.unibank.UniTech.service;

import az.unibank.UniTech.constant.Status;
import az.unibank.UniTech.dto.AccountResponseDto;
import az.unibank.UniTech.dto.LoginUser;
import az.unibank.UniTech.dto.SuccessResponse;
import az.unibank.UniTech.dto.TransferRequest;
import az.unibank.UniTech.error.AccountNotExistException;
import az.unibank.UniTech.mapper.AccountMapper;
import az.unibank.UniTech.model.Account;
import az.unibank.UniTech.model.User;
import az.unibank.UniTech.repository.AccountRepo;
import az.unibank.UniTech.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private AccountMapper accountMapper;

    @Spy
    private AccountService accountService;

    private Account expected;
    private Account passed;
    private LoginUser loginUser;
    private TransferRequest transferRequest;
    private SuccessResponse successResponse;
    private AccountResponseDto responseDto;
    List<Account> accountList;
    List<AccountResponseDto> accountResponseDtoList;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        accountService = spy(new AccountServiceImpl(accountRepo, accountMapper));

        loginUser= new LoginUser();
        loginUser.setPassword("Test");
        loginUser.setPincode("Test");

        User user = new User();
        user.setId(1L);
        user.setPincode(loginUser.getPincode());
        user.setPassword(loginUser.getPassword());

        transferRequest = new TransferRequest();
        transferRequest.setAccountToId(1L);
        transferRequest.setAccountFromId(2L);
        transferRequest.setAmount(new BigDecimal(1.00));

        passed = new Account();
        passed.setBalance(new BigDecimal(12.0));
        passed.setStatus(Status.ACTIVE);
        passed.setUser(user);

    }

    @Test
    public void getAll() {
        when(accountRepo.findByStatus(Status.ACTIVE)).thenReturn(accountList);
        when(accountMapper.toResponseDtoList(accountList)).thenReturn(accountResponseDtoList);

        List<AccountResponseDto> actual = accountService.findAllByStatus();
        assertEquals(accountResponseDtoList, actual);

        verify(accountRepo, only()).findByStatus(Status.ACTIVE);
        verify(accountMapper, only()).toResponseDtoList(accountList);
    }

    @Test
    public void transferBalances_when_accountFromId_not_exist() {
        when(accountRepo.findById(Long.MAX_VALUE)).thenThrow(AccountNotExistException.class);

        assertThrows(AccountNotExistException.class, () -> accountService.transferBalances(transferRequest));

        verify(accountRepo, atMost(1)).findById(Long.MAX_VALUE);
    }
}
