package pl.nieckarz.cryptocurrencyapi.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;


import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUri = "/api/v1/crypto";

    @AfterEach
    void tearDown() {
        cryptoRepository.deleteAll();
    }

    @Test
    void givenSymbols_whenGetCryptoData_then() throws Exception {
        //given
        String[] symbols = {"BTC","EtH"};
        String[] name = {"Bitcoin","Ethereum"};
        String request =  String.join(",", symbols);

        //when
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUri+"/"+request))
            //    .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

       Crypto[] expected = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Crypto[].class);

       assertThat(expected[0].getName()).isEqualTo(name[0]);
       assertThat(expected[1].getName()).isEqualTo(name[1]);
    }

    @Test
    void deleteCrypto() throws Exception {
        String symbol = "BTC";

        mockMvc.perform(MockMvcRequestBuilders.get(baseUri+"/"+symbol))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUri+"/"+symbol))
                // .andDo(print())
                .andExpect(status().isOk());

        boolean isPresent = cryptoRepository.findById(symbol).isPresent();

        assertThat(isPresent).isFalse();
    }

    @Test
    void givenEmptyRequest_whenGetCryptoData_thenNotFound() throws Exception {
        //given

        String request =  "";

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get(baseUri+"/"+request))
                .andExpect(status().is(SC_NOT_FOUND));
    }

    @Test
    void givenInvalidRequest_whenGetCryptoData_thenBadRequest() throws Exception {
        //given

        String request = UUID.randomUUID().toString();

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get(baseUri+"/"+request))
                .andExpect(status().is(SC_BAD_REQUEST));
    }
}