package pl.nieckarz.userapi.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.nieckarz.userapi.user.AppUserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void givenRegistrationRequest_whenPostJsonRequest_thenIsPresentInDatabase() throws Exception {

        //given

        String url ="/api/v1/registration";
        String email = "test@gmail.com";

        RegistrationRequest request = new RegistrationRequest(
                email,
                "testFirstName",
                "testLastName",
                "password"
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        //when
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isCreated());
        boolean isPresent =appUserRepository.findById(email).isPresent();

        //then
        assertThat(isPresent).isTrue();

    }

    @Test
    void givenRegistrationRequestWithInvalidEmail_whenPostJsonRequest_thenInvalidEmailExceptionWithBadRequestStatus() throws Exception {

        //given

        String url ="/api/v1/registration";
        String email = "testgmailcom";

        RegistrationRequest request = new RegistrationRequest(
                email,
                "testFirstName",
                "testLastName",
                "password"
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        //when then
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    void givenRegistrationRequest_whenPostJsonRequestTwice_thenUserExistsExceptionWithConflictStatus() throws Exception {

        //given

        String url ="/api/v1/registration";
        String email = "test@gmail.com";

        RegistrationRequest request = new RegistrationRequest(
                email,
                "testFirstName",
                "testLastName",
                "password"
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        //when then
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isCreated());
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().is(CONFLICT.value()));
    }

}