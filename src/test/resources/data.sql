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
SET REFERENTIAL_INTEGRITY TRUE;

insert into users (`id`,`kakao_id`,`name`,`phone_number`)
values (1, 1, '이재훈', '010-0000-0001'),
       (2, 2, '안한주', '010-0000-0002'),
       (3, 3, '차지원', '010-0000-0003'),
       (4, 4, '최은진', '010-0000-0004'),
       (5, 5, '이현지', '010-0000-0005'),
       (6, 6, '민하린', '010-0000-0006'),
       (7, 7, '홍길동', '010-0000-0007');

insert into groups (`id`, `name`, `phone_number`, `business_number`, `address`)
values (1, '백소정 부산대점', '011-0000-0001', 1, '부산광역시');

-- admin
insert into member (`id`,`is_admin`,`group_id`,`user_id`)
values (1, true, 1, 1);

-- normal
insert into member (`id`,`is_admin`,`group_id`,`user_id`)
values (2, false, 1, 2),
       (3, false, 1, 3),
       (4, false, 1, 4),
       (5, false, 1, 5),
       (6, false, 1, 6);


insert into notification (`id`,`content`,`type`,`date_time`,`is_read`,`user_id`)
values (1, 'ㅁㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', '2222-11-22 12:34:56', false, 3),
       (2, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', '2222-11-23 12:34:56', true, 4),
       (3, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', '2222-11-25 12:34:56', true, 3),
       (4, 'ㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', '2222-10-23 12:34:56', false, 2);


insert into schedule(`id`,`group_id`)
values (1, 1);

insert into week(`id`,`status`,`start_date`,`schedule_id`)
values (1, 'STARTED', '2222-11-22', 1);

insert into days(`id`,`day_of_week`,`week_id`)
values (1,1,1),
       (2,2,1),
       (3,3,1),
       (4,4,1),
       (5,5,1),
       (6,6,1),
       (7,7,1);

-- Monday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (1, '00:00:00', '06:00:00', 3, 1),
    (2, '06:00:00', '09:00:00', 2, 1),
    (3, '09:00:00', '15:00:00', 1, 1);

-- Tuesday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (4, '00:00:00', '06:00:00', 2, 2),
    (5, '06:00:00', '09:00:00', 2, 2),
    (6, '09:00:00', '15:00:00', 2, 2);

-- Wednesday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (7, '00:00:00', '06:00:00', 3, 3),
    (8, '06:00:00', '09:00:00', 3, 3);

-- Thursday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (9, '00:00:00', '06:00:00', 1, 4),
    (10, '06:00:00', '09:00:00', 1, 4),
    (11, '09:00:00', '15:00:00', 1, 4);

-- Friday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (12, '00:00:00', '06:00:00', 1, 5),
    (13, '06:00:00', '09:00:00', 2, 5),
    (14, '09:00:00', '15:00:00', 1, 5);

-- Saturday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (15, '00:00:00', '06:00:00', 1, 6),
    (16, '06:00:00', '09:00:00', 1, 6);

-- Sunday
INSERT INTO worktime (`id`,`start_time`, `end_time`, `amount`, `day_id`)
VALUES
    (17, '00:00:00', '06:00:00', 2, 7),
    (18, '06:00:00', '09:00:00', 2, 7);

-- member2's applies
INSERT INTO apply (`id`,`state`,`member_id`,`worktime_id`)
VALUES
    (1, 'REMAIN', 2, 1),
    (2, 'REMAIN', 2, 2),
    (3, 'REMAIN', 2, 3),
    (4, 'REMAIN', 2, 4),
    (5, 'REMAIN', 2, 5),
    (6, 'REMAIN', 2, 7),
    (7, 'REMAIN', 2, 8),
    (8, 'REMAIN', 2, 9),
    (9, 'REMAIN', 2, 10),
    (11, 'REMAIN', 2, 12),
    (12, 'REMAIN', 2, 13),
    (13, 'REMAIN', 2, 14),
    (14, 'REMAIN', 2, 15),
    (15, 'REMAIN', 2, 16),
    (16, 'REMAIN', 2, 17),
    (17, 'REMAIN', 2, 18);

-- member3's applies
INSERT INTO apply (`id`,`state`,`member_id`,`worktime_id`)
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
INSERT INTO apply (`id`,`state`,`member_id`,`worktime_id`)
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
INSERT INTO apply (`id`,`state`,`member_id`,`worktime_id`)
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
INSERT INTO apply (`id`,`state`,`member_id`,`worktime_id`)
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