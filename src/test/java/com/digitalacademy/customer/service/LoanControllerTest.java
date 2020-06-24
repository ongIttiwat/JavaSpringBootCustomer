package com.digitalacademy.customer.service;

import com.digitalacademy.customer.api.LoanApi;
import com.digitalacademy.customer.controller.LoanController;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import javafx.beans.binding.StringExpression;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock
    LoanApi loanApi;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void testGetLoanInfo() throws Exception {
        Long reqId = 1L;
        GetLoanInfoResponse loanInfo = new GetLoanInfoResponse();
        loanInfo.setAccountPayable("102-222-2200");
        loanInfo.setAccountReceivable("102-222-2200");
        loanInfo.setStatus("OK");
        loanInfo.setId(1L);
        loanInfo.setPrincipalAmount(400000.0);

        when(loanApi.getLoanInfo(reqId)).thenReturn(
                loanInfo
        );

        MvcResult mvcResult = mvc.perform(get(
                "/loan/" + reqId
        )).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse()
                .getContentAsString());

        assertEquals(1L, resp.get("id"));
        assertEquals("OK", resp.get("status").toString());
        assertEquals("102-222-2200", resp.get("account_payable").toString());
        assertEquals("102-222-2200", resp.get("account_receivable").toString());
        assertEquals(400000.0, resp.get("principal_amount"));

    }

}
