-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-04-2016 a las 06:22:40
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `study`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE IF NOT EXISTS `alumno` (
  `cod_alumno` varchar(10) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`cod_alumno`, `nombre`, `apellido`, `email`, `telefono`, `password`) VALUES
('ALUM320', 'Cristian', 'Flores', 'cristian.flores@ujcv.edu.hn', '99234458', '1234'),
('ALUM347', 'Katy', 'Flores', 'katy.flores@ujcv.edu.hn', '99234458', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asignatura`
--

CREATE TABLE IF NOT EXISTS `asignatura` (
  `cod_asignatura` varchar(10) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `horario` varchar(20) DEFAULT NULL,
  `cod_maestro` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asignatura`
--

INSERT INTO `asignatura` (`cod_asignatura`, `nombre`, `horario`, `cod_maestro`) VALUES
('ASIG101', 'Matematicas', '10am :12pm lunes', 'MA0100'),
('ASIG111', 'Precalculo', '10am :12pm jueves', 'MA0100');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maestro`
--

CREATE TABLE IF NOT EXISTS `maestro` (
  `cod_maestro` varchar(10) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `maestro`
--

INSERT INTO `maestro` (`cod_maestro`, `nombre`, `apellido`, `email`, `telefono`, `password`) VALUES
('MA0100', 'Sindy', 'Barahona', 'sindy.barahona@ujcv.edu.hn', '99234458', '1234'),
('MA1328', 'Hector', 'Barahona', 'Hector.barahona@ujcv.edu.hn', '99234458', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matricula`
--

CREATE TABLE IF NOT EXISTS `matricula` (
  `cod_maestro` varchar(10) NOT NULL,
  `cod_alumno` varchar(10) NOT NULL,
  `cod_asignatura` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `matricula`
--

INSERT INTO `matricula` (`cod_maestro`, `cod_alumno`, `cod_asignatura`) VALUES
('MA0100', 'ALUM320', 'ASIG101'),
('MA0100', 'ALUM347', 'ASIG111');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notas`
--

CREATE TABLE IF NOT EXISTS `notas` (
  `cod_nota` int(10) unsigned NOT NULL,
  `periodo` varchar(20) DEFAULT NULL,
  `examen` varchar(20) DEFAULT NULL,
  `acumulativo` varchar(20) NOT NULL,
  `cod_alumno` varchar(10) NOT NULL,
  `cod_asignatura` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `padre`
--

CREATE TABLE IF NOT EXISTS `padre` (
  `cod_padre` varchar(10) NOT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `padre`
--

INSERT INTO `padre` (`cod_padre`, `nombre`, `apellido`, `email`, `telefono`, `password`) VALUES
('PA32043', 'Jose', 'Amaya', 'jose.amaya@ujcv.edu.hn', '99234458', '1234'),
('PA34752', 'Delfina', 'Recarte', 'delfina.Recarte@ujcv.edu.hn', '99234458', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recursos`
--

CREATE TABLE IF NOT EXISTS `recursos` (
  `cod_recurso` int(10) unsigned NOT NULL,
  `tipo_recurso` varchar(20) DEFAULT NULL,
  `url` varchar(35) DEFAULT NULL,
  `detalle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguimiento`
--

CREATE TABLE IF NOT EXISTS `seguimiento` (
  `cod_padre` varchar(10) NOT NULL,
  `cod_alumno` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`cod_alumno`);

--
-- Indices de la tabla `asignatura`
--
ALTER TABLE `asignatura`
  ADD PRIMARY KEY (`cod_asignatura`), ADD KEY `cod_maestro` (`cod_maestro`);

--
-- Indices de la tabla `maestro`
--
ALTER TABLE `maestro`
  ADD PRIMARY KEY (`cod_maestro`);

--
-- Indices de la tabla `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`cod_maestro`,`cod_alumno`,`cod_asignatura`), ADD KEY `cod_asignatura` (`cod_asignatura`), ADD KEY `cod_alumno` (`cod_alumno`);

--
-- Indices de la tabla `notas`
--
ALTER TABLE `notas`
  ADD PRIMARY KEY (`cod_nota`), ADD KEY `cod_asignatura` (`cod_asignatura`), ADD KEY `cod_alumno` (`cod_alumno`);

--
-- Indices de la tabla `padre`
--
ALTER TABLE `padre`
  ADD PRIMARY KEY (`cod_padre`);

--
-- Indices de la tabla `recursos`
--
ALTER TABLE `recursos`
  ADD PRIMARY KEY (`cod_recurso`);

--
-- Indices de la tabla `seguimiento`
--
ALTER TABLE `seguimiento`
  ADD PRIMARY KEY (`cod_padre`,`cod_alumno`), ADD KEY `cod_alumno` (`cod_alumno`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `notas`
--
ALTER TABLE `notas`
  MODIFY `cod_nota` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `recursos`
--
ALTER TABLE `recursos`
  MODIFY `cod_recurso` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asignatura`
--
ALTER TABLE `asignatura`
ADD CONSTRAINT `asignatura_ibfk_1` FOREIGN KEY (`cod_maestro`) REFERENCES `maestro` (`cod_maestro`);

--
-- Filtros para la tabla `matricula`
--
ALTER TABLE `matricula`
ADD CONSTRAINT `matricula_ibfk_1` FOREIGN KEY (`cod_maestro`) REFERENCES `maestro` (`cod_maestro`),
ADD CONSTRAINT `matricula_ibfk_2` FOREIGN KEY (`cod_asignatura`) REFERENCES `asignatura` (`cod_asignatura`),
ADD CONSTRAINT `matricula_ibfk_3` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`cod_alumno`);

--
-- Filtros para la tabla `notas`
--
ALTER TABLE `notas`
ADD CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`cod_asignatura`) REFERENCES `asignatura` (`cod_asignatura`),
ADD CONSTRAINT `notas_ibfk_2` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`cod_alumno`);

--
-- Filtros para la tabla `seguimiento`
--
ALTER TABLE `seguimiento`
ADD CONSTRAINT `seguimiento_ibfk_1` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`cod_alumno`),
ADD CONSTRAINT `seguimiento_ibfk_2` FOREIGN KEY (`cod_padre`) REFERENCES `padre` (`cod_padre`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
