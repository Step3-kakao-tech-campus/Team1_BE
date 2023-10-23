SET REFERENTIAL_INTEGRITY FALSE;
truncate table apply;
truncate table days;
truncate table groups;
truncate table invite;
truncate table member;
truncate table notification;
truncate table schedule;
truncate table substitute;
truncate table users;
truncate table week;
truncate table worktime;
ALTER TABLE apply AUTO_INCREMENT=1;
ALTER TABLE days AUTO_INCREMENT=1;
ALTER TABLE groups AUTO_INCREMENT=1;
ALTER TABLE invite AUTO_INCREMENT=1;
ALTER TABLE member AUTO_INCREMENT=1;
ALTER TABLE notification AUTO_INCREMENT=1;
ALTER TABLE schedule AUTO_INCREMENT=1;
ALTER TABLE substitute AUTO_INCREMENT=1;
ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE week AUTO_INCREMENT=1;
ALTER TABLE worktime AUTO_INCREMENT=1;
SET REFERENTIAL_INTEGRITY TRUE;

insert into users (`id`,`kakao_id`,`name`,`phone_number`, `is_admin`)
values (1, 1, '이재훈', '010-0000-0001', true),
       (2, 2, '안한주', '010-0000-0002', false),
       (3, 3, '차지원', '010-0000-0003', false),
       (4, 4, '최은진', '010-0000-0004', false),
       (5, 5, '이현지', '010-0000-0005', false),
       (6, 6, '민하린', '010-0000-0006', false),
       (7, 7, '홍길동', '010-0000-0007', false);