//package com.talent.expense_manager.expense_manager;
//
//import com.talent.expense_manager.expense_manager.request.AccountRequest;
//import com.talent.expense_manager.expense_manager.request.LoginRequest;
//import com.talent.expense_manager.expense_manager.response.AccountResponse;
//import com.talent.expense_manager.expense_manager.response.BaseResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.client.RestClient;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class RestClientTest {
//
//
//    @LocalServerPort
//    private int port;
//
//    private RestClient restClient;
//
//
//    @BeforeEach
//    void setUp() {
//        restClient = RestClient.builder()
//                .baseUrl("http://localhost:" + port)
//                .build();
//    }
//
//    @Test
//    void testAccountRestService() {
//        // 1. Prepare Request
//        LoginRequest request = new LoginRequest();
//        request.setEmail("admin@admin.com");
//        request.setPassword("admin123");
//
//        BaseResponse<AccountResponse> response = restClient.post()
//                .uri("auth/login")
//                .header("X-expense-api-key", "yt40lwmhskXnTY3cdlECJ5T8iNxdE6IYgBnEpzssomLFPCaZmIsHEmxrlHTWh50Q")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(request)
//                .retrieve()
//                .body(new ParameterizedTypeReference<BaseResponse<AccountResponse>>() {
//                });
//
//        assertThat(response).isNotNull();
//        assertThat(response.getMessage()).isEqualTo("Login Account Successful");
//        assertThat(response.getData().getRole()).isEqualTo("ADMIN");
//
//
//        System.out.println("LOGIN RESPONSE");
//        System.out.println("...........................");
//
//        System.out.println(response.getMessage());
//        System.out.println(response.getData().getRole());
//
//
//    }
//
//    @Test
//    void testUpdateAccount() {
//
//
//        AccountRequest accountRequest = new AccountRequest();
//        accountRequest.setName("Charging");
//        accountRequest.setEmail("char@gmail.com");
//        accountRequest.setPassword("121212");
//
//
//        BaseResponse<AccountResponse> response = restClient.put()
//                .uri("/api/accounts/updateAccount/{id}", "0000")
//                .header("X-expense-api-key", "yt40lwmhskXnTY3cdlECJ5T8iNxdE6IYgBnEpzssomLFPCaZmIsHEmxrlHTWh50Q")
//                .body(accountRequest)
//                .retrieve()
//                .body(new ParameterizedTypeReference<BaseResponse<AccountResponse>>() {
//                });
//
//        assertThat(response).isNotNull();
//        assertThat(response.getData().getName()).isEqualTo("Charging");
//
//
//    }
//
//
//    @Test
//    void testAccountInfo(){
//
//
//        BaseResponse<AccountResponse> response = restClient.get()
//                .uri("/api/accounts/accountinfo/{id}", "0000")
//                .header("X-expense-api-key", "yt40lwmhskXnTY3cdlECJ5T8iNxdE6IYgBnEpzssomLFPCaZmIsHEmxrlHTWh50Q")
//                .retrieve()
//                .body(new ParameterizedTypeReference<BaseResponse<AccountResponse>>() {
//                });
//
//        assertThat(response.getData().getAccountId()).isEqualTo("0000");
//
//    }
//
//    @Test
//    void testDeleteTransaction(){
//        BaseResponse<String> response = restClient.delete()
//                .uri("/api/transactions/{transactionId}", 1)
//                .header("X-expense-api-key", "yt40lwmhskXnTY3cdlECJ5T8iNxdE6IYgBnEpzssomLFPCaZmIsHEmxrlHTWh50Q")
//                .retrieve()
//                .body(new ParameterizedTypeReference<BaseResponse<String>>() {
//                });
//        assertThat(response).isNotNull();
//        assertThat(response.getMessage()).isEqualTo("deleteTransaction Successful");
//        assertThat(response.getData()).isNull();
//
//    }
//
//
//
//
//}
