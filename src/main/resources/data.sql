ALTER TABLE board DISABLE TRIGGER ALL;
ALTER TABLE member DISABLE TRIGGER ALL;
DELETE FROM board;
DELETE FROM member;
ALTER TABLE board ENABLE TRIGGER ALL;
ALTER TABLE member ENABLE TRIGGER ALL;

ALTER TABLE board ALTER COLUMN board_img TYPE TEXT;
INSERT INTO member (member_id, member_name, email, outh_id, refresh_token)
VALUES
    (111, '김가나', 'johndoe@example.com', 1001, 'token123'),
    (112, '이다라', 'janesmith@example.com', 1002, 'token456'),
    (113, '박수영', 'alicejohnson@example.com', 1003, 'token789'),
    (114, '박태준', 'bobbrown@example.com', 1004, 'token012'),
    (115, '이수연', 'charliedavis@example.com', 1005, 'token345'),
    (116, '고동수', 'dianagreen@example.com', 1006, 'token678'),
    (117, '박동수', 'edwardhall@example.com', 1007, 'token901'),
    (118, '이준영', 'fionawhite@example.com', 1008, 'token234'),
    (119, '김민수', 'georgeking@example.com', 1009, 'token567'),
    (120, '고영희', 'hannahlee@example.com', 1010, 'token890');


INSERT INTO "board" ("board_id", "book_id", "member_id", "board_title", "board_content", "board_img", "address", "price", "category")
VALUES
    (11, 1, 111, '아버지의 해방일지 :정지아 장편소설', '사게 팔아요~~', 'https://image.aladin.co.kr/product/26942/84/cover/k582730818_1.jpg', '상인동', 15000, 'SELL'),
    (12, 2, 111, '흔한남매', '상태 좋습니다', 'https://image.aladin.co.kr/product/29858/98/cover/k432838027_1.jpg', '복현동', 20000, 'SELL'),
    (13, 3, 112, '달러구트 꿈 백화점 :이미예 장편소설', '재미있게 읽은 책 입니다.', 'https://image.aladin.co.kr/product/30048/51/cover/8936438832_1.jpg', '범어동', 18000, 'SELL'),
    (14, 4, 113, '도둑맞은 집중력 :집중력 위기의 시대, 삶의 주도권을 되찾는 법 ', '상태좋고 많은것을 느낄수있는 책입니다.', 'https://image.aladin.co.kr/product/24307/73/cover/k822630592_2.jpg', '황금동', 22000, 'SELL'),
    (15, 5, 112, '메리골드 마음 세탁소 =윤정은 장편소설', '이 책 구해요 ~!', 'https://image.aladin.co.kr/product/24512/70/cover/k392630952_1.jpg', '대명동', 17000, 'BUY'),
    (16, 6, 114, '고양이 해결사 깜냥', '이거 파는 사람 있나용', 'https://image.aladin.co.kr/product/31559/97/cover/k682832859_1.jpg', '상인2동', 21000, 'BUY'),
    (17, 7, 113, '세이노의 가르침 :피보다 진하게 살아라 ', '이책을 구매하고 싶습니다.', 'https://image.aladin.co.kr/product/31222/5/cover/k562831250_2.jpg', '수성3동', 25000, 'BUY'),
    (18, 8, 114, '물고기는 존재하지 않는다 :상실, 사랑 그리고 숨어 있는 삶의 질서에 관한 이야기 ', '꼭 읽어보고 소장하고 싶은 책입니다. 파시는분 있을까요?', 'https://image.aladin.co.kr/product/23651/80/cover/8936447696_1.jpg', '월성1동', 23000, 'BUY'),
    (19, 96, 115, '위풍당당 여우 꼬리', '아이들이 좋아하는 책입니다', 'https://image.aladin.co.kr/product/32509/16/cover/8936448498_1.jpg', '상인동', 15000, 'SELL'),
    (20, 93, 116, '호랑이 빵집', '가볍게 읽기 좋은 책 입니다.', 'https://image.aladin.co.kr/product/30782/50/cover/k052832579_1.jpg', '복현동', 20000, 'SELL'),
    (21, 92, 117, '미드나잇 라이브러리', '싼가격에 내놓아요~~', 'https://image.aladin.co.kr/product/26987/37/cover/k962730610_1.jpg', '범어동', 18000, 'SELL'),
    (22, 81, 118, '수상한 놀이공원 천옥원', '소장할만한 책입니다. 팔아요', 'https://image.aladin.co.kr/product/32057/52/cover/k062834799_1.jpg', '황금동', 22000, 'SELL'),
    (23, 80, 119, '긴긴밤', '소장하고 싶은 책입니다. 파는 사람 있을까요?', 'https://image.aladin.co.kr/product/26302/71/cover/8954677150_1.jpg', '대명동', 17000, 'BUY'),
    (25, 63, 113, '나미야 잡화점의 기적 :히가시노 게이고 장편소설 ', '이 책 유명한데 혹시 파는 사람 있나요?', 'http://image.aladin.co.kr/product/15848/6/cover/k622533431_1.jpg', '수성3동', 25000, 'BUY'),
    (26, 70, 114, '죽이고 싶은 아이 :이꽃님 장편소설 ', '구매원합니다.', 'https://image.aladin.co.kr/product/27211/83/cover/k422732197_1.jpg', '월성1동', 23000, 'BUY');
