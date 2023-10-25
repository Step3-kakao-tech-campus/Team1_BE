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
truncate table recommended_worktime_apply;
truncate table recommended_weekly_schedule;
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
ALTER TABLE recommended_worktime_apply AUTO_INCREMENT=1;
ALTER TABLE recommended_weekly_schedule AUTO_INCREMENT=1;
SET REFERENTIAL_INTEGRITY TRUE;



insert into groups (`id`, `name`, `phone_number`, `business_number`, `address`)
values (1, '백소정 부산대점', '011-0000-0001', 1, '부산광역시');

insert into users (`id`,`kakao_id`,`name`,`phone_number`, `is_admin`, `group_id`)
values (1, 1, '이재훈', '010-0000-0001', true, 1),
       (2, 2, '안한주', '010-0000-0002', false, 1),
       (3, 3, '차지원', '010-0000-0003', false, 1),
       (4, 4, '최은진', '010-0000-0004', false, 1),
       (5, 5, '이현지', '010-0000-0005', false, 1),
       (6, 6, '민하린', '010-0000-0006', false, 1),
       (7, 7, '홍길동', '010-0000-0007', false, 1);

insert into invite (`id`, `code`, `group_id`)
values (1, 'testcode1', 1);

-- admin
insert into member (`id`,`group_id`,`user_id`)
values (1, 1, 1);

-- normal
insert into member (`id`,`group_id`,`user_id`)
values (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 1, 5),
       (6, 1, 6);

INSERT INTO notification (`id`, `content`, `type`, `is_read`, `user_id`, `created_by`, `created_at`, `last_updated_by`, `updated_at`)
VALUES (1, 'ㅁㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', false, 3, 1, '2022-11-22 12:34:56', 1, '2022-11-22 12:34:56'),
       (2, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', true, 4, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00'),
       (3, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', true, 3, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00'),
       (4, 'ㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', false, 2, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00');


insert into schedule(`id`,`group_id`)
values (1, 1);

insert into week(`id`,`status`,`start_date`,`schedule_id`)
values (1, 'ENDED', '2023-10-09', 1),
       (2, 'STARTED', '2023-10-16', 1);

insert into days(`id`,`day_of_week`,`week_id`)
values (1,1,1),
       (2,2,1),
       (3,3,1),
       (4,4,1),
       (5,5,1),
       (6,6,1),
       (7,7,1);

insert into days(`id`,`day_of_week`,`week_id`)
values (8,1,2),
       (9,2,2),
       (10,3,2),
       (11,4,2),
       (12,5,2),
       (13,6,2),
       (14,7,2);

-- Monday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (1, '오픈', '00:00:00', '06:00:00', 3, 1),
    (2, '오픈', '06:00:00', '09:00:00', 2, 1),
    (3, '오픈', '09:00:00', '15:00:00', 1, 1);

-- Tuesday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (4, '미들', '00:00:00', '06:00:00', 2, 2),
    (5, '미들', '06:00:00', '09:00:00', 2, 2),
    (6, '미들', '09:00:00', '15:00:00', 2, 2);

-- Wednesday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (7, '마감', '00:00:00', '06:00:00', 3, 3),
    (8, '마감', '06:00:00', '09:00:00', 3, 3);

-- Thursday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (9, '오픈', '00:00:00', '06:00:00', 1, 4),
    (10, '오픈', '06:00:00', '09:00:00', 1, 4),
    (11, '오픈', '09:00:00', '15:00:00', 1, 4);

-- Friday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (12, '미들', '00:00:00', '06:00:00', 1, 5),
    (13, '미들', '06:00:00', '09:00:00', 2, 5),
    (14, '미들', '09:00:00', '15:00:00', 1, 5);

-- Saturday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (15, '토요일', '00:00:00', '06:00:00', 1, 6),
    (16, '토요일', '06:00:00', '09:00:00', 1, 6);

-- Sunday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (17, '마지막', '00:00:00', '06:00:00', 2, 7),
    (18, '마지막', '06:00:00', '09:00:00', 2, 7);

-- member2's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (1, 'FIX', 2, 1),
    (2, 'FIX', 2, 2),
    (3, 'FIX', 2, 3),
    (4, 'FIX', 2, 4),
    (5, 'FIX', 2, 5),
    (6, 'FIX', 2, 7),
    (7, 'FIX', 2, 8),
    (8, 'FIX', 2, 9),
    (9, 'FIX', 2, 10),
    (11, 'FIX', 2, 12),
    (12, 'FIX', 2, 13),
    (13, 'FIX', 2, 14),
    (14, 'FIX', 2, 15),
    (15, 'FIX', 2, 16),
    (16, 'FIX', 2, 17),
    (17, 'FIX', 2, 18);

-- member3's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (18, 'REMAIN', 3, 2),
    (19, 'REMAIN', 3, 3),
    (20, 'REMAIN', 3, 4),
    (21, 'REMAIN', 3, 5),
    (22, 'REMAIN', 3, 6),
    (23, 'REMAIN', 3, 8),
    (24, 'REMAIN', 3, 9),
    (25, 'REMAIN', 3, 10),
    (26, 'REMAIN', 3, 11),
    (27, 'REMAIN', 3, 12),
    (28, 'REMAIN', 3, 13),
    (29, 'REMAIN', 3, 14),
    (30, 'REMAIN', 3, 15),
    (31, 'REMAIN', 3, 16),
    (32, 'REMAIN', 3, 17),
    (33, 'REMAIN', 3, 18);

-- member4's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (34, 'REMAIN', 4, 1),
    (35, 'REMAIN', 4, 3),
    (36, 'REMAIN', 4, 4),
    (37, 'REMAIN', 4, 5),
    (38, 'REMAIN', 4, 7),
    (39, 'REMAIN', 4, 9),
    (40, 'REMAIN', 4, 10),
    (41, 'REMAIN', 4, 15),
    (42, 'REMAIN', 4, 17),
    (43, 'REMAIN', 4, 18);

-- member5's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (44, 'REMAIN', 5, 1),
    (45, 'REMAIN', 5, 3),
    (46, 'REMAIN', 5, 4),
    (47, 'REMAIN', 5, 5),
    (48, 'REMAIN', 5, 6),
    (49, 'REMAIN', 5, 7),
    (50, 'REMAIN', 5, 8),
    (51, 'REMAIN', 5, 9),
    (52, 'REMAIN', 5, 10),
    (53, 'REMAIN', 5, 15),
    (54, 'REMAIN', 5, 16),
    (55, 'REMAIN', 5, 17),
    (56, 'REMAIN', 5, 18);

-- member6's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (57, 'REMAIN', 6, 1),
    (58, 'REMAIN', 6, 3),
    (59, 'REMAIN', 6, 4),
    (60, 'REMAIN', 6, 5),
    (61, 'REMAIN', 6, 7),
    (62, 'REMAIN', 6, 9),
    (63, 'REMAIN', 6, 10),
    (64, 'REMAIN', 6, 15),
    (65, 'REMAIN', 6, 17);

-- Monday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (19, '오픈', '00:00:00', '06:00:00', 3, 8),
    (20, '오픈', '06:00:00', '09:00:00', 2, 8),
    (21, '오픈', '09:00:00', '15:00:00', 1, 8);

-- Tuesday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (22, '미들', '00:00:00', '06:00:00', 2, 9),
    (23, '미들', '06:00:00', '09:00:00', 2, 9),
    (24, '미들', '09:00:00', '15:00:00', 2, 9);

-- Wednesday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (25, '마감', '00:00:00', '06:00:00', 3, 10),
    (26, '마감', '06:00:00', '09:00:00', 3, 10);

-- Thursday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (27, '오픈', '00:00:00', '06:00:00', 1, 11),
    (28, '오픈', '06:00:00', '09:00:00', 1, 11),
    (29, '오픈', '09:00:00', '15:00:00', 1, 11);

-- Friday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (30, '미들', '00:00:00', '06:00:00', 1, 12),
    (31, '미들', '06:00:00', '09:00:00', 2, 12),
    (32, '미들', '09:00:00', '15:00:00', 1, 12);

-- Saturday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (33, '토요일', '00:00:00', '06:00:00', 1, 13),
    (34, '토요일', '06:00:00', '09:00:00', 1, 13);

-- Sunday
INSERT INTO worktime (`id`,`title`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (35, '마지막', '00:00:00', '06:00:00', 2, 14),
    (36, '마지막', '06:00:00', '09:00:00', 2, 14);

-- member2's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (65+1, 'REMAIN', 2, 18+1),
    (65+2, 'REMAIN', 2, 18+2),
    (65+3, 'REMAIN', 2, 18+3),
    (65+4, 'REMAIN', 2, 18+4),
    (65+5, 'REMAIN', 2, 18+5),
    (65+6, 'REMAIN', 2, 18+7),
    (65+7, 'REMAIN', 2, 18+8),
    (65+8, 'REMAIN', 2, 18+9),
    (65+9, 'REMAIN', 2, 18+10),
    (65+11, 'REMAIN', 2, 18+12),
    (65+12, 'REMAIN', 2, 18+13),
    (65+13, 'REMAIN', 2, 18+14),
    (65+14, 'REMAIN', 2, 18+15),
    (65+15, 'REMAIN', 2, 18+16),
    (65+16, 'REMAIN', 2, 18+17),
    (65+17, 'REMAIN', 2, 18+18);

-- member3's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (65+18, 'REMAIN', 3, 18+2),
    (65+19, 'REMAIN', 3, 18+3),
    (65+20, 'REMAIN', 3, 18+4),
    (65+21, 'REMAIN', 3, 18+5),
    (65+22, 'REMAIN', 3, 18+6),
    (65+23, 'REMAIN', 3, 18+8),
    (65+24, 'REMAIN', 3, 18+9),
    (65+25, 'REMAIN', 3, 18+10),
    (65+26, 'REMAIN', 3, 18+11),
    (65+27, 'REMAIN', 3, 18+12),
    (65+28, 'REMAIN', 3, 18+13),
    (65+29, 'REMAIN', 3, 18+14),
    (65+30, 'REMAIN', 3, 18+15),
    (65+31, 'REMAIN', 3, 18+16),
    (65+32, 'REMAIN', 3, 18+17),
    (65+33, 'REMAIN', 3, 18+18);

-- member4's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (65+34, 'REMAIN', 4, 18+1),
    (65+35, 'REMAIN', 4, 18+3),
    (65+36, 'REMAIN', 4, 18+4),
    (65+37, 'REMAIN', 4, 18+5),
    (65+38, 'REMAIN', 4, 18+7),
    (65+39, 'REMAIN', 4, 18+9),
    (65+40, 'REMAIN', 4, 18+10),
    (65+41, 'REMAIN', 4, 18+15),
    (65+42, 'REMAIN', 4, 18+17),
    (65+43, 'REMAIN', 4, 18+18);

-- member5's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (65+44, 'REMAIN', 5, 18+1),
    (65+45, 'REMAIN', 5, 18+3),
    (65+46, 'REMAIN', 5, 18+4),
    (65+47, 'REMAIN', 5, 18+5),
    (65+48, 'REMAIN', 5, 18+6),
    (65+49, 'REMAIN', 5, 18+7),
    (65+50, 'REMAIN', 5, 18+8),
    (65+51, 'REMAIN', 5, 18+9),
    (65+52, 'REMAIN', 5, 18+10),
    (65+53, 'REMAIN', 5, 18+15),
    (65+54, 'REMAIN', 5, 18+16),
    (65+55, 'REMAIN', 5, 18+17),
    (65+56, 'REMAIN', 5, 18+18);

-- member6's applies
INSERT INTO apply (`id`,`status`,`member_id`,`worktime_id`)
VALUES
    (65+57, 'REMAIN', 6, 18+1),
    (65+58, 'REMAIN', 6, 18+3),
    (65+59, 'REMAIN', 6, 18+4),
    (65+60, 'REMAIN', 6, 18+5),
    (65+61, 'REMAIN', 6, 18+7),
    (65+62, 'REMAIN', 6, 18+9),
    (65+63, 'REMAIN', 6, 18+10),
    (65+64, 'REMAIN', 6, 18+15),
    (65+65, 'REMAIN', 6, 18+17);