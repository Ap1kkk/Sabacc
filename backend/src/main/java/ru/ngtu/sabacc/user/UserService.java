package ru.ngtu.sabacc.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.system.config.user.UserConfigProperties;
import ru.ngtu.sabacc.system.event.UserDeletedEvent;
import ru.ngtu.sabacc.system.exception.UserAlreadyExistsException;
import ru.ngtu.sabacc.system.exception.notfound.EntityNotFoundException;
import ru.ngtu.sabacc.system.exception.notfound.UserNotFoundException;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConfigProperties userConfigProperties;
    private final ApplicationEventPublisher eventPublisher;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<Long> getAllExpiredUsers() {
        return userRepository.findAllExpiredUsers();
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deleting user: id={}", id);
        User user = getUserById(id);

        eventPublisher.publishEvent(new UserDeletedEvent(user));
        userRepository.delete(user);
    }

    @Transactional
    public User createOrLoginUser(LoginDto dto) {
        try {
            return getUserByUsername(dto.getUsername());
        }
        catch (EntityNotFoundException ignored) {
            return createUser(dto);
        }
    }

    private User createUser(LoginDto dto) {
        log.info("Creating user: {}", dto);

        User userToCreate = User.builder()
                .username(dto.getUsername())
                .createdAt(ZonedDateTime.now())
                .expireAt(ZonedDateTime.now()
                        .plus(Duration.ofHours(userConfigProperties.getExpirationInHours()))
                )
                .build();

        return userRepository.save(userToCreate);
    }
}
