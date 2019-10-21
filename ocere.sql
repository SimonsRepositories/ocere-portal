-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 21. Okt 2019 um 10:35
-- Server-Version: 10.1.32-MariaDB
-- PHP-Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ocere`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `auth_role`
--

CREATE TABLE `auth_role` (
  `auth_role_id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `auth_role`
--

INSERT INTO `auth_role` (`auth_role_id`, `role_name`, `role_desc`) VALUES
(1, 'DEV_USER', 'DEV_USER'),
(2, 'ADMIN_USER', 'ADMIN_USER'),
(3, 'STAFF_USER', 'STAFF_USER'),
(4, 'CLIENT_USER', 'CLIENT_USER');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `auth_user`
--

CREATE TABLE `auth_user` (
  `auth_user_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `auth_user_role`
--

CREATE TABLE `auth_user_role` (
  `auth_user_id` int(11) NOT NULL,
  `auth_role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_us_page` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `satisfaction` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `tier` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `assigned_user_id` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `files`
--

CREATE TABLE `files` (
  `id` varchar(255) NOT NULL,
  `data` longblob,
  `file_name` varchar(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `job`
--

CREATE TABLE `job` (
  `id` int(11) NOT NULL,
  `client_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `note`
--

CREATE TABLE `note` (
  `id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  `client_id` int(11) DEFAULT NULL,
  `ticket_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ticket`
--

CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `turnaround` datetime DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `assigned_user_id` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `turnaround_id` int(11) DEFAULT NULL,
  `template` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `ticket`
--

INSERT INTO `ticket` (`ticket_id`, `description`, `priority`, `status`, `subject`, `turnaround`, `group_id`, `user_id`, `job_id`, `created_at`, `assigned_user_id`, `author_id`, `turnaround_id`, `template`) VALUES
(1, 'test description', 0, 2, 'test ticket', NULL, NULL, NULL, NULL, '2019-10-18 15:42:06', 36, 36, NULL, b'0');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `turnaround`
--

CREATE TABLE `turnaround` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `hours` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `usergroup`
--

CREATE TABLE `usergroup` (
  `group_id` int(11) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_empty` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `auth_role`
--
ALTER TABLE `auth_role`
  ADD PRIMARY KEY (`auth_role_id`);

--
-- Indizes für die Tabelle `auth_user`
--
ALTER TABLE `auth_user`
  ADD PRIMARY KEY (`auth_user_id`);

--
-- Indizes für die Tabelle `auth_user_role`
--
ALTER TABLE `auth_user_role`
  ADD PRIMARY KEY (`auth_user_id`,`auth_role_id`),
  ADD KEY `FK_user_role` (`auth_role_id`);

--
-- Indizes für die Tabelle `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb8t2yn40v2ju5b8uctnk5qhs3` (`assigned_user_id`),
  ADD KEY `FKfdweye2110m7cd40mjiki3od9` (`author_id`);

--
-- Indizes für die Tabelle `files`
--
ALTER TABLE `files`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt9wp9vrouvtqtl1apc9cemr3k` (`job_id`),
  ADD KEY `FK1w1ernn0qcsctf6ain7c3qg0` (`ticket_id`);

--
-- Indizes für die Tabelle `job`
--
ALTER TABLE `job`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK17amy3r92wfc4rwxm15q4t252` (`client_id`);

--
-- Indizes für die Tabelle `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtp2fn0pmjimuhvkoujx5bo5p9` (`author_id`),
  ADD KEY `FK9s092qdotpc634u7jmarxrei2` (`client_id`),
  ADD KEY `FKg3aicla3k0i12suu27x688vrw` (`ticket_id`);

--
-- Indizes für die Tabelle `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `FKbqfxrk1c75wyflrult7rrqq0a` (`user_id`),
  ADD KEY `FK7q5mdwdxccd95nafya8cg0b5r` (`group_id`),
  ADD KEY `FK2refw6ls9q9h4py781d4wfo90` (`assigned_user_id`),
  ADD KEY `FKojjf1uhlj1autku8fq07qd43g` (`author_id`),
  ADD KEY `FK5purqs3fb3ajdhrfusmjap3rh` (`job_id`),
  ADD KEY `FK8j8b8c006eboemnc4g43oy5bh` (`turnaround_id`);

--
-- Indizes für die Tabelle `turnaround`
--
ALTER TABLE `turnaround`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_rq4rh1sonx37oipn4oms1uynp` (`description`),
  ADD UNIQUE KEY `UK_6btnuab5w00qx3a4lhwmbcjub` (`hours`);

--
-- Indizes für die Tabelle `usergroup`
--
ALTER TABLE `usergroup`
  ADD PRIMARY KEY (`group_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `auth_role`
--
ALTER TABLE `auth_role`
  MODIFY `auth_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `auth_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT für Tabelle `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `job`
--
ALTER TABLE `job`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `note`
--
ALTER TABLE `note`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `ticket`
--
ALTER TABLE `ticket`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT für Tabelle `turnaround`
--
ALTER TABLE `turnaround`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `usergroup`
--
ALTER TABLE `usergroup`
  MODIFY `group_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `auth_user_role`
--
ALTER TABLE `auth_user_role`
  ADD CONSTRAINT `FK_auth_user` FOREIGN KEY (`auth_user_id`) REFERENCES `auth_user` (`auth_user_id`),
  ADD CONSTRAINT `FK_auth_user_role` FOREIGN KEY (`auth_role_id`) REFERENCES `auth_role` (`auth_role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
