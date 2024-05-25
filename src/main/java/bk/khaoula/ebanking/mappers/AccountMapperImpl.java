package bk.khaoula.ebanking.mappers;

import bk.khaoula.ebanking.dtos.CustomerDto;
import bk.khaoula.ebanking.dtos.*;
import bk.khaoula.ebanking.entities.*;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountMapperImpl {
    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
        
    }
    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
    public SavingAccountDto fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto=new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        return savingAccountDto;
    }
    public SavingAccount fromSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        return savingAccount;
    }
    public CurrentAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto=new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount,currentAccountDto);
        return currentAccountDto;
    }
    public CurrentAccount fromCurrentAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
        return currentAccount;
    }

    public OperationDto fromOperation(Operation operation){
        OperationDto operationDto=new OperationDto();
        BeanUtils.copyProperties(operation,operationDto);
        return operationDto;
    }

}
