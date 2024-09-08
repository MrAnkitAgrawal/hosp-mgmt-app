package com.nkit.hospmgmtapp.exceptionhandler;

public interface ExceptionKey {
  // Patient Error Keys
  String PATIENT_ID_NOT_PROVIDED = "PATIENT_ID_NOT_PROVIDED";
  String PATIENT_ID_INVALID = "PATIENT_ID_INVALID";

  String PATIENT_NOT_FOUND = "PATIENT_NOT_FOUND";
  String PATIENT_ALREADY_EXISTS = "PATIENT_ALREADY_EXISTS_WITH_ID: ";
  String PATIENT_GENDER_NOT_PROVIDED = "PATIENT_GENDER_NOT_PROVIDED";
  String PATIENT_GENDER_INVALID = "PATIENT_GENDER_INVALID";
  String PATIENT_FIRST_NAME_NOT_PROVIDED = "PATIENT_FIRST_NAME_NOT_PROVIDED";
  String PATIENT_DOB_NOT_PROVIDED = "PATIENT_DOB_NOT_PROVIDED";
  String PATIENT_DOB_IS_INVALID = "PATIENT_DOB_IS_INVALID";
  String AADHAR_NUMBER_INVALID = "AADHAR_NUMBER_INVALID";
  String PATIENT_MOBILE_NUMBER_NOT_PROVIDED = "PATIENT_MOBILE_NUMBER_NOT_PROVIDED";
  String PATIENT_EMAIL_ID_INVALID = "PATIENT_EMAIL_ID_INVALID";
  String PATIENT_INSURANCE_IS_NULL = "PATIENT_INSURANCE_IS_NULL";

  // Dialysis Station Error Keys
  String DIALYSIS_STATION_INVALID = "DIALYSIS_STATION_INVALID";
  String DIALYSIS_STATION_IS_NOT_AVAILABLE_FOR_DIALYSIS =
      "DIALYSIS_STATION_IS_NOT_AVAILABLE_FOR_DIALYSIS";

  // Dialysis Slot Error Keys
  String DIALYSIS_SLOT_START_TIME_NOT_PROVIDED = "DIALYSIS_SLOT_START_TIME_NOT_PROVIDED";
  String DIALYSIS_SLOT_START_TIME_INVALID = "DIALYSIS_SLOT_START_TIME_INVALID";

  String DIALYSIS_SLOT_NAME_NOT_PROVIDED = "DIALYSIS_SLOT_NAME_NOT_PROVIDED";
  String DIALYSIS_SLOT_NAME_INVALID = "DIALYSIS_SLOT_NAME_INVALID";

  // Dialysis Schedule Error Keys
  String DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED = "DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED";
  String DIALYSIS_SCHEDULE_DATE_INVALID =
      "DIALYSIS_SCHEDULE_DATE_MUST_BE_VALID_IN_DD-MM-YYYY_FORMAT_AND_CANNOT_BE_PAST_DATE";
  String DATE_OF_DIALYSIS_SCHEDULES_DATE_RANGE_IS_INVALID = "DATE_OF_DIALYSIS_SCHEDULES_DATE_RANGE_IS_INVALID";
  String DIALYSIS_SCHEDULES_DATE_RANGE_IS_INVALID = "DIALYSIS_SCHEDULES_DATE_RANGE_IS_INVALID";
  String DIALYSIS_ALREADY_SCHEDULED_FOR_THIS_PATIENT_ON_REQUESTED_DATE =
      "DIALYSIS_ALREADY_SCHEDULED_FOR_THIS_PATIENT_ON_REQUESTED_DATE";
  String NO_DIALYSIS_SCHEDULE_AVAILABLE = "NO_DIALYSIS_SCHEDULE_AVAILABLE";
  String NO_DIALYSIS_SCHEDULE_AVAILABLE_PER_REQUESTED_STATION_AND_SLOT =
      "NO_DIALYSIS_SCHEDULE_AVAILABLE_PER_REQUESTED_STATION_AND_SLOT";
  String DIALYSIS_STATION_LABEL_INVALID = "DIALYSIS_STATION_LABEL_INVALID";
  String DIALYSIS_SLOT_INVALID = "DIALYSIS_SLOT_INVALID";

  String DIALYSIS_SCHEDULE_STATUS_NOT_PROVIDED = "DIALYSIS_SCHEDULE_STATUS_NOT_PROVIDED";
  String DIALYSIS_SCHEDULE_STATUS_CAN_BE_CANCELLED_OR_COMPLETED =
      "DIALYSIS_SCHEDULE_STATUS_CAN_BE_CANCELLED_OR_COMPLETED";

  String CONSENT_MISSING_ON_BILL_CHECK_AND_VERIFICATION_WITH_CUSTOMER = "CONSENT_MISSING_ON_BILL_CHECK_AND_VERIFICATION_WITH_CUSTOMER";
  String DOCTOR_NAME_IS_MISSING = "DOCTOR_NAME_IS_MISSING";
  String NURSING_STAFF_IS_MISSING = "NURSING_STAFF_IS_MISSING";

  String DIALYSIS_SCHEDULE_NOT_FOUND = "DIALYSIS_SCHEDULE_NOT_FOUND";
  String DIALYSIS_BILLING_IS_NOT_AVAILABLE = "DIALYSIS_BILLING_IS_NOT_AVAILABLE";

  String DIALYSIS_IS_ALREADY_CANCELLED_OR_COMPLETED = "DIALYSIS_IS_ALREADY_CANCELLED_OR_COMPLETED";

  // Billing error keys
  String BILL_ITEMS_NOT_PROVIDED = "BILL_ITEMS_NOT_PROVIDED";
  String PAID_AMOUNT_IS_MORE_THAN_BILL = "PAID_AMOUNT_IS_MORE_THAN_BILL";
  String BILL_NOT_OPENED_FOR_UPDATE = "BILL_NOT_OPENED_FOR_UPDATE";
  String BILL_NOT_FOUND = "BILL_NOT_FOUND";
  String BILL_NOT_READY_OR_ALREADY_PAID = "BILL_NOT_READY_OR_ALREADY_PAID";

  // Payment error keys
  String PAID_AMOUNT_IS_MORE_THAN_PENDING_BILLS = "PAID_AMOUNT_IS_MORE_THAN_PENDING_BILLS";
}
