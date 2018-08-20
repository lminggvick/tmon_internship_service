package com.tmoncorp.template.controller;

import com.tmoncorp.core.api.rest.client.RestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mvc-config.xml", "classpath:/spring/application*" })
@WebAppConfiguration
public class TemplateControllerTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private RestClient restClient;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testGet() throws Exception {
//		mockMvc.perform(get("/api/template/index/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(handler().handlerType(.class)).andExpect(handler().methodName("index")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRestClient() throws Exception {
		Object obj = restClient.get("http://commonapi.dev.tmon.co.kr/api/gnb/categorys", Object.class);
		logger.debug("catagories :: {} ", obj);
	}
}
