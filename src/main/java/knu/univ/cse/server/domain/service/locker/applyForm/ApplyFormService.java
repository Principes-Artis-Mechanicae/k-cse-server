package knu.univ.cse.server.domain.service.locker.applyForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormCreateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormDuplicatedException;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormNotFoundException;
import knu.univ.cse.server.domain.exception.locker.applyForm.InvalidApplyFormDateException;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.domain.persistence.ApplyFormRepository;
import knu.univ.cse.server.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {
	/* internal dependency */
	private final ApplyFormRepository applyFormRepository;

	/**
	 * 새로운 신청 폼을 생성합니다.
	 *
	 * @param requestBody 신청 폼 생성 DTO
	 * @return 생성된 신청 폼을 나타내는 DTO
	 * @throws ApplyFormDuplicatedException "APPLY_FORM_DUPLICATED"
	 * @throws InvalidApplyFormDateException "INVALID_APPLY_FORM_DATE"
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ApplyFormReadDto createApplyForm(ApplyFormCreateDto requestBody) {
		/* Check if the apply form already exists */
		if (applyFormRepository.existsByYearAndSemester(requestBody.year(), requestBody.semester()))
			throw new ApplyFormDuplicatedException();

		/* Validate the dates */
		validateApplyFormDates(requestBody);

		/* Save the apply entity */
		ApplyForm applyForm = applyFormRepository.save(requestBody.toEntity());

		/* Return the created apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	/**
	 * 기존 신청 폼을 업데이트합니다.
	 *
	 * @param year 연도
	 * @param semester 학기
	 * @param requestBody 신청 폼 업데이트 DTO
	 * @return 업데이트된 신청 폼을 나타내는 DTO
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 * @throws InvalidApplyFormDateException "INVALID_APPLY_FORM_DATE"
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ApplyFormReadDto updateApplyForm(Integer year, Integer semester, ApplyFormUpdateDto requestBody) {
		/* Check if the apply form exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Validate the dates if they are being updated */
		if (requestBody.firstApplyStartDate() != null && requestBody.firstApplyEndDate() != null && requestBody.semesterEndDate() != null) {
			validateApplyFormDates(applyForm, requestBody);
		}

		/* Update the apply entity */
		applyForm.update(requestBody);
		applyFormRepository.save(applyForm);

		/* Return the updated apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	/**
	 * 특정 신청 폼을 삭제합니다.
	 *
	 * @param year 연도
	 * @param semester 학기
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void deleteApplyForm(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Delete the apply entity */
		applyFormRepository.delete(applyForm);
	}

	/**
	 * 특정 신청 폼을 조회합니다.
	 *
	 * @param year 연도
	 * @param semester 학기
	 * @return 조회된 신청 폼을 나타내는 DTO
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 */
	public ApplyFormReadDto getApplyForm(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = getApplyFormByYearAndSemester(year, semester);

		/* Return the apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	/**
	 * 모든 신청 폼을 조회합니다.
	 *
	 * @return 모든 신청 폼의 DTO 리스트
	 */
	public List<ApplyFormReadDto> getAllApplyForms() {
		/* Return the apply entities */
		return applyFormRepository.findAll().stream()
			.map(ApplyFormReadDto::fromEntity)
			.collect(Collectors.toList());
	}

	/**
	 * 특정 신청 폼의 상태를 업데이트합니다.
	 *
	 * @param year 연도
	 * @param semester 학기
	 * @return 업데이트된 신청 폼을 나타내는 DTO
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 * @throws ApplyFormDuplicatedException "APPLY_FORM_DUPLICATED"
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ApplyFormReadDto updateApplyFormStatus(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Update the apply entity */
		if (applyForm.getStatus() == ApplyFormStatus.INACTIVE) {
			if (isActiveApplyForm())
				throw new ApplyFormDuplicatedException();
			applyForm.updateStatus(ApplyFormStatus.ACTIVE);
		} else {
			applyForm.updateStatus(ApplyFormStatus.INACTIVE);
		}

		applyFormRepository.save(applyForm);

		/* Return the updated apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	/**
	 * 현재 활성화된 신청 폼을 조회합니다.
	 *
	 * @return 현재 활성화된 신청 폼을 나타내는 DTO
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 */
	public ApplyFormReadDto getNowApplyForm() {
		ApplyForm applyForm = getActiveApplyForm();
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	/**
	 * 활성화된 신청 폼이 있는지 확인합니다.
	 *
	 * @return 활성화된 신청 폼이 있으면 true, 없으면 false
	 */
	public boolean isActiveApplyForm() {
		long activeCount = applyFormRepository.countByStatus(ApplyFormStatus.ACTIVE);
		return activeCount == 1;
	}

	/**
	 * 활성화된 신청 폼을 조회합니다.
	 *
	 * @return 활성화된 신청 폼 엔티티
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 */
	public ApplyForm getActiveApplyForm() {
		if (!isActiveApplyForm())
			throw new ApplyFormNotFoundException();
		return applyFormRepository.findByStatus(ApplyFormStatus.ACTIVE)
			.orElseThrow(ApplyFormNotFoundException::new);
	}

	/**
	 * 특정 연도와 학기의 신청 폼을 조회합니다.
	 *
	 * @param year 연도
	 * @param semester 학기
	 * @return 조회된 신청 폼 엔티티
	 * @throws ApplyFormNotFoundException "APPLY_FORM_NOT_FOUND"
	 */
	public ApplyForm getApplyFormByYearAndSemester(Integer year, Integer semester) {
		return applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);
	}

	/**
	 * 신청 폼의 날짜 유효성을 검증합니다.
	 *
	 * @param requestBody 신청 폼 생성 DTO
	 * @throws InvalidApplyFormDateException "INVALID_APPLY_FORM_DATE"
	 */
	private void validateApplyFormDates(ApplyFormCreateDto requestBody) {
		LocalDateTime firstStartDate = DateTimeUtil.stringToLocalDateTime(requestBody.firstApplyStartDate());
		LocalDateTime firstEndDate = DateTimeUtil.stringToLocalDateTime(requestBody.firstApplyEndDate());
		LocalDateTime semesterEndDate = DateTimeUtil.stringToLocalDateTime(requestBody.semesterEndDate());

		if (!firstStartDate.isBefore(firstEndDate) || !firstEndDate.isBefore(semesterEndDate)) {
			throw new InvalidApplyFormDateException();
		}
	}


	/**
	 * 신청 폼의 날짜 유효성을 검증합니다.
	 *
	 * @param requestBody 신청 폼 생성 DTO
	 * @throws InvalidApplyFormDateException "INVALID_APPLY_FORM_DATE"
	 */
	private void validateApplyFormDates(ApplyForm applyForm, ApplyFormUpdateDto requestBody) {
		LocalDateTime firstStartDate =
			requestBody.firstApplyStartDate() == null ? applyForm.getFirstApplyStartDate() : DateTimeUtil.stringToLocalDateTime(requestBody.firstApplyStartDate());
		LocalDateTime firstEndDate =
			requestBody.firstApplyEndDate() == null ? applyForm.getFirstApplyEndDate() : DateTimeUtil.stringToLocalDateTime(requestBody.firstApplyEndDate());
		LocalDateTime semesterEndDate =
			requestBody.semesterEndDate() == null ? applyForm.getSemesterEndDate() : DateTimeUtil.stringToLocalDateTime(requestBody.semesterEndDate());

		if (!firstStartDate.isBefore(firstEndDate) || !firstEndDate.isBefore(semesterEndDate)) {
			throw new InvalidApplyFormDateException();
		}
	}
}
