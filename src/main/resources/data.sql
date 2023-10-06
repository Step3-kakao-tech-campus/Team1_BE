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

/**
    users dummy data
 */
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('1','1','이재훈','010-0000-0001');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('2','2','안한주','010-0000-0002');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('3','3','차지원','010-0000-0003');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('4','4','최은진','010-0000-0004');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('5','5','이현지','010-0000-0005');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('6','6','민하린','010-0000-0006');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('7','7','홍길동','010-0000-0007');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('8','8','박덕춘','010-0000-0008');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('9','9','곽민재','010-0000-0009');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('10','10','선호민','010-0000-0010');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('11','11','이상우','010-0000-0011');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('12','12','이주휘','010-0000-0012');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('13','13','윤정권','010-0000-0013');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('14','14','이코코','010-0000-0014');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('15','15','김보현','010-0000-0015');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('16','16','정혜임','010-0000-0016');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('17','17','고민영','010-0000-0017');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('18','18','박원빈','010-0000-0018');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('19','19','정규민','010-0000-0019');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('20','20','김태이','010-0000-0020');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('21','21','남희두','010-0000-0021');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('22','22','선민기','010-0000-0022');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('23','23','정현규','010-0000-0023');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('24','24','김지수','010-0000-0024');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('25','25','성해은','010-0000-0025');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('26','26','이지연','010-0000-0026');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('27','27','이나연','010-0000-0027');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('28','28','최이현','010-0000-0028');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('29','29','박나언','010-0000-0029');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('30','30','하동균','010-0000-0030');

/**
    group 에 속하지 않는 users dummy data
 */
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('31','31','하현상','010-0000-0031');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('32','32','백이진','010-0000-0032');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('33','33','한요한','010-0000-0033');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('34','34','백예린','010-0000-0034');
insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('35','35','이영지','010-0000-0035');


/**
    groups dummy data
 */
insert into groups (`id`, `name`, `phone_number`, `address`) values ('1','백소정 부산대점','011-0000-0001','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('2','족발삶는마을 본점','011-0000-0002','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('3','천리쿵푸 마라탕&마라샹궈 부산대점','011-0000-0003','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('4','크로플덕 오리아가씨 장전점','011-0000-0004','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('5','하삼동커피 동래래미안점','011-0000-0005','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('6','하이오커피 장전역점','011-0000-0006','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('7','청솔로9 부산대점','011-0000-0007','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('8','요거프레소 장전역점','011-0000-0008','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('9','메가커피 장전역점','011-0000-0009','부산광역시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('10','뚜레쥬르 공덕역점','011-0000-0010','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('11','투썸플레이스 마포대로점','011-0000-0011','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('12','파리바게뜨 공덕역사점','011-0000-0012','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('13','후라이드 참잘하는집 삼전점','011-0000-0013','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('14','동네파스타 송파본점','011-0000-0014','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('15','귀한족발 잠실점','011-0000-0015','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('16','롯데리아 송파점','011-0000-0016','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('17','맥도날드 송파잠실DT점','011-0000-0017','서울특별시');
insert into groups (`id`, `name`, `phone_number`, `address`) values ('18','이나타코야끼 잠실점','011-0000-0018','서울특별시');


/**
    member dummy data
 */
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('1', true, '1', '1');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('2', false, '1', '2');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('3', false, '1', '3');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('4', false, '1', '4');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('5', false, '1', '5');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('6', false, '1', '6');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('7', false, '1', '7');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('8', false, '1', '8');

insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('9', true, '2', '9');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('10', false, '2', '10');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('11', false, '2', '11');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('12', false, '2', '12');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('13', false, '2', '13');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('14', false, '2', '14');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('15', false, '2', '15');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('16', false, '2', '16');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('17', false, '2', '17');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('18', false, '2', '18');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('19', false, '2', '19');

insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('20', true, '10', '20');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('21', false, '10', '21');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('22', false, '10', '22');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('23', false, '10', '23');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('24', false, '10', '24');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('25', false, '10', '25');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('26', false, '10', '26');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('27', false, '10', '27');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('28', false, '10', '28');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('29', false, '10', '29');
insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('30', false, '10', '30');



-- insert into users (`id`,`kakao_id`,`name`,`phone_number`) values ('1','1','이재훈','010-1111-1111');
-- insert into Users (`id`,`kakao_id`,`name`,`phone_number`) values ('2','2','안한주','010-2222-2222');
--
-- insert into groups (`id`, `name`, `phone_number`, `address`) values ('1','맘스터치','011-1111-1111','부산광역시');
-- insert into groups (`id`, `name`, `phone_number`, `address`) values ('2','맘스터치','011-1111-1111','부산광역시');
--
-- insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('1','true','1','1');
-- insert into member (`id`,`is_admin`,`group_id`,`user_id`) values ('2','false','1','2');
--
-- insert into notification (`id`,`content`,`type`,`is_read`,`user_id`) values ('1','환영합니다.','ETC','false','1');
--
-- insert into schedule (`id`, `group_id`) values ('1','1');
--
-- insert into week (`id`,`start_time`,`schedule_id`) values ('1',current_time,'1');
--
-- insert into days (`id`,`weekday`,`week_id`) values ('1','MONDAY','1');
--
-- insert into worktime (`id`,`start_time`,`end_time`,`amount`,`day_id`) values ('1',current_time, current_time,'1','1');
--
-- insert into apply (`id`,`state`,`worktime_id`,`member_id`) values ('1','FIX','1','2');
--
-- insert into substitute (`id`,`content`,`admin_approve`,`applicant_id`,`receptionist_id`) values ('1','사유서','false','1','1');
