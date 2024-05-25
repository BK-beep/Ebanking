package bk.khaoula.ebanking.services;

import bk.khaoula.ebanking.dtos.*;
import bk.khaoula.ebanking.entities.*;
import bk.khaoula.ebanking.enums.OperationType;
import bk.khaoula.ebanking.exceptions.AccountNotFoundException;
import bk.khaoula.ebanking.exceptions.BalanceNotSufficientException;
import bk.khaoula.ebanking.exceptions.CustomerNotFoundException;
import bk.khaoula.ebanking.mappers.AccountMapperImpl;
import bk.khaoula.ebanking.repositories.AccountRepository;
import bk.khaoula.ebanking.repositories.CustomerRepository;
import bk.khaoula.ebanking.repositories.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    AccountRepository accountRepository;
    CustomerRepository customerRepository;
    OperationRepository operationRepository;
    AccountMapperImpl mapper;
    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer=mapper.fromCustomerDto(customerDto);
        customerRepository.save(customer);
        return mapper.fromCustomer(customer);
    }



    @Override
    public CurrentAccountDto saveCurrentAccount(Double initialBalance, Double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found !"));
        CurrentAccount currentAccount=new CurrentAccount(overdraft);
        currentAccount.setCustomer(customer);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        accountRepository.save(currentAccount);
        CurrentAccountDto currentAccountDto=mapper.fromCurrentAccount(currentAccount);
        return currentAccountDto;
    }

    @Override
    public SavingAccountDto saveSavingAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found !"));
        SavingAccount savingAccount=new SavingAccount(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());

        accountRepository.save(savingAccount);
        SavingAccountDto savingAccountDto=mapper.fromSavingAccount(savingAccount);
        return savingAccountDto;
    }

    @Override
    public List<BankAccountDto> bankAccountList(){
        List<BankAccount> accounts = accountRepository.findAll();
        List<BankAccountDto> bankAccountDtos = accounts.stream().map(account->{
          if (account instanceof SavingAccount){
              SavingAccount savingAccount=(SavingAccount) account;
              return mapper.fromSavingAccount(savingAccount);
          }else {
              CurrentAccount currentAccount=(CurrentAccount) account;
              return mapper.fromCurrentAccount(currentAccount);
          }
        }).collect(Collectors.toList());
        return bankAccountDtos;
    }
    @Override
    public List<CustomerDto> listCustomers() {

        return customerRepository.findAll().stream().map(customer -> mapper.fromCustomer(customer)).collect(Collectors.toList());

    }

    @Override
    public BankAccountDto getBankAccount(String accountId) throws AccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException());
        if (bankAccount instanceof CurrentAccount){
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return mapper.fromCurrentAccount(currentAccount);
        }else{
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return mapper.fromSavingAccount(savingAccount);
        }
    }

    @Override
    public void debit(String accountId, Double amount, String description) throws BalanceNotSufficientException, AccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException());
        if (amount>bankAccount.getBalance())
            throw new BalanceNotSufficientException("Balance not Sufficient");
        Operation operation=new Operation();
        operation.setOperationType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(new Date());
        operation.setAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
    }

    @Override
    public void credit(String accountId, Double amount, String description) throws BalanceNotSufficientException, AccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException());
        Operation operation=new Operation();
        operation.setOperationType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(new Date());
        operation.setAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDest, Double amount) throws BalanceNotSufficientException, AccountNotFoundException {
        debit(accountIdSource,amount,"transfer to"+accountIdDest);
        credit(accountIdDest,amount,"transfer from"+accountIdSource);


    }

    @Override
    public CustomerDto getCustomer(Long id) {

        Customer customer=customerRepository.findById(id).orElseThrow();
        return mapper.fromCustomer(customer);
    }

    @Override
    public CustomerDto updateCustomer(Long id,CustomerDto customerDto) {
        Customer customer=mapper.fromCustomerDto(customerDto);
        Customer savedCustomer=customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<OperationDto> accountHistory(String id){
        return operationRepository.findAllByAccountId(id).stream().map(operation -> {
            OperationDto operationDto=mapper.fromOperation(operation);
            return operationDto;
        }).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDto getAccountHistory(String id, int page, int size) {
        Page<Operation> operations=operationRepository.findByAccountId(id, PageRequest.of(page,size));
        AccountHistoryDto accountHistoryDto=new AccountHistoryDto();
        List<OperationDto> operationDtos=operations.getContent().stream().map(operation -> {
            return mapper.fromOperation(operation);
        }).collect(Collectors.toList());

        BankAccount account=accountRepository.findById(id).orElse(null);
        accountHistoryDto.setAccountId(id);
        accountHistoryDto.setAccountOperationDTOS(operationDtos);
        accountHistoryDto.setBalance(account.getBalance());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(operations.getTotalPages());
        return accountHistoryDto;
    }

    @Override
    public List<CustomerDto> searchByKeyword(String keyword) {
        return customerRepository.searchByKeyword("%"+keyword+"%").stream().map(customer -> mapper.fromCustomer(customer)).collect(Collectors.toList());
    }

}
