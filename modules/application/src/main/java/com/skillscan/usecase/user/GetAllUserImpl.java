package com.skillscan.usecase.user;

import com.skillscan.mapper.user.UserMapper;
import com.skillscan.model.user.User;
import com.skillscan.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUserImpl  implements  GetAllUser {
    private final UserRepository userRepository;

    public GetAllUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute() {
        try{
            List<User> user = this.userRepository.all();

            if (user.isEmpty()) return new OutputPort.NoResults();

            return new OutputPort.Ok(
                    user.stream()
                            .map(UserMapper.INSTANCE::userToUserDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return new OutputPort.NotAuthorized();
        }
    }
}
