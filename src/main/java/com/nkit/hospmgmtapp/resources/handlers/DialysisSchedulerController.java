package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.*;

import com.nkit.hospmgmtapp.resources.models.BillingDto;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleRequestDto;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleResponseDto;
import com.nkit.hospmgmtapp.resources.models.DialysisStatusUpdateRequestDto;
import com.nkit.hospmgmtapp.services.BillingMgmtService;
import com.nkit.hospmgmtapp.services.DialysisScheduleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/dialysisScheduler")
@RequiredArgsConstructor
public class DialysisSchedulerController {
  private final DialysisScheduleService dialysisScheduleService;
  private final BillingMgmtService billingMgmtService;

  @PostMapping
  public ResponseEntity<DialysisScheduleResponseDto> scheduleDialysis(
      @RequestBody @Valid DialysisScheduleRequestDto dialysisScheduleRequestDto) {
    return new ResponseEntity<>(
        dialysisScheduleService.scheduleDialysis(dialysisScheduleRequestDto), CREATED);
  }

  @GetMapping
  public ResponseEntity<List<DialysisScheduleResponseDto>> getDialysisSchedules(
      @RequestParam(required = false) String dateFrom,
      @RequestParam(required = false) String dateTo,
      @RequestParam(required = false) Long patientId) {
    // TODO:
    //  - dateFrom and dateTo must be valid and not a past date.
    //  - dateFrom must be previous/same of dateTo.
    return new ResponseEntity<>(
        dialysisScheduleService.getDialysisSchedules(dateFrom, dateTo, patientId), OK);
  }

  @PutMapping("/{dialysisScheduleId}/status")
  public ResponseEntity<String> updateDialysisStatus(
      @PathVariable Long dialysisScheduleId,
      @RequestBody @Valid DialysisStatusUpdateRequestDto dialysisStatusUpdateRequestDto) {
    dialysisScheduleService.updateDialysisStatus(
        dialysisScheduleId, dialysisStatusUpdateRequestDto);
    return new ResponseEntity<>(NO_CONTENT);
  }

  @PostMapping("/{dialysisScheduleId}/billing")
  public ResponseEntity<String> addUpdateBillForDialysis(
      @PathVariable long dialysisScheduleId, @RequestBody BillingDto billingDetails) {
    billingMgmtService.addOrUpdateBilling(dialysisScheduleId, billingDetails);
    return new ResponseEntity<>(NO_CONTENT);
  }
}
