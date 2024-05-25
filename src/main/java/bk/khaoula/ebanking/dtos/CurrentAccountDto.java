package bk.khaoula.ebanking.dtos;

import bk.khaoula.ebanking.entities.Customer;
import bk.khaoula.ebanking.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentAccountDto extends BankAccountDto{

    private Double overDraft;
}
