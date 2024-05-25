package bk.khaoula.ebanking.web;

import bk.khaoula.ebanking.dtos.AccountHistoryDto;
import bk.khaoula.ebanking.dtos.BankAccountDto;
import bk.khaoula.ebanking.dtos.OperationDto;
import bk.khaoula.ebanking.exceptions.AccountNotFoundException;
import bk.khaoula.ebanking.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
    BankAccountService bankAccountService;
    @GetMapping("/accounts")
    public List<BankAccountDto> accounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDto getAccount(@PathVariable String id) throws AccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/accounts/{id}/history")
    public List<OperationDto> getHistory(@PathVariable String id){
        return bankAccountService.accountHistory(id);
    }
    @GetMapping("/accounts/{id}/pageHistory")
    public AccountHistoryDto getAccountHistory(@PathVariable String id,
                                               @RequestParam(name="page", defaultValue = "0") int page,
                                               @RequestParam(name="size", defaultValue = "5")int size){
        return bankAccountService.getAccountHistory(id, page, size);
    }
}
