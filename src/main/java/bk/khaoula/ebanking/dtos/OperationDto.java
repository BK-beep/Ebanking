package bk.khaoula.ebanking.dtos;

import bk.khaoula.ebanking.entities.BankAccount;
import bk.khaoula.ebanking.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDto {
    private Long id;
    private Date date;
    private String Description;
    private Double amount;
    private OperationType operationType;
}
