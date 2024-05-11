package finconecta.spring.cloud.msvc.users.services;

import java.util.List;
import java.util.Optional;

import finconecta.spring.cloud.msvc.users.models.collections.User;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> findById(String id);
    User save(User user);
    User update(User user,String id);
    void delete(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
