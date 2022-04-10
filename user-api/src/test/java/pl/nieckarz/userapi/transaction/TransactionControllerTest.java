package pl.nieckarz.userapi.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.nieckarz.userapi.user.AppUser;
import pl.nieckarz.userapi.user.AppUserRepository;
import pl.nieckarz.userapi.user.Role;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void addTransaction() throws Exception {

        //given
        String url = "/api/v1/transaction";
        String email = "test@gmail.com";

        AppUser appUser = appUserRepository.save(new AppUser("testName", "testLastName", email, "password", Role.ROLE_USER));

        TransactionRequest request = new TransactionRequest("BTC", 10000D, 2D, TransactionType.BUY);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        //when
        mockMvc.perform(post(url).with(user(email)).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isCreated());
        List<Transaction> result = transactionRepository.findByAppUser_Email(email);

        //then
        assertThat(result.get(0).getAppUser().getEmail()).isEqualTo(email);
    }

    @Test
    void shouldDeleteTransaction() throws Exception{

        //given
        String url = "/api/v1/transaction";
        String email ="test@gmail.com";

        AppUser user = new AppUser("testName", "testLastName", email, "password", Role.ROLE_USER);
        appUserRepository.save(user);
        Transaction transaction = transactionRepository.save(new Transaction(user, "BTC", 10000D, 2D, TransactionType.BUY));

        //when
        mockMvc.perform(delete(url+"/"+transaction.getId()).with(user(email))).andExpect(status().is(204));
        List<Transaction> expected = transactionRepository.findByAppUser_Email(email);

        assertThat(expected.isEmpty());
    }

}