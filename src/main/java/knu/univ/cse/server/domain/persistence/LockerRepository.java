package knu.univ.cse.server.domain.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import knu.univ.cse.server.domain.model.locker.Locker;
import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;

public interface LockerRepository extends JpaRepository<Locker, String> {
	/**
	 * 특정 lockerName 으로 Locker 를 조회하되, 주어진 ApplyForm 과 연결되지 않았고,
	 * broken 이 false 인 사물함만 조회
	 */
	@Query("SELECT l FROM Locker l WHERE l.lockerName = :lockerName " +
		"AND l.broken = false " +
		"AND NOT EXISTS (SELECT 1 FROM Allocate a WHERE a.locker = l AND a.applyForm = :applyForm)")
	Optional<Locker> findAvailableLockerByLockerName(
		@Param("lockerName") String lockerName,
		@Param("applyForm") ApplyForm applyForm
	);

	/**
	 * floor, heights, applyForm 을 기준으로 사용 가능한 Locker 조회
	 * broken 이 false 인 사물함만 조회
	 */
	@Query("SELECT l FROM Locker l WHERE l.floor = :floor "
		+ "AND l.height IN :heights AND l.broken = false "
		+ "AND NOT EXISTS (SELECT 1 FROM Allocate a WHERE a.locker = l AND a.applyForm = :applyForm)")
	List<Locker> findAvailableLockers(
		@Param("floor") LockerFloor floor,
		@Param("heights") List<Integer> heights,
		@Param("applyForm") ApplyForm applyForm
	);

	/**
	 * floor 와 applyForm 을 기준으로 사용 가능한 Locker 조회 (heights 조건 없음)
	 * broken 이 false 인 사물함만 조회
	 */
	@Query("SELECT l FROM Locker l WHERE l.floor = :floor "
		+ "AND l.broken = false "
		+ "AND NOT EXISTS (SELECT 1 FROM Allocate a WHERE a.locker = l AND a.applyForm = :applyForm)")
	List<Locker> findAvailableLockers(
		@Param("floor") LockerFloor floor,
		@Param("applyForm") ApplyForm applyForm
	);

	/**
	 * applyForm 만을 기준으로 사용 가능한 Locker 조회 (floor, heights 조건 없음)
	 * broken 이 false 인 사물함만 조회
	 */
	@Query("SELECT l FROM Locker l WHERE l.broken = false "
		+ "AND NOT EXISTS (SELECT 1 FROM Allocate a WHERE a.locker = l AND a.applyForm = :applyForm)")
	List<Locker> findAvailableLockers(
		@Param("applyForm") ApplyForm applyForm
	);
}
