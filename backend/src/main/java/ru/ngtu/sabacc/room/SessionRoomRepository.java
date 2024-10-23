package ru.ngtu.sabacc.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SessionRoomRepository extends JpaRepository<SessionRoom, Long> {
    List<SessionRoom> findAllByMembers_Id(Long userId);
}