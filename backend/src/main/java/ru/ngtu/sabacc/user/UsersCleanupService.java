package ru.ngtu.sabacc.user;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersCleanupService {

    private final UserService userService;

    @Scheduled(cron = "0 * * * * *")
    public void cleanUp() {
        List<Long> allExpiredUserIds = userService.getAllExpiredUsers();
        for (Long userId : allExpiredUserIds) {
            userService.deleteUserById(userId);
        }
    }
}
