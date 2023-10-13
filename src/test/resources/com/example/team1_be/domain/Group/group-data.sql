SET REFERENTIAL_INTEGRITY FALSE;
truncate table apply;
truncate table days;
truncate table groups;
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
ALTER TABLE member AUTO_INCREMENT=1;
ALTER TABLE notification AUTO_INCREMENT=1;
ALTER TABLE schedule AUTO_INCREMENT=1;
ALTER TABLE substitute AUTO_INCREMENT=1;
ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE week AUTO_INCREMENT=1;
ALTER TABLE worktime AUTO_INCREMENT=1;
SET REFERENTIAL_INTEGRITY TRUE;

insert into users (`id`,`kakao_id`,`name`,`phone_number`)
values (1, 1, '이재훈', '010-0000-0001'),
       (2, 2, '안한주', '010-0000-0002'),
       (3, 3, '차지원', '010-0000-0003'),
       (4, 4, '최은진', '010-0000-0004'),
       (5, 5, '이현지', '010-0000-0005'),
       (6, 6, '민하린', '010-0000-0006');