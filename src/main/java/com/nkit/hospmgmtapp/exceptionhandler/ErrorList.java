package com.nkit.hospmgmtapp.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ErrorList {
  private List<Error> errors = new ArrayList<>();

  public ErrorList(List<Error> errors) {
    this.errors = errors;
  }
}
