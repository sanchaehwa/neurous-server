CREATE TABLE IF NOT EXISTS reward_data
(
    id
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT,
    reward_item
    VARCHAR
(
    255
) NOT NULL,
    reward_point INT NOT NULL,
    reward_exp INT NOT NULL,
    PRIMARY KEY
(
    id
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reward_data (reward_item, reward_point, reward_exp)
VALUES ('미션달성시', 40, 40),
       ('글 읽기 시', 0, 5),
       ('퀴즈 정답시', 20, 30),
       ('퀴즈 오답시', 10, 10),
       ('데일리 출석시', 5, 10),
       ('위클리 출석시', 30, 30),
       ('광고 시청시', 0, 60);
