package knu.univ.cse.server.domain.service.student.dues;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.exception.student.dues.DuesNotFoundException;
import knu.univ.cse.server.domain.model.student.dues.Dues;
import knu.univ.cse.server.domain.persistence.DuesRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DuesService {
	private final DuesRepository duesRepository;

	/**
	 * 학생회비 납부 여부 설정
	 * - 만약 해당 학생이 학생회비를 납부한 기록이 있다면, 해당 기록을 수정한다.
	 * - 만약 해당 학생이 학생회비를 납부한 기록이 없다면, 새로운 기록을 생성한다.
	 * @param studentId 학생 식별자
	 * @param isDues 학생회비 납부 여부
	 */
	@Transactional
	public void updateDues(Long studentId, boolean isDues) {
		if (duesRepository.existsDuesById(studentId)) {
			Dues dues = duesRepository.findDuesById(studentId)
				.orElseThrow(DuesNotFoundException::new);
			modifyDues(dues, isDues);
		} else {
			saveDues(studentId, isDues);
		}
	}

	private void modifyDues(Dues dues, boolean isDues) {
		dues.updateDues(isDues);
		duesRepository.save(dues);
	}

	private void saveDues(Long studentId, boolean isDues) {
		duesRepository.save(
			Dues.builder()
				.id(studentId)
				.dues(isDues)
				.build()
		);
	}
}
