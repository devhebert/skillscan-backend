package com.skillscan.configuration;

import com.skillscan.repository.CourseRepository;
import com.skillscan.repository.QuestionRepository;
import com.skillscan.repository.UserRepository;
import com.skillscan.service.*;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EntityScan(basePackages = "com.skillscan.model")
@EnableMongoRepositories(basePackages = "com.skillscan.repository")
public class ServiceConfiguration {
    @Bean
    AnswerCheckServiceImpl answerCheckService(QuestionRepository questionRepository) {
        return new AnswerCheckServiceImpl(questionRepository);
    }

    @Bean
    CourseSuggestionServiceImpl courseSuggestionService(CourseRepository courseRepository, QuestionRepository questionRepository) {
        return new CourseSuggestionServiceImpl(courseRepository, questionRepository);
    }

    @Bean
    CustomResponseService customResponseService() {
        return new CustomResponseServiceImpl();
    }

    @Bean
    AuthorizationService authorizationService(UserRepository userRepository) {
        return new AuthorizationService(userRepository);
    }

    @Bean
    TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    SecurityFilterComponent securityFilter(TokenService tokenService, UserRepository userRepository) {
        return new SecurityFilterComponent(tokenService, userRepository);
    }
}
