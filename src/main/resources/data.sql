ALTER TABLE board DISABLE TRIGGER ALL;
ALTER TABLE member DISABLE TRIGGER ALL;
DELETE FROM board;
DELETE FROM member;
ALTER TABLE board ENABLE TRIGGER ALL;
ALTER TABLE member ENABLE TRIGGER ALL;

ALTER TABLE board ALTER COLUMN board_img TYPE TEXT;
INSERT INTO member (member_id, member_name, email, outh_id, refresh_token)
VALUES
    (11, 'John Doe', 'johndoe@example.com', 1001, 'token123'),
    (12, 'Jane Smith', 'janesmith@example.com', 1002, 'token456'),
    (13, 'Alice Johnson', 'alicejohnson@example.com', 1003, 'token789'),
    (14, 'Bob Brown', 'bobbrown@example.com', 1004, 'token012'),
    (15, 'Charlie Davis', 'charliedavis@example.com', 1005, 'token345'),
    (16, 'Diana Green', 'dianagreen@example.com', 1006, 'token678'),
    (17, 'Edward Hall', 'edwardhall@example.com', 1007, 'token901'),
    (18, 'Fiona White', 'fionawhite@example.com', 1008, 'token234'),
    (19, 'George King', 'georgeking@example.com', 1009, 'token567'),
    (20, 'Hannah Lee', 'hannahlee@example.com', 1010, 'token890');


INSERT INTO board (board_id, book_id, member_id, board_title, board_content, board_img, address, price, category)
VALUES
    (11, 1, 11, 'Great Book', 'good', 'https://image.aladin.co.kr/product/26942/84/cover/k582730818_1.jpg', '상인동', 15000, 'SELL'),
    (12, 2, 11, 'Amazing Read', 'good', 'https://image.aladin.co.kr/product/29858/98/cover/k432838027_1.jpg', '복현동', 20000, 'SELL'),
    (13, 3, 12, 'Incredible Story', 'good.', 'https://image.aladin.co.kr/product/30048/51/cover/8936438832_1.jpg', '범어동', 18000, 'SELL'),
    (14, 4, 13, 'Interesting Insights', 'good', 'https://image.aladin.co.kr/product/24307/73/cover/k822630592_2.jpg', '황금동', 22000, 'SELL'),
    (15, 5, 12, 'Fascinating Narrative', 'good', 'https://image.aladin.co.kr/product/24512/70/cover/k392630952_1.jpg', '대명동', 17000, 'BUY'),
    (16, 6, 14, 'Highly Educational', 'good', 'https://image.aladin.co.kr/product/31559/97/cover/k682832859_1.jpg', '상인2동', 21000, 'BUY'),
    (17, 7, 13, 'A Masterpiece', 'good.', 'https://image.aladin.co.kr/product/31222/5/cover/k562831250_2.jpg', '수성3동', 25000, 'BUY'),
    (18, 8, 14, 'Thought-Provoking', 'good', 'https://image.aladin.co.kr/product/23651/80/cover/8936447696_1.jpg', '월성1동', 23000, 'BUY');
