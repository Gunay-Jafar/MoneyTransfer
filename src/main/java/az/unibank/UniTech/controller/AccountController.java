package az.unibank.UniTech.controller;

import az.unibank.UniTech.dto.AccountResponseDto;
import az.unibank.UniTech.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponseDto> getAll() {
        return accountService.findAllByStatus();
    }
}
