package bk.khaoula.ebanking.repositories;

import bk.khaoula.ebanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<BankAccount,String> {
}
