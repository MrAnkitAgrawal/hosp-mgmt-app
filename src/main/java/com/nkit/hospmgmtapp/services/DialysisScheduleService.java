package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_BILLING_IS_NOT_AVAILABLE;
import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.nkit.hospmgmtapp.domain.entities.*;
import com.nkit.hospmgmtapp.resources.models.*;
import com.nkit.hospmgmtapp.services.serviceextns.DialysisScheduleServiceExtn;
import com.nkit.hospmgmtapp.services.validator.DialysisServiceValidator;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisScheduleService {
  private final DialysisScheduleServiceExtn dialysisScheduleServiceExtn;
  private final DialysisServiceValidator dialysisServiceValidator;
  private final BillingMgmtService billingMgmtService;

  public DialysisScheduleResponseDto scheduleDialysis(
      DialysisScheduleRequestDto dialysisScheduleRequestDto) {
    // Validate patient id
    Long patientId = dialysisScheduleRequestDto.getPatientId();
    PatientE patientE = dialysisServiceValidator.validatePatientId(patientId);

    LocalDate scheduleDate = parseStringToDate(dialysisScheduleRequestDto.getDialysisDate());

    // validate if dialysis already scheduled for this patient on requested date
    dialysisServiceValidator.checkIfDialysisAlreadyScheduled(patientE, scheduleDate);

    // validate dialysis station
    final String dStationLabel = dialysisScheduleRequestDto.getDialysisStationLabel();
    DialysisStationE dStationE = dialysisServiceValidator.validateDialysisStation(dStationLabel);

    // validate dialysis slot
    final String dSlotName = dialysisScheduleRequestDto.getDialysisSlot();
    DialysisSlotE dSlot = dialysisServiceValidator.validateDialysisSlot(dSlotName);

    DialysisScheduleE scheduleE =
        dialysisScheduleServiceExtn.bookDialysisSchedule(
            patientE,
            scheduleDate,
            dStationE,
            dSlot,
            dialysisScheduleRequestDto.isScheduleRandomly());
    return new DialysisScheduleResponseDto(scheduleE);
  }

  @Transactional
  public void updateDialysisStatus(
      Long dialysisScheduleId, DialysisStatusUpdateRequestDto dialysisStatusUpdateRequestDto) {
    DialysisScheduleE currentScheduleE =
        dialysisScheduleServiceExtn.updateDialysisStatus(
            dialysisScheduleId, dialysisStatusUpdateRequestDto);

    // Calculate total bills and update billing status to DUE
    if (currentScheduleE.getBillingE() == null
        || currentScheduleE.getBillingE().getBillItems() == null
        || currentScheduleE.getBillingE().getBillItems().isEmpty()) {
      throw new RuntimeException(DIALYSIS_BILLING_IS_NOT_AVAILABLE);
    }
    billingMgmtService.calculateTotalBillAndSetStatusAsDue(currentScheduleE.getBillingE());

    // check and book next dialysis schedule
    DialysisScheduleRequestDto nextDialysisDetails =
        dialysisStatusUpdateRequestDto.getNextDialysisDetails();
    if (nextDialysisDetails != null) {
      nextDialysisDetails.setPatientId(currentScheduleE.getPatientE().getPatientId());
      scheduleDialysis(nextDialysisDetails);
    }
  }

  public List<DialysisScheduleResponseDto> getDialysisSchedules(
      String dateFromStr, String dateToStr, Long patientId) {
    LocalDate dateFrom = isBlank(dateFromStr) ? now() : parseStringToDate(dateFromStr);
    LocalDate dateTo = isBlank(dateToStr) ? now() : parseStringToDate(dateToStr);

    List<DialysisScheduleE> schedules =
        dialysisScheduleServiceExtn.getDialysisSchedules(dateFrom, dateTo, patientId);
    return schedules.stream().map(DialysisScheduleResponseDto::new).collect(toList());
  }
}
