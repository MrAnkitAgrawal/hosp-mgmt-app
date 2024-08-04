package com.nkit.hospmgmtapp.domain.repos.wrapper;

import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_SCHEDULE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RepositoryWrapper {
  private final DialysisScheduleR dialysisScheduleR;

  /**
   * Check and return dialysis schedule by id from DB. Throw DIALYSIS_SCHEDULE_NOT_FOUND exception
   * if not found.
   *
   * @param dialysisScheduleId
   * @return DialysisScheduleE
   */
  public DialysisScheduleE getScheduleEntity(Long dialysisScheduleId) {
    return dialysisScheduleR
        .findById(dialysisScheduleId)
        .orElseThrow(() -> new RuntimeException(DIALYSIS_SCHEDULE_NOT_FOUND));
  }
}
