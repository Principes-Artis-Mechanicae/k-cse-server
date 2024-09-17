package knu.univ.cse.server.domain.service.student.dues;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.model.student.dues.Dues;
import knu.univ.cse.server.domain.persistence.DuesRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DuesService {
	private final DuesRepository duesRepository;

	@Transactional
	public void updateDues(Long studentId, boolean isDues) {
		if (duesRepository.existsDuesById(studentId)) {
			Dues dues = duesRepository.findDuesById(studentId);
			CustomAssert.notFound(dues, Dues.class);
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
