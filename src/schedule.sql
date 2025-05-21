use schedule

CREATE TABLE event (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    username     VARCHAR(255) NOT NULL COMMENT '작성자',
    password     VARCHAR(255) NOT NULL COMMENT '비밀번호',
    title        VARCHAR(255) NOT NULL COMMENT '제목',
    contents     TEXT COMMENT '내용',
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    modified_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
)

alter table event
	modify column modified_at datetime not null
	default CURRENT_TIMESTAMP on update current_timestamp
	comment '수정일'