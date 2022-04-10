package pl.nieckarz.userapi.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAppUser_Email(String email);

    void deleteByIdAndAppUser_Email(Long id,String email);
}
