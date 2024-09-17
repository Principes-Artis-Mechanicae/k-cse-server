package knu.univ.cse.server.domain.service.locker.applyForm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.dto.request.ApplyFormCreateDto;
import knu.univ.cse.server.domain.dto.request.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.dto.response.ApplyFormReadDto;
import knu.univ.cse.server.domain.exception.locker.ApplyFormDuplicatedException;
import knu.univ.cse.server.domain.exception.locker.ApplyFormNotFoundException;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.domain.persistence.ApplyFormRepository;
import knu.univ.cse.server.domain.persistence.StudentRepository;
import knu.univ.cse.server.global.exception.ErrorCode;
import knu.univ.cse.server.global.exception.support.DuplicatedException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {
	/* internal dependency */
	private final ApplyFormRepository applyFormRepository;

	/* external dependency */
	private final StudentRepository studentRepository;

	@Transactional
	public ApplyFormReadDto createApplyForm(ApplyFormCreateDto requestBody) {
		/* Check if the student not exists */
		if (applyFormRepository.existsByYearAndSemester(requestBody.year(), requestBody.semester()))
			throw new ApplyFormDuplicatedException();

		/* Save the apply entity */
		ApplyForm applyForm = applyFormRepository.save(requestBody.toEntity());

		/* Return the created apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}


	@Transactional
	public ApplyFormReadDto updateApplyForm(Integer year, Integer semester, ApplyFormUpdateDto requestBody) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Update the apply entity */
		applyForm.update(requestBody);
		applyFormRepository.save(applyForm);

		/* Return the updated apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	@Transactional
	public void deleteApplyForm(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Delete the apply entity */
		applyFormRepository.delete(applyForm);
	}

	public ApplyFormReadDto getApplyForm(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Return the apply entity */
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	public List<ApplyFormReadDto> getAllApplyForms() {
		/* Return the apply entities */
		return applyFormRepository.findAll().stream()
			.map(ApplyFormReadDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public String updateApplyFormStatus(Integer year, Integer semester) {
		/* Check if the student exists */
		ApplyForm applyForm = applyFormRepository.findByYearAndSemester(year, semester)
			.orElseThrow(ApplyFormNotFoundException::new);

		/* Update the apply entity */
		applyForm.updateStatus(
			applyForm.getStatus() == ApplyFormStatus.ACTIVE ? ApplyFormStatus.INACTIVE : ApplyFormStatus.ACTIVE
		);
		applyFormRepository.save(applyForm);

		/* Return the updated apply entity */
		// return ApplyFormReadDto.fromEntity(applyForm);
	}
}
