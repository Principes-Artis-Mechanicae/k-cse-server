package knu.univ.cse.server.domain.model.locker.apply;

import java.time.LocalDateTime;

import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.global.util.DateTimeUtil;

public enum ApplyPeriod {
	PRIMARY {
		@Override
		public boolean isWithinPeriod(ApplyForm applyForm, LocalDateTime currentTime) {
			return DateTimeUtil.isBetweenInclusive(
				currentTime,
				applyForm.getFirstApplyStartDate(),
				applyForm.getFirstApplyEndDate()
			);
		}
	},
	ADDITIONAL {
		@Override
		public boolean isWithinPeriod(ApplyForm applyForm, LocalDateTime currentTime) {
			return DateTimeUtil.isBetweenInclusive(
				currentTime,
				applyForm.getFirstApplyEndDate(),
				applyForm.getSemesterEndDate()
			);
		}
	},
	REPLACEMENT {
		@Override
		public boolean isWithinPeriod(ApplyForm applyForm, LocalDateTime currentTime) {
			return DateTimeUtil.isBetweenInclusive(
				currentTime,
				applyForm.getFirstApplyStartDate(),
				applyForm.getSemesterEndDate()
			);
		}
	};

	/**
	 * Determines if the current time is within the application's period.
	 *
	 * @param applyForm    The active application form.
	 * @param currentTime The current time.
	 * @return True if within period, else false.
	 */
	public abstract boolean isWithinPeriod(ApplyForm applyForm, LocalDateTime currentTime);
}
