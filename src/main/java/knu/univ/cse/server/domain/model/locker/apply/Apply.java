package knu.univ.cse.server.domain.model.locker.apply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import knu.univ.cse.server.domain.model.base.BaseTimeEntity;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply extends BaseTimeEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@Column(name = "first_floor", length = 5)
	private String firstFloor;

	@Column(name = "first_height", length = 5)
	private String firstHeight;

	@Column(name = "second_floor", length = 5)
	private String secondFloor;

	@Column(name = "second_height", length = 5)
	private String secondHeight;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ApplyStatus status;

	@Builder
	public Apply(Long id, Student student, String firstFloor, String firstHeight, String secondFloor,
		String secondHeight, ApplyStatus status) {
		this.id = id;
		this.student = student;
		this.firstFloor = firstFloor;
		this.firstHeight = firstHeight;
		this.secondFloor = secondFloor;
		this.secondHeight = secondHeight;
		this.status = status;
	}

	public void updateStatus(ApplyStatus status) {
		this.status = status;
	}
}
