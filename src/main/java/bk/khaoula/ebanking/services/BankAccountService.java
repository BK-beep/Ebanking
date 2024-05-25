package bk.khaoula.ebanking.services;

import bk.khaoula.ebanking.dtos.*;
import bk.khaoula.ebanking.entities.BankAccount;
import bk.khaoula.ebanking.entities.CurrentAccount;
import bk.khaoula.ebanking.entities.Customer;
import bk.khaoula.ebanking.entities.SavingAccount;
import bk.khaoula.ebanking.exceptions.AccountNotFoundException;
import bk.khaoula.ebanking.exceptions.BalanceNotSufficientException;
import bk.khaoula.ebanking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    CurrentAccountDto saveCurrentAccount(Double initialBalance, Double overdraft, Long customerId) throws CustomerNotFoundException;

    SavingAccountDto saveSavingAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException;

    List<BankAccountDto> bankAccountList();

    List<CustomerDto> listCustomers();
    BankAccountDto getBankAccount(String accountId) throws AccountNotFoundException;

    void credit(String accountId, Double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;

    void debit(String accountId, Double amount, String description) throws BalanceNotSufficientException, AccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDest,Double amount) throws BalanceNotSufficientException, AccountNotFoundException;

    CustomerDto getCustomer(Long id);

    CustomerDto updateCustomer(Long id,CustomerDto customerDto);
    void deleteCustomer(Long id);

    List<OperationDto> accountHistory(String id);

    AccountHistoryDto getAccountHistory(String id, int page, int size);

    List<CustomerDto> searchByKeyword(String keyword);

    //List<>
}
