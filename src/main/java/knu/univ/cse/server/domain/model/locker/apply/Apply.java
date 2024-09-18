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
import jakarta.persistence.Table;
import knu.univ.cse.server.domain.model.base.BaseTimeEntity;
import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "apply")
public class Apply extends BaseTimeEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_form_id")
	private ApplyForm applyForm;

	@Column(name = "first_floor")
	private LockerFloor firstFloor;

	@Column(name = "first_height")
	private ApplyHeight firstHeight;

	@Column(name = "second_floor")
	private LockerFloor secondFloor;

	@Column(name = "second_height")
	private ApplyHeight secondHeight;

	@Enumerated(EnumType.STRING)
	@Column(name = "period")
	private ApplyPeriod period;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ApplyStatus status;

	@Builder
	public Apply(Long id, Student student, ApplyForm applyForm, LockerFloor firstFloor, ApplyHeight firstHeight,
		LockerFloor secondFloor, ApplyHeight secondHeight, ApplyPeriod period, ApplyStatus status) {
		this.id = id;
		this.student = student;
		this.applyForm = applyForm;
		this.firstFloor = firstFloor;
		this.firstHeight = firstHeight;
		this.secondFloor = secondFloor;
		this.secondHeight = secondHeight;
		this.period = period;
		this.status = status;
	}

	public void updateStatus(ApplyStatus status) {
		this.status = status;
	}
}
