package knu.univ.cse.server.domain.model.locker.allocate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Allocate {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
