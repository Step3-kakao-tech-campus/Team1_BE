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
values (1, 1, '이재훈', '010-0000-0001', true, 1),
       (2, 2, '안한주', '010-0000-0002', false, 1),
       (3, 3, '차지원', '010-0000-0003', false, 1),
       (4, 4, '최은진', '010-0000-0004', false, 1),
       (5, 5, '이현지', '010-0000-0005', false, 1),
       (6, 6, '민하린', '010-0000-0006', false, 1),
       (7, 7, '홍길동', '010-0000-0007', false, 1);

insert into invite (`id`, `code`, `group_id`)
values (1, 'testcode1', 1);

INSERT INTO notification (`id`, `content`, `type`, `is_read`, `user_id`, `created_by`, `created_at`, `last_updated_by`,
                          `updated_at`)
VALUES (1, 'ㅁㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', false, 1, 1, '2022-11-22 12:34:56', 1, '2022-11-22 12:34:56'),
       (2, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', true, 1, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00'),
       (3, 'ㅇㅇㅇ 님! 새로운 알림입니다.', 'START', true, 1, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00'),
       (4, 'ㅁㅁ 님! 새로운 모임을 만들어보세요~', 'START', false, 1, 1, '2023-10-13 10:00:00', 1, '2023-10-13 10:00:00');

insert into week(`id`, `status`, `start_date`, `group_id`)
values (1, 'ENDED', '2023-10-09', 1),
       (2, 'STARTED', '2023-10-16', 1);

-- first week schedule
insert into worktime(`id`, `title`, `start_time`, `end_time`, `week_id`)
values (1, '오픈', '00:00:00', '06:00:00', 1),
       (2, '미들', '07:00:00', '12:00:00', 1),
       (3, '마감', '14:00:00', '18:00:00', 1);

-- second week schedule
insert into worktime(`id`, `title`, `start_time`, `end_time`, `week_id`)
values (4, '오픈', '00:00:00', '06:00:00', 2),
       (5, '미들', '07:00:00', '12:00:00', 2),
       (6, '마감', '14:00:00', '18:00:00', 2);

-- -- 1st week schedule
-- monday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (1, '2023-10-09', 0, 3, 1),
       (2, '2023-10-09', 0, 2, 2),
       (3, '2023-10-09', 0, 1, 3);

-- tuesday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (4, '2023-10-10', 1, 2, 1),
       (5, '2023-10-10', 1, 2, 2),
       (6, '2023-10-10', 1, 2, 3);

-- wednesday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (7, '2023-10-11', 2, 3, 1),
       (8, '2023-10-11', 2, 3, 2),
       (9, '2023-10-11', 2, 0, 3);

-- thursday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (10, '2023-10-12', 3, 1, 1),
       (11, '2023-10-12', 3, 1, 2),
       (12, '2023-10-12', 3, 1, 3);

-- friday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (13, '2023-10-13', 4, 1, 1),
       (14, '2023-10-13', 4, 2, 2),
       (15, '2023-10-13', 4, 1, 3);

-- saturday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (16, '2023-10-14', 5, 1, 1),
       (17, '2023-10-14', 5, 1, 2),
       (18, '2023-10-14', 5, 0, 3);

-- sunday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (19, '2023-10-15', 6, 2, 1),
       (20, '2023-10-15', 6, 2, 2),
       (21, '2023-10-15', 6, 0, 3);

-- 1st member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
values (1, 'FIX', 2, 1),
       (2, 'FIX', 2, 2),
       (3, 'FIX', 2, 3),

       (4, 'FIX', 2, 4),
       (5, 'FIX', 2, 5),

       (6, 'FIX', 2, 7),
       (7, 'FIX', 2, 8),

       (8, 'FIX', 2, 10),
       (9, 'FIX', 2, 11),

       (10, 'FIX', 2, 13),
       (11, 'FIX', 2, 14),
       (12, 'FIX', 2, 15),

       (13, 'FIX', 2, 16),
       (14, 'FIX', 2, 17),

       (15, 'FIX', 2, 19),
       (16, 'FIX', 2, 20);

-- 2nd member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (17, 'FIX', 3, 2),
       (18, 'REMAIN', 3, 3),

       (19, 'FIX', 3, 4),
       (20, 'FIX', 3, 5),
       (21, 'FIX', 3, 6),

       (22, 'FIX', 3, 8),

       (23, 'REMAIN', 3, 10),
       (24, 'REMAIN', 3, 11),
       (25, 'FIX', 3, 12),

       (26, 'REMAIN', 3, 13),
       (27, 'FIX', 3, 14),
       (28, 'REMAIN', 3, 15),

       (29, 'REMAIN', 3, 16),
       (30, 'REMAIN', 3, 17),

       (31, 'FIX', 3, 19),
       (32, 'FIX', 3, 20);

-- 3rd member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (33, 'FIX', 4, 1),
       (34, 'REMAIN', 4, 3),

       (35, 'REMAIN', 4, 4),
       (36, 'REMAIN', 4, 5),

       (37, 'FIX', 4, 7),

       (38, 'REMAIN', 4, 10),
       (39, 'REMAIN', 4, 11),

       (40, 'REMAIN', 4, 16),

       (41, 'REMAIN', 4, 19),
       (42, 'REMAIN', 4, 20);

-- 4th member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (43, 'FIX', 5, 1),
       (44, 'REMAIN', 5, 3),

       (45, 'REMAIN', 5, 4),
       (46, 'REMAIN', 5, 5),
       (47, 'FIX', 5, 6),

       (48, 'FIX', 5, 7),
       (49, 'FIX', 5, 8),

       (50, 'REMAIN', 5, 10),
       (51, 'REMAIN', 5, 11),

       (52, 'REMAIN', 5, 16),
       (53, 'REMAIN', 5, 17),

       (54, 'REMAIN', 5, 19),
       (55, 'REMAIN', 5, 20);

-- 5th member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (56, 'REMAIN', 6, 1),
       (57, 'REMAIN', 6, 3),

       (58, 'REMAIN', 6, 4),
       (59, 'REMAIN', 6, 5),

       (60, 'REMAIN', 6, 7),

       (61, 'REMAIN', 6, 10),
       (62, 'REMAIN', 6, 11),

       (63, 'REMAIN', 6, 16),

       (64, 'REMAIN', 6, 19);

-- -- 2nd week schedule
-- monday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (22, '2023-10-16', 0, 3, 4),
       (23, '2023-10-16', 0, 2, 5),
       (24, '2023-10-16', 0, 1, 6);

-- tuesday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (25, '2023-10-17', 1, 2, 4),
       (26, '2023-10-17', 1, 2, 5),
       (27, '2023-10-17', 1, 2, 6);

-- wednesday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (28, '2023-10-18', 2, 3, 4),
       (29, '2023-10-18', 2, 3, 5),
       (30, '2023-10-18', 2, 0, 6);

-- thursday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (31, '2023-10-19', 3, 1, 4),
       (32, '2023-10-19', 3, 1, 5),
       (33, '2023-10-19', 3, 1, 6);

-- friday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (34, '2023-10-20', 4, 1, 4),
       (35, '2023-10-20', 4, 2, 5),
       (36, '2023-10-20', 4, 1, 6);

-- saturday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (37, '2023-10-21', 5, 1, 4),
       (38, '2023-10-21', 5, 1, 5),
       (39, '2023-10-21', 5, 0, 6);

-- sunday
insert into detail_worktime(`id`, `date`, `day_of_week`, `amount`, `worktime_id`)
values (40, '2023-10-22', 6, 2, 4),
       (41, '2023-10-22', 6, 2, 5),
       (42, '2023-10-22', 6, 0, 6);

-- 1st member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
values (65, 'REMAIN', 2, 22),
       (66, 'REMAIN', 2, 23),
       (67, 'REMAIN', 2, 24),

       (68, 'REMAIN', 2, 25),
       (69, 'REMAIN', 2, 26),

       (70, 'REMAIN', 2, 28),
       (71, 'REMAIN', 2, 29),

       (72, 'REMAIN', 2, 31),
       (73, 'REMAIN', 2, 32),

       (74, 'REMAIN', 2, 34),
       (75, 'REMAIN', 2, 35),
       (76, 'REMAIN', 2, 36),

       (77, 'REMAIN', 2, 37),
       (78, 'REMAIN', 2, 38),

       (79, 'REMAIN', 2, 40),
       (80, 'REMAIN', 2, 41);

-- 2nd member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (81, 'REMAIN', 3, 23),
       (82, 'REMAIN', 3, 24),

       (83, 'REMAIN', 3, 25),
       (84, 'REMAIN', 3, 26),
       (85, 'REMAIN', 3, 27),

       (86, 'REMAIN', 3, 29),

       (87, 'REMAIN', 3, 31),
       (88, 'REMAIN', 3, 32),
       (89, 'REMAIN', 3, 33),

       (90, 'REMAIN', 3, 34),
       (91, 'REMAIN', 3, 35),
       (92, 'REMAIN', 3, 36),

       (93, 'REMAIN', 3, 37),
       (94, 'REMAIN', 3, 38),

       (95, 'REMAIN', 3, 40),
       (96, 'REMAIN', 3, 41);

-- 3rd member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (97, 'REMAIN', 4, 22),
       (98, 'REMAIN', 4, 24),

       (99, 'REMAIN', 4, 25),
       (100, 'REMAIN', 4, 26),

       (101, 'REMAIN', 4, 28),

       (102, 'REMAIN', 4, 31),
       (103, 'REMAIN', 4, 32),

       (104, 'REMAIN', 4, 37),

       (105, 'REMAIN', 4, 40),
       (106, 'REMAIN', 4, 41);

-- 4th member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (107, 'REMAIN', 5, 22),
       (108, 'REMAIN', 5, 24),

       (109, 'REMAIN', 5, 25),
       (110, 'REMAIN', 5, 26),
       (111, 'REMAIN', 5, 27),

       (112, 'REMAIN', 5, 27),
       (113, 'REMAIN', 5, 29),

       (114, 'REMAIN', 5, 31),
       (115, 'REMAIN', 5, 32),

       (116, 'REMAIN', 5, 37),
       (117, 'REMAIN', 5, 38),

       (118, 'REMAIN', 5, 40),
       (119, 'REMAIN', 5, 41);

-- 5th member's applies
insert into apply (`id`, `status`, `user_id`, `detail_worktime_id`)
VALUES (120, 'REMAIN', 6, 22),
       (121, 'REMAIN', 6, 24),

       (122, 'REMAIN', 6, 25),
       (123, 'REMAIN', 6, 26),

       (124, 'REMAIN', 6, 28),

       (125, 'REMAIN', 6, 31),
       (126, 'REMAIN', 6, 32),

       (127, 'REMAIN', 6, 37),

       (128, 'REMAIN', 6, 40);