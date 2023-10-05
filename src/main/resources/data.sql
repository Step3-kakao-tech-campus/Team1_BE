SET REFERENTIAL_INTEGRITY FALSE;
truncate table Apply_tb;
truncate table days;
truncate table groups;
truncate table member;
truncate table notification;
truncate table schedule;
truncate table substitute;
truncate table users;
truncate table week;
truncate table worktime;
SET REFERENTIAL_INTEGRITY TRUE;

-- insert into users (`user_id`,`kakao_id`,`name`,`phone_number`) values ('1','1','이재훈','010-1111-1111');
-- insert into Users (`user_id`,`kakao_id`,`name`,`phone_number`) values ('2','2','안한주','010-2222-2222');
--
-- insert into groups (`group_id`, `name`, `phone_number`, `address`) values ('1','맘스터치','011-1111-1111','부산광역시');
--
-- insert into member (`member_id`,`is_admin`,`group_group_id`,`user_user_id`) values ('1','true','1','1');
-- insert into member (`member_id`,`is_admin`,`group_group_id`,`user_user_id`) values ('2','false','1','2');
--
-- insert into notification (`notification_id`,`content`,`type`,`is_read`,`user_user_id`) values ('1','환영합니다.','ETC','false','1');
--
-- insert into schedule (`schedule_id`, `group_group_id`) values ('1','1');
--
-- insert into week (`week_id`,`start_time`,`schedule_schedule_id`) values ('1',current_time,'1');
--
-- insert into days (`day_id`,`weekday`,`week_week_id`) values ('1','MONDAY','1');
--
-- insert into worktime (`worktime_id`,`start_time`,`end_time`,`amount`,`day_day_id`) values ('1',current_time, current_time,'1','1');
--
-- insert into Apply_tb (`apply_id`,`state`,`worktime_worktime_id`,`member_member_id`) values ('1','FIX','1','2');
--
-- insert into substitute (`substitute_id`,`content`,`admin_approve`,`applicant_apply_id`,`receptionist_apply_id`) values ('1','사유서','false','1','1');
