package knu.univ.cse.server.domain.model.locker.apply;

public enum ApplyStatus {
	APPLY, APPROVE, REJECT, BROKEN_APPLY;

	public boolean isApply() {
		return this == APPLY;
	}
}
