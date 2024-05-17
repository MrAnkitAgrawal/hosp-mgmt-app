package com.nkit.hospmgmtapp.domain.entities;

public enum DialysisSlot {
  MORNING("SLOT#1"),
  AFTER_NOON("SLOT#2"),
  EVENING("SLOT#3"),
  NIGHT("SLOT#4");

  private final String slotName;

  DialysisSlot(String slotName) {
    this.slotName = slotName;
  }
}
