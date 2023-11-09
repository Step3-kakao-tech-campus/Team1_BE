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

insert into groups (`id`, `name`, `phone_number`, `business_number`, `address`)
values (1, '백소정 부산대점', '011-0000-0001', 1, '부산광역시');

insert into users (`id`, `kakao_id`, `name`, `phone_number`, `is_admin`, `group_id`)
values (1, 1, '이재훈', '010-0000-0001', true, 1);

insert into invite (`id`, `code`, `group_id`)
values (1, 'testcode1', 1);

insert into week