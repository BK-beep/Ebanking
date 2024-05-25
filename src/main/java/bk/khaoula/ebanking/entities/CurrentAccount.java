package bk.khaoula.ebanking.entities;

import bk.khaoula.ebanking.enums.AccountStatus;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount{
    private Double overDraft;
}
