package kz.com.aven.Service;

import kz.com.aven.Entity.Status;
import kz.com.aven.Entity.User;
import kz.com.aven.Exception.UserNotFoundException;
import kz.com.aven.Repository.UserRepository;
import kz.com.aven.Response.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User getOneUserById(Long userId) {
        return userRepository.findById (userId).orElse (null);
    }

    public Long saveUser(User user) {
        userRepository.save (user);
        return user.getId ();
    }

    public UserStatus updateStatus(Long id, Status newStatus) {
        User user = userRepository.findById (id).orElseThrow (UserNotFoundException::new);
        Status oldStatus = user.getStatus ();
        user.setStatus (newStatus);
        userRepository.save (user);
        return new UserStatus (user.getId (), oldStatus, newStatus);
    }
}
