package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.*;

import com.nkit.hospmgmtapp.resources.models.*;
import com.nkit.hospmgmtapp.services.BillingMgmtService;
import com.nkit.hospmgmtapp.services.DialysisScheduleService;
import com.nkit.hospmgmtapp.validators.annotations.ValidDate;
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

  /**
   * used to book dialysis
   *
   * @param dialysisScheduleRequestDto
   * @return
   */
  @PostMapping
  public ResponseEntity<DialysisScheduleResponseDto> scheduleDialysis(
      @RequestBody @Valid DialysisScheduleRequestDto dialysisScheduleRequestDto) {
    return new ResponseEntity<>(
        dialysisScheduleService.scheduleDialysis(dialysisScheduleRequestDto), CREATED);
  }

  /**
   * Used to fetch all scheduled dialysis between given date range
   *
   * @param dateFrom
   * @param dateTo
   * @param patientId
   * @return
   */
  @GetMapping
  public ResponseEntity<List<DialysisScheduleResponseDto>> getDialysisSchedules(
      @RequestParam(required = false) String dateFrom,
      @RequestParam(required = false) String dateTo,
      @RequestParam(required = false) Long patientId) {
    return new ResponseEntity<>(
        dialysisScheduleService.getDialysisSchedules(dateFrom, dateTo, patientId), OK);
  }

  /**
   * Used to complete or cancel the dialysis. W
   *
   * <p>When completed then - it calculate all the bills and update bill status to DUE. - After that
   * no changes in billing is possible
   *
   * @param dialysisScheduleId
   * @param dialysisStatusUpdateRequestDto
   * @return
   */
  @PutMapping("/{dialysisScheduleId}/status")
  public ResponseEntity<String> updateDialysisStatus(
      @PathVariable Long dialysisScheduleId,
      @RequestBody @Valid DialysisStatusUpdateRequestDto dialysisStatusUpdateRequestDto) {
    dialysisScheduleService.updateDialysisStatus(
        dialysisScheduleId, dialysisStatusUpdateRequestDto);
    return new ResponseEntity<>(NO_CONTENT);
  }

  /**
   * Used to add dialysis bills
   *
   * @param dialysisScheduleId
   * @param billingDetails
   * @return
   */
  @PostMapping("/{dialysisScheduleId}/billing")
  public ResponseEntity<String> addUpdateBillForDialysis(
      @PathVariable long dialysisScheduleId, @RequestBody BillingDto billingDetails) {
    billingMgmtService.addOrUpdateBilling(dialysisScheduleId, billingDetails);
    return new ResponseEntity<>(NO_CONTENT);
  }

  @GetMapping("/{dialysisScheduleId}/billing")
  public ResponseEntity<BillingDto> getDialysisBillingDetails(
      @PathVariable long dialysisScheduleId) {
    return new ResponseEntity<>(
        billingMgmtService.getDialysisBillingDetails(dialysisScheduleId), OK);
  }
}
