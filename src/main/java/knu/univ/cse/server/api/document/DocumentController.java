package knu.univ.cse.server.api.document;

import java.io.StringWriter;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;

import knu.univ.cse.server.api.locker.allocate.dto.AllocateReadDto;
import knu.univ.cse.server.domain.service.document.DocumentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
public class DocumentController {
	private final DocumentService documentService;

	@GetMapping("/download/csv")
	public ResponseEntity<String> downloadAllocationsCsv() {
		try {
			List<AllocateReadDto> allocations = documentService.getAllStudentAllocateForm();

			StringWriter stringWriter = new StringWriter();
			CSVWriter csvWriter = new CSVWriter(stringWriter);

			// Write CSV header
			String[] header = {
				"studentName",
				"studentNumber",
				"lockerName",
				"floor",
				"height",
				"pw",
				"broken",
				"applyFormYear",
				"applyFormSemester"
			};
			csvWriter.writeNext(header);

			// Write data rows
			for (AllocateReadDto dto : allocations) {
				String[] row = {
					dto.studentName(),
					dto.studentNumber(),
					dto.lockerName(),
					dto.floor().toString(),
					dto.height().toString(),
					dto.pw(),
					dto.broken().toString(),
					dto.applyForm().year().toString(),
					dto.applyForm().semester().toString()
				};
				csvWriter.writeNext(row);
			}

			csvWriter.close();

			String csvContent = stringWriter.toString();

			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=allocations.csv");
			headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

			return ResponseEntity
				.ok()
				.headers(headers)
				.body(csvContent);
		} catch (Exception e) {
			// Handle exceptions appropriately
			return ResponseEntity.status(500).body("An error occurred while generating the CSV.");
		}
	}
}
