SET REFERENTIAL_INTEGRITY FALSE;
truncate table Apply_tb;
truncate table Day_tb;
truncate table Group_tb;
truncate table Member_tb;
truncate table Notification_tb;
truncate table Schedule_tb;
truncate table Substitute_tb;
truncate table User_tb;
truncate table Week_tb;
truncate table Worktime_tb;
SET REFERENTIAL_INTEGRITY TRUE;

insert into User_tb (`user_id`,`kakao_id`, `name`, `phone_number`) values ('1','3041041899','이재훈','010-1111-1111');
insert into User_tb (`user_id`,`kakao_id`, `name`, `phone_number`) values ('2','3041041892','안한주','010-2222-2222');

insert into Group_tb (`group_id`, `name`, `phone_number`, `address`) values ('1','맘스터치','011-1111-1111','부산광역시');

insert into Member_tb (`member_id`,`is_admin`,`group_group_id`,`user_user_id`) values ('1','true','1','1');
insert into Member_tb (`member_id`,`is_admin`,`group_group_id`,`user_user_id`) values ('2','false','1','2');

insert into Notification_tb (`notification_id`,`content`,`type`,`is_read`,`user_user_id`) values ('1','환영합니다.','ETC','false','1');

insert into Schedule_tb (`schedule_id`, `group_group_id`) values ('1','1');

insert into Week_tb (`week_id`,`start_time`,`schedule_schedule_id`) values ('1',current_time,'1');

insert into Day_tb (`day_id`,`weekday`,`week_week_id`) values ('1','MONDAY','1');

insert into Worktime_tb (`worktime_id`,`start_time`,`end_time`,`amount`,`day_day_id`) values ('1',current_time, current_time,'1','1');

insert into Apply_tb (`apply_id`,`state`,`worktime_worktime_id`,`member_member_id`) values ('1','FIX','1','2');

insert into Substitute_tb (`substitute_id`,`content`,`admin_approve`,`applicant_apply_id`,`receptionist_apply_id`) values ('1','사유서','false','1','1');