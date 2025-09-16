package com.teamread.poc.video_generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class VideoGeneratorApplicationTests {

	@MockBean
	private BedrockService bedrockService;

	@MockBean
	private S3Service s3Service;

	@Test
	void contextLoads() {
	}

}
