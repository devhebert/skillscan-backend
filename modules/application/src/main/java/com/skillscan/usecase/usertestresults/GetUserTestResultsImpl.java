package com.skillscan.usecase.usertestresults;

import com.skillscan.dto.usertestresults.UserTestResultsDTO;
import com.skillscan.model.quizattempt.QuizAttempt;
import com.skillscan.model.tecnology.Technology;
import com.skillscan.model.user.User;
import com.skillscan.repository.QuizAttemptRepository;
import com.skillscan.repository.TechnologyRepository;
import com.skillscan.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

public class GetUserTestResultsImpl implements GetUserTestResults {
    private final UserRepository userRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final TechnologyRepository technologyRepository;

    public GetUserTestResultsImpl(UserRepository userRepository, QuizAttemptRepository quizAttemptRepository, TechnologyRepository technologyRepository) {
        this.userRepository = userRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.technologyRepository = technologyRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<User> users = this.userRepository.all();
            List<Technology> technologies = this.technologyRepository.all();

            List<UserTestResultsDTO> userTestResultsDTOList = users.stream()
                    .flatMap(user -> technologies.stream()
                            .map(tech -> new AbstractMap.SimpleEntry<>(user, tech)))
                    .flatMap(userTechPair -> {
                        List<QuizAttempt> quizAttempts = this.quizAttemptRepository.findByUserIdAndTechnologyIdOrderByTestDateDesc(userTechPair.getKey().getId(), userTechPair.getValue().getId());
                        return quizAttempts.stream()
                                .map(quizAttempt -> new UserTestResultsDTO(userTechPair.getKey().getId(), userTechPair.getKey().getUsername(), userTechPair.getValue().getId(), userTechPair.getValue().getName(), quizAttempt.getScore(), quizAttempt.getCourseSuggestions().size(), quizAttempt.getTestDate()));
                    })
                    .collect(Collectors.toList());

            if (userTestResultsDTOList.isEmpty()) {
                return new OutputPort.NoResults();
            } else {
                return new OutputPort.Ok(userTestResultsDTOList);
            }
        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
