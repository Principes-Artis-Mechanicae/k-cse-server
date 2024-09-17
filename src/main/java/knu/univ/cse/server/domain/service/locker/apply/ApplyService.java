package knu.univ.cse.server.domain.service.locker.apply;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.api.locker.apply.dto.ApplyCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReadDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportReadDto;
import knu.univ.cse.server.domain.exception.locker.apply.ApplyNotFoundException;
import knu.univ.cse.server.domain.exception.locker.apply.InvalidApplyPeriodException;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyPeriod;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.report.Report;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.persistence.ApplyRepository;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.domain.service.locker.report.ReportService;
import knu.univ.cse.server.domain.service.student.StudentService;
import knu.univ.cse.server.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {
	/* Internal Dependencies */
	private final ApplyRepository applyRepository;

	/* External Dependencies */
	private final ApplyFormService applyFormService;
	private final StudentService studentService;
	private final ReportService reportService;

	/**
	 * Handles the creation of a primary application.
	 *
	 * @param createDto The DTO containing application details.
	 * @return The DTO representing the created application.
	 */
	@Transactional
	public ApplyReadDto handlePrimaryApply(ApplyCreateDto createDto) {
		return processApplication(createDto, ApplyPeriod.PRIMARY);
	}

	/**
	 * Handles the creation of an additional application.
	 *
	 * @param createDto The DTO containing application details.
	 * @return The DTO representing the created application.
	 */
	@Transactional
	public ApplyReadDto handleAdditionalApply(ApplyCreateDto createDto) {
		return processApplication(createDto, ApplyPeriod.ADDITIONAL);
	}

	/**
	 * Handles the creation of a replacement application along with a report.
	 *
	 * @param createDto The DTO containing application and report details.
	 * @return The DTO representing the created application.
	 */
	@Transactional
	public ApplyReportReadDto handleReplacementApply(ApplyReportCreateDto createDto) {
		ApplyReadDto applyReadDto = processApplication(createDto.apply(), ApplyPeriod.REPLACEMENT);
		Report report = attachReport(createDto.content(), applyReadDto.applyId());
		return ApplyReportReadDto.of(applyReadDto, report);
	}

	/**
	 * Processes the application based on the specified period and report content.
	 *
	 * @param createDto    The DTO containing application details.
	 * @param period       The period of the application.
	 * @return The DTO representing the created application.
	 */
	private ApplyReadDto processApplication(ApplyCreateDto createDto, ApplyPeriod period) {
		ApplyForm activeApplyForm = applyFormService.getActiveApplyForm();
		validateApplicationPeriod(activeApplyForm, period);

		Student student = retrieveStudent(createDto);
		Apply apply = applyRepository.save(createDto.toEntity(student, period));

		return ApplyReadDto.fromEntity(apply, student);
	}

	/**
	 * Attaches a report to the specified application.
	 *
	 * @param content  The content of the report.
	 * @param applyId The ID of the application to attach the report to.
	 */
	private Report attachReport(String content, Long applyId) {
		Apply apply = applyRepository.findById(applyId)
			.orElseThrow(ApplyNotFoundException::new);
		return reportService.writeReport(content, apply);
	}

	/**
	 * Validates whether the current time is within the application period.
	 *
	 * @param applyForm The active application form.
	 * @param period    The period of the application.
	 */
	private void validateApplicationPeriod(ApplyForm applyForm, ApplyPeriod period) {
		LocalDateTime now = DateTimeUtil.now();
		if (!period.isWithinPeriod(applyForm, now)) {
			throw new InvalidApplyPeriodException();
		}
	}

	/**
	 * Retrieves the student based on the provided application details.
	 *
	 * @param createDto The DTO containing student details.
	 * @return The Student entity.
	 */
	private Student retrieveStudent(ApplyCreateDto createDto) {
		return studentService.findStudentByNameAndNumber(
			createDto.studentName(),
			createDto.studentNumber()
		);
	}

	/**
	 * Retrieves the application by the student number.
	 *
	 * @param studentNumber The student number to search for.
	 * @return The DTO representing the application.
	 */
	public ApplyReadDto getApplyByStudentNumber(String studentNumber) {
		Student student = studentService.findStudentByStudentNumber(studentNumber);
		Apply apply = applyRepository.findByStudent(student)
			.orElseThrow(ApplyNotFoundException::new);
		return ApplyReadDto.fromEntity(apply, student);
	}
}
