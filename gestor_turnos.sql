-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 28, 2024 at 10:21 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestor_turnos`
--

-- --------------------------------------------------------

--
-- Table structure for table `CIUDADANO`
--

CREATE TABLE `CIUDADANO` (
  `ID` bigint(20) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `CIUDADANO`
--

INSERT INTO `CIUDADANO` (`ID`, `dni`, `nombre`, `apellido`, `USER_ID`) VALUES
(1, '12345678G', 'Lucia', 'Collado', 1),
(2, '87654321L', 'Admin', 'Admin', 2),
(3, '13425364E', 'Elena', 'Martinez', 3),
(6, '98765432A', 'Laura', 'Collado', 4);

-- --------------------------------------------------------

--
-- Table structure for table `TRAMITE`
--

CREATE TABLE `TRAMITE` (
  `ID` bigint(20) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `TRAMITE`
--

INSERT INTO `TRAMITE` (`ID`, `descripcion`) VALUES
(5, 'Declaración de Impuestos Anuales'),
(7, 'Inscripción en el Registro de Empresas'),
(3, 'Registro de Marca Comercial'),
(1, 'Renovación de Licencia Comercial'),
(8, 'Solicitud de Licencia Ambiental'),
(2, 'Solicitud de Permiso de Construcción'),
(6, 'Solicitud de Permiso de Trabajo'),
(4, 'Solicitud de Subvención Gubernamental');

-- --------------------------------------------------------

--
-- Table structure for table `TURNO`
--

CREATE TABLE `TURNO` (
  `ID` bigint(20) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT 0,
  `fecha` date NOT NULL,
  `id_ciudadano` bigint(20) DEFAULT NULL,
  `id_tramite` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `TURNO`
--

INSERT INTO `TURNO` (`ID`, `estado`, `fecha`, `id_ciudadano`, `id_tramite`) VALUES
(1, 0, '2024-03-23', 1, 5),
(2, 1, '2024-03-29', 1, 1),
(3, 1, '2024-04-05', 1, 5),
(4, 0, '2024-04-02', 1, 4),
(5, 0, '2024-03-29', 3, 8),
(6, 0, '2024-04-05', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `USUARIO`
--

CREATE TABLE `USUARIO` (
  `ID` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `rol` varchar(255) NOT NULL,
  `ciudadano_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `USUARIO`
--

INSERT INTO `USUARIO` (`ID`, `nombre`, `contrasenia`, `rol`, `ciudadano_id`) VALUES
(1, 'Lucia', '$2a$10$vwxpfHtcswd1WMglD9JNJOnorDhfMYYiU8AFf.ghqYJFwj4GIx9n.', 'Basic', 1),
(2, 'Admin', '$2a$10$B/aHK.TMFZ9WLEh.A6PeHutsz5bvRDo8.5.ANfJY.hPoZsMUXbexi', 'Admin', 2),
(3, 'Elena', '$2a$10$Dfih.TyYEUT3cfnyWTNUDuVJYpTq24aPxZpOY2s5cx/a2JVAVpUNW', 'Basic', 3),
(4, 'Laura', '$2a$10$Xv/FSz5XUMCeUgamTxvIhuOUqI6G1eKxe/Jqw.IO.rWNVdqzhBffK', 'Basic', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CIUDADANO`
--
ALTER TABLE `CIUDADANO`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD KEY `FK_CIUDADANO_USER_ID` (`USER_ID`);

--
-- Indexes for table `TRAMITE`
--
ALTER TABLE `TRAMITE`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `descripcion` (`descripcion`);

--
-- Indexes for table `TURNO`
--
ALTER TABLE `TURNO`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TURNO_id_ciudadano` (`id_ciudadano`),
  ADD KEY `FK_TURNO_id_tramite` (`id_tramite`);

--
-- Indexes for table `USUARIO`
--
ALTER TABLE `USUARIO`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_USUARIO_ciudadano_id` (`ciudadano_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CIUDADANO`
--
ALTER TABLE `CIUDADANO`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `TRAMITE`
--
ALTER TABLE `TRAMITE`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `TURNO`
--
ALTER TABLE `TURNO`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `USUARIO`
--
ALTER TABLE `USUARIO`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `CIUDADANO`
--
ALTER TABLE `CIUDADANO`
  ADD CONSTRAINT `FK_CIUDADANO_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USUARIO` (`ID`);

--
-- Constraints for table `TURNO`
--
ALTER TABLE `TURNO`
  ADD CONSTRAINT `FK_TURNO_id_ciudadano` FOREIGN KEY (`id_ciudadano`) REFERENCES `CIUDADANO` (`ID`),
  ADD CONSTRAINT `FK_TURNO_id_tramite` FOREIGN KEY (`id_tramite`) REFERENCES `TRAMITE` (`ID`);

--
-- Constraints for table `USUARIO`
--
ALTER TABLE `USUARIO`
  ADD CONSTRAINT `FK_USUARIO_ciudadano_id` FOREIGN KEY (`ciudadano_id`) REFERENCES `CIUDADANO` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
