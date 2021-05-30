package pl.hk.zadanie_29.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hk.zadanie_29.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}
