package knu.univ.cse.server.domain.service.locker.apply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.dto.request.ApplyCreateDto;
import knu.univ.cse.server.domain.dto.response.ApplyReadDto;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.persistence.ApplyRepository;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.persistence.StudentRepository;
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
