-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2019 at 09:17 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `environment`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(20) NOT NULL,
  `officeNumber` int(7) NOT NULL,
  `name` varchar(70) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `officeNumber`, `name`, `password`) VALUES
(1, 5138, 'cyprian', '52c69e3a57331081823331c4e69d3f2e');

-- --------------------------------------------------------

--
-- Table structure for table `kfs_report`
--

CREATE TABLE `kfs_report` (
  `report_id` int(20) NOT NULL,
  `photo_name` varchar(20) NOT NULL,
  `photo_url` varchar(100) NOT NULL,
  `activity` varchar(100) NOT NULL,
  `county` varchar(50) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=REDUNDANT;

--
-- Dumping data for table `kfs_report`
--

INSERT INTO `kfs_report` (`report_id`, `photo_name`, `photo_url`, `activity`, `county`, `latitude`, `longitude`) VALUES
(1, '1.jpg', 'http://localhost/webapp/kfmUploads/1.jpg', 'charcoal', 'lamu', '0.0315592', '36.2818197'),
(2, '2.jpg', 'http://localhost/webapp/kfmUploads/2.jpg', 'charcoal', 'Lamu', '0.0306726', '36.280878'),
(3, '3.jpg', 'http://localhost/webapp/kfmUploads/3.jpg', 'charcoal', 'Lamu', '0.0306726', '36.280878');

-- --------------------------------------------------------

--
-- Table structure for table `municiple_report`
--

CREATE TABLE `municiple_report` (
  `report_id` int(20) NOT NULL,
  `photo_name` varchar(100) NOT NULL,
  `photo_url` varchar(100) NOT NULL,
  `activity` varchar(50) NOT NULL,
  `county` varchar(50) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=REDUNDANT;

--
-- Dumping data for table `municiple_report`
--

INSERT INTO `municiple_report` (`report_id`, `photo_name`, `photo_url`, `activity`, `county`, `latitude`, `longitude`) VALUES
(1, '1.jpg', 'http://localhost/webapp/mUploads/1.jpg', 'plastic', 'Laikipia', '0.0297323', '36.2798614'),
(2, '2.jpg', 'http://localhost/webapp/mUploads/2.jpg', 'litter', 'Narok', '0.0295764', '36.279198'),
(3, '2.jpg', 'http://localhost/webapp/mUploads/2.jpg', 'litter', 'Narok', '0.0295764', '36.279198'),
(4, '4.jpg', 'http://localhost/webapp/mUploads/4.jpg', 'litter', 'Narok', '0.0295764', '36.279198'),
(5, '5.jpg', 'http://localhost/webapp/mUploads/5.jpg', 'litter', 'Narok', '0.0295764', '36.279198'),
(6, '6.jpg', 'http://localhost/webapp/mUploads/6.jpg', 'littering', 'Lamu', '0.0306726', '36.280878');

-- --------------------------------------------------------

--
-- Table structure for table `nema_image`
--

CREATE TABLE `nema_image` (
  `report_id` int(20) NOT NULL,
  `photo_name` varchar(50) NOT NULL,
  `photo_url` varchar(50) NOT NULL,
  `activity` varchar(50) NOT NULL,
  `county` varchar(50) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=REDUNDANT;

--
-- Dumping data for table `nema_image`
--

INSERT INTO `nema_image` (`report_id`, `photo_name`, `photo_url`, `activity`, `county`, `latitude`, `longitude`) VALUES
(1, '1.jpg', 'http://localhost/webapp/uploads/1.jpg', 'coding', 'Lamu', '0.0280884', '36.2741001'),
(2, '2.jpg', 'http://localhost/webapp/uploads/2.jpg', 'lettering', 'Mombasa', '0.0280884', '36.2741001'),
(3, '3.jpg', 'http://localhost/webapp/uploads/3.jpg', 'electronic', 'Lamu', '0.0280884', '36.2741001'),
(4, '4.jpg', 'http://localhost/webapp/uploads/4.jpg', 'laravel', 'Lamu', '0.0280884', '36.2741001'),
(5, '5.jpg', 'http://localhost/webapp/uploads/5.jpg', 'kotlin', 'Lamu', '0.0280884', '36.2741001'),
(6, '6.jpg', 'http://localhost/webapp/uploads/6.jpg', 'Pollution', 'Lamu', '0.035285', '36.2711451'),
(7, '7.jpg', 'http://localhost/webapp/uploads/7.jpg', 'Pollution', 'Lamu', '0.035285', '36.2711451'),
(8, '8.jpg', 'http://localhost/webapp/uploads/8.jpg', 'Pollution', 'Lamu', '0.035285', '36.2711451'),
(9, '9.jpg', 'http://localhost/webapp/uploads/9.jpg', 'noise', 'Lamu', '0.0294973', '36.2795225'),
(10, '10.jpg', 'http://localhost/webapp/uploads/10.jpg', 'noise', 'Lamu', '0.0294973', '36.2795225'),
(11, '11.jpg', 'http://localhost/webapp/uploads/11.jpg', 'hello', 'Kisumu', '0.0306726', '36.280878');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `id` int(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=REDUNDANT;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`id`, `username`, `password`) VALUES
(1, 'manyatta', '52c69e3a57331081823331c4e69d3f2e'),
(2, 'Msangi', 'e10adc3949ba59abbe56e057f20f883e');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `oNumber` (`officeNumber`);

--
-- Indexes for table `kfs_report`
--
ALTER TABLE `kfs_report`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `municiple_report`
--
ALTER TABLE `municiple_report`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `nema_image`
--
ALTER TABLE `nema_image`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `kfs_report`
--
ALTER TABLE `kfs_report`
  MODIFY `report_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `municiple_report`
--
ALTER TABLE `municiple_report`
  MODIFY `report_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `nema_image`
--
ALTER TABLE `nema_image`
  MODIFY `report_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user_info`
--
ALTER TABLE `user_info`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
