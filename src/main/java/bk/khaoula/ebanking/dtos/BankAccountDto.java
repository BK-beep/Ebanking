package bk.khaoula.ebanking.dtos;

import bk.khaoula.ebanking.entities.Customer;
import bk.khaoula.ebanking.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDto {
    private String id;
    private Date createdAt;
    private Double balance;
    private AccountStatus status;
    private String currency;
    private Customer customer;
}
