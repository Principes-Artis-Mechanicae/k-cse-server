package knu.univ.cse.server.domain.service.document;

import java.util.ArrayList;
import java.util.List;

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
		List<Student> students = studentService.findAllStudents();
		List<AllocateReadDto> allocateReadDtos = new ArrayList<>();

		for (Student student : students) {
			ApplyForm applyForm = applyFormService.getActiveApplyForm();
			Allocate allocate = allocateRepository.findByStudentAndApplyForm(student, applyForm)
				.orElseThrow(AllocateNotFoundException::new);
			allocateReadDtos.add(AllocateReadDto.fromEntity(student, allocate.getApply(), applyForm, allocate.getLocker()));
		}

		return allocateReadDtos;
	}
}
