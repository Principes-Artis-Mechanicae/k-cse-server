package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.student.dues.Dues;

public interface DuesRepository extends JpaRepository<Dues, Long> {
	boolean existsDuesById(Long id);
	Dues findDuesById(Long id);
}