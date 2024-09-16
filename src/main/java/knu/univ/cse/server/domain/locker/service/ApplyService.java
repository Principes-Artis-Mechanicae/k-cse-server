package knu.univ.cse.server.domain.locker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.locker.dto.request.ApplyCreateDto;
import knu.univ.cse.server.domain.locker.dto.response.ApplyReadDto;
import knu.univ.cse.server.domain.locker.entity.Apply;
import knu.univ.cse.server.domain.locker.repository.ApplyRepository;
import knu.univ.cse.server.domain.student.entity.Student;
import knu.univ.cse.server.domain.student.repository.StudentRepository;
import knu.univ.cse.server.global.exception.CustomAssert;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {
	/* internal dependency */
	private final ApplyRepository applyRepository;

	/* external dependency */
	private final StudentRepository studentRepository;

	@Transactional
	public ApplyReadDto createApply(ApplyCreateDto createDto) {
		/* Check if the student exists */
		Student student = studentRepository.findByStudentNameAndStudentNumber(
			createDto.studentName(), createDto.studentNumber());
		CustomAssert.notFound(student, Student.class);

		/* Save the apply entity */
		Apply apply = applyRepository.save(createDto.toEntity(student));

		/* Return the created apply entity */
		return ApplyReadDto.fromEntity(apply, student);
	}


}
