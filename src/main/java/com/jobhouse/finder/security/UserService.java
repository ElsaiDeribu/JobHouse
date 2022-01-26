package com.jobhouse.finder.security;

import com.jobhouse.finder.Helpers.RegistrationForm;
import com.jobhouse.finder.Tables.User;

public interface UserService {
    public User register(RegistrationForm form) throws UserAlreadyExistException;

    public boolean checkIfUserExist(String email);
}
