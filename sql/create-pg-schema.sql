create sequence bill_item_seq_gen start with 1 increment by 1;
create sequence billing_id_seq_gen start with 1 increment by 1;
create sequence d_schedule_id_seq_gen start with 1 increment by 1;
    
create sequence d_slot_seq_gen start with 1 increment by 1;
    
create sequence d_station_seq_gen start with 1 increment by 1;
    
create sequence insurance_id_seq_gen start with 1 increment by 1;
create sequence patient_id_seq_gen start with 1 increment by 1;
create sequence payment_id_seq_gen start with 1 increment by 1;

create table bill_item (
        amount float4 not null,
        item_quantity integer,
        bill_item_id bigint not null,
        billing_id bigint not null,
        bill_item_name varchar(255) not null,
        bill_item_type varchar(255) not null,
        primary key (bill_item_id)
);

create table billing (
        paid_amount float4,
        total_bill float4,
        billing_id bigint not null,
        billing_timestamp timestamp(6) not null,
        d_schedule_id bigint unique,
        patient_id bigint not null,
        bill_status varchar(255),
        billing_head varchar(255) not null,
        billing_remarks varchar(255),
        primary key (billing_id)
);

create table billing_payment (
        billing_id bigint not null,
        payment_id bigint not null
);

create table dialysis_schedule (
        d_schedule_date date not null,
        create_timestamp timestamp(6) not null,
        d_schedule_id bigint not null,
        d_slot_id bigint not null,
        d_station_id bigint not null,
        patient_id bigint not null,
        update_timestamp timestamp(6) not null,
        d_schedule_status varchar(255) not null,
        doctor_name varchar(255),
        nursing_staff varchar(255),
        primary key (d_schedule_id)
);


create table dialysis_slot (
        d_slot_end_time time(6) not null,
        d_slot_start_time time(6) not null,
        d_slot_id bigint not null,
        d_slot_name varchar(255) not null,
        primary key (d_slot_id)
);


create table dialysis_station (
        last_maintenance_date date,
        d_station_id bigint not null,
        bed_number varchar(255) not null,
        dialysis_machine_number varchar(255) not null,
        dialysis_station_status varchar(255) not null,
        label varchar(255) not null,
        last_maintenance_details varchar(255),
        primary key (d_station_id)
);


create table insurance (
        insurance_id bigint not null,
        patient_id bigint not null,
        insurance_company varchar(255),
        policy_number varchar(255),
        primary key (insurance_id)
);


create table patient (
        dob date,
        patient_id bigint not null,
        aadhar_number varchar(255),
        email_id varchar(255),
        first_name varchar(255) not null,
        gender varchar(255),
        last_name varchar(255) not null,
        middle_name varchar(255),
        mobile_number varchar(255),
        whatsapp_number varchar(255),
        primary key (patient_id)
);

create table payment (
        amount float4 not null,
        patient_id bigint not null,
        payment_id bigint not null,
        payment_timestamp timestamp(6) not null,
        payment_mode varchar(255) not null,
        payment_remark varchar(255),
        payment_type varchar(255) not null,
        primary key (payment_id)
);


alter table if exists bill_item 
       add constraint FKmnjf591m1ldlgm2dixmd1aq25 
       foreign key (billing_id) 
       references billing;


alter table if exists billing 
       add constraint FKavwest52v7f4qhxn6m4ffnkqa 
       foreign key (d_schedule_id) 
       references dialysis_schedule;


alter table if exists billing 
       add constraint FKu08erpk3um4b9vratnsipspk 
       foreign key (patient_id) 
       references patient;


alter table if exists billing_payment 
       add constraint FK995mcph4t4hdy4wseqwive1e9 
       foreign key (payment_id) 
       references payment;


alter table if exists billing_payment 
       add constraint FKqoavacke6eh9w3w7mdf2oys2i 
       foreign key (billing_id) 
       references billing;


alter table if exists dialysis_schedule 
       add constraint FK75gm37kigw9ankai76fyqj6jm 
       foreign key (d_slot_id) 
       references dialysis_slot;


alter table if exists dialysis_schedule 
       add constraint FKp54ssw5c9irl8f8uxobynxan8 
       foreign key (d_station_id) 
       references dialysis_station;


alter table if exists dialysis_schedule 
       add constraint FKa2qf3wisrca8stjgegijgqhre 
       foreign key (patient_id) 
       references patient;


alter table if exists insurance 
       add constraint FK68un5r1lv3f9y6m5ujm4plww5 
       foreign key (patient_id) 
       references patient;


alter table if exists payment 
       add constraint FK8t7hyujfhrl2jneu9jayv89tq 
       foreign key (patient_id) 
       references patient;