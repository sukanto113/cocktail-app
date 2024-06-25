package com.cocktail.app;

import com.cocktail.app.models.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
}
