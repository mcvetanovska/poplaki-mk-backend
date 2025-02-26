package mk.poplaki.service;

import mk.poplaki.dto.auth.UserRequest;

public interface UserService {
    void createUser(UserRequest data);

    long getUserCount();
}
