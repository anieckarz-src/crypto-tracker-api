package pl.nieckarz.userapi.registration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nieckarz.userapi.user.AppUserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RegistrationServiceTest {

    @Autowired
    private RegistrationService underTest;

    @Autowired
    private AppUserRepository appUserRepository;

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void givenRegistrationRequest_whenRegisterNewUser_thenIsPresentInDatabase() {

        //given
        String email = "test@gmail.com";

        RegistrationRequest request = new RegistrationRequest(
                email,
                "testFirstName",
                "testLastName",
                "password"
        );

        //when
        underTest.register(request);
        boolean isPresent = appUserRepository.findById(email).isPresent();

        //then
        assertThat(isPresent).isTrue();
    }
}