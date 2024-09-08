package knu.univ.cse.server.domain.dues.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.dues.entity.Dues;

public interface DuesRepository extends JpaRepository<Dues, Long> {
}
