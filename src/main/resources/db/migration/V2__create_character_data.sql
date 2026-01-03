CREATE TABLE IF NOT EXISTS character_data
(
    level
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    character_level
    VARCHAR
(
    255
) NOT NULL,
    character_name VARCHAR
(
    255
) NOT NULL,
    character_image_url VARCHAR
(
    500
) NOT NULL,
    level_description TEXT,
    PRIMARY KEY
(
    level
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO character_data (level, character_level, character_name, character_image_url, level_description)
VALUES (1, 'LV1', '아메바', 'https://kr.object.ncloudstorage.com/neurous-registry-bucket/profile/lv1_profile.png',
        '단세포 원시 생명체로 아직 글을 읽는게 낯설지만 성장의 씨앗이 싹트는 단계예요.'),

       (2, 'LV2', '꼬물물고기', 'https://kr.object.ncloudstorage.com/neurous-registry-bucket/profile/lv2_profile.png',
        '호기심이 생기기 시작했어요. 새로운 글을 만나며 넓은 바다로 뛰어드는 단계예요.'),

       (3, 'LV3', '리틀 몽키', 'https://kr.object.ncloudstorage.com/neurous-registry-bucket/profile/lv3_profile.png',
        '지식을 도구처럼 활용하기 시작했어요. 글에 대해 스스로 생각하는 단계예요'),

       (4, 'LV4', '꼬마원시인', 'https://kr.object.ncloudstorage.com/neurous-registry-bucket/profile/lv4_profile.png',
        '배운 지식을 연결해 쓰며 글의 의미를 깊게 이해하기 시작하는 단계예요.'),

       (5, 'LV5', '아인슈타인', 'https://kr.object.ncloudstorage.com/neurous-registry-bucket/profile/lv5_profile.png',
        '글을 이해하는데 그치지 않고 새로운 생각으로 확장하는 능력이 생겼어요.');
