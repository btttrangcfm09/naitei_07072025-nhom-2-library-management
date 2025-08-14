use library_db;

-- Xóa dữ liệu cũ (Tùy chọn, cẩn thận khi dùng trên production)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE receipt_fines;
TRUNCATE TABLE borrowing_details;
TRUNCATE TABLE borrowing_receipts;
TRUNCATE TABLE comments;
TRUNCATE TABLE ratings;
TRUNCATE TABLE follows;
TRUNCATE TABLE book_instances;
TRUNCATE TABLE editions;
TRUNCATE TABLE author_book;
TRUNCATE TABLE book_genre;
TRUNCATE TABLE books;
TRUNCATE TABLE users;
TRUNCATE TABLE authors;
TRUNCATE TABLE publishers;
TRUNCATE TABLE genres;
TRUNCATE TABLE fines;
SET FOREIGN_KEY_CHECKS = 1;

-- =================================================================
-- BẢNG 1: genres (Thể loại)
-- =================================================================
INSERT INTO `genres` (`id`, `name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Tiểu thuyết', 'Các tác phẩm hư cấu, sáng tạo dựa trên trí tưởng tượng.', NOW(), NOW()),
(2, 'Trinh thám', 'Câu chuyện xoay quanh việc phá án, tìm ra thủ phạm hoặc giải mã bí ẩn.', NOW(), NOW()),
(3, 'Khoa học viễn tưởng', 'Khai thác các yếu tố công nghệ, khoa học tương lai, du hành vũ trụ...', NOW(), NOW()),
(4, 'Giả tưởng (Fantasy)', 'Thế giới huyền ảo, phép thuật, sinh vật thần thoại.', NOW(), NOW()),
(5, 'Kinh dị', 'Gây cảm giác sợ hãi, rùng rợn, thường liên quan đến ma quái.', NOW(), NOW()),
(6, 'Tâm lý – xã hội', 'Khai thác chiều sâu nội tâm nhân vật và các vấn đề xã hội.', NOW(), NOW()),
(7, 'Lãng mạn', 'Chuyện tình cảm, cảm xúc và hành trình yêu đương của nhân vật.', NOW(), NOW()),
(8, 'Tự truyện – hồi ký', 'Ghi chép về cuộc đời và trải nghiệm cá nhân.', NOW(), NOW()),
(9, 'Phát triển bản thân', 'Chia sẻ kinh nghiệm, phương pháp cải thiện cuộc sống, công việc.', NOW(), NOW()),
(10, 'Sách thiếu nhi', 'Nội dung đơn giản, minh họa sinh động cho trẻ em.', NOW(), NOW());

-- =================================================================
-- BẢNG 2: authors (Tác giả)
-- =================================================================
INSERT INTO `authors` (`id`, `name`, `dob`, `dod`, `biography`, `created_at`, `updated_at`) VALUES
(1,  'Nguyễn Nhật Ánh',      '1955-05-07', NULL, 'Nhà văn Việt Nam, nổi tiếng viết sách cho thiếu nhi và thanh thiếu niên; tác phẩm tiêu biểu: Kính vạn hoa, Cho tôi xin một vé đi tuổi thơ, Mắt biếc.', NOW(), NOW()),
(2,  'Tô Hoài',              '1920-09-27', '2014-07-06', 'Nhà văn, nhà báo Việt Nam (tên thật Nguyễn Sen); nổi tiếng với văn học thiếu nhi và tác phẩm Dế Mèn phiêu lưu ký.', NOW(), NOW()),
(3,  'Vũ Trọng Phụng',       '1912-10-20', '1939-10-13', 'Nhà văn hiện thực Việt Nam đầu thế kỷ 20, nổi bật với các tác phẩm châm biếm xã hội như Số đỏ.', NOW(), NOW()),
(4,  'Ngô Tất Tố',           '1894-01-01', '1954-04-20', 'Nhà văn, nhà báo Việt Nam; tác phẩm tiêu biểu: Tắt đèn; nổi bật ở dòng văn học hiện thực phản ánh số phận nông dân.', NOW(), NOW()),
(5,  'J.K. Rowling',         '1965-07-31', NULL, 'Nhà văn người Anh (Joanne Rowling), tác giả bộ tiểu thuyết Harry Potter.', NOW(), NOW()),
(6,  'Haruki Murakami',      '1949-01-12', NULL, 'Tiểu thuyết gia và nhà văn Nhật Bản đương đại, nổi tiếng với phong cách hiện thực huyền ảo; tác phẩm tiêu biểu: Rừng Na Uy.', NOW(), NOW()),
(7,  'Paulo Coelho',         '1947-08-24', NULL, 'Nhà văn người Brazil, nổi tiếng toàn cầu với Nhà Giả Kim (The Alchemist) — tiểu thuyết mang yếu tố triết lý và hành trình nội tâm.', NOW(), NOW()),
(8,  'Dale Carnegie',        '1888-11-24', '1955-11-01', 'Tác giả và diễn giả người Mỹ, nổi tiếng với sách kỹ năng giao tiếp và phát triển bản thân, tiêu biểu: How to Win Friends and Influence People.', NOW(), NOW()),
(9,  'George Orwell',        '1903-06-25', '1950-01-21', 'Nhà văn, nhà báo người Anh (tên thật Eric Arthur Blair), nổi tiếng với tác phẩm phản địa đàng 1984 và ngụ ngôn Animal Farm.', NOW(), NOW()),
(10, 'Agatha Christie',      '1890-09-15', '1976-01-12', 'Tiểu thuyết gia trinh thám người Anh, tác giả nhiều vụ án trinh thám nổi tiếng như Murder on the Orient Express.', NOW(), NOW()),
(11, 'Trần Đăng Khoa',       '1958-04-26', NULL, 'Nhà thơ và nhà báo Việt Nam, nổi tiếng với nhiều bài thơ cho thiếu nhi và phong cách giàu cảm xúc.', NOW(), NOW()),
(12, 'Nam Cao',              '1917-10-29', '1951-11-30', 'Nhà văn hiện thực Việt Nam, nổi bật với các truyện ngắn và tiểu thuyết phản ánh bi kịch xã hội như Chí Phèo, Lão Hạc.', NOW(), NOW()),
(13, 'Nguyễn Du',            '1766-01-03', '1820-09-16', 'Đại thi hào Việt Nam, tác giả Truyện Kiều — kiệt tác văn học tiêu biểu của văn học cổ điển Việt Nam.', NOW(), NOW()),
(14, 'Stephen King',         '1947-09-21', NULL, 'Nhà văn Mỹ nổi tiếng thể loại kinh dị và giả tưởng, tác phẩm tiêu biểu: The Shining, It.', NOW(), NOW()),
(15, 'Yuval Noah Harari',    '1976-02-24', NULL, 'Sử gia, học giả người Israel, tác giả Sapiens: Lược sử loài người — sách khảo cứu phổ biến về lịch sử nhân loại.', NOW(), NOW()),
(16, 'Tố Hữu',               '1920-10-04', '2002-12-09', 'Nhà thơ lớn của Việt Nam, tác phẩm ca ngợi kháng chiến và cách mạng; vừa là chính trị gia, có ảnh hưởng văn học sâu rộng.', NOW(), NOW()),
(17, 'Nguyễn Tuân',          '1910-07-10', '1987-07-28', 'Nhà văn Việt Nam nổi tiếng với thể tùy bút, ký và quan niệm sáng tác về cái đẹp; tác phẩm tiêu biểu: Người lái đò sông Đà (tùy bút).', NOW(), NOW()),
(18, 'Nguyên Hồng',          '1918-11-05', '1982-05-02', 'Nhà văn Việt Nam, nổi tiếng với những tác phẩm phản ánh tầng lớp lao động và tuổi thơ cơ cực; tác phẩm: Những ngày thơ ấu.', NOW(), NOW()),
(19, 'Jane Austen',          '1775-12-16', '1817-07-18', 'Nhà văn Anh, tác giả các tiểu thuyết lãng mạn kinh điển như Pride and Prejudice (Kiêu hãnh và định kiến).', NOW(), NOW()),
(20, 'J.R.R. Tolkien',       '1892-01-03', '1973-09-02', 'Nhà văn và học giả Anh, cha đẻ của văn học giả tưởng hiện đại; tác phẩm tiêu biểu: The Hobbit, The Lord of the Rings.', NOW(), NOW()),
(21, 'Margaret Mitchell',    '1900-11-08', '1949-08-16', 'Nhà văn Mỹ, tác giả Gone with the Wind (Cuốn theo chiều gió), đoạt giải Pulitzer.', NOW(), NOW()),
(22, 'Jeffrey Archer',       '1940-04-15', NULL, 'Nhà văn người Anh, tác giả nhiều tiểu thuyết giải trí và chính trị gia trước đây; tác phẩm tiêu biểu: Kane and Abel.', NOW(), NOW()),
(23, 'Hector Malot',         '1830-05-20', '1907-07-18', 'Nhà văn Pháp thế kỷ 19, nổi tiếng với tiểu thuyết thiếu nhi và thiếu niên "Không gia đình" (Sans Famille).', NOW(), NOW()),
(24, 'Antoine de Saint-Exupéry','1900-06-29','1944-07-31','Nhà văn, phi công và nhà thơ người Pháp, tác giả Hoàng tử bé (Le Petit Prince).', NOW(), NOW());


-- =================================================================
-- BẢNG 3: publishers (Nhà xuất bản)
-- =================================================================
INSERT INTO `publishers` (`id`, `name`, `address`, `established_year`, `email`, `phone`, `website`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Nhà xuất bản Trẻ', '161B Lý Chính Thắng, Quận 3, TP.HCM', 1981, 'hopthu@nxbtre.com.vn', '(028) 39316289', 'https://www.nxbtre.com.vn', 'Một trong những NXB lớn nhất Việt Nam.', NOW(), NOW()),
(2, 'Nhà xuất bản Kim Đồng', '55 Quang Trung, Hai Bà Trưng, Hà Nội', 1957, 'info@nxbkimdong.com.vn', '(024) 39428633', 'https://nxbkimdong.com.vn', 'NXB chuyên về sách cho thiếu nhi.', NOW(), NOW()),
(3, 'Nhã Nam', '59 Đỗ Quang, Cầu Giấy, Hà Nội', 2005, 'info@nhanam.vn', '(024) 35146876', 'https://nhanam.vn', 'Công ty văn hóa và truyền thông chuyên về sách văn học.', NOW(), NOW()),
(4, 'Alphabooks', '176 Thái Hà, Đống Đa, Hà Nội', 2005, 'info@alphabooks.vn', '0932 329 959', 'https://alphabooks.vn', 'Chuyên về sách quản trị kinh doanh và phát triển bản thân.', NOW(), NOW()),
(5, 'Penguin Random House', '1745 Broadway, New York, NY, USA', 2013, 'contact@penguinrandomhouse.com', '(212) 782-9000', 'https://www.penguinrandomhouse.com', 'Nhà xuất bản lớn nhất thế giới.', NOW(), NOW()),
(6, 'HarperCollins', '195 Broadway, New York, NY, USA', 1989, 'contact@harpercollins.com', '(212) 207-7000', 'https://www.harpercollins.com', 'Một trong những công ty xuất bản sách lớn nhất thế giới.', NOW(), NOW()),
(7, 'Nhà xuất bản Hội Nhà văn', '65 Nguyễn Du, Hai Bà Trưng, Hà Nội', 1957, 'nxbhoinhavan@gmail.com', '(024) 38222135', 'http://nxbhoinhavan.vn', 'Xuất bản các tác phẩm của các nhà văn trong và ngoài nước.', NOW(), NOW()),
(8, 'Nhà xuất bản Phụ nữ Việt Nam', '39 Hàng Chuối, Hai Bà Trưng, Hà Nội', 1947, 'truyenthong.nxbpn@gmail.com', '(024) 39710717', 'https://nxbphunu.com.vn', 'Chuyên xuất bản sách cho phụ nữ và trẻ em.', NOW(), NOW()),
(9, 'Bloomsbury Publishing', '50 Bedford Square, London, UK', 1986, 'contact@bloomsbury.com', '+44 20 7631 5600', 'https://www.bloomsbury.com', 'Một nhà xuất bản lớn của Anh.', NOW(), NOW()),
(10, 'Simon & Schuster', '1230 Avenue of the Americas, New York, NY', 1924, 'contact@simonandschuster.com', '(212) 698-7000', 'https://www.simonandschuster.com', 'Một nhà xuất bản lớn của Mỹ.', NOW(), NOW());


-- =================================================================
-- BẢNG 4: users (Người dùng/Độc giả) - chèn dữ liệu có avatar_url và bcrypt(password='123') mã hoá bằng bcrypt (cost=10)
-- role 0: client, 1: admin
-- status 0: inactive, 1: active
-- =================================================================
INSERT INTO `users` (`id`, `name`, `email`, `password`, `dob`, `avatar_url`, `phone`, `address`, `role`, `status`, `created_at`, `updated_at`) VALUES
(1, 'Library Admin', 'admin@gmail.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1995-08-15', 'https://png.pngtree.com/png-vector/20220628/ourmid/pngtree-user-profile-avatar-vector-admin-png-image_5289691.png', '0912345678', '123 Đường ABC, Quận 1, TP.HCM', 1, 1, NOW(), NOW()),
(2, 'Trần Thị Bình', 'binh.tran@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1998-02-20', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0987654321', '456 Đường XYZ, Quận Ba Đình, Hà Nội', 0, 0, NOW(), NOW()),
(3, 'Lê Minh Cường', 'cuong.le@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1990-11-30', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0905123456', '789 Đường LMN, Quận Hải Châu, Đà Nẵng', 0, 1, NOW(), NOW()),
(4, 'Phạm Thị Dung', 'dung.pham@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '2001-07-25', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0978563412', '101 Đường PQR, Quận 3, TP.HCM', 0, 1, NOW(), NOW()),
(5, 'Wang ChuQin', 'datou@gmail.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '2000-05-11', 'https://i.pinimg.com/1200x/ab/5e/0a/ab5e0ad1bb7f370a16c4adad74e351cb.jpg', '0934789123', 'Beijing, China', 0, 1, NOW(), NOW()),
(6, 'Sun YingSha', 'shasha@gmail.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '2000-11-04', 'https://i.pinimg.com/736x/e3/ed/12/e3ed129c503e6d78fbf3ec149144f934.jpg', '0909090909', 'Hebei, China', 0, 1, NOW(), NOW()),
(7, 'John Doe', 'john.doe@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1992-06-18', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0918273645', '321 Baker Street, London', 0, 1, NOW(), NOW()),
(8, 'Jane Smith', 'jane.smith@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1999-09-09', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0988776655', '1600 Pennsylvania Avenue, Washington DC', 0, 0, NOW(), NOW()),
(9, 'Vũ Anh Tuấn', 'tuan.vu@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '2003-04-10', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0911223344', '88 Lê Lợi, TP. Huế', 0, 1, NOW(), NOW()),
(10, 'Đỗ Mỹ Linh', 'linh.do@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1996-12-01', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0965874123', '55 Nguyễn Trãi, Quận Thanh Xuân, Hà Nội', 0, 1, NOW(), NOW()),
(11, 'Kim Jisoo', 'jisoo.kim@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1995-01-03', 'https://upload.wikimedia.org/wikipedia/commons/f/fd/Kim_Jisoo_on_July_3%2C_2023_07.jpg', '0987123456', 'Seoul, South Korea', 0, 1, NOW(), NOW()),
(12, 'Lý Thị Thảo', 'thao.ly@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '2000-08-08', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0912987654', '77 Pasteur, Quận 1, TP.HCM', 0, 1, NOW(), NOW()),
(13, 'Trịnh Văn Sơn', 'son.trinh@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1979-02-28', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0901234876', '12 Phạm Ngọc Thạch, TP.HCM', 0, 1, NOW(), NOW()),
(14, 'Kim Jennie', 'jennie.kim@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1996-01-16', 'https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da84e596564bfde3618bd370c861', '0988112233', 'Seoul, Korea', 0, 1, NOW(), NOW()),
(15, 'Bùi Tiến Dũng', 'dung.bui@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1997-02-28', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0977665544', '45 Thanh Hóa, Tỉnh Thanh Hóa', 0, 1, NOW(), NOW()),
(16, 'Nguyễn Quang Hải', 'hai.nguyen@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1997-04-12', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0915678345', '99 Đông Anh, Hà Nội', 0, 1, NOW(), NOW()),
(17, 'Đoàn Văn Hậu', 'hau.doan@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1999-04-19', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0987654123', '11 Thái Bình, Tỉnh Thái Bình', 0, 1, NOW(), NOW()),
(18, 'Lalisa Manobal', 'lisa@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1997-03-27', 'https://lavo.com.vn/wp-content/uploads/2020/12/lisa-2-1591157461166-min.png', '0905432789', 'Buriram, Thailand', 0, 1, NOW(), NOW()),
(19, 'Vũ Văn Thanh', 'thanh.vu@example.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1996-04-14', 'https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=', '0912345987', '33 Hải Dương, Tỉnh Hải Dương', 0, 1, NOW(), NOW()),
(20, 'Roseanne Park', 'rose.park@gmail.com', '$2b$10$LDQtkk.l8.4mNrmGu.pvjuNJIqTAPsIKrxdKcSNxrAvUWh8/2j2v6', '1997-02-11', 'https://i.mydramalist.com/66L5p_5c.jpg', '0987123654', 'Auckland / Seoul', 0, 1, NOW(), NOW());


-- =================================================================
-- BẢNG 5: fines (Các loại phí phạt)
-- =================================================================
INSERT INTO `fines` (`id`, `type`, `fee`) VALUES
(1, 0, 50000),
(2, 1, 100000),
(3, 2, 500000);

-- =================================================================
-- BẢNG 6: books (Sách)
-- =================================================================
INSERT INTO `books` (`id`, `title`, `description`) VALUES
(1,  'Dế Mèn phiêu lưu ký', 'Truyện dài của Tô Hoài, kể về cuộc phiêu lưu và bài học trưởng thành của chú Dế Mèn.'),
(2,  'Cho tôi xin một vé đi tuổi thơ', 'Tác phẩm của Nguyễn Nhật Ánh, kể về những trò nghịch ngợm và ký ức tuổi thơ trong sáng của bốn người bạn.'),
(3,  'Số đỏ', 'Tiểu thuyết trào phúng của Vũ Trọng Phụng, châm biếm lối sống trưởng giả và xã hội thành thị Việt Nam thời Pháp thuộc.'),
(4,  'Tắt đèn', 'Tác phẩm hiện thực của Ngô Tất Tố, phản ánh số phận bi kịch của người nông dân dưới ách sưu thuế.'),
(5,  'Harry Potter and the Sorcerer''s Stone', 'Phần đầu tiên của loạt truyện Harry Potter, kể về hành trình của cậu bé phù thủy Harry tại Hogwarts.'),
(6,  'Rừng Na Uy', 'Tiểu thuyết của Haruki Murakami, kể về tuổi trẻ, tình yêu và sự mất mát.'),
(7,  'Nhà Giả Kim', 'Tiểu thuyết của Paulo Coelho, kể về hành trình tìm kiếm kho báu và ý nghĩa cuộc sống của cậu bé Santiago.'),
(8,  'Đắc Nhân Tâm', 'Sách kỹ năng sống của Dale Carnegie, hướng dẫn nghệ thuật giao tiếp và tạo ảnh hưởng.'),
(9,  '1984', 'Tiểu thuyết phản địa đàng của George Orwell, mô tả một xã hội toàn trị kiểm soát mọi suy nghĩ.'),
(10, 'Án mạng trên chuyến tàu tốc hành Phương Đông', 'Tiểu thuyết trinh thám nổi tiếng của Agatha Christie với vụ án mạng ly kỳ trên chuyến tàu Orient Express.'),
(11, 'Góc sân và khoảng trời', 'Tập thơ thiếu nhi của Trần Đăng Khoa, gắn liền với tuổi thơ nhiều thế hệ.'),
(12, 'Lão Hạc', 'Truyện ngắn của Nam Cao, kể về cuộc đời đầy bi kịch của một lão nông nghèo.'),
(13, 'Chí Phèo', 'Tác phẩm hiện thực phê phán của Nam Cao, khắc họa bi kịch tha hóa của người nông dân.'),
(14, 'Truyện Kiều', 'Kiệt tác thơ Nôm của Nguyễn Du, kể về số phận truân chuyên của Thúy Kiều.'),
(15, 'Sapiens: Lược sử loài người', 'Sách của Yuval Noah Harari, trình bày lịch sử và tiến hóa của loài người từ thời nguyên thủy đến hiện đại.'),
(16, 'The Shining', 'Tiểu thuyết kinh dị của Stephen King, xoay quanh một khách sạn bí ẩn và những hiện tượng siêu nhiên.'),
(17, 'Tôi thấy hoa vàng trên cỏ xanh', 'Truyện dài của Nguyễn Nhật Ánh, kể về tuổi thơ ở làng quê với những rung động đầu đời.'),
(18, 'Kính vạn hoa', 'Bộ truyện dài nhiều tập của Nguyễn Nhật Ánh về nhóm bạn và những câu chuyện hài hước, cảm động.'),
(19, 'How to Win Friends and Influence People', 'Sách của Dale Carnegie về nghệ thuật giao tiếp và tạo ảnh hưởng tích cực đến người khác.'),
(20, 'The Alchemist', 'Bản tiếng Anh của tiểu thuyết Nhà Giả Kim của Paulo Coelho.'),
(21, 'Animal Farm', 'Tiểu thuyết ngụ ngôn chính trị của George Orwell, ẩn dụ về chế độ toàn trị.'),
(22, 'Tuyển truyện ngắn hiện thực Việt Nam', 'Tuyển tập chọn lọc truyện ngắn hiện thực, gồm các tác giả tiêu biểu thời đầu thế kỷ XX.'),
(23, 'Tuyển thơ thiếu nhi', 'Tuyển tập thơ thiếu nhi chọn lọc từ nhiều nhà thơ Việt Nam.'),
(24, 'Tuyển truyện trào phúng Việt Nam', 'Tuyển tập truyện trào phúng, phản ánh xã hội thời cận đại.'),
(25, 'Tuyển tiểu thuyết kinh dị', 'Tuyển tập các truyện/đoạn trích kinh dị tiêu biểu.'),
(26, 'Tuyển sách kỹ năng giao tiếp', 'Tuyển chọn sách kỹ năng và self-help từ các tác giả cổ điển.'),
(27, 'Tuyển sách lịch sử suy ngẫm', 'Tuyển tập các bài viết/đoạn trích khai thác lịch sử và nhân văn.'),
(28, 'Tuyển truyện cho tuổi mới lớn', 'Tuyển chọn sách thiếu niên, thanh thiếu niên nổi bật.'),
(29, 'Để yên cho bác sĩ Hiền', 'Truyện dài của Nguyễn Nhật Ánh, kể về những câu chuyện hài hước và cảm động trong một thị trấn nhỏ.'),
(30, 'Đời thừa', 'Truyện ngắn của Nam Cao, phê phán xã hội và số phận bi kịch của người trí thức nghèo.'),
(31, 'Việt Bắc', 'Tập thơ của Tố Hữu, ca ngợi kháng chiến và tình nghĩa quân dân.'),
(32, 'Vợ chồng A Phủ', 'Truyện ngắn của Tô Hoài, kể về cuộc đời khổ cực và hành trình vùng lên của đôi vợ chồng người Mông.'),
(33, 'Người lái đò sông Đà', 'Tùy bút của Nguyễn Tuân, miêu tả vẻ đẹp hung vĩ và thơ mộng của sông Đà.'),
(34, 'It', 'Tiểu thuyết kinh dị của Stephen King về một thực thể ma quái ẩn mình dưới hình dạng chú hề.'),
(35, 'Tuyển tiểu thuyết xã hội', 'Tuyển chọn tiểu thuyết phản ánh xã hội và con người.'),
(36, 'Tuyển truyện tình tuổi học trò', 'Tuyển chọn các truyện về tuổi học trò và tình cảm đầu đời.'),
(37, 'Kiêu hãnh và định kiến', 'Bản dịch/ấn bản dịch tiếng Việt, tạm thời quản lý trong bộ sưu tập chung.'),
(38, 'Mắt biếc', 'Truyện dài của Nguyễn Nhật Ánh, kể về mối tình đơn phương đầy tiếc nuối.'),
(39, 'Những ngày thơ ấu', 'Hồi ký của Nguyên Hồng về tuổi thơ cơ cực nhưng giàu tình thương.'),
(40, 'Vận may hóa điên', 'Bản dịch tiếng Anh của tiểu thuyết trào phúng "Số Đỏ" của Vũ Trọng Phụng.');


-- =================================================================
-- BẢNG 7: book_genre (Sách - Thể loại)
-- =================================================================
INSERT INTO `book_genre` (`id`, `book_id`, `genre_id`) VALUES
(1,  1, 10), -- Dế Mèn phiêu lưu ký -> Sách thiếu nhi
(2,  2,  1), -- Cho tôi xin một vé đi tuổi thơ -> Tiểu thuyết
(3,  2, 10), -- + Sách thiếu nhi / tuổi thơ
(4,  3,  1), -- Số đỏ -> Tiểu thuyết
(5,  3,  6), -- + Tâm lý – xã hội / châm biếm xã hội
(6,  4,  1), -- Tắt đèn -> Tiểu thuyết / hiện thực
(7,  4,  6), -- + Tâm lý – xã hội
(8,  5,  4), -- Harry Potter -> Fantasy
(9,  5, 10), -- + Sách thiếu nhi / thiếu niên
(10, 6,  1), -- Rừng Na Uy -> Tiểu thuyết
(11, 6,  6), -- + Tâm lý – xã hội / chiều sâu nội tâm
(12, 6,  7), -- + Lãng mạn (yếu tố tình yêu)
(13, 7,  1), -- Nhà Giả Kim -> Tiểu thuyết (hành trình)
(14, 7,  6), -- + Tâm lý – xã hội / triết lý
(15, 8,  9), -- Đắc Nhân Tâm -> Phát triển bản thân
(16, 9,  3), -- 1984 -> Khoa học viễn tưởng / dystopia
(17, 9,  6), -- + Tâm lý – xã hội (chính trị, ý thức)
(18,10,  2), -- Án mạng... -> Trinh thám
(19,11, 10), -- Góc sân và khoảng trời -> Sách thiếu nhi (thơ thiếu nhi)
(20,12,  1), -- Lão Hạc -> Tiểu thuyết / truyện ngắn hiện thực
(21,12,  6), -- + Tâm lý – xã hội
(22,13,  1), -- Chí Phèo -> Tiểu thuyết / hiện thực
(23,13,  6), -- + Tâm lý – xã hội
(24,14,  1), -- Truyện Kiều -> Tiểu thuyết (thơ Nôm được xếp tạm vào tiểu thuyết/kiệt tác)
(25,14,  7), -- + Lãng mạn (yếu tố tình yêu, bi kịch)
(26,15,  9), -- Sapiens -> Phát triển bản thân / tri thức phổ thông (gán vào 9)
(27,16,  5), -- The Shining -> Kinh dị
(28,17,  1), -- Tôi thấy hoa vàng trên cỏ xanh -> Tiểu thuyết (tuổi thơ)
(29,17, 10), -- + Sách thiếu nhi / thiếu niên
(30,18, 10), -- Kính vạn hoa -> Sách thiếu nhi (bộ truyện thiếu nhi)
(31,18,  1), -- + Tiểu thuyết / truyện thiếu niên
(32,19,  9), -- How to Win Friends -> Phát triển bản thân
(33,20,  1), -- The Alchemist -> Tiểu thuyết (hành trình)
(34,20,  6), -- + Tâm lý – xã hội / triết lý
(35,21,  3), -- Animal Farm -> Khoa học viễn tưởng / ngụ ngôn chính trị (gán 3)
(36,21,  6), -- + Tâm lý – xã hội (ẩn dụ chính trị)
(37,22,  1), -- Tuyển truyện ngắn hiện thực -> Tiểu thuyết / truyện
(38,22,  6), -- + Tâm lý – xã hội
(39,23, 10), -- Tuyển thơ thiếu nhi -> Sách thiếu nhi
(40,24,  1), -- Tuyển truyện trào phúng -> Tiểu thuyết / truyện
(41,24,  6), -- + Tâm lý – xã hội / châm biếm
(42,25,  5), -- Tuyển tiểu thuyết kinh dị -> Kinh dị
(43,25,  1), -- + Tiểu thuyết (tuyển)
(44,26,  9), -- Tuyển sách kỹ năng giao tiếp -> Phát triển bản thân
(45,27,  9), -- Tuyển sách lịch sử suy ngẫm -> Phát triển bản thân / tri thức (gán 9)
(46,27,  6), -- + Tâm lý – xã hội / phân tích lịch sử
(47,28, 10), -- Tuyển truyện cho tuổi mới lớn -> Sách thiếu nhi / thiếu niên
(48,28,  1), -- + Tiểu thuyết (thiếu niên)
(49,29,  1), -- Để yên cho bác sĩ Hiền -> Tiểu thuyết
(50,29, 10), -- + Sách thiếu nhi / nhẹ nhàng (yếu tố tuổi thơ)
(51,30,  1), -- Đời thừa -> Tiểu thuyết / truyện ngắn
(52,30,  6), -- + Tâm lý – xã hội
(53,31,  1), -- Việt Bắc -> Tiểu thuyết / thơ (gán 1 tạm)
(54,31,  6), -- + Tâm lý – xã hội / ca ngợi kháng chiến
(55,32,  1), -- Vợ chồng A Phủ -> Tiểu thuyết / truyện ngắn
(56,32,  6), -- + Tâm lý – xã hội
(57,33,  1), -- Người lái đò sông Đà -> Tùy bút / Tiểu thuyết (gán 1)
(58,33,  6), -- + Tâm lý – xã hội / miêu tả văn hóa
(59,34,  5), -- It -> Kinh dị
(60,35,  1), -- Tuyển tiểu thuyết xã hội -> Tiểu thuyết
(61,35,  6), -- + Tâm lý – xã hội
(62,36,  7), -- Tuyển truyện tình tuổi học trò -> Lãng mạn
(63,36,  1), -- + Tiểu thuyết (tuổi học trò)
(64,36, 10), -- + Sách thiếu nhi / thiếu niên (nếu phù hợp)
(65,37,  1), -- Kiêu hãnh và định kiến (bản VN) -> Tiểu thuyết
(66,37,  7), -- + Lãng mạn
(67,38,  1), -- Mắt biếc -> Tiểu thuyết
(68,38,  7), -- + Lãng mạn
(69,39,  8), -- Những ngày thơ ấu -> Tự truyện – hồi ký
(70,39, 10), -- + Sách thiếu nhi / kỷ niệm tuổi thơ
(71,40,  1), -- Vận may hóa điên (Số Đỏ bản Anh) -> Tiểu thuyết
(72,40,  6); -- + Tâm lý – xã hội (châm biếm)


-- =================================================================
-- BẢNG 8: author_book (Sách - Tác giả)
-- =================================================================
INSERT INTO `author_book` (`id`, `book_id`, `author_id`) VALUES
(1,  1,  2),   -- Dế Mèn phiêu lưu ký  - Tô Hoài
(2,  2,  1),   -- Cho tôi xin một vé đi tuổi thơ - Nguyễn Nhật Ánh
(3,  3,  3),   -- Số đỏ - Vũ Trọng Phụng
(4,  4,  4),   -- Tắt đèn - Ngô Tất Tố
(5,  5,  5),   -- Harry Potter and the Sorcerer's Stone - J.K. Rowling
(6,  6,  6),   -- Rừng Na Uy - Haruki Murakami
(7,  7,  7),   -- Nhà Giả Kim - Paulo Coelho
(8,  8,  8),   -- Đắc Nhân Tâm - Dale Carnegie
(9,  9,  9),   -- 1984 - George Orwell
(10, 10, 10),  -- Án mạng trên chuyến tàu tốc hành Phương Đông - Agatha Christie
(11, 11, 11),  -- Góc sân và khoảng trời - Trần Đăng Khoa
(12, 12, 12),  -- Lão Hạc - Nam Cao
(13, 13, 12),  -- Chí Phèo - Nam Cao
(14, 14, 13),  -- Truyện Kiều - Nguyễn Du
(15, 15, 15),  -- Sapiens: Lược sử loài người - Yuval Noah Harari
(16, 16, 14),  -- The Shining - Stephen King
(17, 17, 1),   -- Tôi thấy hoa vàng trên cỏ xanh - Nguyễn Nhật Ánh
(18, 18, 1),   -- Kính vạn hoa - Nguyễn Nhật Ánh
(19, 19, 8),   -- How to Win Friends and Influence People - Dale Carnegie
(20, 20, 7),   -- The Alchemist - Paulo Coelho
(21, 21, 9),   -- Animal Farm - George Orwell
-- Tuyển tập / nhiều tác giả
(22, 22, 12),  -- Tuyển truyện ngắn hiện thực -> Nam Cao
(23, 22, 3),   -- -> Vũ Trọng Phụng
(24, 22, 4),   -- -> Ngô Tất Tố
(25, 22, 17),  -- -> Nguyễn Tuân (nếu tuyển có bài tùy bút / truyện)
(26, 23, 11),  -- Tuyển thơ thiếu nhi -> Trần Đăng Khoa
(27, 23, 1),   -- -> Nguyễn Nhật Ánh (đóng góp thiếu nhi/thiếu niên)
(28, 24, 3),   -- Tuyển truyện trào phúng -> Vũ Trọng Phụng
(29, 24, 12),  -- -> Nam Cao
(30, 25, 14),  -- Tuyển tiểu thuyết kinh dị -> Stephen King
(31, 25, 6),   -- -> Haruki Murakami (ví dụ tuyển các truyện kỳ ảo/kinh dị)
(32, 26, 8),   -- Tuyển sách kỹ năng giao tiếp -> Dale Carnegie
(33, 26, 15),  -- -> Yuval Noah Harari (nếu có bài phân tích/đoạn trích liên quan)
(34, 27, 15),  -- Tuyển sách lịch sử suy ngẫm -> Yuval Noah Harari
(35, 28, 1),   -- Tuyển truyện cho tuổi mới lớn -> Nguyễn Nhật Ánh
(36, 28, 11),  -- -> Trần Đăng Khoa
(37, 29, 1),   -- Để yên cho bác sĩ Hiền - Nguyễn Nhật Ánh
(38, 30, 12),  -- Đời thừa - Nam Cao
(39, 31, 16),  -- Việt Bắc - Tố Hữu
(40, 32, 2),   -- Vợ chồng A Phủ - Tô Hoài
(41, 33, 17),  -- Người lái đò sông Đà - Nguyễn Tuân
(42, 34, 14),  -- It - Stephen King
(43, 35, 9),   -- Tuyển tiểu thuyết xã hội -> George Orwell (biên soạn / tiêu biểu)
(44, 35, 12),  -- -> Nam Cao
(45, 35, 3),   -- -> Vũ Trọng Phụng
(46, 36, 1),   -- Tuyển truyện tình tuổi học trò -> Nguyễn Nhật Ánh
(47, 37, 19),  -- Kiêu hãnh và định kiến (bản VN) -> Jane Austen (tác giả gốc)
(48, 38, 1),   -- Mắt biếc - Nguyễn Nhật Ánh
(49, 39, 18),  -- Những ngày thơ ấu - Nguyên Hồng
(50, 40, 3);   -- Vận may hóa điên (Số Đỏ bản Anh) - Vũ Trọng Phụng

-- =================================================================
-- BẢNG 9: editions (Ấn bản sách)
-- format: HARDCOVER=0, SOFTCOVER=1
-- =================================================================

INSERT INTO `editions` (`id`, `book_id`, `publisher_id`, `isbn`, `title`, `language`, `publication_date`, `page_number`, `cover_image_url`, `format`, `initial_quantity`, `available_quantity`) VALUES
(1, 1, 2, '9786042187334', 'Dế Mèn phiêu lưu ký (Tái bản 2020)', 'Tiếng Việt', '2020-05-19', 156, NULL, 1, 5, 5),
(2, 1, 2, '9786042079349', 'Dế Mèn phiêu lưu ký (Bìa cứng)', 'Tiếng Việt', '2018-10-25', 160, NULL, 0, 5, 5),
(3, 2, 1, '9786041182978', 'Cho tôi xin một vé đi tuổi thơ (Tái bản 2022)', 'Tiếng Việt', '2022-03-15', 212, NULL, 1, 5, 5),
(4, 3, 7, '9786049945229', 'Số đỏ (Ấn bản 2019)', 'Tiếng Việt', '2019-11-20', 250, NULL, 1, 5, 5),
(5, 4, 7, '9786049791420', 'Tắt đèn (Tái bản 2021)', 'Tiếng Việt', '2021-08-01', 180, NULL, 1, 5, 5),
(6, 5, 9, '9781408855652', 'Harry Potter and the Sorcerer''s Stone - First Edition', 'Tiếng Anh', '2014-09-01', 352, NULL, 1, 5, 5),
(7, 5, 1, '9786041182961', 'Harry Potter and the Sorcerer''s Stone - Illustrated Edition', 'Tiếng Anh', '2017-02-14', 440, NULL, 1, 5, 5),
(8, 6, 3, '9786045339239', 'Rừng Na Uy (Bản dịch của Trịnh Lữ)', 'Tiếng Việt', '2016-09-10', 528, NULL, 1, 5, 5),
(9, 7, 3, '9786045302349', 'Nhà Giả Kim (Tái bản 2023)', 'Tiếng Việt', '2023-01-10', 236, NULL, 1, 5, 5),
(10, 8, 4, '9786045888249', 'Đắc Nhân Tâm', 'Tiếng Việt', '2020-01-01', 320, NULL, 1, 5, 5),
(11, 9, 5, '9780451524935', '1984 (Signet Classics)', 'Tiếng Anh', '1961-01-01', 328, NULL, 1, 5, 5),
(12, 10, 8, '9786045645514', 'Án mạng trên chuyến tàu tốc hành Phương Đông', 'Tiếng Việt', '2017-06-15', 288, NULL, 1, 5, 5),
(13, 11, 2, '9786042171173', 'Góc sân và khoảng trời (Tái bản 2019)', 'Tiếng Việt', '2019-01-01', 108, NULL, 0, 5, 5),
(14, 12, 7, '9786049692994', 'Lão Hạc (Tuyển tập truyện ngắn Nam Cao)', 'Tiếng Việt', '2018-01-01', 200, NULL, 1, 5, 5),
(15, 13, 7, '9786049800053', 'Chí Phèo (Tái bản 2020)', 'Tiếng Việt', '2020-03-01', 120, NULL, 1, 5, 5),
(16, 14, 7, '9786049968419', 'Truyện Kiều (Bản kỷ niệm 200 năm)', 'Tiếng Việt', '2020-09-16', 350, NULL, 0, 5, 5),
(17, 15, 4, '9786045585810', 'Sapiens: Lược sử loài người', 'Tiếng Việt', '2018-05-01', 512, NULL, 1, 5, 5),
(18, 16, 10, '9780307743657', 'The Shining', 'Tiếng Anh', '2012-06-26', 688, NULL, 1, 5, 5),
(19, 17, 1, '9786041168922', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tiếng Việt', '2015-09-01', 380, NULL, 1, 5, 5),
(20, 18, 1, '9786041223442', 'Kính vạn hoa (Trọn bộ)', 'Tiếng Việt', '2021-01-01', 2500, NULL, 0, 5, 5),
(21, 19, 10, '9780671027032', 'How to Win Friends and Influence People', 'Tiếng Anh', '1998-10-01', 288, NULL, 1, 5, 5),
(22, 20, 6, '9780061122415', 'The Alchemist', 'Tiếng Anh', '2006-10-25', 208, NULL, 1, 5, 5),
(23, 21, 5, '9780451526342', 'Animal Farm (75th Anniversary Edition)', 'Tiếng Anh', '2021-08-17', 144, NULL, 1, 5, 5),
(24, 22, 7, '9786049590500', 'Tuyển truyện ngắn hiện thực Việt Nam', 'Tiếng Việt', '2017-01-01', 450, NULL, 1, 5, 5),
(25, 23, 2, '9786042080802', 'Tuyển thơ thiếu nhi Việt Nam', 'Tiếng Việt', '2018-06-01', 300, NULL, 1, 5, 5),
(26, 24, 7, '9786049945304', 'Tuyển truyện trào phúng Việt Nam', 'Tiếng Việt', '2019-01-01', 350, NULL, 1, 5, 5),
(27, 25, 8, '9786045674316', 'Tuyển tập tiểu thuyết kinh dị', 'Tiếng Việt', '2019-10-31', 420, NULL, 1, 5, 5),
(28, 26, 4, '9786045899993', 'Tuyển sách kỹ năng giao tiếp kinh điển', 'Tiếng Việt', '2021-01-01', 500, NULL, 0, 5, 5),
(29, 27, 4, '9786044585827', 'Tuyển sách lịch sử suy ngẫm', 'Tiếng Việt', '2020-01-01', 450, NULL, 1, 5, 5),
(30, 28, 1, '9786041211111', 'Tuyển truyện cho tuổi mới lớn', 'Tiếng Việt', '2020-06-01', 380, NULL, 1, 5, 5),
(31, 29, 1, '9786041178988', 'Để yên cho bác sĩ Hiền', 'Tiếng Việt', '2016-01-01', 250, NULL, 1, 5, 5),
(32, 30, 7, '9786049693007', 'Đời thừa', 'Tiếng Việt', '2018-03-01', 280, NULL, 1, 5, 5),
(33, 31, 7, '9786049580013', 'Việt Bắc (Tuyển tập Tố Hữu)', 'Tiếng Việt', '2016-01-01', 200, NULL, 1, 5, 5),
(34, 32, 2, '9786042188881', 'Vợ chồng A Phủ', 'Tiếng Việt', '2020-01-01', 180, NULL, 1, 5, 5),
(35, 33, 7, '9786049968426', 'Người lái đò sông Đà (Tuyển tập Nguyễn Tuân)', 'Tiếng Việt', '2020-01-01', 210, NULL, 1, 5, 5),
(36, 34, 10, '9781501175466', 'It', 'Tiếng Anh', '2017-07-11', 1168, NULL, 1, 5, 5),
(37, 35, 7, '9786049590527', 'Tuyển tiểu thuyết xã hội', 'Tiếng Việt', '2018-01-01', 480, NULL, 1, 5, 5),
(38, 36, 1, '9786041211128', 'Tuyển truyện tình tuổi học trò', 'Tiếng Việt', '2020-08-01', 320, NULL, 1, 5, 5),
(39, 37, 8, '9786045638783', 'Kiêu hãnh và định kiến', 'Tiếng Việt', '2017-01-01', 450, NULL, 1, 5, 5),
(40, 38, 1, '9786041179008', 'Mắt biếc', 'Tiếng Việt', '2019-10-01', 312, NULL, 1, 5, 5),
(41, 39, 2, '9786042187341', 'Những ngày thơ ấu', 'Tiếng Việt', '2020-01-01', 150, NULL, 1, 5, 5),
(42, 40, 3, '9780300189196', 'Dumb Luck: A Novel', 'Tiếng Anh', '2002-01-01', 224, NULL, 1, 5, 5),
(43, 2, 1, '9786041234567', 'Cho tôi xin một vé đi tuổi thơ (Bìa cứng, tặng kèm bookmark)', 'Tiếng Việt', '2023-05-20', 220, NULL, 0, 5, 5),
(44, 9, 3, '9786043456789', '1984 (Bản dịch mới)', 'Tiếng Việt', '2022-10-01', 350, NULL, 1, 5, 5),
(45, 17, 1, '9786041168934', 'Tôi thấy hoa vàng trên cỏ xanh (Tái bản 2021)', 'Tiếng Việt', '2021-06-15', 380, NULL, 1, 5, 5),
(46, 38, 1, '9786041179015', 'Mắt biếc (Tái bản 2021)', 'Tiếng Việt', '2021-11-01', 312, NULL, 1, 5, 5),
(47, 7, 6, '9780062315007', 'Nhà Giả Kim (Tái bản 2014)', 'Tiếng Anh', '2014-04-15', 208, NULL, 0, 5, 5),
(48, 1, 2, '9786042222223', 'Dế Mèn phiêu lưu ký (Minh họa mới)', 'Tiếng Việt', '2022-06-01', 168, NULL, 1, 5, 5),
(49, 3, 3, '9786048888888', 'Số đỏ (Ấn bản đặc biệt)', 'Tiếng Việt', '2023-04-01', 260, NULL, 0, 5, 5),
(50, 5, 1, '9786041231234', 'Harry Potter and the Sorcerer''s Stone - School Market Edition', 'Tiếng Anh', '2020-11-20', 450, NULL, 0, 5, 5),
(51, 6, 3, '9786045339246', 'Rừng Na Uy (Tái bản 2021)', 'Tiếng Việt', '2021-08-15', 528, NULL, 1, 5, 5),
(52, 8, 4, '9786045888256', 'Đắc Nhân Tâm (Tái bản 2022)', 'Tiếng Việt', '2022-01-01', 330, NULL, 0, 5, 5),
(53, 10, 8, '9786045645521', 'Án mạng trên chuyến tàu tốc hành Phương Đông (Tái bản)', 'Tiếng Việt', '2020-09-01', 288, NULL, 1, 5, 5),
(54, 15, 4, '9786045585827', 'Sapiens: Lược sử loài người (Tái bản 2021)', 'Tiếng Việt', '2021-10-10', 512, NULL, 1, 5, 5),
(55, 16, 8, '9786045614323', 'The Shining (Bản dịch)', 'Tiếng Việt', '2020-10-31', 720, NULL, 1, 5, 5),
(56, 18, 1, '9786041223459', 'Kính vạn hoa (Tập 1)', 'Tiếng Việt', '2019-05-01', 150, NULL, 1, 5, 5),
(57, 19, 4, '9786041899986', 'How to Win Friends and Influence People (Bản dịch)', 'Tiếng Việt', '2019-01-01', 300, NULL, 1, 5, 5),
(58, 29, 1, '9786041178995', 'Để yên cho bác sĩ Hiền (Tái bản 2022)', 'Tiếng Việt', '2022-02-14', 250, NULL, 1, 5, 5),
(59, 34, 8, '9786045674330', 'It (Bản dịch - Tập 1)', 'Tiếng Việt', '2021-09-01', 600, NULL, 1, 5, 5),
(60, 34, 8, '9786045674347', 'It (Bản dịch - Tập 2)', 'Tiếng Việt', '2021-09-01', 650, NULL, 1, 5, 5),
(61, 2, 1, '9786041182985', 'Cho tôi xin một vé đi tuổi thơ (Ấn bản kỷ niệm)', 'Tiếng Việt', '2020-01-01', 215, NULL, 0, 5, 5),
(62, 4, 7, '9786049791437', 'Tắt đèn (Bản đặc biệt)', 'Tiếng Việt', '2023-01-01', 190, NULL, 0, 5, 5),
(63, 7, 3, '9786045302356', 'Nhà Giả Kim (Bìa cứng)', 'Tiếng Việt', '2022-05-01', 240, NULL, 0, 5, 5),
(64, 11, 2, '9786042171180', 'Góc sân và khoảng trời (Tái bản 2021)', 'Tiếng Việt', '2021-01-01', 110, NULL, 0, 5, 5),
(65, 12, 3, '9786049999999', 'Lão Hạc (Ấn bản Nhã Nam)', 'Tiếng Việt', '2021-04-01', 210, NULL, 1, 5, 5),
(66, 14, 1, '9786041111111', 'Truyện Kiều (Bản phổ thông)', 'Tiếng Việt', '2019-01-01', 300, NULL, 1, 5, 5),
(67, 20, 3, '9786045302363', 'The Alchemist (Bản dịch Nhã Nam)', 'Tiếng Việt', '2019-03-01', 220, NULL, 1, 5, 5),
(68, 21, 3, '9786043333333', 'Trại súc vật (Animal Farm)', 'Tiếng Việt', '2020-06-01', 150, NULL, 1, 5, 5),
(69, 39, 8, '9786045666666', 'Những ngày thơ ấu (Tái bản)', 'Tiếng Việt', '2022-01-01', 160, NULL, 1, 5, 5),
(70, 40, 5, '9780141185805', 'Dumb Luck', 'Tiếng Anh', '2004-01-29', 224, NULL, 1, 5, 5),
(71, 13, 1, '9786041222223', 'Chí Phèo (Bản Trẻ)', 'Tiếng Việt', '2022-08-01', 125, NULL, 1, 5, 5),
(72, 3, 2, '9786041112222', 'Số đỏ (Tái bản Trẻ)', 'Tiếng Việt', '2022-09-01', 255, NULL, 1, 5, 5),
(73, 6, 4, '9786045555555', 'Rừng Na Uy (Bìa mềm)', 'Tiếng Việt', '2021-12-01', 528, NULL, 0, 5, 5),
(74, 16, 10, '9780307743664', 'The Shining (Tái bản 2013)', 'Tiếng Anh', '2013-06-26', 688, NULL, 1, 5, 5),
(75, 18, 1, '9786041223466', 'Kính vạn hoa (Tập 2)', 'Tiếng Việt', '2019-06-01', 150, NULL, 0, 5, 5),
(76, 22, 7, '9786049590517', 'Tuyển truyện ngắn hiện thực Việt Nam (Bìa mềm)', 'Tiếng Việt', '2018-02-01', 450, NULL, 0, 5, 5),
(77, 25, 8, '9786045674323', 'Tuyển tập tiểu thuyết kinh dị (Bản minh họa)', 'Tiếng Việt', '2019-11-01', 420, NULL, 0, 5, 5),
(78, 28, 4, '9786045899986', 'Tuyển truyện cho tuổi mới lớn (Tái bản 2021)', 'Tiếng Việt', '2021-02-01', 500, NULL, 0, 5, 5),
(79, 31, 7, '9786049580020', 'Việt Bắc (Tái bản 2020)', 'Tiếng Việt', '2020-01-01', 200, NULL, 0, 5, 5),
(80, 36, 10, '9781501175266', 'Tuyển truyện tình tuổi học trò (Bản đặc biệt)', 'Tiếng Việt', '2017-07-11', 1168, NULL, 1, 5, 5);

-- =================================================================
-- BẢNG 10: book_instances (Các quyển sách vật lý)
-- status: AVAILABLE(0), BORROWED(1), RESERVED(2), LOST(3), DAMAGED(4), REPAIRING(5), ARCHIVED(6)
-- =================================================================
INSERT INTO `book_instances` (`id`, `edition_id`, `barcode`, `call_number`, `acquired_date`, `acquired_price`, `status`, `note`) VALUES
-- Edition 1: Dế Mèn phiêu lưu ký (Tái bản 2020)
(1, 1, 'DM2020-001', 'VNA-TOH-01-001', '2020-06-01', 75000.00, 0, 'Sách mới nhập kho'),
(2, 1, 'DM2020-002', 'VNA-TOH-01-002', '2020-06-01', 75000.00, 0, 'Sách mới nhập kho'),
(3, 1, 'DM2020-003', 'VNA-TOH-01-003', '2020-06-01', 75000.00, 0, 'Sách mới nhập kho'),
(4, 1, 'DM2020-004', 'VNA-TOH-01-004', '2020-06-01', 75000.00, 0, 'Sách mới nhập kho'),
(5, 1, 'DM2020-005', 'VNA-TOH-01-005', '2020-06-01', 75000.00, 0, 'Sách mới nhập kho'),

-- Edition 2: Dế Mèn phiêu lưu ký (Bìa cứng)
(6, 2, 'DM-BC-001', 'VNA-TOH-02-001', '2018-11-15', 120000.00, 0, 'Sách mới nhập kho'),
(7, 2, 'DM-BC-002', 'VNA-TOH-02-002', '2018-11-15', 120000.00, 0, 'Sách mới nhập kho'),
(8, 2, 'DM-BC-003', 'VNA-TOH-02-003', '2018-11-15', 120000.00, 0, 'Sách mới nhập kho'),
(9, 2, 'DM-BC-004', 'VNA-TOH-02-004', '2018-11-15', 120000.00, 0, 'Sách mới nhập kho'),
(10, 2, 'DM-BC-005', 'VNA-TOH-02-005', '2018-11-15', 120000.00, 0, 'Sách mới nhập kho'),

-- Edition 3: Cho tôi xin một vé đi tuổi thơ (Tái bản 2022)
(11, 3, 'CTV2022-001', 'VNA-NNA-01-001', '2022-04-01', 88000.00, 0, 'Sách mới nhập kho'),
(12, 3, 'CTV2022-002', 'VNA-NNA-01-002', '2022-04-01', 88000.00, 0, 'Sách mới nhập kho'),
(13, 3, 'CTV2022-003', 'VNA-NNA-01-003', '2022-04-01', 88000.00, 0, 'Sách mới nhập kho'),
(14, 3, 'CTV2022-004', 'VNA-NNA-01-004', '2022-04-01', 88000.00, 0, 'Sách mới nhập kho'),
(15, 3, 'CTV2022-005', 'VNA-NNA-01-005', '2022-04-01', 88000.00, 0, 'Sách mới nhập kho'),

-- Edition 4: Số đỏ (Ấn bản 2019)
(16, 4, 'SD2019-001', 'VNA-VTP-01-001', '2019-12-05', 95000.00, 0, 'Sách mới nhập kho'),
(17, 4, 'SD2019-002', 'VNA-VTP-01-002', '2019-12-05', 95000.00, 0, 'Sách mới nhập kho'),
(18, 4, 'SD2019-003', 'VNA-VTP-01-003', '2019-12-05', 95000.00, 0, 'Sách mới nhập kho'),
(19, 4, 'SD2019-004', 'VNA-VTP-01-004', '2019-12-05', 95000.00, 0, 'Sách mới nhập kho'),
(20, 4, 'SD2019-005', 'VNA-VTP-01-005', '2019-12-05', 95000.00, 0, 'Sách mới nhập kho'),

-- Edition 5: Tắt đèn (Tái bản 2021)
(21, 5, 'TD2021-001', 'VNA-NTT-01-001', '2021-08-20', 70000.00, 0, 'Sách mới nhập kho'),
(22, 5, 'TD2021-002', 'VNA-NTT-01-002', '2021-08-20', 70000.00, 0, 'Sách mới nhập kho'),
(23, 5, 'TD2021-003', 'VNA-NTT-01-003', '2021-08-20', 70000.00, 0, 'Sách mới nhập kho'),
(24, 5, 'TD2021-004', 'VNA-NTT-01-004', '2021-08-20', 70000.00, 0, 'Sách mới nhập kho'),
(25, 5, 'TD2021-005', 'VNA-NTT-01-005', '2021-08-20', 70000.00, 0, 'Sách mới nhập kho'),

-- Edition 6: Harry Potter and the Sorcerer's Stone - First Edition
(26, 6, 'HP1-FE-001', 'ENG-JKR-01-001', '2014-10-01', 180000.00, 0, 'New arrival'),
(27, 6, 'HP1-FE-002', 'ENG-JKR-01-002', '2014-10-01', 180000.00, 0, 'New arrival'),
(28, 6, 'HP1-FE-003', 'ENG-JKR-01-003', '2014-10-01', 180000.00, 0, 'New arrival'),
(29, 6, 'HP1-FE-004', 'ENG-JKR-01-004', '2014-10-01', 180000.00, 0, 'New arrival'),
(30, 6, 'HP1-FE-005', 'ENG-JKR-01-005', '2014-10-01', 180000.00, 0, 'New arrival'),

-- Edition 7: Harry Potter and the Sorcerer's Stone - Illustrated Edition
(31, 7, 'HP1-ILL-001', 'ENG-JKR-02-001', '2017-03-01', 250000.00, 0, 'New arrival, illustrated'),
(32, 7, 'HP1-ILL-002', 'ENG-JKR-02-002', '2017-03-01', 250000.00, 0, 'New arrival, illustrated'),
(33, 7, 'HP1-ILL-003', 'ENG-JKR-02-003', '2017-03-01', 250000.00, 0, 'New arrival, illustrated'),
(34, 7, 'HP1-ILL-004', 'ENG-JKR-02-004', '2017-03-01', 250000.00, 0, 'New arrival, illustrated'),
(35, 7, 'HP1-ILL-005', 'ENG-JKR-02-005', '2017-03-01', 250000.00, 0, 'New arrival, illustrated'),

-- Edition 8: Rừng Na Uy (Bản dịch của Trịnh Lữ)
(36, 8, 'RNU-TL-001', 'JPN-HM-01-001', '2016-10-01', 135000.00, 0, 'Sách mới nhập kho'),
(37, 8, 'RNU-TL-002', 'JPN-HM-01-002', '2016-10-01', 135000.00, 0, 'Sách mới nhập kho'),
(38, 8, 'RNU-TL-003', 'JPN-HM-01-003', '2016-10-01', 135000.00, 0, 'Sách mới nhập kho'),
(39, 8, 'RNU-TL-004', 'JPN-HM-01-004', '2016-10-01', 135000.00, 0, 'Sách mới nhập kho'),
(40, 8, 'RNU-TL-005', 'JPN-HM-01-005', '2016-10-01', 135000.00, 0, 'Sách mới nhập kho'),

-- Edition 9: Nhà Giả Kim (Tái bản 2023)
(41, 9, 'NGK2023-001', 'BRA-PC-01-001', '2023-01-25', 99000.00, 0, 'Sách mới nhập kho'),
(42, 9, 'NGK2023-002', 'BRA-PC-01-002', '2023-01-25', 99000.00, 0, 'Sách mới nhập kho'),
(43, 9, 'NGK2023-003', 'BRA-PC-01-003', '2023-01-25', 99000.00, 0, 'Sách mới nhập kho'),
(44, 9, 'NGK2023-004', 'BRA-PC-01-004', '2023-01-25', 99000.00, 0, 'Sách mới nhập kho'),
(45, 9, 'NGK2023-005', 'BRA-PC-01-005', '2023-01-25', 99000.00, 0, 'Sách mới nhập kho'),

-- Edition 10: Đắc Nhân Tâm
(46, 10, 'DNT-001', 'USA-DC-01-001', '2020-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(47, 10, 'DNT-002', 'USA-DC-01-002', '2020-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(48, 10, 'DNT-003', 'USA-DC-01-003', '2020-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(49, 10, 'DNT-004', 'USA-DC-01-004', '2020-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(50, 10, 'DNT-005', 'USA-DC-01-005', '2020-02-01', 80000.00, 0, 'Sách mới nhập kho'),

-- Edition 11: 1984 (Signet Classics)
(51, 11, '1984-SC-001', 'ENG-GO-01-001', '1961-02-01', 150000.00, 0, 'New arrival, vintage edition'),
(52, 11, '1984-SC-002', 'ENG-GO-01-002', '1961-02-01', 150000.00, 0, 'New arrival, vintage edition'),
(53, 11, '1984-SC-003', 'ENG-GO-01-003', '1961-02-01', 150000.00, 0, 'New arrival, vintage edition'),
(54, 11, '1984-SC-004', 'ENG-GO-01-004', '1961-02-01', 150000.00, 0, 'New arrival, vintage edition'),
(55, 11, '1984-SC-005', 'ENG-GO-01-005', '1961-02-01', 150000.00, 0, 'New arrival, vintage edition'),

-- Edition 12: Án mạng trên chuyến tàu tốc hành Phương Đông
(56, 12, 'AMPT-001', 'ENG-AC-01-001', '2017-07-01', 110000.00, 0, 'Sách mới nhập kho'),
(57, 12, 'AMPT-002', 'ENG-AC-01-002', '2017-07-01', 110000.00, 0, 'Sách mới nhập kho'),
(58, 12, 'AMPT-003', 'ENG-AC-01-003', '2017-07-01', 110000.00, 0, 'Sách mới nhập kho'),
(59, 12, 'AMPT-004', 'ENG-AC-01-004', '2017-07-01', 110000.00, 0, 'Sách mới nhập kho'),
(60, 12, 'AMPT-005', 'ENG-AC-01-005', '2017-07-01', 110000.00, 0, 'Sách mới nhập kho'),

-- Edition 13: Góc sân và khoảng trời (Tái bản 2019)
(61, 13, 'GSKT2019-001', 'VNA-TDK-01-001', '2019-02-01', 150000.00, 0, 'Sách mới nhập kho, bìa cứng'),
(62, 13, 'GSKT2019-002', 'VNA-TDK-01-002', '2019-02-01', 150000.00, 0, 'Sách mới nhập kho, bìa cứng'),
(63, 13, 'GSKT2019-003', 'VNA-TDK-01-003', '2019-02-01', 150000.00, 0, 'Sách mới nhập kho, bìa cứng'),
(64, 13, 'GSKT2019-004', 'VNA-TDK-01-004', '2019-02-01', 150000.00, 0, 'Sách mới nhập kho, bìa cứng'),
(65, 13, 'GSKT2019-005', 'VNA-TDK-01-005', '2019-02-01', 150000.00, 0, 'Sách mới nhập kho, bìa cứng'),

-- Edition 14: Lão Hạc (Tuyển tập truyện ngắn Nam Cao)
(66, 14, 'LH-NC-001', 'VNA-NC-01-001', '2018-02-01', 65000.00, 0, 'Sách mới nhập kho'),
(67, 14, 'LH-NC-002', 'VNA-NC-01-002', '2018-02-01', 65000.00, 0, 'Sách mới nhập kho'),
(68, 14, 'LH-NC-003', 'VNA-NC-01-003', '2018-02-01', 65000.00, 0, 'Sách mới nhập kho'),
(69, 14, 'LH-NC-004', 'VNA-NC-01-004', '2018-02-01', 65000.00, 0, 'Sách mới nhập kho'),
(70, 14, 'LH-NC-005', 'VNA-NC-01-005', '2018-02-01', 65000.00, 0, 'Sách mới nhập kho'),

-- Edition 15: Chí Phèo (Tái bản 2020)
(71, 15, 'CP2020-001', 'VNA-NC-02-001', '2020-03-15', 50000.00, 0, 'Sách mới nhập kho'),
(72, 15, 'CP2020-002', 'VNA-NC-02-002', '2020-03-15', 50000.00, 0, 'Sách mới nhập kho'),
(73, 15, 'CP2020-003', 'VNA-NC-02-003', '2020-03-15', 50000.00, 0, 'Sách mới nhập kho'),
(74, 15, 'CP2020-004', 'VNA-NC-02-004', '2020-03-15', 50000.00, 0, 'Sách mới nhập kho'),
(75, 15, 'CP2020-005', 'VNA-NC-02-005', '2020-03-15', 50000.00, 0, 'Sách mới nhập kho'),

-- Edition 16: Truyện Kiều (Bản kỷ niệm 200 năm)
(76, 16, 'TK200-001', 'VNA-ND-01-001', '2020-10-01', 200000.00, 0, 'Bản kỷ niệm, bìa cứng'),
(77, 16, 'TK200-002', 'VNA-ND-01-002', '2020-10-01', 200000.00, 0, 'Bản kỷ niệm, bìa cứng'),
(78, 16, 'TK200-003', 'VNA-ND-01-003', '2020-10-01', 200000.00, 0, 'Bản kỷ niệm, bìa cứng'),
(79, 16, 'TK200-004', 'VNA-ND-01-004', '2020-10-01', 200000.00, 0, 'Bản kỷ niệm, bìa cứng'),
(80, 16, 'TK200-005', 'VNA-ND-01-005', '2020-10-01', 200000.00, 0, 'Bản kỷ niệm, bìa cứng'),

-- Edition 17: Sapiens: Lược sử loài người
(81, 17, 'SP-VN-001', 'ISR-YNH-01-001', '2018-05-15', 189000.00, 0, 'Sách mới nhập kho'),
(82, 17, 'SP-VN-002', 'ISR-YNH-01-002', '2018-05-15', 189000.00, 0, 'Sách mới nhập kho'),
(83, 17, 'SP-VN-003', 'ISR-YNH-01-003', '2018-05-15', 189000.00, 0, 'Sách mới nhập kho'),
(84, 17, 'SP-VN-004', 'ISR-YNH-01-004', '2018-05-15', 189000.00, 0, 'Sách mới nhập kho'),
(85, 17, 'SP-VN-005', 'ISR-YNH-01-005', '2018-05-15', 189000.00, 0, 'Sách mới nhập kho'),

-- Edition 18: The Shining
(86, 18, 'SHN-ENG-001', 'USA-SK-01-001', '2012-07-10', 210000.00, 0, 'New arrival'),
(87, 18, 'SHN-ENG-002', 'USA-SK-01-002', '2012-07-10', 210000.00, 0, 'New arrival'),
(88, 18, 'SHN-ENG-003', 'USA-SK-01-003', '2012-07-10', 210000.00, 0, 'New arrival'),
(89, 18, 'SHN-ENG-004', 'USA-SK-01-004', '2012-07-10', 210000.00, 0, 'New arrival'),
(90, 18, 'SHN-ENG-005', 'USA-SK-01-005', '2012-07-10', 210000.00, 0, 'New arrival'),

-- Edition 19: Tôi thấy hoa vàng trên cỏ xanh
(91, 19, 'HVTCX-001', 'VNA-NNA-02-001', '2015-09-20', 125000.00, 0, 'Sách mới nhập kho'),
(92, 19, 'HVTCX-002', 'VNA-NNA-02-002', '2015-09-20', 125000.00, 0, 'Sách mới nhập kho'),
(93, 19, 'HVTCX-003', 'VNA-NNA-02-003', '2015-09-20', 125000.00, 0, 'Sách mới nhập kho'),
(94, 19, 'HVTCX-004', 'VNA-NNA-02-004', '2015-09-20', 125000.00, 0, 'Sách mới nhập kho'),
(95, 19, 'HVTCX-005', 'VNA-NNA-02-005', '2015-09-20', 125000.00, 0, 'Sách mới nhập kho'),

-- Edition 20: Kính vạn hoa (Trọn bộ)
(96, 20, 'KVH-TB-001', 'VNA-NNA-03-001', '2021-02-01', 1500000.00, 0, 'Trọn bộ, bìa cứng'),
(97, 20, 'KVH-TB-002', 'VNA-NNA-03-002', '2021-02-01', 1500000.00, 0, 'Trọn bộ, bìa cứng'),
(98, 20, 'KVH-TB-003', 'VNA-NNA-03-003', '2021-02-01', 1500000.00, 0, 'Trọn bộ, bìa cứng'),
(99, 20, 'KVH-TB-004', 'VNA-NNA-03-004', '2021-02-01', 1500000.00, 0, 'Trọn bộ, bìa cứng'),
(100, 20, 'KVH-TB-005', 'VNA-NNA-03-005', '2021-02-01', 1500000.00, 0, 'Trọn bộ, bìa cứng'),

-- Edition 21: How to Win Friends and Influence People
(101, 21, 'HWF-ENG-001', 'USA-DC-02-001', '1998-11-01', 160000.00, 0, 'New arrival'),
(102, 21, 'HWF-ENG-002', 'USA-DC-02-002', '1998-11-01', 160000.00, 0, 'New arrival'),
(103, 21, 'HWF-ENG-003', 'USA-DC-02-003', '1998-11-01', 160000.00, 0, 'New arrival'),
(104, 21, 'HWF-ENG-004', 'USA-DC-02-004', '1998-11-01', 160000.00, 0, 'New arrival'),
(105, 21, 'HWF-ENG-005', 'USA-DC-02-005', '1998-11-01', 160000.00, 0, 'New arrival'),

-- Edition 22: The Alchemist
(106, 22, 'ALCH-ENG-001', 'BRA-PC-02-001', '2006-11-10', 175000.00, 0, 'New arrival'),
(107, 22, 'ALCH-ENG-002', 'BRA-PC-02-002', '2006-11-10', 175000.00, 0, 'New arrival'),
(108, 22, 'ALCH-ENG-003', 'BRA-PC-02-003', '2006-11-10', 175000.00, 0, 'New arrival'),
(109, 22, 'ALCH-ENG-004', 'BRA-PC-02-004', '2006-11-10', 175000.00, 0, 'New arrival'),
(110, 22, 'ALCH-ENG-005', 'BRA-PC-02-005', '2006-11-10', 175000.00, 0, 'New arrival'),

-- Edition 23: Animal Farm (75th Anniversary Edition)
(111, 23, 'AF-75-001', 'ENG-GO-02-001', '2021-09-01', 190000.00, 0, '75th anniversary edition'),
(112, 23, 'AF-75-002', 'ENG-GO-02-002', '2021-09-01', 190000.00, 0, '75th anniversary edition'),
(113, 23, 'AF-75-003', 'ENG-GO-02-003', '2021-09-01', 190000.00, 0, '75th anniversary edition'),
(114, 23, 'AF-75-004', 'ENG-GO-02-004', '2021-09-01', 190000.00, 0, '75th anniversary edition'),
(115, 23, 'AF-75-005', 'ENG-GO-02-005', '2021-09-01', 190000.00, 0, '75th anniversary edition'),

-- Edition 24: Tuyển truyện ngắn hiện thực Việt Nam
(116, 24, 'TTNHT-001', 'VNA-MULTI-01-001', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(117, 24, 'TTNHT-002', 'VNA-MULTI-01-002', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(118, 24, 'TTNHT-003', 'VNA-MULTI-01-003', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(119, 24, 'TTNHT-004', 'VNA-MULTI-01-004', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(120, 24, 'TTNHT-005', 'VNA-MULTI-01-005', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),

-- Edition 25: Tuyển thơ thiếu nhi Việt Nam
(121, 25, 'TTTN-001', 'VNA-MULTI-02-001', '2018-07-01', 90000.00, 0, 'Sách mới nhập kho'),
(122, 25, 'TTTN-002', 'VNA-MULTI-02-002', '2018-07-01', 90000.00, 0, 'Sách mới nhập kho'),
(123, 25, 'TTTN-003', 'VNA-MULTI-02-003', '2018-07-01', 90000.00, 0, 'Sách mới nhập kho'),
(124, 25, 'TTTN-004', 'VNA-MULTI-02-004', '2018-07-01', 90000.00, 0, 'Sách mới nhập kho'),
(125, 25, 'TTTN-005', 'VNA-MULTI-02-005', '2018-07-01', 90000.00, 0, 'Sách mới nhập kho'),

-- Edition 26: Tuyển truyện trào phúng Việt Nam
(126, 26, 'TTTP-001', 'VNA-MULTI-03-001', '2019-02-01', 95000.00, 0, 'Sách mới nhập kho'),
(127, 26, 'TTTP-002', 'VNA-MULTI-03-002', '2019-02-01', 95000.00, 0, 'Sách mới nhập kho'),
(128, 26, 'TTTP-003', 'VNA-MULTI-03-003', '2019-02-01', 95000.00, 0, 'Sách mới nhập kho'),
(129, 26, 'TTTP-004', 'VNA-MULTI-03-004', '2019-02-01', 95000.00, 0, 'Sách mới nhập kho'),
(130, 26, 'TTTP-005', 'VNA-MULTI-03-005', '2019-02-01', 95000.00, 0, 'Sách mới nhập kho'),

-- Edition 27: Tuyển tập tiểu thuyết kinh dị
(131, 27, 'TTTKD-001', 'MULTI-HOR-01-001', '2019-11-15', 130000.00, 0, 'Sách mới nhập kho'),
(132, 27, 'TTTKD-002', 'MULTI-HOR-01-002', '2019-11-15', 130000.00, 0, 'Sách mới nhập kho'),
(133, 27, 'TTTKD-003', 'MULTI-HOR-01-003', '2019-11-15', 130000.00, 0, 'Sách mới nhập kho'),
(134, 27, 'TTTKD-004', 'MULTI-HOR-01-004', '2019-11-15', 130000.00, 0, 'Sách mới nhập kho'),
(135, 27, 'TTTKD-005', 'MULTI-HOR-01-005', '2019-11-15', 130000.00, 0, 'Sách mới nhập kho'),

-- Edition 28: Tuyển sách kỹ năng giao tiếp kinh điển
(136, 28, 'TSKNGT-001', 'MULTI-SKL-01-001', '2021-02-01', 250000.00, 0, 'Bìa cứng, sách mới'),
(137, 28, 'TSKNGT-002', 'MULTI-SKL-01-002', '2021-02-01', 250000.00, 0, 'Bìa cứng, sách mới'),
(138, 28, 'TSKNGT-003', 'MULTI-SKL-01-003', '2021-02-01', 250000.00, 0, 'Bìa cứng, sách mới'),
(139, 28, 'TSKNGT-004', 'MULTI-SKL-01-004', '2021-02-01', 250000.00, 0, 'Bìa cứng, sách mới'),
(140, 28, 'TSKNGT-005', 'MULTI-SKL-01-005', '2021-02-01', 250000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 29: Tuyển sách lịch sử suy ngẫm
(141, 29, 'TSLS-001', 'MULTI-HIS-01-001', '2020-02-01', 145000.00, 0, 'Sách mới nhập kho'),
(142, 29, 'TSLS-002', 'MULTI-HIS-01-002', '2020-02-01', 145000.00, 0, 'Sách mới nhập kho'),
(143, 29, 'TSLS-003', 'MULTI-HIS-01-003', '2020-02-01', 145000.00, 0, 'Sách mới nhập kho'),
(144, 29, 'TSLS-004', 'MULTI-HIS-01-004', '2020-02-01', 145000.00, 0, 'Sách mới nhập kho'),
(145, 29, 'TSLS-005', 'MULTI-HIS-01-005', '2020-02-01', 145000.00, 0, 'Sách mới nhập kho'),

-- Edition 30: Tuyển truyện cho tuổi mới lớn
(146, 30, 'TTTML-001', 'VNA-MULTI-04-001', '2020-06-15', 115000.00, 0, 'Sách mới nhập kho'),
(147, 30, 'TTTML-002', 'VNA-MULTI-04-002', '2020-06-15', 115000.00, 0, 'Sách mới nhập kho'),
(148, 30, 'TTTML-003', 'VNA-MULTI-04-003', '2020-06-15', 115000.00, 0, 'Sách mới nhập kho'),
(149, 30, 'TTTML-004', 'VNA-MULTI-04-004', '2020-06-15', 115000.00, 0, 'Sách mới nhập kho'),
(150, 30, 'TTTML-005', 'VNA-MULTI-04-005', '2020-06-15', 115000.00, 0, 'Sách mới nhập kho'),

-- Edition 31: Để yên cho bác sĩ Hiền
(151, 31, 'DYBSH-001', 'VNA-NNA-04-001', '2016-02-01', 85000.00, 0, 'Sách mới nhập kho'),
(152, 31, 'DYBSH-002', 'VNA-NNA-04-002', '2016-02-01', 85000.00, 0, 'Sách mới nhập kho'),
(153, 31, 'DYBSH-003', 'VNA-NNA-04-003', '2016-02-01', 85000.00, 0, 'Sách mới nhập kho'),
(154, 31, 'DYBSH-004', 'VNA-NNA-04-004', '2016-02-01', 85000.00, 0, 'Sách mới nhập kho'),
(155, 31, 'DYBSH-005', 'VNA-NNA-04-005', '2016-02-01', 85000.00, 0, 'Sách mới nhập kho'),

-- Edition 32: Đời thừa
(156, 32, 'DT-NC-001', 'VNA-NC-03-001', '2018-03-20', 72000.00, 0, 'Sách mới nhập kho'),
(157, 32, 'DT-NC-002', 'VNA-NC-03-002', '2018-03-20', 72000.00, 0, 'Sách mới nhập kho'),
(158, 32, 'DT-NC-003', 'VNA-NC-03-003', '2018-03-20', 72000.00, 0, 'Sách mới nhập kho'),
(159, 32, 'DT-NC-004', 'VNA-NC-03-004', '2018-03-20', 72000.00, 0, 'Sách mới nhập kho'),
(160, 32, 'DT-NC-005', 'VNA-NC-03-005', '2018-03-20', 72000.00, 0, 'Sách mới nhập kho'),

-- Edition 33: Việt Bắc (Tuyển tập Tố Hữu)
(161, 33, 'VB-TH-001', 'VNA-TH-01-001', '2016-02-01', 68000.00, 0, 'Sách mới nhập kho'),
(162, 33, 'VB-TH-002', 'VNA-TH-01-002', '2016-02-01', 68000.00, 0, 'Sách mới nhập kho'),
(163, 33, 'VB-TH-003', 'VNA-TH-01-003', '2016-02-01', 68000.00, 0, 'Sách mới nhập kho'),
(164, 33, 'VB-TH-004', 'VNA-TH-01-004', '2016-02-01', 68000.00, 0, 'Sách mới nhập kho'),
(165, 33, 'VB-TH-005', 'VNA-TH-01-005', '2016-02-01', 68000.00, 0, 'Sách mới nhập kho'),

-- Edition 34: Vợ chồng A Phủ
(166, 34, 'VCAP-001', 'VNA-TOH-03-001', '2020-02-01', 55000.00, 0, 'Sách mới nhập kho'),
(167, 34, 'VCAP-002', 'VNA-TOH-03-002', '2020-02-01', 55000.00, 0, 'Sách mới nhập kho'),
(168, 34, 'VCAP-003', 'VNA-TOH-03-003', '2020-02-01', 55000.00, 0, 'Sách mới nhập kho'),
(169, 34, 'VCAP-004', 'VNA-TOH-03-004', '2020-02-01', 55000.00, 0, 'Sách mới nhập kho'),
(170, 34, 'VCAP-005', 'VNA-TOH-03-005', '2020-02-01', 55000.00, 0, 'Sách mới nhập kho'),

-- Edition 35: Người lái đò sông Đà (Tuyển tập Nguyễn Tuân)
(171, 35, 'NLĐSĐ-001', 'VNA-NT-01-001', '2020-02-01', 78000.00, 0, 'Sách mới nhập kho'),
(172, 35, 'NLĐSĐ-002', 'VNA-NT-01-002', '2020-02-01', 78000.00, 0, 'Sách mới nhập kho'),
(173, 35, 'NLĐSĐ-003', 'VNA-NT-01-003', '2020-02-01', 78000.00, 0, 'Sách mới nhập kho'),
(174, 35, 'NLĐSĐ-004', 'VNA-NT-01-004', '2020-02-01', 78000.00, 0, 'Sách mới nhập kho'),
(175, 35, 'NLĐSĐ-005', 'VNA-NT-01-005', '2020-02-01', 78000.00, 0, 'Sách mới nhập kho'),

-- Edition 36: It
(176, 36, 'IT-ENG-001', 'USA-SK-02-001', '2017-08-01', 350000.00, 0, 'New arrival'),
(177, 36, 'IT-ENG-002', 'USA-SK-02-002', '2017-08-01', 350000.00, 0, 'New arrival'),
(178, 36, 'IT-ENG-003', 'USA-SK-02-003', '2017-08-01', 350000.00, 0, 'New arrival'),
(179, 36, 'IT-ENG-004', 'USA-SK-02-004', '2017-08-01', 350000.00, 0, 'New arrival'),
(180, 36, 'IT-ENG-005', 'USA-SK-02-005', '2017-08-01', 350000.00, 0, 'New arrival'),

-- Edition 37: Tuyển tiểu thuyết xã hội
(181, 37, 'TTTXH-001', 'VNA-MULTI-05-001', '2018-02-01', 155000.00, 0, 'Sách mới nhập kho'),
(182, 37, 'TTTXH-002', 'VNA-MULTI-05-002', '2018-02-01', 155000.00, 0, 'Sách mới nhập kho'),
(183, 37, 'TTTXH-003', 'VNA-MULTI-05-003', '2018-02-01', 155000.00, 0, 'Sách mới nhập kho'),
(184, 37, 'TTTXH-004', 'VNA-MULTI-05-004', '2018-02-01', 155000.00, 0, 'Sách mới nhập kho'),
(185, 37, 'TTTXH-005', 'VNA-MULTI-05-005', '2018-02-01', 155000.00, 0, 'Sách mới nhập kho'),

-- Edition 38: Tuyển truyện tình tuổi học trò
(186, 38, 'TTTTHT-001', 'VNA-MULTI-06-001', '2020-08-20', 105000.00, 0, 'Sách mới nhập kho'),
(187, 38, 'TTTTHT-002', 'VNA-MULTI-06-002', '2020-08-20', 105000.00, 0, 'Sách mới nhập kho'),
(188, 38, 'TTTTHT-003', 'VNA-MULTI-06-003', '2020-08-20', 105000.00, 0, 'Sách mới nhập kho'),
(189, 38, 'TTTTHT-004', 'VNA-MULTI-06-004', '2020-08-20', 105000.00, 0, 'Sách mới nhập kho'),
(190, 38, 'TTTTHT-005', 'VNA-MULTI-06-005', '2020-08-20', 105000.00, 0, 'Sách mới nhập kho'),

-- Edition 39: Kiêu hãnh và định kiến
(191, 39, 'KHVDK-001', 'ENG-JA-01-001', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(192, 39, 'KHVDK-002', 'ENG-JA-01-002', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(193, 39, 'KHVDK-003', 'ENG-JA-01-003', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(194, 39, 'KHVDK-004', 'ENG-JA-01-004', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),
(195, 39, 'KHVDK-005', 'ENG-JA-01-005', '2017-02-01', 120000.00, 0, 'Sách mới nhập kho'),

-- Edition 40: Mắt biếc
(196, 40, 'MB-NNA-001', 'VNA-NNA-05-001', '2019-10-15', 98000.00, 0, 'Sách mới nhập kho'),
(197, 40, 'MB-NNA-002', 'VNA-NNA-05-002', '2019-10-15', 98000.00, 0, 'Sách mới nhập kho'),
(198, 40, 'MB-NNA-003', 'VNA-NNA-05-003', '2019-10-15', 98000.00, 0, 'Sách mới nhập kho'),
(199, 40, 'MB-NNA-004', 'VNA-NNA-05-004', '2019-10-15', 98000.00, 0, 'Sách mới nhập kho'),
(200, 40, 'MB-NNA-005', 'VNA-NNA-05-005', '2019-10-15', 98000.00, 0, 'Sách mới nhập kho'),

-- Edition 41: Những ngày thơ ấu
(201, 41, 'NNTA-001', 'VNA-NH-01-001', '2020-02-01', 60000.00, 0, 'Sách mới nhập kho'),
(202, 41, 'NNTA-002', 'VNA-NH-01-002', '2020-02-01', 60000.00, 0, 'Sách mới nhập kho'),
(203, 41, 'NNTA-003', 'VNA-NH-01-003', '2020-02-01', 60000.00, 0, 'Sách mới nhập kho'),
(204, 41, 'NNTA-004', 'VNA-NH-01-004', '2020-02-01', 60000.00, 0, 'Sách mới nhập kho'),
(205, 41, 'NNTA-005', 'VNA-NH-01-005', '2020-02-01', 60000.00, 0, 'Sách mới nhập kho'),

-- Edition 42: Dumb Luck: A Novel
(206, 42, 'DL-ENG-001', 'ENG-VTP-02-001', '2002-02-15', 180000.00, 0, 'New arrival'),
(207, 42, 'DL-ENG-002', 'ENG-VTP-02-002', '2002-02-15', 180000.00, 0, 'New arrival'),
(208, 42, 'DL-ENG-003', 'ENG-VTP-02-003', '2002-02-15', 180000.00, 0, 'New arrival'),
(209, 42, 'DL-ENG-004', 'ENG-VTP-02-004', '2002-02-15', 180000.00, 0, 'New arrival'),
(210, 42, 'DL-ENG-005', 'ENG-VTP-02-005', '2002-02-15', 180000.00, 0, 'New arrival'),

-- Edition 43: Cho tôi xin một vé đi tuổi thơ (Bìa cứng, tặng kèm bookmark)
(211, 43, 'CTV-BC-BM-001', 'VNA-NNA-06-001', '2023-06-01', 150000.00, 0, 'Bản đặc biệt, bìa cứng'),
(212, 43, 'CTV-BC-BM-002', 'VNA-NNA-06-002', '2023-06-01', 150000.00, 0, 'Bản đặc biệt, bìa cứng'),
(213, 43, 'CTV-BC-BM-003', 'VNA-NNA-06-003', '2023-06-01', 150000.00, 0, 'Bản đặc biệt, bìa cứng'),
(214, 43, 'CTV-BC-BM-004', 'VNA-NNA-06-004', '2023-06-01', 150000.00, 0, 'Bản đặc biệt, bìa cứng'),
(215, 43, 'CTV-BC-BM-005', 'VNA-NNA-06-005', '2023-06-01', 150000.00, 0, 'Bản đặc biệt, bìa cứng'),

-- Edition 44: 1984 (Bản dịch mới)
(216, 44, '1984-VN-001', 'ENG-GO-03-001', '2022-10-15', 110000.00, 0, 'Sách mới nhập kho'),
(217, 44, '1984-VN-002', 'ENG-GO-03-002', '2022-10-15', 110000.00, 0, 'Sách mới nhập kho'),
(218, 44, '1984-VN-003', 'ENG-GO-03-003', '2022-10-15', 110000.00, 0, 'Sách mới nhập kho'),
(219, 44, '1984-VN-004', 'ENG-GO-03-004', '2022-10-15', 110000.00, 0, 'Sách mới nhập kho'),
(220, 44, '1984-VN-005', 'ENG-GO-03-005', '2022-10-15', 110000.00, 0, 'Sách mới nhập kho'),

-- Edition 45: Tôi thấy hoa vàng trên cỏ xanh (Tái bản 2021)
(221, 45, 'HVTCX-2021-001', 'VNA-NNA-07-001', '2021-07-01', 125000.00, 0, 'Sách mới nhập kho'),
(222, 45, 'HVTCX-2021-002', 'VNA-NNA-07-002', '2021-07-01', 125000.00, 0, 'Sách mới nhập kho'),
(223, 45, 'HVTCX-2021-003', 'VNA-NNA-07-003', '2021-07-01', 125000.00, 0, 'Sách mới nhập kho'),
(224, 45, 'HVTCX-2021-004', 'VNA-NNA-07-004', '2021-07-01', 125000.00, 0, 'Sách mới nhập kho'),
(225, 45, 'HVTCX-2021-005', 'VNA-NNA-07-005', '2021-07-01', 125000.00, 0, 'Sách mới nhập kho'),

-- Edition 46: Mắt biếc (Tái bản 2021)
(226, 46, 'MB-2021-001', 'VNA-NNA-08-001', '2021-11-20', 105000.00, 0, 'Sách mới nhập kho'),
(227, 46, 'MB-2021-002', 'VNA-NNA-08-002', '2021-11-20', 105000.00, 0, 'Sách mới nhập kho'),
(228, 46, 'MB-2021-003', 'VNA-NNA-08-003', '2021-11-20', 105000.00, 0, 'Sách mới nhập kho'),
(229, 46, 'MB-2021-004', 'VNA-NNA-08-004', '2021-11-20', 105000.00, 0, 'Sách mới nhập kho'),
(230, 46, 'MB-2021-005', 'VNA-NNA-08-005', '2021-11-20', 105000.00, 0, 'Sách mới nhập kho'),

-- Edition 47: Nhà Giả Kim (Tái bản 2014)
(231, 47, 'NGK-ENG-2014-001', 'BRA-PC-03-001', '2014-05-01', 220000.00, 0, 'Hardcover, new arrival'),
(232, 47, 'NGK-ENG-2014-002', 'BRA-PC-03-002', '2014-05-01', 220000.00, 0, 'Hardcover, new arrival'),
(233, 47, 'NGK-ENG-2014-003', 'BRA-PC-03-003', '2014-05-01', 220000.00, 0, 'Hardcover, new arrival'),
(234, 47, 'NGK-ENG-2014-004', 'BRA-PC-03-004', '2014-05-01', 220000.00, 0, 'Hardcover, new arrival'),
(235, 47, 'NGK-ENG-2014-005', 'BRA-PC-03-005', '2014-05-01', 220000.00, 0, 'Hardcover, new arrival'),

-- Edition 48: Dế Mèn phiêu lưu ký (Minh họa mới)
(236, 48, 'DM-MH-001', 'VNA-TOH-04-001', '2022-06-20', 95000.00, 0, 'Bản minh họa màu'),
(237, 48, 'DM-MH-002', 'VNA-TOH-04-002', '2022-06-20', 95000.00, 0, 'Bản minh họa màu'),
(238, 48, 'DM-MH-003', 'VNA-TOH-04-003', '2022-06-20', 95000.00, 0, 'Bản minh họa màu'),
(239, 48, 'DM-MH-004', 'VNA-TOH-04-004', '2022-06-20', 95000.00, 0, 'Bản minh họa màu'),
(240, 48, 'DM-MH-005', 'VNA-TOH-04-005', '2022-06-20', 95000.00, 0, 'Bản minh họa màu'),

-- Edition 49: Số đỏ (Ấn bản đặc biệt)
(241, 49, 'SD-SPECIAL-001', 'VNA-VTP-03-001', '2023-04-15', 180000.00, 0, 'Bản đặc biệt, bìa cứng'),
(242, 49, 'SD-SPECIAL-002', 'VNA-VTP-03-002', '2023-04-15', 180000.00, 0, 'Bản đặc biệt, bìa cứng'),
(243, 49, 'SD-SPECIAL-003', 'VNA-VTP-03-003', '2023-04-15', 180000.00, 0, 'Bản đặc biệt, bìa cứng'),
(244, 49, 'SD-SPECIAL-004', 'VNA-VTP-03-004', '2023-04-15', 180000.00, 0, 'Bản đặc biệt, bìa cứng'),
(245, 49, 'SD-SPECIAL-005', 'VNA-VTP-03-005', '2023-04-15', 180000.00, 0, 'Bản đặc biệt, bìa cứng'),

-- Edition 50: Harry Potter... (School Market Edition)
(246, 50, 'HP1-SME-001', 'ENG-JKR-03-001', '2020-12-05', 280000.00, 0, 'School market edition, hardcover'),
(247, 50, 'HP1-SME-002', 'ENG-JKR-03-002', '2020-12-05', 280000.00, 0, 'School market edition, hardcover'),
(248, 50, 'HP1-SME-003', 'ENG-JKR-03-003', '2020-12-05', 280000.00, 0, 'School market edition, hardcover'),
(249, 50, 'HP1-SME-004', 'ENG-JKR-03-004', '2020-12-05', 280000.00, 0, 'School market edition, hardcover'),
(250, 50, 'HP1-SME-005', 'ENG-JKR-03-005', '2020-12-05', 280000.00, 0, 'School market edition, hardcover'),

-- Edition 51: Rừng Na Uy (Tái bản 2021)
(251, 51, 'RNU-2021-001', 'JPN-HM-02-001', '2021-09-01', 140000.00, 0, 'Sách mới nhập kho'),
(252, 51, 'RNU-2021-002', 'JPN-HM-02-002', '2021-09-01', 140000.00, 0, 'Sách mới nhập kho'),
(253, 51, 'RNU-2021-003', 'JPN-HM-02-003', '2021-09-01', 140000.00, 0, 'Sách mới nhập kho'),
(254, 51, 'RNU-2021-004', 'JPN-HM-02-004', '2021-09-01', 140000.00, 0, 'Sách mới nhập kho'),
(255, 51, 'RNU-2021-005', 'JPN-HM-02-005', '2021-09-01', 140000.00, 0, 'Sách mới nhập kho'),

-- Edition 52: Đắc Nhân Tâm (Tái bản 2022)
(256, 52, 'DNT-2022-001', 'USA-DC-03-001', '2022-01-20', 160000.00, 0, 'Bìa cứng, sách mới'),
(257, 52, 'DNT-2022-002', 'USA-DC-03-002', '2022-01-20', 160000.00, 0, 'Bìa cứng, sách mới'),
(258, 52, 'DNT-2022-003', 'USA-DC-03-003', '2022-01-20', 160000.00, 0, 'Bìa cứng, sách mới'),
(259, 52, 'DNT-2022-004', 'USA-DC-03-004', '2022-01-20', 160000.00, 0, 'Bìa cứng, sách mới'),
(260, 52, 'DNT-2022-005', 'USA-DC-03-005', '2022-01-20', 160000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 53: Án mạng trên chuyến tàu... (Tái bản)
(261, 53, 'AMPT-TB-001', 'ENG-AC-02-001', '2020-09-15', 115000.00, 0, 'Sách mới nhập kho'),
(262, 53, 'AMPT-TB-002', 'ENG-AC-02-002', '2020-09-15', 115000.00, 0, 'Sách mới nhập kho'),
(263, 53, 'AMPT-TB-003', 'ENG-AC-02-003', '2020-09-15', 115000.00, 0, 'Sách mới nhập kho'),
(264, 53, 'AMPT-TB-004', 'ENG-AC-02-004', '2020-09-15', 115000.00, 0, 'Sách mới nhập kho'),
(265, 53, 'AMPT-TB-005', 'ENG-AC-02-005', '2020-09-15', 115000.00, 0, 'Sách mới nhập kho'),

-- Edition 54: Sapiens... (Tái bản 2021)
(266, 54, 'SPVN-2021-001', 'ISR-YNH-02-001', '2021-11-01', 195000.00, 0, 'Sách mới nhập kho'),
(267, 54, 'SPVN-2021-002', 'ISR-YNH-02-002', '2021-11-01', 195000.00, 0, 'Sách mới nhập kho'),
(268, 54, 'SPVN-2021-003', 'ISR-YNH-02-003', '2021-11-01', 195000.00, 0, 'Sách mới nhập kho'),
(269, 54, 'SPVN-2021-004', 'ISR-YNH-02-004', '2021-11-01', 195000.00, 0, 'Sách mới nhập kho'),
(270, 54, 'SPVN-2021-005', 'ISR-YNH-02-005', '2021-11-01', 195000.00, 0, 'Sách mới nhập kho'),

-- Edition 55: The Shining (Bản dịch)
(271, 55, 'SHN-VN-001', 'USA-SK-03-001', '2020-11-15', 190000.00, 0, 'Bản dịch tiếng Việt'),
(272, 55, 'SHN-VN-002', 'USA-SK-03-002', '2020-11-15', 190000.00, 0, 'Bản dịch tiếng Việt'),
(273, 55, 'SHN-VN-003', 'USA-SK-03-003', '2020-11-15', 190000.00, 0, 'Bản dịch tiếng Việt'),
(274, 55, 'SHN-VN-004', 'USA-SK-03-004', '2020-11-15', 190000.00, 0, 'Bản dịch tiếng Việt'),
(275, 55, 'SHN-VN-005', 'USA-SK-03-005', '2020-11-15', 190000.00, 0, 'Bản dịch tiếng Việt'),

-- Edition 56: Kính vạn hoa (Tập 1)
(276, 56, 'KVH-T1-001', 'VNA-NNA-09-001', '2019-05-15', 35000.00, 0, 'Sách mới nhập kho'),
(277, 56, 'KVH-T1-002', 'VNA-NNA-09-002', '2019-05-15', 35000.00, 0, 'Sách mới nhập kho'),
(278, 56, 'KVH-T1-003', 'VNA-NNA-09-003', '2019-05-15', 35000.00, 0, 'Sách mới nhập kho'),
(279, 56, 'KVH-T1-004', 'VNA-NNA-09-004', '2019-05-15', 35000.00, 0, 'Sách mới nhập kho'),
(280, 56, 'KVH-T1-005', 'VNA-NNA-09-005', '2019-05-15', 35000.00, 0, 'Sách mới nhập kho'),

-- Edition 57: How to Win Friends... (Bản dịch)
(281, 57, 'HWF-VN-001', 'USA-DC-04-001', '2019-01-20', 85000.00, 0, 'Bản dịch tiếng Việt'),
(282, 57, 'HWF-VN-002', 'USA-DC-04-002', '2019-01-20', 85000.00, 0, 'Bản dịch tiếng Việt'),
(283, 57, 'HWF-VN-003', 'USA-DC-04-003', '2019-01-20', 85000.00, 0, 'Bản dịch tiếng Việt'),
(284, 57, 'HWF-VN-004', 'USA-DC-04-004', '2019-01-20', 85000.00, 0, 'Bản dịch tiếng Việt'),
(285, 57, 'HWF-VN-005', 'USA-DC-04-005', '2019-01-20', 85000.00, 0, 'Bản dịch tiếng Việt'),

-- Edition 58: Để yên cho bác sĩ Hiền (Tái bản 2022)
(286, 58, 'DYBSH-2022-001', 'VNA-NNA-10-001', '2022-03-01', 90000.00, 0, 'Sách mới nhập kho'),
(287, 58, 'DYBSH-2022-002', 'VNA-NNA-10-002', '2022-03-01', 90000.00, 0, 'Sách mới nhập kho'),
(288, 58, 'DYBSH-2022-003', 'VNA-NNA-10-003', '2022-03-01', 90000.00, 0, 'Sách mới nhập kho'),
(289, 58, 'DYBSH-2022-004', 'VNA-NNA-10-004', '2022-03-01', 90000.00, 0, 'Sách mới nhập kho'),
(290, 58, 'DYBSH-2022-005', 'VNA-NNA-10-005', '2022-03-01', 90000.00, 0, 'Sách mới nhập kho'),

-- Edition 59: It (Bản dịch - Tập 1)
(291, 59, 'IT-VN-T1-001', 'USA-SK-04-001', '2021-09-20', 180000.00, 0, 'Bản dịch, Tập 1'),
(292, 59, 'IT-VN-T1-002', 'USA-SK-04-002', '2021-09-20', 180000.00, 0, 'Bản dịch, Tập 1'),
(293, 59, 'IT-VN-T1-003', 'USA-SK-04-003', '2021-09-20', 180000.00, 0, 'Bản dịch, Tập 1'),
(294, 59, 'IT-VN-T1-004', 'USA-SK-04-004', '2021-09-20', 180000.00, 0, 'Bản dịch, Tập 1'),
(295, 59, 'IT-VN-T1-005', 'USA-SK-04-005', '2021-09-20', 180000.00, 0, 'Bản dịch, Tập 1'),

-- Edition 60: It (Bản dịch - Tập 2)
(296, 60, 'IT-VN-T2-001', 'USA-SK-05-001', '2021-09-20', 190000.00, 0, 'Bản dịch, Tập 2'),
(297, 60, 'IT-VN-T2-002', 'USA-SK-05-002', '2021-09-20', 190000.00, 0, 'Bản dịch, Tập 2'),
(298, 60, 'IT-VN-T2-003', 'USA-SK-05-003', '2021-09-20', 190000.00, 0, 'Bản dịch, Tập 2'),
(299, 60, 'IT-VN-T2-004', 'USA-SK-05-004', '2021-09-20', 190000.00, 0, 'Bản dịch, Tập 2'),
(300, 60, 'IT-VN-T2-005', 'USA-SK-05-005', '2021-09-20', 190000.00, 0, 'Bản dịch, Tập 2'),

-- Edition 61: Cho tôi xin một vé đi tuổi thơ (Ấn bản kỷ niệm)
(301, 61, 'CTV-KN-001', 'VNA-NNA-11-001', '2020-02-01', 140000.00, 0, 'Ấn bản kỷ niệm, bìa cứng'),
(302, 61, 'CTV-KN-002', 'VNA-NNA-11-002', '2020-02-01', 140000.00, 0, 'Ấn bản kỷ niệm, bìa cứng'),
(303, 61, 'CTV-KN-003', 'VNA-NNA-11-003', '2020-02-01', 140000.00, 0, 'Ấn bản kỷ niệm, bìa cứng'),
(304, 61, 'CTV-KN-004', 'VNA-NNA-11-004', '2020-02-01', 140000.00, 0, 'Ấn bản kỷ niệm, bìa cứng'),
(305, 61, 'CTV-KN-005', 'VNA-NNA-11-005', '2020-02-01', 140000.00, 0, 'Ấn bản kỷ niệm, bìa cứng'),

-- Edition 62: Tắt đèn (Bản đặc biệt)
(306, 62, 'TD-SPECIAL-001', 'VNA-NTT-02-001', '2023-01-20', 165000.00, 0, 'Bản đặc biệt, bìa cứng'),
(307, 62, 'TD-SPECIAL-002', 'VNA-NTT-02-002', '2023-01-20', 165000.00, 0, 'Bản đặc biệt, bìa cứng'),
(308, 62, 'TD-SPECIAL-003', 'VNA-NTT-02-003', '2023-01-20', 165000.00, 0, 'Bản đặc biệt, bìa cứng'),
(309, 62, 'TD-SPECIAL-004', 'VNA-NTT-02-004', '2023-01-20', 165000.00, 0, 'Bản đặc biệt, bìa cứng'),
(310, 62, 'TD-SPECIAL-005', 'VNA-NTT-02-005', '2023-01-20', 165000.00, 0, 'Bản đặc biệt, bìa cứng'),

-- Edition 63: Nhà Giả Kim (Bìa cứng)
(311, 63, 'NGK-BC-001', 'BRA-PC-04-001', '2022-05-15', 170000.00, 0, 'Bìa cứng, sách mới'),
(312, 63, 'NGK-BC-002', 'BRA-PC-04-002', '2022-05-15', 170000.00, 0, 'Bìa cứng, sách mới'),
(313, 63, 'NGK-BC-003', 'BRA-PC-04-003', '2022-05-15', 170000.00, 0, 'Bìa cứng, sách mới'),
(314, 63, 'NGK-BC-004', 'BRA-PC-04-004', '2022-05-15', 170000.00, 0, 'Bìa cứng, sách mới'),
(315, 63, 'NGK-BC-005', 'BRA-PC-04-005', '2022-05-15', 170000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 64: Góc sân và khoảng trời (Tái bản 2021)
(316, 64, 'GSKT-2021-001', 'VNA-TDK-02-001', '2021-02-01', 155000.00, 0, 'Bìa cứng, sách mới'),
(317, 64, 'GSKT-2021-002', 'VNA-TDK-02-002', '2021-02-01', 155000.00, 0, 'Bìa cứng, sách mới'),
(318, 64, 'GSKT-2021-003', 'VNA-TDK-02-003', '2021-02-01', 155000.00, 0, 'Bìa cứng, sách mới'),
(319, 64, 'GSKT-2021-004', 'VNA-TDK-02-004', '2021-02-01', 155000.00, 0, 'Bìa cứng, sách mới'),
(320, 64, 'GSKT-2021-005', 'VNA-TDK-02-005', '2021-02-01', 155000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 65: Lão Hạc (Ấn bản Nhã Nam)
(321, 65, 'LH-NN-001', 'VNA-NC-04-001', '2021-04-20', 70000.00, 0, 'Sách mới nhập kho'),
(322, 65, 'LH-NN-002', 'VNA-NC-04-002', '2021-04-20', 70000.00, 0, 'Sách mới nhập kho'),
(323, 65, 'LH-NN-003', 'VNA-NC-04-003', '2021-04-20', 70000.00, 0, 'Sách mới nhập kho'),
(324, 65, 'LH-NN-004', 'VNA-NC-04-004', '2021-04-20', 70000.00, 0, 'Sách mới nhập kho'),
(325, 65, 'LH-NN-005', 'VNA-NC-04-005', '2021-04-20', 70000.00, 0, 'Sách mới nhập kho'),

-- Edition 66: Truyện Kiều (Bản phổ thông)
(326, 66, 'TK-PT-001', 'VNA-ND-02-001', '2019-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(327, 66, 'TK-PT-002', 'VNA-ND-02-002', '2019-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(328, 66, 'TK-PT-003', 'VNA-ND-02-003', '2019-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(329, 66, 'TK-PT-004', 'VNA-ND-02-004', '2019-02-01', 80000.00, 0, 'Sách mới nhập kho'),
(330, 66, 'TK-PT-005', 'VNA-ND-02-005', '2019-02-01', 80000.00, 0, 'Sách mới nhập kho'),

-- Edition 67: The Alchemist (Bản dịch Nhã Nam)
(331, 67, 'ALCH-NN-001', 'BRA-PC-05-001', '2019-03-15', 88000.00, 0, 'Sách mới nhập kho'),
(332, 67, 'ALCH-NN-002', 'BRA-PC-05-002', '2019-03-15', 88000.00, 0, 'Sách mới nhập kho'),
(333, 67, 'ALCH-NN-003', 'BRA-PC-05-003', '2019-03-15', 88000.00, 0, 'Sách mới nhập kho'),
(334, 67, 'ALCH-NN-004', 'BRA-PC-05-004', '2019-03-15', 88000.00, 0, 'Sách mới nhập kho'),
(335, 67, 'ALCH-NN-005', 'BRA-PC-05-005', '2019-03-15', 88000.00, 0, 'Sách mới nhập kho'),

-- Edition 68: Trại súc vật (Animal Farm)
(336, 68, 'TSV-VN-001', 'ENG-GO-04-001', '2020-06-20', 65000.00, 0, 'Bản dịch tiếng Việt'),
(337, 68, 'TSV-VN-002', 'ENG-GO-04-002', '2020-06-20', 65000.00, 0, 'Bản dịch tiếng Việt'),
(338, 68, 'TSV-VN-003', 'ENG-GO-04-003', '2020-06-20', 65000.00, 0, 'Bản dịch tiếng Việt'),
(339, 68, 'TSV-VN-004', 'ENG-GO-04-004', '2020-06-20', 65000.00, 0, 'Bản dịch tiếng Việt'),
(340, 68, 'TSV-VN-005', 'ENG-GO-04-005', '2020-06-20', 65000.00, 0, 'Bản dịch tiếng Việt'),

-- Edition 69: Những ngày thơ ấu (Tái bản)
(341, 69, 'NNTA-TB-001', 'VNA-NH-02-001', '2022-01-15', 62000.00, 0, 'Sách mới nhập kho'),
(342, 69, 'NNTA-TB-002', 'VNA-NH-02-002', '2022-01-15', 62000.00, 0, 'Sách mới nhập kho'),
(343, 69, 'NNTA-TB-003', 'VNA-NH-02-003', '2022-01-15', 62000.00, 0, 'Sách mới nhập kho'),
(344, 69, 'NNTA-TB-004', 'VNA-NH-02-004', '2022-01-15', 62000.00, 0, 'Sách mới nhập kho'),
(345, 69, 'NNTA-TB-005', 'VNA-NH-02-005', '2022-01-15', 62000.00, 0, 'Sách mới nhập kho'),

-- Edition 70: Dumb Luck
(346, 70, 'DL-PENGUIN-001', 'ENG-VTP-03-001', '2004-02-10', 175000.00, 0, 'New arrival'),
(347, 70, 'DL-PENGUIN-002', 'ENG-VTP-03-002', '2004-02-10', 175000.00, 0, 'New arrival'),
(348, 70, 'DL-PENGUIN-003', 'ENG-VTP-03-003', '2004-02-10', 175000.00, 0, 'New arrival'),
(349, 70, 'DL-PENGUIN-004', 'ENG-VTP-03-004', '2004-02-10', 175000.00, 0, 'New arrival'),
(350, 70, 'DL-PENGUIN-005', 'ENG-VTP-03-005', '2004-02-10', 175000.00, 0, 'New arrival'),

-- Edition 71: Chí Phèo (Bản Trẻ)
(351, 71, 'CP-TRE-001', 'VNA-NC-05-001', '2022-08-15', 55000.00, 0, 'Sách mới nhập kho'),
(352, 71, 'CP-TRE-002', 'VNA-NC-05-002', '2022-08-15', 55000.00, 0, 'Sách mới nhập kho'),
(353, 71, 'CP-TRE-003', 'VNA-NC-05-003', '2022-08-15', 55000.00, 0, 'Sách mới nhập kho'),
(354, 71, 'CP-TRE-004', 'VNA-NC-05-004', '2022-08-15', 55000.00, 0, 'Sách mới nhập kho'),
(355, 71, 'CP-TRE-005', 'VNA-NC-05-005', '2022-08-15', 55000.00, 0, 'Sách mới nhập kho'),

-- Edition 72: Số đỏ (Tái bản Trẻ)
(356, 72, 'SD-TRE-001', 'VNA-VTP-04-001', '2022-09-15', 98000.00, 0, 'Sách mới nhập kho'),
(357, 72, 'SD-TRE-002', 'VNA-VTP-04-002', '2022-09-15', 98000.00, 0, 'Sách mới nhập kho'),
(358, 72, 'SD-TRE-003', 'VNA-VTP-04-003', '2022-09-15', 98000.00, 0, 'Sách mới nhập kho'),
(359, 72, 'SD-TRE-004', 'VNA-VTP-04-004', '2022-09-15', 98000.00, 0, 'Sách mới nhập kho'),
(360, 72, 'SD-TRE-005', 'VNA-VTP-04-005', '2022-09-15', 98000.00, 0, 'Sách mới nhập kho'),

-- Edition 73: Rừng Na Uy (Bìa mềm)
(361, 73, 'RNU-BM-001', 'JPN-HM-03-001', '2022-01-01', 130000.00, 0, 'Bìa mềm, sách mới'),
(362, 73, 'RNU-BM-002', 'JPN-HM-03-002', '2022-01-01', 130000.00, 0, 'Bìa mềm, sách mới'),
(363, 73, 'RNU-BM-003', 'JPN-HM-03-003', '2022-01-01', 130000.00, 0, 'Bìa mềm, sách mới'),
(364, 73, 'RNU-BM-004', 'JPN-HM-03-004', '2022-01-01', 130000.00, 0, 'Bìa mềm, sách mới'),
(365, 73, 'RNU-BM-005', 'JPN-HM-03-005', '2022-01-01', 130000.00, 0, 'Bìa mềm, sách mới'),

-- Edition 74: The Shining (Tái bản 2013)
(366, 74, 'SHN-2013-001', 'USA-SK-06-001', '2013-07-10', 220000.00, 0, 'New arrival'),
(367, 74, 'SHN-2013-002', 'USA-SK-06-002', '2013-07-10', 220000.00, 0, 'New arrival'),
(368, 74, 'SHN-2013-003', 'USA-SK-06-003', '2013-07-10', 220000.00, 0, 'New arrival'),
(369, 74, 'SHN-2013-004', 'USA-SK-06-004', '2013-07-10', 220000.00, 0, 'New arrival'),
(370, 74, 'SHN-2013-005', 'USA-SK-06-005', '2013-07-10', 220000.00, 0, 'New arrival'),

-- Edition 75: Kính vạn hoa (Tập 2)
(371, 75, 'KVH-T2-001', 'VNA-NNA-12-001', '2019-06-15', 35000.00, 0, 'Bìa cứng'),
(372, 75, 'KVH-T2-002', 'VNA-NNA-12-002', '2019-06-15', 35000.00, 0, 'Bìa cứng'),
(373, 75, 'KVH-T2-003', 'VNA-NNA-12-003', '2019-06-15', 35000.00, 0, 'Bìa cứng'),
(374, 75, 'KVH-T2-004', 'VNA-NNA-12-004', '2019-06-15', 35000.00, 0, 'Bìa cứng'),
(375, 75, 'KVH-T2-005', 'VNA-NNA-12-005', '2019-06-15', 35000.00, 0, 'Bìa cứng'),

-- Edition 76: Tuyển truyện ngắn hiện thực Việt Nam (Bìa mềm)
(376, 76, 'TTNHT-BM-001', 'VNA-MULTI-07-001', '2018-03-01', 110000.00, 0, 'Bìa mềm, sách mới'),
(377, 76, 'TTNHT-BM-002', 'VNA-MULTI-07-002', '2018-03-01', 110000.00, 0, 'Bìa mềm, sách mới'),
(378, 76, 'TTNHT-BM-003', 'VNA-MULTI-07-003', '2018-03-01', 110000.00, 0, 'Bìa mềm, sách mới'),
(379, 76, 'TTNHT-BM-004', 'VNA-MULTI-07-004', '2018-03-01', 110000.00, 0, 'Bìa mềm, sách mới'),
(380, 76, 'TTNHT-BM-005', 'VNA-MULTI-07-005', '2018-03-01', 110000.00, 0, 'Bìa mềm, sách mới'),

-- Edition 77: Tuyển tập tiểu thuyết kinh dị (Bản minh họa)
(381, 77, 'TTTKD-MH-001', 'MULTI-HOR-02-001', '2019-11-15', 180000.00, 0, 'Bản minh họa, bìa cứng'),
(382, 77, 'TTTKD-MH-002', 'MULTI-HOR-02-002', '2019-11-15', 180000.00, 0, 'Bản minh họa, bìa cứng'),
(383, 77, 'TTTKD-MH-003', 'MULTI-HOR-02-003', '2019-11-15', 180000.00, 0, 'Bản minh họa, bìa cứng'),
(384, 77, 'TTTKD-MH-004', 'MULTI-HOR-02-004', '2019-11-15', 180000.00, 0, 'Bản minh họa, bìa cứng'),
(385, 77, 'TTTKD-MH-005', 'MULTI-HOR-02-005', '2019-11-15', 180000.00, 0, 'Bản minh họa, bìa cứng'),

-- Edition 78: Tuyển truyện cho tuổi mới lớn (Tái bản 2021)
(386, 78, 'TTTML-2021-001', 'VNA-MULTI-08-001', '2021-03-01', 260000.00, 0, 'Bìa cứng, sách mới'),
(387, 78, 'TTTML-2021-002', 'VNA-MULTI-08-002', '2021-03-01', 260000.00, 0, 'Bìa cứng, sách mới'),
(388, 78, 'TTTML-2021-003', 'VNA-MULTI-08-003', '2021-03-01', 260000.00, 0, 'Bìa cứng, sách mới'),
(389, 78, 'TTTML-2021-004', 'VNA-MULTI-08-004', '2021-03-01', 260000.00, 0, 'Bìa cứng, sách mới'),
(390, 78, 'TTTML-2021-005', 'VNA-MULTI-08-005', '2021-03-01', 260000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 79: Việt Bắc (Tái bản 2020)
(391, 79, 'VB-2020-001', 'VNA-TH-02-001', '2020-02-01', 120000.00, 0, 'Bìa cứng, sách mới'),
(392, 79, 'VB-2020-002', 'VNA-TH-02-002', '2020-02-01', 120000.00, 0, 'Bìa cứng, sách mới'),
(393, 79, 'VB-2020-003', 'VNA-TH-02-003', '2020-02-01', 120000.00, 0, 'Bìa cứng, sách mới'),
(394, 79, 'VB-2020-004', 'VNA-TH-02-004', '2020-02-01', 120000.00, 0, 'Bìa cứng, sách mới'),
(395, 79, 'VB-2020-005', 'VNA-TH-02-005', '2020-02-01', 120000.00, 0, 'Bìa cứng, sách mới'),

-- Edition 80: Tuyển truyện tình tuổi học trò (Bản đặc biệt)
(396, 80, 'TTTTHT-DB-001', 'VNA-MULTI-09-001', '2017-08-01', 130000.00, 0, 'Sách mới nhập kho'),
(397, 80, 'TTTTHT-DB-002', 'VNA-MULTI-09-002', '2017-08-01', 130000.00, 0, 'Sách mới nhập kho'),
(398, 80, 'TTTTHT-DB-003', 'VNA-MULTI-09-003', '2017-08-01', 130000.00, 0, 'Sách mới nhập kho'),
(399, 80, 'TTTTHT-DB-004', 'VNA-MULTI-09-004', '2017-08-01', 130000.00, 0, 'Sách mới nhập kho'),
(400, 80, 'TTTTHT-DB-005', 'VNA-MULTI-09-005', '2017-08-01', 130000.00, 0, 'Sách mới nhập kho');


-- =================================================================
-- BẢNG 11: follows (Theo dõi Tác giả/NXB/Sách)
-- target_type: AUTHOR(0), PUBLISHER(1), EDITION(2)
-- =================================================================
INSERT INTO `follows` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES
-- Theo dõi tác giả (target_type = 0)
(1, 3, 1, 0, NOW()),       -- Lê Minh Cường (user 3) theo dõi Nguyễn Nhật Ánh (author 1)
(2, 4, 1, 0, NOW()),       -- Phạm Thị Dung (user 4) cũng theo dõi Nguyễn Nhật Ánh
(3, 5, 5, 0, NOW()),       -- Wang ChuQin (user 5) theo dõi J.K. Rowling (author 5)
(4, 6, 5, 0, NOW()),       -- Sun YingSha (user 6) cũng theo dõi J.K. Rowling
(5, 7, 14, 0, NOW()),      -- John Doe (user 7) theo dõi Stephen King (author 14)
(6, 8, 10, 0, NOW()),      -- Jane Smith (user 8) theo dõi Agatha Christie (author 10)
(7, 5, 6, 0, NOW()),       -- Wang ChuQin (user 5) cũng theo dõi Haruki Murakami (author 6)
(8, 9, 12, 0, NOW()),      -- Vũ Anh Tuấn (user 9) theo dõi Nam Cao (author 12)
(9, 11, 15, 0, NOW()),     -- Kim Jisoo (user 11) theo dõi Yuval Noah Harari (author 15)
(10, 13, 13, 0, NOW()),    -- Trịnh Văn Sơn (user 13) theo dõi đại thi hào Nguyễn Du (author 13)
(11, 20, 7, 0, NOW()),     -- Roseanne Park (user 20) theo dõi Paulo Coelho (author 7)
(12, 15, 8, 0, NOW()),     -- Bùi Tiến Dũng (user 15) theo dõi Dale Carnegie (author 8)
(13, 7, 9, 0, NOW()),      -- John Doe (user 7) cũng theo dõi George Orwell (author 9)

-- Theo dõi nhà xuất bản (target_type = 1)
(14, 2, 1, 1, NOW()),      -- Trần Thị Bình (user 2) theo dõi NXB Trẻ (publisher 1)
(15, 3, 2, 1, NOW()),      -- Lê Minh Cường (user 3) theo dõi NXB Kim Đồng (publisher 2)
(16, 10, 3, 1, NOW()),     -- Đỗ Mỹ Linh (user 10) theo dõi Nhã Nam (publisher 3)
(17, 7, 5, 1, NOW()),      -- John Doe (user 7) theo dõi Penguin Random House (publisher 5)
(18, 11, 9, 1, NOW()),     -- Kim Jisoo (user 11) theo dõi Bloomsbury (NXB của Harry Potter) (publisher 9)
(19, 15, 4, 1, NOW()),     -- Bùi Tiến Dũng (user 15) theo dõi Alphabooks (publisher 4)
(20, 13, 7, 1, NOW()),     -- Trịnh Văn Sơn (user 13) theo dõi NXB Hội Nhà văn (publisher 7)
(21, 6, 3, 1, NOW()),      -- Sun YingSha (user 6) cũng theo dõi Nhã Nam

-- Theo dõi một ấn bản cụ thể (target_type = 2)
(22, 14, 7, 2, NOW()),     -- Kim Jennie (user 14) theo dõi bản Harry Potter minh họa (edition 7)
(23, 11, 7, 2, NOW()),     -- Kim Jisoo (user 11) cũng theo dõi bản Harry Potter minh họa (edition 7)
(24, 13, 16, 2, NOW()),    -- Trịnh Văn Sơn (user 13) theo dõi bản Truyện Kiều kỷ niệm (edition 16)
(25, 4, 61, 2, NOW()),     -- Phạm Thị Dung (user 4) theo dõi bản kỷ niệm của 'Cho tôi xin một vé...' (edition 61)
(26, 7, 23, 2, NOW()),     -- John Doe (user 7) theo dõi bản Animal Farm kỷ niệm 75 năm (edition 23)
(27, 2, 48, 2, NOW());     -- Trần Thị Bình (user 2) theo dõi bản Dế Mèn có minh họa mới (edition 48)

-- =================================================================
-- BẢNG 12: borrowing_receipts 
-- status: PENDING(0), APPROVED(1), BORROWED(2), REJECTED(3), RETURNED(4), OVERDUE(5), LOST_REPORTED(6), CANCELLED(7)
-- =================================================================
INSERT INTO `borrowing_receipts` (`id`, `user_id`, `borrowed_date`, `due_date`, `status`, `rejected_reason`, `created_at`, `updated_at`) VALUES
-- 1. Phiếu đã hoàn thành (RETURNED)
(1, 3, '2025-07-10 00:00:00', '2025-07-24 00:00:00', 4, NULL, '2025-07-10 09:10:00', '2025-07-23 17:00:00'),
(2, 4, '2025-07-15 00:00:00', '2025-07-29 00:00:00', 4, NULL, '2025-07-15 14:25:00', '2025-07-28 11:20:00'),
(3, 7, '2025-06-20 00:00:00', '2025-07-04 00:00:00', 4, NULL, '2025-06-20 10:55:00', '2025-07-03 16:45:00'),
(4, 10, '2025-08-01 00:00:00', '2025-08-15 00:00:00', 4, NULL, '2025-08-01 09:50:00', '2025-08-12 09:30:00'),

-- 2. Phiếu đang mượn, chưa đến hạn (BORROWED)
(5, 5, '2025-08-05 00:00:00', '2025-09-19 00:00:00', 2, NULL, '2025-08-05 14:55:00', '2025-08-05 15:00:00'),
(6, 6, '2025-08-10 00:00:00', '2025-09-24 00:00:00', 2, NULL, '2025-08-10 15:50:00', '2025-08-10 16:00:00'),
(7, 11, '2025-08-12 00:00:00', '2025-09-26 00:00:00', 2, NULL, '2025-08-12 11:25:00', '2025-08-12 11:30:00'),

-- 3. Phiếu đã quá hạn (OVERDUE)
(8, 7, '2025-07-20 00:00:00', '2025-08-03 00:00:00', 5, NULL, '2025-07-20 10:15:00', '2025-08-04 00:00:01'),
(9, 19, '2025-07-25 00:00:00', '2025-08-08 00:00:00', 5, NULL, '2025-07-25 08:55:00', '2025-08-09 00:00:01'),

-- 4. Phiếu đang chờ duyệt (PENDING)
(10, 9, '2025-08-13 00:00:00', '2025-09-27 00:00:00', 0, NULL, '2025-08-12 20:00:00', '2025-08-12 20:00:00'),
(11, 16, '2025-08-14 00:00:00', '2025-09-28 00:00:00', 0, NULL, '2025-08-13 10:00:00', '2025-08-13 10:00:00'),

-- 5. Phiếu đã được duyệt, chờ lấy sách (APPROVED)
(12, 14, '2025-08-14 00:00:00', '2025-08-28 00:00:00', 1, NULL, '2025-08-12 18:00:00', '2025-08-13 11:00:00'),

-- 6. Phiếu bị từ chối (REJECTED)
(13, 8, '2025-08-12 00:00:00', '2025-08-26 00:00:00', 3, 'Bạn đang có sách mượn đã quá hạn. Vui lòng hoàn trả trước khi mượn sách mới.', '2025-08-11 14:00:00', '2025-08-11 14:05:00'),
(14, 2, '2025-08-14 00:00:00', '2025-08-28 00:00:00', 3, 'Quyển sách "Dế Mèn phiêu lưu ký" (barcode DM2020-001) bạn yêu cầu đã được phát hiện bị hỏng và đang chờ sửa chữa.', '2025-08-13 09:10:00', '2025-08-13 09:15:00'),

-- 7. Phiếu đã được người dùng hủy (CANCELLED)
(15, 17, '2025-08-13 00:00:00', '2025-08-27 00:00:00', 7, NULL, '2025-08-12 15:00:00', '2025-08-12 15:30:00'),

-- 8. Phiếu có sách bị báo mất (LOST_REPORTED)
(16, 12, '2025-07-30 00:00:00', '2025-08-13 00:00:00', 6, NULL, '2025-07-30 12:55:00', '2025-08-11 10:00:00');

-- =================================================================
-- BẢNG 13: borrowing_details (Chi tiết các quyển sách trong phiếu mượn)
-- =================================================================
INSERT INTO `borrowing_details` (`id`, `borrowing_receipt_id`, `book_instance_id`, `refund_date`) VALUES
-- Phiếu 1 (user 3, RETURNED)
(1, 1, 16, '2025-07-23 16:55:00'),  -- Số đỏ

-- Phiếu 2 (user 4, RETURNED)
(2, 2, 11, '2025-07-28 11:15:00'),  -- Cho tôi xin một vé đi tuổi thơ
(3, 2, 91, '2025-07-28 11:15:00'),  -- Tôi thấy hoa vàng trên cỏ xanh

-- Phiếu 3 (user 7, RETURNED)
(4, 3, 26, '2025-07-03 16:40:00'),  -- Harry Potter and the Sorcerer's Stone
(5, 3, 51, '2025-07-03 16:40:00'),  -- 1984

-- Phiếu 4 (user 10, RETURNED)
(6, 4, 36, '2025-08-12 09:25:00'),  -- Rừng Na Uy

-- Phiếu 5 (user 5, BORROWED)
(7, 5, 31, NULL),                  -- Harry Potter (Illustrated Edition)

-- Phiếu 6 (user 6, BORROWED)
(8, 6, 246, NULL),                 -- Harry Potter (School Market Edition)

-- Phiếu 7 (user 11, BORROWED)
(9, 7, 81, NULL),                   -- Sapiens: Lược sử loài người
(10, 7, 191, NULL),                 -- Kiêu hãnh và định kiến

-- Phiếu 8 (user 7, OVERDUE)
(11, 8, 86, NULL),                  -- The Shining

-- Phiếu 9 (user 19, OVERDUE)
(12, 9, 46, NULL),                  -- Đắc Nhân Tâm
(13, 9, 101, NULL),                 -- How to Win Friends and Influence People

-- Phiếu 10 (user 9, PENDING)
(14, 10, 66, NULL),                 -- Lão Hạc
(15, 10, 71, NULL),                 -- Chí Phèo

-- Phiếu 11 (user 16, PENDING)
(16, 11, 116, NULL),                -- Tuyển truyện ngắn hiện thực Việt Nam

-- Phiếu 12 (user 14, APPROVED)
(17, 12, 226, NULL),                -- Mắt biếc (Tái bản 2021)

-- Phiếu 13 (user 8, REJECTED)
(18, 13, 106, NULL),                -- The Alchemist

-- Phiếu 14 (user 2, REJECTED)
(19, 14, 1, NULL),                  -- Dế Mèn phiêu lưu ký (Tái bản 2020)

-- Phiếu 15 (user 17, CANCELLED)
(20, 15, 151, NULL),                -- Để yên cho bác sĩ Hiền

-- Phiếu 16 (user 12, LOST_REPORTED)
(21, 16, 171, NULL),                -- Người lái đò sông Đà
(22, 16, 166, NULL);                -- Vợ chồng A Phủ

-- =================================================================
-- BẢNG 14: receipt_fines (Chi tiết các khoản phạt)
-- =================================================================
INSERT INTO `receipt_fines` (`id`, `borrowing_receipt_id`, `book_instance_id`, `fine_id`, `amount`, `fine_note`) VALUES
-- Phạt cho Phiếu mượn #8 (user 7, OVERDUE)
-- Sách quá hạn là book_instance_id = 86 (The Shining)
(1, 8, 86, 1, 50000.00, 'Phạt trả sách trễ hạn. Hạn trả: 2025-08-03.'),

-- Phạt cho Phiếu mượn #9 (user 19, OVERDUE)
-- Sách quá hạn là book_instance_id = 46 (Đắc Nhân Tâm)
(2, 9, 46, 1, 50000.00, 'Phạt trả sách trễ hạn. Hạn trả: 2025-08-08.'),
-- Sách quá hạn là book_instance_id = 101 (How to Win Friends...)
(3, 9, 101, 1, 50000.00, 'Phạt trả sách trễ hạn. Hạn trả: 2025-08-08.'),

-- Phạt cho Phiếu mượn #16 (user 12, LOST_REPORTED)
-- Giả sử user báo mất quyển 'Người lái đò sông Đà' (book_instance_id = 171)
-- Phạt sẽ bao gồm phí phạt cố định + giá trị quyển sách (78,000)
(4, 16, 171, 3, 578000.00, 'Phạt làm mất sách. Bao gồm phí phạt mất sách (500,000) và giá bìa sách (78,000).'),
-- Giả sử quyển còn lại 'Vợ chồng A Phủ' (book_instance_id = 166) bị hư hỏng khi trả lại
(5, 16, 166, 2, 100000.00, 'Phạt làm hỏng sách (bìa bị ướt và cong vênh).');

-- CẬP NHẬT LẠI STATUS SÁCH VẬT LÝ VÀ SỐ LƯỢNG SẴN SÀNG CỦA CÁC ẤN BẢN

-- =================================================================
-- BƯỚC 1: CẬP NHẬT TRẠNG THÁI CỦA CÁC QUYỂN SÁCH VẬT LÝ
-- BookStatus: AVAILABLE(0), BORROWED(1), RESERVED(2), LOST(3), DAMAGED(4), REPAIRING(5)
-- =================================================================

-- a. Cập nhật các sách đang được MƯỢN (BORROWED) hoặc QUÁ HẠN (OVERDUE)
-- Trạng thái phiếu: BORROWED(2), OVERDUE(5) -> Trạng thái sách: BORROWED(1)
UPDATE `book_instances`
SET `status` = 1 -- BORROWED
WHERE `id` IN (
    -- Liệt kê các book_instance_id từ các phiếu đang mượn và quá hạn
    SELECT `book_instance_id` FROM `borrowing_details` WHERE `borrowing_receipt_id` IN (5, 6, 7, 8, 9)
);

-- b. Cập nhật các sách đang trong trạng thái CHỜ DUYỆT (PENDING) hoặc ĐÃ DUYỆT (APPROVED)
-- Trạng thái phiếu: PENDING(0), APPROVED(1) -> Trạng thái sách: RESERVED(2)
UPDATE `book_instances`
SET `status` = 2 -- RESERVED
WHERE `id` IN (
    -- Liệt kê các book_instance_id từ các phiếu chờ duyệt hoặc đã duyệt
    SELECT `book_instance_id` FROM `borrowing_details` WHERE `borrowing_receipt_id` IN (10, 11, 12)
);

-- c. Cập nhật các sách đã bị BÁO MẤT (LOST)
-- Dựa vào receipt_fines có fine_id = 3 (LOST)
UPDATE `book_instances`
SET `status` = 3 -- LOST
WHERE `id` IN (
    SELECT `book_instance_id` FROM `receipt_fines` WHERE `fine_id` = 3
); -- Cụ thể là book_instance_id = 171

-- d. Cập nhật các sách đã bị HƯ HỎNG (DAMAGED)
-- Dựa vào receipt_fines có fine_id = 2 (DAMAGED)
UPDATE `book_instances`
SET `status` = 4 -- DAMAGED
WHERE `id` IN (
    SELECT `book_instance_id` FROM `receipt_fines` WHERE `fine_id` = 2
); -- Cụ thể là book_instance_id = 166

-- e. Cập nhật sách đang được SỬA CHỮA (REPAIRING)
-- Dựa vào lý do từ chối của phiếu mượn #14
UPDATE `book_instances`
SET `status` = 5 -- REPAIRING
WHERE `id` = 1; -- Quyển 'Dế Mèn phiêu lưu ký' (barcode DM2020-001)

-- f. Cập nhật các sách đã được TRẢ LẠI (RETURNED) hoặc giao dịch bị HỦY/TỪ CHỐI
-- Trạng thái phiếu: RETURNED(4), REJECTED(3), CANCELLED(7) -> Trạng thái sách: AVAILABLE(0)
-- LƯU Ý: Phải loại trừ các sách đã bị xử lý ở các bước trên (mất, hỏng)
UPDATE `book_instances`
SET `status` = 0 -- AVAILABLE
WHERE `id` IN (
    SELECT `book_instance_id` FROM `borrowing_details` WHERE `borrowing_receipt_id` IN (1, 2, 3, 4, 13, 15)
);

-- =================================================================
-- BƯỚC 2: CẬP NHẬT LẠI SỐ LƯỢNG KHẢ DỤNG CỦA CÁC ẤN BẢN
-- =================================================================

UPDATE `editions` e
LEFT JOIN (
    -- Đếm số lượng sách KHÔNG KHẢ DỤNG (status != 0) cho mỗi edition
    SELECT 
        `edition_id`, 
        COUNT(`id`) AS unavailable_count
    FROM `book_instances`
    WHERE `status` != 0 -- Bất kỳ trạng thái nào khác AVAILABLE
    GROUP BY `edition_id`
) AS bi_counts ON e.id = bi_counts.edition_id
SET 
    e.available_quantity = e.initial_quantity - IFNULL(bi_counts.unavailable_count, 0);
    
-- =================================================================
-- BẢNG 15: ratings (Đánh giá của người dùng cho các ấn bản đã trả)
-- Chỉ những user đã mượn và trả sách mới có thể đánh giá.
-- =================================================================
INSERT INTO `ratings` (`id`, `user_id`, `edition_id`, `rate`, `created_at`, `updated_at`) VALUES
-- Đánh giá từ User 3 cho phiếu mượn #1
(1, 3, 4, 5, '2025-07-24 10:00:00', '2025-07-24 10:00:00'),  -- User 3 đánh giá 5 sao cho Edition 4 (Số đỏ)

-- Đánh giá từ User 4 cho phiếu mượn #2
(2, 4, 3, 5, '2025-07-29 09:00:00', '2025-07-29 09:00:00'),   -- User 4 đánh giá 5 sao cho Edition 3 (Cho tôi xin một vé...)
(3, 4, 19, 4, '2025-07-29 09:05:00', '2025-07-29 09:05:00'), -- User 4 đánh giá 4 sao cho Edition 19 (Tôi thấy hoa vàng...)

-- Đánh giá từ User 7 cho phiếu mượn #3
(4, 7, 6, 5, '2025-07-04 11:00:00', '2025-07-04 11:00:00'),   -- User 7 đánh giá 5 sao cho Edition 6 (Harry Potter)
(5, 7, 11, 5, '2025-07-04 11:02:00', '2025-07-04 11:02:00'),  -- User 7 cũng đánh giá 5 sao cho Edition 11 (1984)

-- Đánh giá từ User 10 cho phiếu mượn #4
(6, 10, 8, 4, '2025-08-12 14:00:00', '2025-08-12 14:00:00'); -- User 10 đánh giá 4 sao cho Edition 8 (Rừng Na Uy)

-- =================================================================
-- BẢNG 16: comments (Bình luận của người dùng cho các ấn bản đã trả)
-- =================================================================
INSERT INTO `comments` (`id`, `user_id`, `edition_id`, `content`, `created_at`, `updated_at`) VALUES
-- Bình luận từ User 3 (tương ứng rating #1)
(1, 3, 4, 'Một tác phẩm châm biếm sâu sắc, bất hủ của văn học Việt Nam. Đọc rất cuốn!', '2025-07-24 10:01:00', '2025-07-24 10:01:00'),

-- Bình luận từ User 4 (tương ứng rating #2)
(2, 4, 3, 'Cuốn sách này thực sự là một tấm vé về tuổi thơ. Nhẹ nhàng, hài hước và đầy ý nghĩa.', '2025-07-29 09:02:00', '2025-07-29 09:02:00'),
-- User 4 đánh giá Edition 19 nhưng không để lại bình luận.

-- Bình luận từ User 7 (tương ứng rating #4 và #5)
(3, 7, 6, 'An absolute classic. The world-building is incredible. A must-read for all ages.', '2025-07-04 11:01:00', '2025-07-04 11:01:00'),
(4, 7, 11, 'Chilling, thought-provoking, and more relevant than ever. A masterpiece that stays with you long after you finish it.', '2025-07-04 11:03:00', '2025-07-04 11:03:00'),

-- Bình luận từ User 10 (tương ứng rating #6)
(5, 10, 8, 'Câu chuyện buồn nhưng đẹp. Murakami có lối viết rất đặc biệt, đầy ám ảnh.', '2025-08-12 14:00:30', '2025-08-12 14:00:30');
