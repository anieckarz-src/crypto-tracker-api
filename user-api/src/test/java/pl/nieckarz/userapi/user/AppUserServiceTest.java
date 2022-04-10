package pl.nieckarz.userapi.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AppUserServiceTest {

    @Autowired
    private AppUserService underTest;

    @Autowired
    private AppUserRepository appUserRepository;


    @Test
    void shouldSignUpUser() {
        //given
        String email = "test@gmail.com";
        AppUser appUser = new AppUser("testFirstName","testLastName",email,"password",Role.ROLE_USER);

        //when
        underTest.signUpUser(appUser);
        AppUser expected = appUserRepository.findById(email).orElseThrow();

        //then
        assertThat(expected).isEqualTo(appUser);

    }
}