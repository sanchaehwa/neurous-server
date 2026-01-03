-- 1. reward_info_data 테이블 생성
CREATE TABLE IF NOT EXISTS reward_info_data
(
    id
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    reward_type
    VARCHAR
(
    255
) NOT NULL,
    reward_description TEXT NOT NULL,
    PRIMARY KEY
(
    id
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reward_info_data (reward_type, reward_description)
VALUES ('경험치', '경험치를 모아 캐릭터 레벨을 올릴 수 있어요'),
       ('포인트', '포인트를 사용해 더 많은 글을 읽을 수 있어요 글 한 편당 30포인트가 필요해요');
