package knu.univ.cse.server.domain.service.student.dues;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
	 * 학생의 학생회비 납부 여부를 설정합니다.
	 * - 학생회비 납부 기록이 있으면 수정하고, 없으면 새로 생성합니다.
	 *
	 * @param studentId 학생 식별자
	 * @param isDues 학생회비 납부 여부
	 * @throws DuesNotFoundException "DUES_NOT_FOUND"
	 */
	@Transactional(isolation = Isolation.REPEATABLE_READ)
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

	public boolean isDues(Long studentId) {
		return duesRepository.findDuesById(studentId)
			.map(Dues::isDues)
			.orElse(false);
	}
}
