select * from user_table;
;;
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


insert into user_table (user_name, user_email, user_nickname, user_phone, user_birth, user_password, created_at, user_level) values ('십오', '15@gmail.com', '15', '010-5555-5555', '2010-01-01', '$2a$10$xcWOSCsY4tsPgkUQ9WNf0OYSNyBZ7TVAPzPjcq9LBcOFuFTEMXy/.', now(), '일반회원');;
insert into user_table (user_name, user_email, user_nickname, user_phone, user_birth, user_password, created_at, user_level) values ('십육', '16@gmail.com', '16', '010-6666-6666', '2010-01-01', '$2a$10$xcWOSCsY4tsPgkUQ9WNf0OYSNyBZ7TVAPzPjcq9LBcOFuFTEMXy/.', now(), '일반회원');;
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
	values(now(), concat('contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent',i),0,concat('titletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitletitle',i),1,1);

    INSERT INTO post_table (created_at,post_content,post_heart,post_title,board_id,writer_id)
    values(now(), concat('ReviewContentReviewContentReviewContentReviewContentReviewContentReviewContentReviewContentReviewContentReviewContent',i),0,concat('ReviewTitleReviewTitleReviewTitleReviewTitleReviewTitleReviewTitleReviewTitleReviewTitleReviewTitleReviewTitle',i),2,2);

	SET i = i + 1;
    END WHILE;
END;;


call loop_insert_posts;;
