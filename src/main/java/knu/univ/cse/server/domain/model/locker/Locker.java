package knu.univ.cse.server.domain.model.locker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locker {
	@Id
	@Column(name = "name", length = 10, nullable = false)
	private String lockerName;

	@Column(name = "floor", nullable = false)
	private int floor;

	@Column(name = "height", nullable = false)
	private int height;

	@Column(name = "pw", length = 4, nullable = false)
	private String pw;


}
