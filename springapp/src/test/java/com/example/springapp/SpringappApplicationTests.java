package com.example.springapp;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.json.simple.parser.ParseException;

@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringappApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

    private String generatedToken;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@Order(1)
	void testRegisterUser() throws Exception {
		String requestBody = "{\"email\": \"abcd@gmail.com\", \"password\": \"abc\", \"username\": \"abcd\", \"userRole\": \"APPLICANT\"}";
			mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").value(true))
					.andReturn();
	}

	@Test
	@Order(2)
	void testRegisterAdmin() throws Exception {
		String requestBody = "{\"email\": \"abc@gmail.com\", \"password\": \"abc\", \"username\": \"abc\", \"userRole\": \"ADMIN\"}";

			mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").value(true))
					.andReturn();
	}

	@Test
	@Order(3)
	public void testLoginUser() throws Exception {
		testRegisterUser();
			String requestBody = "{\"email\": \"abcd@gmail.com\", \"password\": \"abc\"}";

			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(responseString, Map.class);

            String token = responseMap.get("token");
            generatedToken = token;

			assertNotNull(token);
	}

	
    @Test
    @Order(4)
    public void testLoginAdmin() throws Exception {
        testRegisterAdmin();
        String requestBody = "{\"email\": \"abc@gmail.com\",\"password\": \"abc\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> responseMap = mapper.readValue(responseString, Map.class);

            String token = responseMap.get("token");
            generatedToken = token;

			assertNotNull(token);
	
    }

	@Test
	@Order(5)
	void testCreateJob() throws Exception {
        testLoginAdmin();
		String jobJson = "{\"jobTitle\":\"test\",\"department\":\"test\",\"location\":\"test\",\"responsibility\":\"test\",\"qualification\":\"test\",\"deadline\":\"2022-12-31T23:59:59.000+00:00\",\"category\":\"free\",\"amount\":\"100\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/Job")
        	.header("Authorization", "Bearer " + generatedToken)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jobJson).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();    
	}

	@Test
	@Order(6)
	void testGetAllJobs() throws Exception {
        testLoginAdmin();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/Job")
		.header("Authorization", "Bearer " + generatedToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
        .andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$").isArray())
			.andReturn();		
	}

    // @Test
    // @Order(7)
    // void testGetJobByJobTitle() throws Exception {
    //     testLoginAdmin();

    //     String jobTitle = "test";

    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/Job/{jobTitle}", jobTitle)
    //         .header("Authorization", "Bearer " + generatedToken)
    //         .contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(MockMvcResultMatchers.status().isOk())
    //         .andExpect(MockMvcResultMatchers.jsonPath("$.jobTitle", Matchers.is(jobTitle)))
    //         .andReturn();
    // }


    @Test
	@Order(7)
	void testUpdateJob() throws Exception {
        testLoginAdmin();
		String jobJson = "{\"jobTitle\":\"test\",\"department\":\"testing\",\"location\":\"test\",\"responsibility\":\"test\",\"qualification\":\"test\",\"deadline\":\"2022-12-31T23:59:59.000+00:00\",\"category\":\"free\",\"amount\":\"100\"}";
		mockMvc.perform(MockMvcRequestBuilders.put("/api/Job/1")
        .header("Authorization", "Bearer " + generatedToken)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jobJson).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();	
	}


	@Test
	@Order(8)
	void testCreateApplication() throws Exception {
        testLoginUser();
        String applicationJson =
        "{"
        + "\"applicationID\":1,"
        + "\"applicationName\":\"Application1\","
        + "\"contactNumber\":\"1234567890\","
        + "\"mailID\":\"application1@example.com\","
        + "\"jobTitle\":\"Software Engineer\","
        + "\"status\":\"Applied\","
        + "\"job\": {"
            + "\"jobID\": 1"
        + "}"
    + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/Applicant")
            .header("Authorization", "Bearer " + generatedToken)
		.contentType(MediaType.APPLICATION_JSON)
		.content(applicationJson)
		.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();	
	}

	@Test
	@Order(9)
	void testGetJob() throws Exception {
        testLoginUser();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/Applicant/getAlljob")
        .header("Authorization", "Bearer " + generatedToken)
        .contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();	
	}

	@Test
	@Order(10)
	void testMakePayment() throws Exception {
        testLoginUser();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/Applicant/make-payment")
        .header("Authorization", "Bearer " + generatedToken)
		.contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();	
	}


	@Test
	@Order(11)
	void testUpdateApplication() throws Exception {
        testLoginAdmin();
        String applicationJson = "{\"status\":\"Accepted\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/Applicant/ChangeStatus/1")
        .header("Authorization", "Bearer " + generatedToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(applicationJson).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();	
    }

    @Test
    @Order(12)
    void testGetFreeJobsAdmin() throws Exception {
        testLoginAdmin();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/Job/freeJobs")
        .header("Authorization", "Bearer " + generatedToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    }

    @Test
    @Order(13)
    void testGetFreeJobsUser() throws Exception {
        testLoginUser();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/Job/freeJobs")
        .header("Authorization", "Bearer " + generatedToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    }
   
    @Test
    @Order(14)
    void testGetPremiumJobsAdmin() throws Exception {
        testLoginAdmin();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/Job/premiumJobs")
        .header("Authorization", "Bearer " + generatedToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    }

    @Test
    @Order(15)
    void testGetPremiumJobsUser() throws Exception {
        testLoginUser();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/Job/premiumJobs")
        .header("Authorization", "Bearer " + generatedToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    }

}
