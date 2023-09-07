package com.project.productService;

import com.project.productService.dto.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ObjectMapper.class})
@Slf4j
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@DynamicPropertySource
	static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void saveProducts() throws Exception {
		String data = objectMapper.writeValueAsString(getProduct());
	     mockMvc.getDispatcherServlet().getHandlerMappings().stream().forEach(h -> log.info(" {}", h.toString()));
		log.info("data : {}",data);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(data)
		).andExpect(status().isCreated());

	}
	private ProductRequest getProduct() {
		return ProductRequest.builder()
				.name("Laptop")
				.description("Powerful laptop with high-resolution display")
				.price(BigDecimal.valueOf(999.99))
				.build();
	}

	@Test
	void getProducts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/product")
		).andExpect(status().isOk());

	}

}
