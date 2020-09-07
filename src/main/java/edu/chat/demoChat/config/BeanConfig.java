package edu.chat.demoChat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class BeanConfig {
  @Bean
  public HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }
}
