package com.nexora.digisolutions.nexora.digisolutions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class NexoraDigiSolutionsWebsiteApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@ParameterizedTest
	@ValueSource(strings = {"/", "/about", "/services", "/portfolio", "/contact", "/privacy", "/terms"})
	void rendersEveryPage(String path) throws Exception {
		mockMvc.perform(get(path))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Nexora")));
	}

	@Test
	void landingPageShowsWebAndMarketingCube() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("digital-cube")))
				.andExpect(content().string(containsString("Web<br>development")))
				.andExpect(content().string(containsString("Digital<br>marketing")));
	}

	@Test
	void aboutPageShowsAnimatedWalkingIllustrationAndServices() throws Exception {
		mockMvc.perform(get("/about"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("walking-person")))
				.andExpect(content().string(containsString("Web development")))
				.andExpect(content().string(containsString("Digital marketing")));
	}

	@Test
	void rejectsInvalidContactSubmission() throws Exception {
		mockMvc.perform(post("/contact")
						.param("name", "")
						.param("email", "not-an-email")
						.param("phone", "invalid")
						.param("subject", "")
						.param("message", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("contact"))
				.andExpect(content().string(containsString("Please enter your name.")))
				.andExpect(content().string(containsString("Please enter a valid email address.")));
	}

	@Test
	void validatesContactSubmissionWithoutClaimingDelivery() throws Exception {
		mockMvc.perform(post("/contact")
						.param("name", "Asha")
						.param("email", "asha@example.com")
						.param("phone", "+91 63031 80265")
						.param("subject", "Website enquiry")
						.param("message", "I would like to discuss a new website."))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/contact"))
				.andExpect(flash().attributeExists("deliveryNotice"));
	}
}
