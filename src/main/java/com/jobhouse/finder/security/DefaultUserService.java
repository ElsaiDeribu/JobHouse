package com.jobhouse.finder.security;

import com.jobhouse.finder.Helpers.RegistrationForm;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User register(RegistrationForm form) throws UserAlreadyExistException {

        if (checkIfUserExist(form.getUsername()) || checkIfUserExist(form.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this username or email");
        }
        User user = new User();

        BeanUtils.copyProperties(form, user);
        encodePassword(user, form);
        return user;
    }

    @Override
    public boolean checkIfUserExist(String email) {

        return userRepository.findByUsername(email) != null ? true : false;
    }

    private void encodePassword(User userEntity, RegistrationForm user) {
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}