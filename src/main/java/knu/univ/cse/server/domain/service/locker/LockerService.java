package knu.univ.cse.server.domain.service.locker;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.exception.locker.LockerFullNotFoundException;
import knu.univ.cse.server.domain.exception.locker.LockerNotFoundException;
import knu.univ.cse.server.domain.model.locker.Locker;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.persistence.LockerRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LockerService {
	private final LockerRepository lockerRepository;

	/**
	 * 특정 이름과 신청 폼에 해당하는 사물함을 조회합니다.
	 *
	 * @param lockerName 사물함 이름
	 * @param applyForm 신청 폼 엔티티
	 * @return 조회된 사물함 엔티티
	 * @throws LockerNotFoundException "LOCKER_NOT_FOUND"
	 */
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Locker getLockerByLockerName(String lockerName, ApplyForm applyForm) {
		return lockerRepository.findAvailableLockerByLockerName(lockerName, applyForm)
			.orElseThrow(LockerNotFoundException::new);
	}

	/**
	 * 신청에 따라 랜덤한 사물함을 할당합니다.
	 *
	 * @param apply 신청 엔티티
	 * @return 할당된 사물함 엔티티
	 * @throws LockerFullNotFoundException "LOCKER_FULL_NOT_FOUND"
	 */
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Locker getRandomLocker(Apply apply) {
		List<Locker> lockers = getLockerByApplyWithoutAllocate(apply, apply.getApplyForm());
		if (lockers.isEmpty()) throw new LockerFullNotFoundException();

		/* 랜덤하게 Locker 선택 */
		Random random = new Random();
		return lockers.get(random.nextInt(lockers.size()));
	}

	/**
	 * 신청과 신청 폼에 따라 사물함을 조회하되, 할당되지 않은 사물함만 반환합니다.
	 *
	 * @param apply 신청 엔티티
	 * @param applyForm 신청 폼 엔티티
	 * @return 사용 가능한 사물함 리스트
	 * @throws LockerFullNotFoundException "LOCKER_FULL_NOT_FOUND"
	 */
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public List<Locker> getLockerByApplyWithoutAllocate(Apply apply, ApplyForm applyForm) {
		List<Integer> firstLockerHeight = apply.getFirstHeight().getLockerHeight();
		List<Locker> firstLockers = lockerRepository.findAvailableLockers(apply.getFirstFloor(), firstLockerHeight, applyForm);
		if (!firstLockers.isEmpty()) return firstLockers;

		List<Integer> secondLockerHeight = apply.getSecondHeight().getLockerHeight();
		List<Locker> secondLockers = lockerRepository.findAvailableLockers(apply.getSecondFloor(), secondLockerHeight, applyForm);
		if (!secondLockers.isEmpty()) return secondLockers;

		List<Locker> floorLockers = lockerRepository.findAvailableLockers(apply.getFirstFloor(), applyForm);
		if (!floorLockers.isEmpty()) return floorLockers;

		List<Locker> remainLockers = lockerRepository.findAvailableLockers(applyForm);
		if (!remainLockers.isEmpty()) return remainLockers;

		throw new LockerFullNotFoundException();
	}
}
