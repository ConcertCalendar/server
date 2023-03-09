SET FOREIGN_KEY_CHECKS = 0;
;;
truncate table user_table;
;;
truncate table board_table;
;;
truncate table post_table;
;;
truncate table calendar;
;;
SET FOREIGN_KEY_CHECKS = 1;
;;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('해리 스타일스 (Harry Styles)','해리 스타일스 첫 내한공연','지정석VIP석 250,000원\n지정석P석 198,000원\n스탠딩P석 165,000원\n지정석R석 165,000원\n지정석S석 143,000원\n지정석A석 121,000원\n지정석B석 110,000원\n지정석C석 88,000원', "20:00:00","2023-03-20 20:00:00","2023-03-20 21:30:00",now());;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('Men I Trust','MEN I TRUST LIVE IN SEOUL','스탠딩석 99,000원\n지정석 99,000원', "20:00:00","2023-04-22 20:00:00","2023-04-22 21:00:00",now());;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('NEW HOPE CLUB','NEW HOPE CLUB LIVE IN SEOUL','VIP석 200,000원\n일반석 99,000원', "19:20:00","2023-04-23 19:20:00","2023-04-23 20:50:00",now());;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('선셋 롤러코스터(Sunset Rollercoaster)','선셋 롤러코스터 내한공연 (Sunset Rollercoaster ＂Infinity Sunset＂ Tour in Seoul)','스탠딩석 88,000원\n지정석 88,000원', "20:00:00","2023-03-29 20:00:00","2023-03-29 21:40:00",now());;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('Sum 41','Sum 41 Live in Seoul (썸 41 내한공연)','스탠딩석 115,000원\n지정석 115,000원', "20:00:00","2023-03-28 20:00:00","2023-03-28 21:30:00",now());;

insert into calendar (singer, con_title, con_content, con_time, con_start, con_end, created_date) values ('킹스 오브 컨비니언스 (Kings Of Convenience)','킹스 오브 컨비니언스 내한공연（Kings Of Convenience Live in Seoul','스탠딩석 99,000원\n지정석 99,000원', "20:00:00","2023-03-17 20:00:00","2023-03-17 21:30:00",now());;


insert into user_table (user_name, user_email, user_nickname, user_phone, user_birth, user_password, created_at, user_level) values ('십오', '15@gmail.com', '중현15', '010-5555-5555', '1996-01-06', '$2a$10$xcWOSCsY4tsPgkUQ9WNf0OYSNyBZ7TVAPzPjcq9LBcOFuFTEMXy/.', now(), '일반회원');;
insert into user_table (user_name, user_email, user_nickname, user_phone, user_birth, user_password, created_at, user_level) values ('십육', '16@gmail.com', '휘경16', '010-6666-6666', '1997-01-01', '$2a$10$xcWOSCsY4tsPgkUQ9WNf0OYSNyBZ7TVAPzPjcq9LBcOFuFTEMXy/.', now(), '일반회원');;
insert into user_roles (user_id, roles) values (1, 'ROLE_USER');;
insert into user_roles (user_id, roles) values (2, 'ROLE_USER');;

insert into board_table (created_at,board_name) values (now(), '자유게시판');
;;
insert into board_table (created_at,board_name) values (now(), '공연후기 게시판');
;;

DROP PROCEDURE IF EXISTS loop_insert_posts;;

CREATE PROCEDURE loop_insert_posts()
BEGIN
	DECLARE i INT DEFAULT 1;
    WHILE (i <= 50) DO
	INSERT INTO post_table (created_at,post_content,post_heart,post_title,board_id,writer_id)
	values(now(), concat('내용내용내용내용내용내용내용\n내용내용내용내용내용내용내용\n내용내용내용내용내용내용내용\n',i),0,concat('제목제목제목제목\n제목제목제목제목\n',i),1,1);

    INSERT INTO post_table (created_at,post_content,post_heart,post_title,board_id,writer_id)
    values(now(), concat('후기내용후기내용후기내용후기내용\n후기내용후기내용후기내용후기내용',i),0,concat('후기제목후기제목후기제목후기제목\n후기제목후기제목후기제목후기제목',i),2,2);

	SET i = i + 1;
    END WHILE;
END;;


call loop_insert_posts;;

insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1댓글1',1,1);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2댓글2',1,2);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3댓글3',2,1);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4댓글4',2,2);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5댓글5',1,1);
;;

insert into reply_table (created_at,reply_content,comment_id,writer_id) values (now(), '답글1답글1',1,1);
;;
insert into reply_table (created_at,reply_content,comment_id,writer_id) values (now(), '답글2답글2',1,1);
;;
insert into reply_table (created_at,reply_content,comment_id,writer_id) values (now(), '답글3답글3',2,1);
;;
insert into reply_table (created_at,reply_content,comment_id,writer_id) values (now(), '답글4답글4',2,2);
;;
insert into reply_table (created_at,reply_content,comment_id,writer_id) values (now(), '답글5답글5',2,2);
;;