package pl.nieckarz.userapi.transaction;

import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.nieckarz.userapi.user.AppUser;
import pl.nieckarz.userapi.user.AppUserRepository;
import pl.nieckarz.userapi.user.Role;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository underTest;

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void shouldFindByAppUser_Email() {
        //given
        String email ="test@gmail.com";

        AppUser firstUser = new AppUser("testName", "testLastName", email, "password", Role.ROLE_USER);
        appUserRepository.save(firstUser);
        underTest.save(new Transaction(firstUser, "BTC", 10000D, 2D, TransactionType.BUY));
        underTest.save(new Transaction(firstUser, "BTC", 100020D, 2D, TransactionType.SELL));

        AppUser secondUser =new AppUser("testName", "testLastName", "wrong@user.com", "password", Role.ROLE_USER);
        appUserRepository.save(secondUser);
        underTest.save(new Transaction(secondUser, "BTC", 10000D, 2D, TransactionType.BUY));


        //when
        List<Transaction> expected = underTest.findByAppUser_Email(email);

        //then
        assertThat(expected.size()).isEqualTo(2);

    }

    @Test
    void shouldDeleteByIdAndAppUser_Email() {
        //given
        String email ="test@gmail.com";

        AppUser firstUser = new AppUser("testName", "testLastName", email, "password", Role.ROLE_USER);
        appUserRepository.save(firstUser);
        Transaction transaction = underTest.save(new Transaction(firstUser, "BTC", 10000D, 2D, TransactionType.BUY));

        //when
        underTest.deleteByIdAndAppUser_Email(transaction.getId(),email);
        List<Transaction> expected = underTest.findByAppUser_Email(email);

        //then
        assertThat(expected.isEmpty());
    }
}