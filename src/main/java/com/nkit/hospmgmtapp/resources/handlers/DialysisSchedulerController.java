package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.CREATED;

import com.nkit.hospmgmtapp.resources.models.DialysisScheduleRequestDto;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleResponseDto;
import com.nkit.hospmgmtapp.services.DialysisScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
