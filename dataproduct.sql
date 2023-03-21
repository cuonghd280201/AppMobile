-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 21, 2023 lúc 06:49 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dataproduct`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `newproduct`
--

CREATE TABLE `newproduct` (
  `id` int(11) NOT NULL,
  `productname` varchar(250) NOT NULL,
  `image` text NOT NULL,
  `description` text NOT NULL,
  `type` int(2) NOT NULL,
  `price` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `newproduct`
--

INSERT INTO `newproduct` (`id`, `productname`, `image`, `description`, `type`, `price`) VALUES
(2, 'Fig cake', 'https://static01.nyt.com/images/2013/08/21/dining/21KITCHEN1_SPAN/21KITCHEN1_SPAN-articleLarge.jpg', 'Good', 2, '200000'),
(3, 'Cake Lemon', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_XqVxkZlbvVXxranTU878RUp4V6pl35_Zbg&usqp=CAU', '', 2, '100000'),
(4, 'Raspberry Tiramisu', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_XqVxkZlbvVXxranTU878RUp4V6pl35_Zbg&usqp=CAU', 'berries, rose & berry jelly.', 2, '25000'),
(6, 'Caramel Mouse', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_XqVxkZlbvVXxranTU878RUp4V6pl35_Zbg&usqp=CAU', 'Layers of lady finger i', 2, '40000'),
(7, 'Sapodilla saint', 'https://cdn.shopify.com/s/files/1/2675/2320/products/SAPODILLACARAMELSAINTHONORE_01_360x.jpg?v=1678786402', 'Caramel choux, bitter cara', 2, '90000'),
(8, 'Caramel Bakes Croissant', 'https://e7.pngegg.com/pngimages/52/689/png-clipart-birthday-cake-wedding-cake-chocolate-cake-strawberry-cream-cake-black-forest-gateau-cake-coffee-birthday-cake-wedding-cake.png', 'Caramel cream & bitter caramel', 2, '89000'),
(9, 'Caramel Mouse', 'https://cdn.shopify.com/s/files/1/2675/2320/products/CARAMEL4INCH.jpg?format=pjpg&v=1638370177https://i0.wp.com/baketotheroots.de/wp-content/uploads/2022/04/HK_220314_Salted-Caramel-Chocolate-Croissants3.jpg?resize=700%2C1050&ssl=1', 'Chocolate and caramel\n\n', 2, '390000'),
(10, 'Raspberry Tiramisu I 12cm\r\n', 'https://cdn.shopify.com/s/files/1/2675/2320/products/SAPODILLACARAMELSAINTHONORE_01_360x.jpg?v=1678786402', 'Raspberry-infused ', 2, '350000'),
(11, 'Matcha mochi cake\r\n', 'https://i.pinimg.com/originals/ee/af/97/eeaf9709ae4e6db000850a7ca41ac902.png', 'Chocolate and milk\n', 2, '65000'),
(15, 'Bireafa', 'box1.jpg', 'Cakes for birthdays, weddings', 2, '240000'),
(16, 'Pina Colada', 'c9.png', 'Compote made with poached pineapple', 2, '160000'),
(17, 'Classic Colada', 'box3.jpg', 'Layers of tiramisu cream', 2, '550000'),
(18, 'Raspherry Cake', 'o6.png', 'Himalayan salt Dalat raspberries.', 2, '120000'),
(19, 'Raspherry Cake', 'o6.png', 'Himalayan salt Dalat raspberries.', 2, '120000'),
(20, 'Raspherry Cake', 'o6.png', 'Himalayan salt Dalat raspberries.', 2, '120000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `quality` int(11) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `total` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order`
--

INSERT INTO `order` (`id`, `iduser`, `email`, `address`, `quality`, `phone`, `total`) VALUES
(7, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(8, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(9, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(10, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(11, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(12, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(13, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(14, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(15, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(16, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(17, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(18, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(19, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(20, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(21, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(22, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(23, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(24, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(25, 3, 'huong@gmail.com', 'HCM', 5, '0915641370', '25000'),
(26, 3, 'huong@gmail.com', 'Ha Noi', 1, '0966633230', '40000'),
(27, 3, 'huong@gmail.com', 'Ha Noi', 1, '0966633230', '40000'),
(28, 3, 'huong@gmail.com', 'Dak Lak', 1, '0966633230', '40000'),
(29, 3, 'huong@gmail.com', 'HN', 1, '0966633230', '40000'),
(30, 3, 'huong@gmail.com', '66/55 Dien Bien Phu', 2, '0966633230', '130000'),
(31, 3, 'huong@gmail.com', '66/11 Hem 65', 1, '0966633230', '390000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderdetail`
--

CREATE TABLE `orderdetail` (
  `idorder` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `quality` int(11) NOT NULL,
  `price` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orderdetail`
--

INSERT INTO `orderdetail` (`idorder`, `id`, `quality`, `price`) VALUES
(25, 6, 2, '40000'),
(26, 6, 1, '40000'),
(27, 6, 1, '40000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `productname` varchar(100) NOT NULL,
  `image` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `productname`, `image`) VALUES
(1, 'Home', 'https://cdn-icons-png.flaticon.com/512/25/25694.png'),
(2, 'Cake', 'https://natashaskitchen.com/wp-content/uploads/2020/05/Vanilla-Cupcakes-3.jpg'),
(3, 'Logout', 'https://cdn-icons-png.flaticon.com/512/182/182448.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `username` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `username`, `phone`, `type`) VALUES
(1, 'cuong@gmail.com', '123', 'duycuong', '0966633211', ''),
(2, 'cuong@gmail.com', '123', 'duycuong', '0966633211', ''),
(3, 'huong@gmail.com', '12345', 'thanhhuong', '0966633230', ''),
(4, 'hu@gmail.com', '12', '093213123', 'huy hoang', ''),
(5, 'duycuong@gmail.com', '12', '0913123123', 'duycuong', ''),
(6, 'duy@gmail.com', '123', '09146313211', 'Duy Trinh', ''),
(7, 'thuong@gmail.com', '123', '093213213', 'thuong', ''),
(8, 'cuong1@gmail.com', '12', '0933231', 'hoangcuong', ''),
(9, 'khai@gmail.com', '123', '09312321', 'khaihoan', ''),
(10, 'thuhuong@gmail.com', '12', '09923213', 'ThuHuong', ''),
(11, 'phongnha@gmail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'PhongNha', '0921321321', 'admin'),
(12, 'admin@gmail.com', 'c4ca4238a0b923820dcc509a6f75849b', 'Admin', '0988333311', 'admin');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `newproduct`
--
ALTER TABLE `newproduct`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `newproduct`
--
ALTER TABLE `newproduct`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
