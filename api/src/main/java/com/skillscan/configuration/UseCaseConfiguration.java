package com.skillscan.configuration;

import com.skillscan.repository.*;
import com.skillscan.service.AnswerCheckServiceImpl;
import com.skillscan.service.CourseSuggestionServiceImpl;
import com.skillscan.service.CustomResponseServiceImpl;
import com.skillscan.service.TokenService;
import com.skillscan.usecase.authentication.AuthenticationGenerete;
import com.skillscan.usecase.authentication.AuthenticationGenerateImpl;
import com.skillscan.usecase.chartdata.GetChartDataByUserId;
import com.skillscan.usecase.chartdata.GetChartDataByUserIdImpl;
import com.skillscan.usecase.course.*;
import com.skillscan.usecase.question.*;
import com.skillscan.usecase.quizattempt.*;
import com.skillscan.usecase.randomquestion.GetRandomQuestions;
import com.skillscan.usecase.randomquestion.GetRandomQuestionsImpl;
import com.skillscan.usecase.technology.*;
import com.skillscan.usecase.user.*;
import com.skillscan.usecase.usertestresults.GetUserTestResultsImpl;
import com.skillscan.usecase.usertestresults.GetUserTestResults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class UseCaseConfiguration {

    @RequestScope
    @Bean
    public CreateUser createUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new CreateUserImpl(userRepository, passwordEncoder);
    }

    @RequestScope
    @Bean
    public GetAllUser getAllUser(UserRepository userRepository) {
        return new GetAllUserImpl(userRepository);
    }

    @RequestScope
    @Bean
    public UpdateUser updateUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UpdateUserImpl(userRepository, passwordEncoder);
    }

    @RequestScope
    @Bean
    public DeleteUser deleteUser(UserRepository userRepository) {
        return new DeleteUserImpl(userRepository);
    }

    @RequestScope
    @Bean
    public CreateTechnology createTechnology(TechnologyRepository technologyRepository) {
        return new CreateTechnologyImpl(technologyRepository);
    }

    @RequestScope
    @Bean
    public GetAllTechnology getAllTechnology(TechnologyRepository technologyRepository) {
        return new GetAllTechnologyImpl(technologyRepository);
    }

    @RequestScope
    @Bean
    public UpdateTechnology updateTechnology(TechnologyRepository technologyRepository) {
        return new UpdateTechnologyImpl(technologyRepository);
    }

    @RequestScope
    @Bean
    public DeleteTechnology deleteTechnology(TechnologyRepository technologyRepository) {
        return new DeleteTechnologyImpl(technologyRepository);
    }

    @RequestScope
    @Bean
    public CreateCourse createCourse(CourseRepository courseRepository) {
        return new CreateCourseImpl(courseRepository);
    }

    @RequestScope
    @Bean
    public GetAllCourse getAllCourse(CourseRepository courseRepository) {
        return new GetAllCourseImpl(courseRepository);
    }

    @RequestScope
    @Bean
    public UpdateCourse updateCourse(CourseRepository courseRepository) {
        return new UpdateCourseImpl(courseRepository);
    }

    @RequestScope
    @Bean
    public DeleteCourse deleteCourse(CourseRepository courseRepository) {
        return new DeleteCourseImpl(courseRepository);
    }

    @RequestScope
    @Bean
    public CreateQuestion createQuestion(QuestionRepository questionRepository) {
        return new CreateQuestionImpl(questionRepository);
    }

    @RequestScope
    @Bean
    public GetAllQuestion getAllQuestion(QuestionRepository questionRepository) {
        return new GetAllQuestionImpl(questionRepository);
    }

    @RequestScope
    @Bean
    public UpdateQuestion updateQuestion(QuestionRepository questionRepository) {
        return new UpdateQuestionImpl(questionRepository);
    }

    @RequestScope
    @Bean
    public DeleteQuestion deleteQuestion(QuestionRepository questionRepository) {
        return new DeleteQuestionImpl(questionRepository);
    }

    @RequestScope
    @Bean
    public GetRandomQuestions getRandomQuestions(MongoTemplate mongoTemplate, QuizAttemptRepository quizAttemptRepository) {
        return new GetRandomQuestionsImpl(mongoTemplate, quizAttemptRepository);
    }

    @RequestScope
    @Bean
    public CreateQuizAttempt createQuizAttempt(AnswerCheckServiceImpl answerCheckServiceImpl, CourseSuggestionServiceImpl courseSuggestionServiceImpl, CustomResponseServiceImpl customResponseServiceImpl, QuizAttemptRepository quizAttemptRepository) {
        return new CreateQuizAttemptImpl(answerCheckServiceImpl, courseSuggestionServiceImpl, customResponseServiceImpl, quizAttemptRepository);
    }

    @RequestScope
    @Bean
    public GetTechnologyById getTechnologyById(TechnologyRepository technologyRepository) {
        return new GetTechnologyByIdImpl(technologyRepository);
    }

    @RequestScope
    @Bean
    public GetCourseById getCourseById(CourseRepository courseRepository) {
        return new GetCourseByIdImpl(courseRepository);
    }

    @RequestScope
    @Bean
    public GetUserById getUserById(UserRepository userRepository) {
        return new GetUserByIdImpl(userRepository);
    }

    @RequestScope
    @Bean
    public GetQuestionById getQuestion(QuestionRepository questionRepository) {
        return new GetQuestionByIdImpl(questionRepository);
    }

    @RequestScope
    @Bean
    public GetAllQuizAttempt getAllQuizAttempt(QuizAttemptRepository quizAttemptRepository) {
        return new GetAllQuizAttemptImpl(quizAttemptRepository);
    }

    @RequestScope
    @Bean
    public GetQuizAttemptById getQuizAttemptById(QuizAttemptRepository quizAttemptRepository) {
        return new GetQuizAttemptByIdImpl(quizAttemptRepository);
    }

    @RequestScope
    @Bean
    public GetChartDataByUserId getChartDataById(QuizAttemptRepository quizAttemptRepository, TechnologyRepository technologyRepository) {
        return new GetChartDataByUserIdImpl(quizAttemptRepository, technologyRepository);
    }

    @RequestScope
    @Bean
    public AuthenticationGenerete authenticationGenerete(AuthenticationManager authenticationManager, TokenService tokenService) {
        return new AuthenticationGenerateImpl(authenticationManager, tokenService);
    }

    @RequestScope
    @Bean
    public GetUserTestResults getUserTestResults(UserRepository userRepository, QuizAttemptRepository quizAttemptRepository, TechnologyRepository technologyRepository) {
        return new GetUserTestResultsImpl(userRepository, quizAttemptRepository, technologyRepository);
    }
}
