package com.skillscan.service;

import com.skillscan.dto.course.CourseSuggestionDTO;
import com.skillscan.dto.question.QuestionResponseDTO;
import com.skillscan.model.course.Course;
import com.skillscan.model.question.Question;
import com.skillscan.repository.CourseRepository;
import com.skillscan.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseSuggestionServiceImpl implements CourseSuggestionService {
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;

    public CourseSuggestionServiceImpl(CourseRepository courseRepository, QuestionRepository questionRepository) {
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
    }

    public List<CourseSuggestionDTO> suggestCourses(List<QuestionResponseDTO> wrongAnswers, UUID technologyId) {
        List<Course> allCourses = this.courseRepository.findByTechnologyId(technologyId);

        List<String> questionWords = wrongAnswers.stream()
                .flatMap(wrongAnswer -> {
                    Optional<Question> question = questionRepository.findById(wrongAnswer.questionId());
                    return Arrays.stream(question.get().getQuestionText().toLowerCase().split("\\s+"));
                })
                .toList();

        Map<Course, Integer> courseScores = new HashMap<>();
        for (Course course : allCourses) {
            for (String keyword : course.getKeywords()) {
                if (questionWords.contains(keyword.toLowerCase())) {
                    courseScores.put(course, courseScores.getOrDefault(course, 0) + 1);
                }
            }
        }

        return courseScores.entrySet().stream()
                .sorted(Map.Entry.<Course, Integer>comparingByValue().reversed())
                .map(entry -> new CourseSuggestionDTO(entry.getKey().getName(), entry.getKey().getContent()))
                .collect(Collectors.toList());
    }
}
