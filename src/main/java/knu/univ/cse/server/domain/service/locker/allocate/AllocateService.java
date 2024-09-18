package knu.univ.cse.server.domain.service.locker.allocate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.api.locker.allocate.dto.AllocateReadDto;
import knu.univ.cse.server.domain.exception.locker.LockerFullNotFoundException;
import knu.univ.cse.server.domain.exception.locker.LockerNotFoundException;
import knu.univ.cse.server.domain.exception.locker.allocate.AllocateDuplicatedException;
import knu.univ.cse.server.domain.exception.locker.apply.ApplyNotFoundException;
import knu.univ.cse.server.domain.exception.student.StudentNotFoundException;
import knu.univ.cse.server.domain.model.locker.Locker;
import knu.univ.cse.server.domain.model.locker.allocate.Allocate;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.persistence.AllocateRepository;
import knu.univ.cse.server.domain.persistence.LockerRepository;
import knu.univ.cse.server.domain.service.locker.LockerService;
import knu.univ.cse.server.domain.service.locker.apply.ApplyService;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.domain.service.student.StudentService;
import knu.univ.cse.server.domain.service.student.dues.DuesService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllocateService {
	/* Internal Dependencies */
	private final AllocateRepository allocateRepository;

	/* External Dependencies */
	private final StudentService studentService;
	private final ApplyService applyService;
	private final LockerService lockerService;
	private final ApplyFormService applyFormService;
	private final DuesService duesService;

	/**
	 * 학번으로 받은 학생의 신청을 특정 사물함에 할당합니다.
	 *
	 * @param studentNumber 학생의 학번
	 * @param lockerName 할당할 사물함의 이름
	 * @return 할당된 사물함의 DTO
	 * @throws StudentNotFoundException "STUDENT_NOT_FOUND"
	 * @throws ApplyNotFoundException "APPLY_NOT_FOUND"
	 * @throws LockerNotFoundException "LOCKER_NOT_FOUND"
	 * @throws AllocateDuplicatedException "ALLOCATE_DUPLICATED"
	 */
	@Transactional
	public AllocateReadDto allocateLockerByStudentNumber(String studentNumber, String lockerName) {
		// 1. 학번으로 학생을 찾는다.
		Student student = studentService.findStudentByStudentNumber(studentNumber);

		// 2. 현재 활성화된 신청폼을 찾는다.
		ApplyForm applyForm = applyFormService.getActiveApplyForm();

		// 3. 현재 활성화된 신청폼에서 해당 학생의 미처리된 신청을 찾는다.
		Apply targetApply = applyService
			.getApplyByStudentNumberAndApplyFormWhenStatusIsApply(student, applyForm);

		// 4. 학생에게 부여할 사물함을 찾고 고장 및 유효성 검사를 한다.
		Locker targetLocker = lockerService.getLockerByLockerName(lockerName, applyForm);

		// 5. Allocate 객체를 생성하고 저장한다.
		Allocate allocate = Allocate.builder()
			.applyForm(applyForm)
			.student(student)
			.locker(targetLocker)
			.apply(targetApply)
			.build();

		allocateRepository.save(allocate);

		// 6. 신청 상태를 승인으로 변경한다.
		applyService.updateApplyStatus(targetApply, ApplyStatus.APPROVE);

		// 7. AllocateReadDto 를 반환한다.
		return AllocateReadDto.fromEntity(student, targetApply, applyForm, targetLocker);
	}

	/**
	 * 학번으로 받은 학생의 신청을 랜덤 사물함에 할당합니다.
	 *
	 * @param studentNumber 학생의 학번
	 * @return 할당된 사물함의 DTO
	 * @throws StudentNotFoundException "STUDENT_NOT_FOUND"
	 * @throws ApplyNotFoundException "APPLY_NOT_FOUND"
	 * @throws LockerFullNotFoundException "LOCKER_FULL_NOT_FOUND"
	 * @throws AllocateDuplicatedException "ALLOCATE_DUPLICATED"
	 */
	@Transactional
	public AllocateReadDto allocateRandomLockerByStudentNumber(String studentNumber) {
		// 1. 학번으로 학생을 찾는다.
		Student student = studentService.findStudentByStudentNumber(studentNumber);

		// 2. 현재 활성화된 신청폼을 찾는다.
		ApplyForm applyForm = applyFormService.getActiveApplyForm();

		// 3. 현재 활성화된 신청폼에서 해당 학생의 미처리된 신청을 찾는다.
		Apply targetApply = applyService
			.getApplyByStudentNumberAndApplyFormWhenStatusIsApply(student, applyForm);

		// 4. 학생에게 부여할 랜덤 사물함을 찾고 고장 및 유효성 검사를 한다.
		Locker targetLocker = lockerService.getRandomLocker(targetApply);

		// 5. Allocate 객체를 생성하고 저장한다.
		Allocate allocate = Allocate.builder()
			.applyForm(applyForm)
			.student(student)
			.locker(targetLocker)
			.apply(targetApply)
			.build();

		allocateRepository.save(allocate);

		// 6. 신청 상태를 승인으로 변경한다.
		applyService.updateApplyStatus(targetApply, ApplyStatus.APPROVE);

		// 7. AllocateReadDto 를 반환한다.
		return AllocateReadDto.fromEntity(student, targetApply, applyForm, targetLocker);
	}

	/**
	 * 현재 활성화된 신청 폼의 모든 신청에 대해 랜덤 사물함을 할당합니다.
	 *
	 * @return 할당된 모든 사물함의 DTO 리스트
	 * @throws LockerFullNotFoundException "LOCKER_FULL_NOT_FOUND"
	 * @throws ApplyNotFoundException "APPLY_NOT_FOUND"
	 */
	/**
	 * 현재 활성화된 신청 폼의 모든 신청에 대해 랜덤 사물함을 할당합니다.
	 *
	 * @return 할당된 모든 사물함의 DTO 리스트
	 * @throws LockerFullNotFoundException "LOCKER_FULL_NOT_FOUND"
	 * @throws ApplyNotFoundException "APPLY_NOT_FOUND"
	 */
	@Transactional
	public List<AllocateReadDto> allocateAllLockers() {
		// 1. 현재 활성화된 신청폼을 찾는다.
		ApplyForm applyForm = applyFormService.getActiveApplyForm();

		// 2. 현재 활성화된 신청폼에서 모든 미처리된 신청을 찾는다.
		List<Apply> applies = applyService.getAppliesByApplyFormAndStatus(applyForm, ApplyStatus.APPLY);
		if (applies.isEmpty()) throw new ApplyNotFoundException();

		// 3. 신청별 가중치를 계산하여 Pair 리스트로 저장
		List<ApplyWithWeight> applyWithWeights = new ArrayList<>();
		for (Apply apply : applies) {
			long studentId = apply.getStudent().getId();
			int weight = 0;

			// 회비 납부 여부에 따른 가중치 부여
			if (duesService.isDues(studentId)) {
				weight += 100;
			}

			// todo: 추가적인 가중치 부여 로직

			applyWithWeights.add(new ApplyWithWeight(apply, weight));
		}

		// 4. 가중치가 높은 순으로 정렬
		applyWithWeights.sort(Comparator.comparingInt(ApplyWithWeight::getWeight).reversed());

		// 5. 정렬된 신청 리스트로부터 AllocateReadDto 리스트 생성
		List<AllocateReadDto> allocateReadDtos = new ArrayList<>();

		for (ApplyWithWeight applyWithWeight : applyWithWeights) {
			Apply apply = applyWithWeight.getApply();

			try {
				// 5.1. 학생에게 부여할 랜덤 사물함을 찾고 고장 및 유효성 검사를 한다.
				Locker targetLocker = lockerService.getRandomLocker(apply);

				// 5.2. Allocate 객체를 생성하고 저장한다.
				Allocate allocate = Allocate.builder()
					.applyForm(applyForm)
					.student(apply.getStudent())
					.locker(targetLocker)
					.apply(apply)
					.build();

				allocateRepository.save(allocate);

				// 5.3. 신청 상태를 승인으로 변경한다.
				applyService.updateApplyStatus(apply, ApplyStatus.APPROVE);

				// 5.4. AllocateReadDto 를 생성하여 리스트에 추가한다.
				AllocateReadDto dto = AllocateReadDto.fromEntity(apply.getStudent(), apply, applyForm, targetLocker);
				allocateReadDtos.add(dto);
			} catch (LockerFullNotFoundException e) {
				// 모든 Locker 가 할당되어 있거나 고장 상태인 경우, 더 이상 할당 불가
				throw new LockerFullNotFoundException();
			}
		}

		return allocateReadDtos;
	}

	/**
	 * 신청과 가중치를 함께 저장하기 위한 내부 클래스
	 */
	@Getter
	private static class ApplyWithWeight {
		private final Apply apply;
		private final int weight;

		public ApplyWithWeight(Apply apply, int weight) {
			this.apply = apply;
			this.weight = weight;
		}

	}
}
