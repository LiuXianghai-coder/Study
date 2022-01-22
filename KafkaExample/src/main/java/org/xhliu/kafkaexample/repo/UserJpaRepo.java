package org.xhliu.kafkaexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xhliu.kafkaexample.entity.UserEntity;

/**
 * @author xhliu
 * @time 2022-01-22-上午10:00
 */
@Repository
public interface UserJpaRepo extends JpaRepository<UserEntity, Long> {
}
