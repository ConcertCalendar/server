SET FOREIGN_KEY_CHECKS = 0;
;;
truncate table user_table;
;;
truncate table board_table;
;;
truncate table post_table;
;;
SET FOREIGN_KEY_CHECKS = 1;
;;


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

insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글1댓글1',1,1);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글2댓글2',1,2);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글3댓글3',2,1);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글4댓글4',2,2);
;;
insert into comment_table (created_at,comment_content,post_id,writer_id) values (now(), '댓글5댓글5',1,1);
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