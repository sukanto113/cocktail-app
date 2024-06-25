package com.cocktail.app.repository;

import com.cocktail.app.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);
}
