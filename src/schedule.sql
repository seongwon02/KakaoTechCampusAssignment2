use schedule

CREATE TABLE event (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    username     VARCHAR(255) NOT NULL COMMENT '작성자',
    password     VARCHAR(255) NOT NULL COMMENT '비밀번호',
    title        VARCHAR(255) NOT NULL COMMENT '제목',
    contents     TEXT COMMENT '내용',
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    modified_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
)


create table user
(
	id 			 bigint auto_increment primary key comment '사용자 식별자',
	name 		 varchar(255) not null comment '이름',
	email 		 varchar(255) not null comment '이메일',
	created_at   datetime not null default current_timestamp comment '생성일',
    modified_at  datetime not null default current_timestamp on update current_timestamp comment '수정일'
)

alter table event drop column username
alter table event add column user_id bigint comment '작성자 id'
alter table event add constraint foreign key(user_id) references user(id)
alter table user add constraint email UNIQUE (email)



