package com.nkit.hospmgmtapp.exceptionhandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Error {
  private String code;
  private String message;
  private String traceId;
  private int Status;

  public Error(String code, String message, String traceId, int status) {
    this.code = code;
    this.message = message;
    this.traceId = traceId;
    Status = status;
  }
}
