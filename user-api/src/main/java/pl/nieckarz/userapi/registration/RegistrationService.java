package pl.nieckarz.userapi.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nieckarz.userapi.exceptions.email.InvalidEmailException;
import pl.nieckarz.userapi.user.AppUser;
import pl.nieckarz.userapi.user.AppUserService;
import pl.nieckarz.userapi.user.Role;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;

    public void register(RegistrationRequest request) {

        boolean isValid = EmailValidator.validate(request.getEmail());

        if (!isValid) {
            throw new InvalidEmailException(request.getEmail());
        }

        appUserService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                Role.ROLE_USER

        ));
    }


}
