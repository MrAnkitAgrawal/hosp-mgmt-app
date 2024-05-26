package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.nkit.hospmgmtapp.resources.models.DialysisScheduleRequestDto;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleResponseDto;
import com.nkit.hospmgmtapp.resources.models.DialysisStatusUpdateRequestDto;
import com.nkit.hospmgmtapp.services.DialysisScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/dialysisScheduler")
@RequiredArgsConstructor
public class DialysisSchedulerController {
  private final DialysisScheduleService dialysisScheduleService;

  @PostMapping
  public ResponseEntity<DialysisScheduleResponseDto> scheduleDialysis(
      @RequestBody @Valid DialysisScheduleRequestDto dialysisScheduleRequestDto) {
    return new ResponseEntity<>(
        dialysisScheduleService.scheduleDialysis(dialysisScheduleRequestDto), CREATED);
  }

  @PutMapping("/{dialysisScheduleId}/status")
  public ResponseEntity<String> updateDialysisStatus(
      @PathVariable Long dialysisScheduleId,
      @RequestBody @Valid DialysisStatusUpdateRequestDto dialysisStatusUpdateRequestDto) {
    dialysisScheduleService.updateDialysisStatus(
        dialysisScheduleId, dialysisStatusUpdateRequestDto);
    return new ResponseEntity<>(NO_CONTENT);
  }
}
