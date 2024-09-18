package knu.univ.cse.server.domain.model.student.dues;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dues")
public class Dues {
	@Id
	private Long id;

	private boolean dues;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@Builder
	public Dues(Long id, boolean dues, Student student) {
		this.id = id;
		this.dues = dues;
		this.student = student;
	}

	public void updateDues(boolean dues) {
		this.dues = dues;
	}
}
