package com.entropy.springboot3nativedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.ByteBuffer;

@SpringBootApplication
public class SpringBoot3NativeDemoApplication {

	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 100);
		System.out.println("main方法执行了....");
		byte b = byteBuffer.get();
		SpringApplication.run(SpringBoot3NativeDemoApplication.class, args);
	}

}
