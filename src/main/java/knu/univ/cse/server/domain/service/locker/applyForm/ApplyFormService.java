package knu.univ.cse.server.domain.service.locker.applyForm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormCreateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormDuplicatedException;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormNotFoundException;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.domain.persistence.ApplyFormRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {
	/* internal dependency */
	private final ApplyFormRepository applyFormRepository;

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

	public ApplyFormReadDto getNowApplyForm() {
		ApplyForm applyForm = getActiveApplyForm();
		return ApplyFormReadDto.fromEntity(applyForm);
	}

	public boolean isActiveApplyForm() {
		long activeCount = applyFormRepository.countByStatus(ApplyFormStatus.ACTIVE);
		return activeCount == 1;
	}

	public ApplyForm getActiveApplyForm() {
		if (!isActiveApplyForm())
			throw new ApplyFormNotFoundException();
		return applyFormRepository.findByStatus(ApplyFormStatus.ACTIVE)
			.orElseThrow(ApplyFormNotFoundException::new);
	}
}
