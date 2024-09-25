package knu.univ.cse.server.domain.model.locker.apply;

import java.util.List;

import knu.univ.cse.server.domain.model.locker.LockerFloor;

public enum ApplyHeight {
	TOP, MIDDLE, BOTTOM;

	public List<Integer> getLockerHeight(LockerFloor floor) {
		return switch (floor) {
			case FLOOR_L, FLOOR_B1 -> switch (this) {
				case TOP -> List.of(2, 1);
				case MIDDLE -> List.of(2, 3);
				case BOTTOM -> List.of(3, 4);
			};
			case FLOOR_3F -> switch (this) {
				case TOP -> List.of(1, 2);
				case MIDDLE -> List.of(3, 4);
				case BOTTOM -> List.of(4, 5);
			};
		};
	}
}
