SET
REFERENTIAL_INTEGRITY FALSE;
truncate table apply;
ALTER TABLE apply AUTO_INCREMENT=1;

truncate table detail_worktime;
ALTER TABLE detail_worktime AUTO_INCREMENT=1;

truncate table groups;
ALTER TABLE groups AUTO_INCREMENT=1;

truncate table invite;
ALTER TABLE invite AUTO_INCREMENT=1;

truncate table notification;
ALTER TABLE notification AUTO_INCREMENT=1;

truncate table recommended_weekly_schedule;
ALTER TABLE recommended_weekly_schedule AUTO_INCREMENT=1;

truncate table recommended_worktime_apply;
ALTER TABLE recommended_worktime_apply AUTO_INCREMENT=1;

truncate table substitute;
ALTER TABLE substitute AUTO_INCREMENT=1;

truncate table unfinished_user;
ALTER TABLE unfinished_user AUTO_INCREMENT=1;

truncate table users;
ALTER TABLE users AUTO_INCREMENT=1;

truncate table week;
ALTER TABLE week AUTO_INCREMENT=1;

truncate table worktime;
ALTER TABLE worktime AUTO_INCREMENT=1;

SET
REFERENTIAL_INTEGRITY TRUE;

insert into users (`id`, `kakao_id`, `name`, `phone_number`, `is_admin`)
values (1, 1, '이재훈', '010-0000-0001', true),
       (2, 2, '안한주', '010-0000-0002', false),
       (3, 3, '차지원', '010-0000-0003', false),
       (4, 4, '최은진', '010-0000-0004', false),
       (5, 5, '이현지', '010-0000-0005', false),
       (6, 6, '민하린', '010-0000-0006', false);