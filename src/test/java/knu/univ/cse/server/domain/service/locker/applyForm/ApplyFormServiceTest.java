package knu.univ.cse.server.domain.service.locker.applyForm;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormCreateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormDuplicatedException;
import knu.univ.cse.server.domain.exception.locker.applyForm.ApplyFormNotFoundException;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.domain.persistence.ApplyFormRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyFormServiceTest {

	@Mock
	private ApplyFormRepository applyFormRepository;

	@InjectMocks
	private ApplyFormService applyFormService;

	@Test
	void testCreateApplyForm_Success() {
		// Given
		ApplyFormCreateDto requestBody = new ApplyFormCreateDto(
			2023, 1, "2023-01-01 00:00:00", "2023-01-31 23:59:59", "2023-06-30 23:59:59"
		);
		ApplyForm applyForm = requestBody.toEntity();

		when(applyFormRepository.existsByYearAndSemester(2023, 1)).thenReturn(false);
		when(applyFormRepository.save(any(ApplyForm.class))).thenReturn(applyForm);

		// When
		ApplyFormReadDto result = applyFormService.createApplyForm(requestBody);

		// Then
		assertNotNull(result);
		assertEquals(2023, result.year());
		assertEquals(1, result.semester());
		verify(applyFormRepository, times(1)).save(any(ApplyForm.class));
	}

	@Test
	void testCreateApplyForm_DuplicateException() {
		// Given
		ApplyFormCreateDto requestBody = new ApplyFormCreateDto(
			2023, 1, "2023-01-01 00:00:00", "2023-01-31 23:59:59", "2023-06-30 23:59:59"
		);

		when(applyFormRepository.existsByYearAndSemester(2023, 1)).thenReturn(true);

		// When & Then
		assertThrows(ApplyFormDuplicatedException.class, () -> applyFormService.createApplyForm(requestBody));
		verify(applyFormRepository, never()).save(any(ApplyForm.class));
	}

	@Test
	void testUpdateApplyForm_Success() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyFormUpdateDto requestBody = ApplyFormUpdateDto.builder()
			.firstApplyStartDate("2023-02-01 00:00:00")
			.firstApplyEndDate("2023-02-28 23:59:59")
			.semesterEndDate("2023-07-31 23:59:59")
			.status("ACTIVE")
			.build();

		ApplyForm existingForm = ApplyForm.builder()
			.year(year)
			.semester(semester)
			.firstApplyStartDate(LocalDateTime.now())
			.firstApplyEndDate(LocalDateTime.now())
			.semesterEndDate(LocalDateTime.now())
			.status(ApplyFormStatus.INACTIVE)
			.build();

		when(applyFormRepository.findByYearAndSemester(eq(year), eq(semester)))
			.thenReturn(Optional.of(existingForm));
		when(applyFormRepository.save(any(ApplyForm.class))).thenReturn(existingForm);

		// When
		ApplyFormReadDto result = applyFormService.updateApplyForm(year, semester, requestBody);

		// Then
		assertNotNull(result);
		assertEquals("2023-02-01 00:00:00", result.firstApplyStartDate());
		assertEquals("2023-02-28 23:59:59", result.firstApplyEndDate());
		assertEquals("2023-07-31 23:59:59", result.semesterEndDate());
		assertEquals(ApplyFormStatus.ACTIVE, result.status());
	}

	@Test
	void testUpdateApplyForm_NotFoundException() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyFormUpdateDto requestBody = ApplyFormUpdateDto.builder().build();

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ApplyFormNotFoundException.class, () -> applyFormService.updateApplyForm(year, semester, requestBody));
		verify(applyFormRepository, never()).save(any(ApplyForm.class));
	}

	@Test
	void testDeleteApplyForm_Success() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyForm existingForm = ApplyForm.builder().build();

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.of(existingForm));
		doNothing().when(applyFormRepository).delete(existingForm);

		// When
		applyFormService.deleteApplyForm(year, semester);

		// Then
		verify(applyFormRepository, times(1)).delete(existingForm);
	}

	@Test
	void testDeleteApplyForm_NotFoundException() {
		// Given
		Integer year = 2023;
		Integer semester = 1;

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ApplyFormNotFoundException.class, () -> applyFormService.deleteApplyForm(year, semester));
		verify(applyFormRepository, never()).delete(any(ApplyForm.class));
	}

	@Test
	void testGetApplyForm_Success() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyForm existingForm = ApplyForm.builder()
			.year(year)
			.semester(semester)
			.firstApplyStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
			.firstApplyEndDate(LocalDateTime.of(2023, 1, 31, 23, 59, 59))
			.semesterEndDate(LocalDateTime.of(2023, 6, 30, 23, 59, 59))
			.status(ApplyFormStatus.INACTIVE)
			.build();

		when(applyFormRepository.findByYearAndSemester(eq(year), eq(semester))).thenReturn(Optional.of(existingForm));

		// When
		ApplyFormReadDto result = applyFormService.getApplyForm(year, semester);

		// Then
		assertNotNull(result);
		assertEquals(year, result.year());
		assertEquals(semester, result.semester());
	}

	@Test
	void testGetApplyForm_NotFoundException() {
		// Given
		Integer year = 2023;
		Integer semester = 1;

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ApplyFormNotFoundException.class, () -> applyFormService.getApplyForm(year, semester));
	}

	@Test
	void testGetAllApplyForms() {
		// Given
		ApplyForm form1 = ApplyForm.builder()
			.year(2023)
			.semester(1)
			.firstApplyStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
			.firstApplyEndDate(LocalDateTime.of(2023, 1, 31, 23, 59, 59))
			.semesterEndDate(LocalDateTime.of(2023, 6, 30, 23, 59, 59))
			.status(ApplyFormStatus.ACTIVE)
			.build();

		ApplyForm form2 = ApplyForm.builder()
			.year(2023)
			.semester(2)
			.firstApplyStartDate(LocalDateTime.of(2023, 7, 1, 0, 0))
			.firstApplyEndDate(LocalDateTime.of(2023, 7, 31, 23, 59, 59))
			.semesterEndDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59))
			.status(ApplyFormStatus.INACTIVE)
			.build();

		List<ApplyForm> forms = Arrays.asList(form1, form2);

		when(applyFormRepository.findAll()).thenReturn(forms);

		// When
		List<ApplyFormReadDto> result = applyFormService.getAllApplyForms();

		// Then
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void testUpdateApplyFormStatus_ActivateSuccess() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyForm existingForm = ApplyForm.builder()
			.year(year)
			.semester(semester)
			.status(ApplyFormStatus.INACTIVE)
			.build();

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.of(existingForm));
		when(applyFormRepository.countByStatus(ApplyFormStatus.ACTIVE)).thenReturn(0L);
		when(applyFormRepository.save(any(ApplyForm.class))).thenReturn(existingForm);

		// When
		ApplyFormReadDto result = applyFormService.updateApplyFormStatus(year, semester);

		// Then
		assertNotNull(result);
		assertEquals(ApplyFormStatus.ACTIVE, result.status());
	}

	@Test
	void testUpdateApplyFormStatus_DeactivateSuccess() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyForm existingForm = ApplyForm.builder()
			.year(year)
			.semester(semester)
			.firstApplyStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
			.firstApplyEndDate(LocalDateTime.of(2023, 1, 31, 23, 59, 59))
			.semesterEndDate(LocalDateTime.of(2023, 6, 30, 23, 59, 59))
			.status(ApplyFormStatus.ACTIVE)
			.build();

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.of(existingForm));
		when(applyFormRepository.save(any(ApplyForm.class))).thenReturn(existingForm);

		// When
		ApplyFormReadDto result = applyFormService.updateApplyFormStatus(year, semester);

		// Then
		assertNotNull(result);
		assertEquals(ApplyFormStatus.INACTIVE, result.status());
	}

	@Test
	void testUpdateApplyFormStatus_AlreadyActiveException() {
		// Given
		Integer year = 2023;
		Integer semester = 1;
		ApplyForm existingForm = ApplyForm.builder()
			.year(year)
			.semester(semester)
			.status(ApplyFormStatus.INACTIVE)
			.build();

		when(applyFormRepository.findByYearAndSemester(year, semester)).thenReturn(Optional.of(existingForm));
		when(applyFormRepository.countByStatus(ApplyFormStatus.ACTIVE)).thenReturn(1L);

		// When & Then
		assertThrows(ApplyFormDuplicatedException.class, () -> applyFormService.updateApplyFormStatus(year, semester));
		verify(applyFormRepository, never()).save(any(ApplyForm.class));
	}
}
