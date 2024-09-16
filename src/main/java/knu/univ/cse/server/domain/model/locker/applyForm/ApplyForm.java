package knu.univ.cse.server.domain.model.locker.applyForm;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import knu.univ.cse.server.domain.dto.request.ApplyFormUpdateDto;
import knu.univ.cse.server.global.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyForm {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_form_id")
	private Long id;

	@Column(name = "year")
	private Integer year;

	@Column(name = "semester")
	private Integer semester;

	@Column(name = "first_apply_start_date")
	private LocalDateTime firstApplyStartDate;

	@Column(name = "first_apply_end_date")
	private LocalDateTime firstApplyEndDate;

	@Column(name = "semester_end_date")
	private LocalDateTime semesterEndDate;

	@Column(name = "status")
	private ApplyFormStatus status;

	@Builder
	public ApplyForm(Long id, Integer year, Integer semester, LocalDateTime firstApplyStartDate,
		LocalDateTime firstApplyEndDate,
		LocalDateTime semesterEndDate, ApplyFormStatus status) {
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.firstApplyStartDate = firstApplyStartDate;
		this.firstApplyEndDate = firstApplyEndDate;
		this.semesterEndDate = semesterEndDate;
		this.status = status;
	}

	public void updateStatus(ApplyFormStatus status) {
		this.status = status;
	}

	public void update(ApplyFormUpdateDto applyFormUpdateDto) {
		if (applyFormUpdateDto.firstApplyEndDate() != null) {
			this.firstApplyEndDate = DateTimeUtil.stringToLocalDateTime(applyFormUpdateDto.firstApplyEndDate());
		}
		if (applyFormUpdateDto.firstApplyStartDate() != null) {
			this.firstApplyStartDate = DateTimeUtil.stringToLocalDateTime(applyFormUpdateDto.firstApplyStartDate());
		}
		if (applyFormUpdateDto.semesterEndDate() != null) {
			this.semesterEndDate = DateTimeUtil.stringToLocalDateTime(applyFormUpdateDto.semesterEndDate());
		}
		if (applyFormUpdateDto.status() != null) {
			this.status = ApplyFormStatus.valueOf(applyFormUpdateDto.status());
		}
	}
}
