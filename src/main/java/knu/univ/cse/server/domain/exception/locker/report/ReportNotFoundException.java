package knu.univ.cse.server.domain.exception.locker.report;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class ReportNotFoundException extends NotFoundException {
	private static final String code = "REPORT_NOT_FOUND";

	public ReportNotFoundException() {
		super(code);
	}
}
