package knu.univ.cse.server.domain.model.locker.report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
	@Id
	@Column(name = "report_id")
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_form_id")
	private Apply apply;

	private String content;

	@Builder
	public Report(Long id, Apply apply, String content) {
		this.id = id;
		this.apply = apply;
		this.content = content;
	}
}
