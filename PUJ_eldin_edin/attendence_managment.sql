-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:4306
-- Generation Time: Jan 08, 2025 at 01:24 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `attendence_managment`
--

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `taskId` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `isCompleted` tinyint(1) DEFAULT 0,
  `employeeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`taskId`, `description`, `isCompleted`, `employeeId`) VALUES
(1, 'trash', 1, 6),
(2, 'test task', 1, 6),
(3, 'random_task', 0, 32),
(4, 'random_task', 1, 6),
(5, 'neizvrsen_task', 0, 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('Employee','Manager','SuperAdmin') NOT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `image_url`) VALUES
(1, 'admin', 'admin', 'SuperAdmin', 'https://randomuser.me/api/portraits/men/69.jpg'),
(2, 'mngr', 'mngr', 'Manager', NULL),
(6, 'emp', 'emp', 'Employee', NULL),
(26, 'ahmed.ahmedovic', 'password1', 'Manager', NULL),
(27, 'marko.markovic', 'password2', 'Manager', NULL),
(28, 'ivan.ilic', 'password3', 'Manager', NULL),
(29, 'emir.kurtovic', 'password4', 'Manager', NULL),
(30, 'adnan.hajdar', 'password5', 'Manager', NULL),
(31, 'amir.besic', 'password6', 'Employee', NULL),
(32, 'mirsad.mujic', 'password7', 'Employee', NULL),
(33, 'edin.hodzic', 'password8', 'Employee', NULL),
(34, 'alen.karanovic', 'password9', 'Employee', NULL),
(35, 'tarik.avic', 'password10', 'Employee', NULL),
(36, 'kenan.berbic', 'password11', 'Employee', NULL),
(37, 'selma.selimovic', 'password12', 'Employee', NULL),
(38, 'amir.malic', 'password13', 'Employee', NULL),
(39, 'mustafa.mustafic', 'password14', 'Employee', NULL),
(40, 'haris.balic', 'password15', 'Employee', NULL),
(41, 'senad.salihovic', 'password16', 'Employee', NULL),
(42, 'adnan.kolaric', 'password17', 'Employee', NULL),
(43, 'mehmed.mehmedovic', 'password18', 'Employee', NULL),
(44, 'omer.omerovic', 'password19', 'Employee', NULL),
(46, 'dino.djedovic', 'password21', 'Employee', NULL),
(47, 'amir.catic', 'password22', 'Employee', NULL),
(48, 'milica.milicevic', 'password23', 'Employee', NULL),
(49, 'petar.petrovic', 'password24', 'Employee', NULL),
(50, 'dusan.djuric', 'password25', 'Employee', NULL),
(51, 'ivan.pavic', 'password26', 'Employee', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`taskId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `taskId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
