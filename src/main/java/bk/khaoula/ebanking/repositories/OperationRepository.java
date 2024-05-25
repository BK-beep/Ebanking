package bk.khaoula.ebanking.repositories;

import bk.khaoula.ebanking.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {

    List<Operation> findAllByAccountId(String id);
    Page<Operation> findByAccountId(String id, Pageable pageable);

}
