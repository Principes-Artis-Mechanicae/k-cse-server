package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.apply.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
