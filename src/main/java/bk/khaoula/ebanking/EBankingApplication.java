package bk.khaoula.ebanking;

import bk.khaoula.ebanking.dtos.CustomerDto;
import bk.khaoula.ebanking.entities.*;
import bk.khaoula.ebanking.enums.AccountStatus;
import bk.khaoula.ebanking.enums.OperationType;
import bk.khaoula.ebanking.exceptions.AccountNotFoundException;
import bk.khaoula.ebanking.exceptions.BalanceNotSufficientException;
import bk.khaoula.ebanking.exceptions.CustomerNotFoundException;
import bk.khaoula.ebanking.repositories.AccountRepository;
import bk.khaoula.ebanking.repositories.CustomerRepository;
import bk.khaoula.ebanking.repositories.OperationRepository;
import bk.khaoula.ebanking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner  commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("HAssan","Laila","Mohammed").forEach(name->{
                CustomerDto customerDto=new CustomerDto();
                customerDto.setName(name);
                customerDto.setEmail(name+"@mail.com");
                bankAccountService.saveCustomer(customerDto);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentAccount(Math.random()*12000,9000D,customer.getId());
                    bankAccountService.saveSavingAccount(Math.random()*12000,5.5,customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }

            });
            bankAccountService.bankAccountList().forEach(account -> {
               for (int i=0;i<10;i++){
                   try {
                       bankAccountService.credit(account.getId(),Math.random()*100,"Credit");
                       bankAccountService.debit(account.getId(),Math.random()*100,"Debit");
                   } catch (AccountNotFoundException e) {
                       throw new RuntimeException(e);
                   } catch (BalanceNotSufficientException e) {
                       throw new RuntimeException(e);
                   }
               }
            });

        };
    }

    //@Bean
    CommandLineRunner start(AccountRepository accountRepository, CustomerRepository customerRepository, OperationRepository operationRepository){
        return args -> {
            Stream.of("Hassan","Hossein","LHsen","Hassan").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setBalance(Math.random()*12000);
                savingAccount.setCurrency("dirham");
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                accountRepository.save(savingAccount);

                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setBalance(Math.random()*12000);
                currentAccount.setCurrency("dirham");
                currentAccount.setOverDraft(1200D);
                currentAccount.setCustomer(customer);
                accountRepository.save(currentAccount);
            });

            accountRepository.findAll().forEach(bankAccount -> {
                for (int i=0;i<10;i++){
                    Operation operation=new Operation();
                    operation.setAmount(Math.random()*100000);
                    operation.setAccount(bankAccount);
                    operation.setDate(new Date());
                    operation.setOperationType(Math.random()<0.5? OperationType.DEBIT :OperationType.CREDIT);
                    operationRepository.save(operation);
                }
            });

        };
    }
}
