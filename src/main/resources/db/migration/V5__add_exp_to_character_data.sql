-- 1. exp 컬럼 추가 (기존 데이터가 있을 수 있으므로 우선 DEFAULT 0으로 생성)
ALTER TABLE character_data
    ADD COLUMN exp INT NOT NULL DEFAULT 0;

-- 2. 각 레벨별 경험치 데이터 업데이트
UPDATE character_data
SET exp = 0
WHERE level = 1;
UPDATE character_data
SET exp = 100
WHERE level = 2;
UPDATE character_data
SET exp = 500
WHERE level = 3;
UPDATE character_data
SET exp = 2000
WHERE level = 4;
UPDATE character_data
SET exp = 6000
WHERE level = 5;
