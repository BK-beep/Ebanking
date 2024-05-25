package bk.khaoula.ebanking.dtos;

import bk.khaoula.ebanking.entities.Customer;
import bk.khaoula.ebanking.entities.Operation;
import bk.khaoula.ebanking.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SavingAccountDto extends BankAccountDto{
    private Double interestRate;
}
