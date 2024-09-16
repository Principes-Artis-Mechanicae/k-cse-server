package knu.univ.cse.server.domain.locker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.locker.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
