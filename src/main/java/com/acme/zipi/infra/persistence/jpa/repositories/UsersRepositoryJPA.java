package com.acme.zipi.infra.persistence.jpa.repositories;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.infra.persistence.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepositoryJPA extends JpaRepository<UserEntity, UUID> {

}
