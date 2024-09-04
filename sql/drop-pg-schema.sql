alter table if exists bill_item 
    drop constraint if exists FKmnjf591m1ldlgm2dixmd1aq25;

alter table if exists billing 
    drop constraint if exists FKavwest52v7f4qhxn6m4ffnkqa;

alter table if exists billing 
    drop constraint if exists FKu08erpk3um4b9vratnsipspk;

alter table if exists billing_payment 
    drop constraint if exists FK995mcph4t4hdy4wseqwive1e9;

alter table if exists billing_payment 
    drop constraint if exists FKqoavacke6eh9w3w7mdf2oys2i;

alter table if exists dialysis_schedule 
    drop constraint if exists FK75gm37kigw9ankai76fyqj6jm;

alter table if exists dialysis_schedule 
    drop constraint if exists FKp54ssw5c9irl8f8uxobynxan8;

alter table if exists dialysis_schedule 
    drop constraint if exists FKa2qf3wisrca8stjgegijgqhre;

alter table if exists insurance 
    drop constraint if exists FK68un5r1lv3f9y6m5ujm4plww5;

alter table if exists payment 
    drop constraint if exists FK8t7hyujfhrl2jneu9jayv89tq;



drop table if exists bill_item cascade;
drop table if exists billing cascade;
drop table if exists billing_payment cascade;
drop table if exists dialysis_schedule cascade;
drop table if exists dialysis_slot cascade;
drop table if exists dialysis_station cascade;
drop table if exists insurance cascade;
drop table if exists patient cascade;
drop table if exists payment cascade;

drop sequence if exists bill_item_seq_gen;
drop sequence if exists billing_id_seq_gen;
drop sequence if exists d_schedule_id_seq_gen;
drop sequence if exists d_slot_seq_gen;
drop sequence if exists d_station_seq_gen;
drop sequence if exists insurance_id_seq_gen;
drop sequence if exists patient_id_seq_gen;
drop sequence if exists payment_id_seq_gen;