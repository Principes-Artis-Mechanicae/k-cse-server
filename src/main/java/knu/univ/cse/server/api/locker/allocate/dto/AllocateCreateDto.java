package knu.univ.cse.server.api.locker.allocate.dto;

public record AllocateCreateDto(
	String studentNumber,
	String lockerName
) {}
