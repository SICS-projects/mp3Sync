-- phpMyAdmin SQL Dump
-- version 3.3.7deb3
-- http://www.phpmyadmin.net
--
-- Host: joachimg.jo.funpicsql.de
-- Erstellungszeit: 04. März 2012 um 20:09
-- Server Version: 5.1.49
-- PHP-Version: 5.3.3-6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `joachi_773735`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `devices`
--

CREATE TABLE IF NOT EXISTS `devices` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `belongsToUser` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `devices`
--

INSERT INTO `devices` (`ID`, `name`, `belongsToUser`) VALUES
(1, 'TestDevice', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `rights` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `groups`
--

INSERT INTO `groups` (`ID`, `name`, `rights`) VALUES
(1, 'Hauptgruppe', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `songs`
--

CREATE TABLE IF NOT EXISTS `songs` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `filePath` text NOT NULL,
  `isSynched` tinyint(1) NOT NULL DEFAULT '0',
  `belongsToUser` int(11) NOT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `ID_2` (`_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Daten für Tabelle `songs`
--

INSERT INTO `songs` (`_id`, `name`, `filePath`, `isSynched`, `belongsToUser`) VALUES
(1, 'testSong', 'testSong1.mp3', 0, 1),
(2, 'TestSong2', 'sdff', 0, 1),
(3, 'TestSong3', 'asdfasdf', 0, 1),
(4, 'TestSong454', 'sadf', 0, 1),
(5, '456', '456', 0, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `specialrights`
--

CREATE TABLE IF NOT EXISTS `specialrights` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `rightOfSong` int(11) NOT NULL,
  `rightOfUserOrGrp` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Daten für Tabelle `specialrights`
--


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `password` text NOT NULL,
  `belongsToGrp` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`ID`, `name`, `password`, `belongsToGrp`) VALUES
(1, 'TestUser', '1234', 1);
