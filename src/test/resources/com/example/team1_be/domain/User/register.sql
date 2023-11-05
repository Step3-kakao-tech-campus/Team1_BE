SET
REFERENTIAL_INTEGRITY FALSE;
truncate table unfinished_user;
ALTER TABLE unfinished_user AUTO_INCREMENT=1;
SET
REFERENTIAL_INTEGRITY TRUE;

SET
REFERENTIAL_INTEGRITY FALSE;
truncate table apply;
truncate table groups;
truncate table invite;
truncate table notification;
truncate table substitute;
truncate table users;
truncate table week;
truncate table worktime;
truncate table recommended_worktime_apply;
truncate table recommended_weekly_schedule;
ALTER TABLE apply AUTO_INCREMENT=1;
ALTER TABLE groups AUTO_INCREMENT=1;
ALTER TABLE invite AUTO_INCREMENT=1;
ALTER TABLE notification AUTO_INCREMENT=1;
ALTER TABLE substitute AUTO_INCREMENT=1;
ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE week AUTO_INCREMENT=1;
ALTER TABLE worktime AUTO_INCREMENT=1;
ALTER TABLE recommended_worktime_apply AUTO_INCREMENT=1;
ALTER TABLE recommended_weekly_schedule AUTO_INCREMENT=1;
SET
REFERENTIAL_INTEGRITY TRUE;

insert into unfinished_user (`id`, `code`, `kakao_id`)
values (1L, 'aaaa', 10000L),
       (2L, 'bbbb', 20000L);

insert into users (`id`, `kakao_id`, `name`, `phone_number`, `is_admin`, `group_id`)
values (1L, 10000L, 'eunjin', null, true, null);