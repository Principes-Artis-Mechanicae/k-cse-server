package knu.univ.cse.server.domain.model.locker.apply;

import java.util.List;


public enum ApplyHeight {
	TOP, MIDDLE, BOTTOM;

	public List<Integer> getLockerHeight() {
		return switch (this) {
			case TOP -> List.of(3, 4);
			case MIDDLE -> List.of(2, 3);
			case BOTTOM -> List.of(1, 2);
		};
	}
}
