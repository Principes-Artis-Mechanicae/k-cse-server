package knu.univ.cse.server.domain.service.document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.api.locker.allocate.dto.AllocateReadDto;
import knu.univ.cse.server.domain.exception.locker.allocate.AllocateNotFoundException;
import knu.univ.cse.server.domain.model.locker.allocate.Allocate;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.persistence.AllocateRepository;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.domain.service.student.StudentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {
	private final StudentService studentService;
	private final ApplyFormService applyFormService;
	private final AllocateRepository allocateRepository;

	public List<AllocateReadDto> getAllStudentAllocateForm() {
		// 모든 학생을 한 번에 조회
		List<Student> students = studentService.findAllStudents();

		// 활성화된 ApplyForm을 루프 외부에서 한 번만 조회
		ApplyForm applyForm = applyFormService.getActiveApplyForm();

		// 학생 리스트로부터 Allocate를 한 번에 조회
		List<Allocate> allocates = allocateRepository.findByApplyFormAndStudentIn(applyForm, students);

		// Allocate를 DTO로 변환하여 반환
		return allocates.stream()
			.map(allocate -> AllocateReadDto.fromEntity(
				allocate.getStudent(),
				allocate.getApply(),
				applyForm,
				allocate.getLocker()))
			.collect(Collectors.toList());
	}
}
