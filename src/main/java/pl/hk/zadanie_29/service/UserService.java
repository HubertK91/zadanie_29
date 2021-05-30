package pl.hk.zadanie_29.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.hk.zadanie_29.model.Role;
import pl.hk.zadanie_29.model.User;
import pl.hk.zadanie_29.model.UserRole;
import pl.hk.zadanie_29.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String rawPassword) {
        User userToAdd = new User();

        userToAdd.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        userToAdd.setPassword(encryptedPassword);

        List<UserRole> list = Collections.singletonList(new UserRole(userToAdd, Role.ROLE_USER));
        userToAdd.setRoles(new HashSet<>(list));

        userRepository.save(userToAdd);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    public User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void updateUserData(User user) {
        Optional<User> userEdit = userRepository.findById(user.getId());
        if (userEdit.isPresent()){
            userEdit.get().setUsername(user.getUsername());
            userEdit.get().setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userEdit.get());
        }else {
            throw new RuntimeException();
        }
    }

    public void updateUserRole(User user) {
        Optional<User> userEdit = userRepository.findById(user.getId());
        if (userEdit.isPresent()){
            userEdit.get().setRoles(user.getRoles());
            userRepository.save(userEdit.get());
        }else {
            throw new RuntimeException();
        }
    }
}
