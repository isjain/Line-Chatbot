SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `wedding`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `user` (
-- `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `weight` float(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `height` float(11) NOT NULL,
  `gymFrequency` int(11) NOT NULL,
  `bmi` float(11) NOT NULL,  
  `bmr` float(11) NOT NULL,  
  `loseGainPerWeek` float(11) NOT NULL,

    `age` int(11) NOT NULL,  
    `waterReminder` int(11) NOT NULL,  


  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `goal` varchar(64) COLLATE utf8_unicode_ci NOT NULL,

  -- FOREIGN KEY (weddingId) REFERENCES information(id)
);


CREATE TABLE `userStoredData` (
  `userId` int(11) NOT NULL,
  `cal` float(11) NOT NULL,
);


-- ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;
-- main nutrients usage (protein, carbs, water)
-- how much to maintain to weight - daily consumption