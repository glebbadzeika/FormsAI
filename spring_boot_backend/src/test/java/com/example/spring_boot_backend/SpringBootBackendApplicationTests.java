package com.example.spring_boot_backend;

import com.example.spring_boot_backend.controller.FormController;
import com.example.spring_boot_backend.controller.QuestionController;

import com.example.spring_boot_backend.controller.ReportController;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;



import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootBackendApplicationTests {

	@Autowired
	private FormController formController;
	@Autowired
	private QuestionController questionController;
	@Autowired
	private ReportController reportController;
	@Autowired
	private MockMvc mockMvc;

	private Matcher<String> doesNotContainString(String s) {
		return CoreMatchers.not(containsString(s));
	}

	@Test
	void contextLoads() {
		assertThat(formController).isNotNull();
		assertThat(questionController).isNotNull();
		assertThat(reportController).isNotNull();


	}

	@Test
	void testOfGettingAllForms() throws Exception {
		mockMvc.perform(get("/forms")).andExpect(status().isOk());

	}

	@Test
	void testOfCreatingNewForm() throws Exception {
		String jsonOfGoodForm= """
                {
                "title": "Sample Form ghghjg",
                "collect_email": false,
                "sections": [
                {
                "title": "Section 1",
                "description": "privet",

                "questions": [
                {
                "question_text": "When was the last time you went to the cinema?",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "it was last year",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "it was last month",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                },
                {
                "title": "Section 2",
                "description": "cmon pdu",

                "questions": [
                {
                "question_text": "OpenQuestion2",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "Reading",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "Swimming",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                }
                ]
                }""";
		mockMvc.perform(post("/forms")).andExpect(status().isBadRequest());
		mockMvc.perform(post("/forms").contentType("application/json").content(jsonOfGoodForm)).andExpect(status().isOk());
		mockMvc.perform(get("/forms")).andExpect(status().isOk());

		mockMvc.perform(get("/forms")).andExpect(content().string(containsString("Sample Form ghghjg")));

	}

	@Test
	void testOfFormUpdate() throws Exception {
		String jsonOfGoodForm= """
                {
                "title": "Sample Form ghghjg",
                "collect_email": false,
                "sections": [
                {
                "title": "Section 1",
                "description": "privet",

                "questions": [
                {
                "question_text": "When was the last time you went to the cinema?",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "it was last year",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "it was last month",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                },
                {
                "title": "Section_before_update",
                "description": "cmon pdu",

                "questions": [
                {
                "question_text": "OpenQuestion2",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "Reading",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "Swimming",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                }
                ]
                }""";

		mockMvc.perform(post("/forms").contentType("application/json").content(jsonOfGoodForm)).andExpect(status().isOk());


		String jsonOfGoodForm2= """
                {
                "title": "Sample Form ghghjg",
                "collect_email": false,
                "sections": [
                {
                "title": "Section 1",
                "description": "privet",

                "questions": [
                {
                "question_text": "When was the last time you went to the cinema?",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "it was last year",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "it was last month",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                },
                {
                "title": "Section__updated",
                "description": "cmon pdu",

                "questions": [
                {
                "question_text": "OpenQuestion2",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "Reading",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "Swimming",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                }
                ]
                }""";

		mockMvc.perform(put("/forms/2").contentType("application/json").content(jsonOfGoodForm2)).andExpect(status().isOk());
		mockMvc.perform(get("/forms")).andExpect(status().isOk());

		mockMvc.perform(get("/forms")).andExpect(content().string(doesNotContainString("Section_before_update")));
		mockMvc.perform(get("/forms")).andExpect(content().string(containsString("Section__updated")));

	}

//	@Test
//	void testFormDeletion() throws Exception{
//		String jsonOfGoodForm="{\n" +
//				"\"title\": \"Sample Form ghghjg\",\n" +
//				"\"collect_email\": false,\n" +
//				"\"sections\": [\n" +
//				"{\n" +
//				"\"title\": \"Section 1\",\n" +
//				"\"description\": \"privet\",\n" +
//				"\n" +
//				"\"questions\": [\n" +
//				"{\n" +
//				"\"question_text\": \"When was the last time you went to the cinema?\",\n" +
//				"\"question_type\": \"OPEN_QUESTION\",\n" +
//				"\"is_required\": true,\n" +
//				"\"options\": [\n" +
//				"{\n" +
//				"\"option_text\": \"it was last year\",\n" +
//				"\"is_open\": true,\n" +
//				"\"leads_to_another_section\": false\n" +
//				"},\n" +
//				"{\n" +
//				"\"option_text\": \"it was last month\",\n" +
//				"\"is_open\": false,\n" +
//				"\"leads_to_another_section\": false\n" +
//				"}\n" +
//				"]\n" +
//				"\n" +
//				"}\n" +
//				"]\n" +
//				"},\n" +
//				"{\n" +
//				"\"title\": \"Section 2\",\n" +
//				"\"description\": \"cmon pdu\",\n" +
//				"\n" +
//				"\"questions\": [\n" +
//				"{\n" +
//				"\"question_text\": \"OpenQuestion2\",\n" +
//				"\"question_type\": \"OPEN_QUESTION\",\n" +
//				"\"is_required\": true,\n" +
//				"\"options\": [\n" +
//				"{\n" +
//				"\"option_text\": \"Reading\",\n" +
//				"\"is_open\": true,\n" +
//				"\"leads_to_another_section\": false\n" +
//				"},\n" +
//				"{\n" +
//				"\"option_text\": \"Swimming\",\n" +
//				"\"is_open\": false,\n" +
//				"\"leads_to_another_section\": false\n" +
//				"}\n" +
//				"]\n" +
//				"\n" +
//				"}\n" +
//				"]\n" +
//				"}\n" +
//				"]\n" +
//				"}";
//
//		mockMvc.perform(post("/forms").contentType("application/json").content(jsonOfGoodForm)).andExpect(status().isOk());
//		mockMvc.perform(delete("/forms/1")).andExpect(status().isOk());
//
//	}

	@Test
	void testOfGettingAllAnswers() throws Exception {
		String jsonOfGoodForm= """
                {
                "title": "Sample Form ghghjg",
                "collect_email": false,
                "sections": [
                {
                "title": "Section 1",
                "description": "privet",

                "questions": [
                {
                "question_text": "When was the last time you went to the cinema?",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "it was last year",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "it was last month",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                },
                {
                "title": "Section 2",
                "description": "cmon pdu",

                "questions": [
                {
                "question_text": "OpenQuestion2",
                "question_type": "OPEN_QUESTION",
                "is_required": true,
                "options": [
                {
                "option_text": "Reading",
                "is_open": true,
                "leads_to_another_section": false
                },
                {
                "option_text": "Swimming",
                "is_open": false,
                "leads_to_another_section": false
                }
                ]

                }
                ]
                }
                ]
                }""";

		mockMvc.perform(post("/forms").contentType("application/json").content(jsonOfGoodForm)).andExpect(status().isOk());

		mockMvc.perform(get("/forms/1/answers")).andExpect(status().isOk());

	}

	@Test
	void testOfGettingAllAnswersForNonExistingForm() throws Exception {
		mockMvc.perform(get("/forms/155/answers")).andExpect(status().isBadRequest());

	}







}
