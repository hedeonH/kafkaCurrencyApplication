package org.kpi.kafka;

import org.kpi.kafka.message.services.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    TestService testService = context.getBean(TestService.class);
    testService.test();
  }
}