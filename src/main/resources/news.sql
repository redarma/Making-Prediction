-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.5.36-MariaDB - mariadb.org binary distribution
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportiere Struktur von Tabelle enbw01.categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cat_id` int(10) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '- Namen fuer die Rubrik angeben -',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='Alle Rubriken';

-- Exportiere Daten aus Tabelle enbw01.categories: ~4 rows (ungef�hr)
--DELETE FROM `categories`;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`id`, `cat_id`, `name`, `timestamp`) VALUES
	(70, 0, 'Allgemein', '2014-05-05 12:22:09'),
	(71, 10, 'Sport', '2014-05-05 12:22:09'),
	(72, 20, 'Welt', '2014-05-05 12:22:09'),
	(73, 30, 'Lokales', '2014-05-05 12:22:09');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;


-- Exportiere Struktur von Tabelle enbw01.news
DROP TABLE IF EXISTS `news`;
CREATE TABLE IF NOT EXISTS `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `newsid` int(10) NOT NULL,
  `title` varchar(160) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `categoryid` int(10) NOT NULL DEFAULT '0',
  `text` varchar(16384) DEFAULT NULL,
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_news_categories` (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='Alle Nachrichten';

-- Exportiere Daten aus Tabelle enbw01.news: ~12 rows (ungef�hr)
--DELETE FROM `news`;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` (`id`, `newsid`, `title`, `description`, `categoryid`, `text`, `createdate`) VALUES
	(26, 1, 'Lorem Ipsum', 'Beschreibung 1', 10, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibu', '2014-04-08 16:43:10'),
	(27, 2, 'Lorem Ipsum auf Deutsch', 'Beschreibung 2', 20, 'Auch gibt es niemanden, der den Schmerz an sich liebt, sucht oder w�nscht, nur, weil er Schmerz ist, es sei denn, es kommt zu zuf�lligen Umst�nden, in denen M�hen und Schmerz ihm gro�e Freude bereiten k�nnen. Um ein triviales Beispiel zu nehmen, wer von uns unterzieht sich je anstrengender k�rperlicher Bet�tigung, au�er um Vorteile daraus zu ziehen? Aber wer hat irgend ein Recht, einen Menschen zu tadeln, der die Entscheidung trifft, eine Freude zu genie�en, die keine unangenehmen Folgen hat, od', '2014-04-08 16:43:11'),
	(28, 3, 'Li Europan Lingues', 'Beschreibung 3', 30, 'Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, li grammatica del resultant lingue es plu s', '2014-04-08 16:43:12'),
	(29, 4, 'Hinter den Wortbergen', 'Beschreibung 4', 10, 'Weit hinten, hinter den Wortbergen, fern der L�nder Vokalien und Konsonantien leben die Blindtexte. Abgeschieden wohnen sie in Buchstabhausen an der K�ste des Semantik, eines gro�en Sprachozeans.\r\n\r\nEin kleines B�chlein namens Duden flie�t durch ihren Ort und versorgt sie mit den n�tigen Regelialien. Es ist ein paradiesmatisches Land, in dem einem gebratene Satzteile in den Mund fliegen. Nicht einmal von der allm�chtigen Interpunktion werden die Blindtexte beherrscht � ein geradezu unorthographisch', '2014-04-08 16:43:13'),
	(30, 5, 'Werther', 'Beschreibung 5', 20, 'Eine wunderbare Heiterkeit hat meine ganze Seele eingenommen, gleich den s��en Fr�hlingsmorgen, die ich mit ganzem Herzen genie�e. Ich bin allein und freue mich meines Lebens in dieser Gegend, die f�r solche Seelen geschaffen ist wie die meine.\n\nIch bin so gl�cklich, mein Bester, so ganz in dem Gef�hle von ruhigem Dasein versunken, da� meine Kunst darunter leidet. Ich k�nnte jetzt nicht zeichnen, nicht einen Strich, und bin nie ein gr��erer Maler gewesen als in diesen Augenblicken. Wenn das liebe', '2014-04-08 16:43:14'),
	(31, 6, 'Kafka', 'Beschreibung 6', 30, 'Jemand musste Josef K. verleumdet haben, denn ohne dass er etwas B�ses getan h�tte, wurde er eines Morgens verhaftet. �Wie ein Hund! � sagte er, es war, als sollte die Scham ihn �berleben.\r\n\r\nAls Gregor Samsa eines Morgens aus unruhigen Tr�umen erwachte, fand er sich in seinem Bett zu einem ungeheueren Ungeziefer verwandelt. Und es war ihnen wie eine Best�tigung ihrer neuen Tr�ume und guten Absichten, als am Ziele ihrer Fahrt die Tochter als erste sich erhob und ihren jungen K�rper dehnte. �Es ist e', '2014-04-08 16:43:15'),
	(32, 7, 'Trapattoni \'98', 'Beschreibung 7', 10, 'Es gibt im Moment in diese Mannschaft, oh, einige Spieler vergessen ihnen Profi was sie sind. Ich lese nicht sehr viele Zeitungen, aber ich habe geh�rt viele Situationen.\r\n\r\nErstens: wir haben nicht offensiv gespielt. Es gibt keine deutsche Mannschaft spielt offensiv und die Name offensiv wie Bayern. Letzte Spiel hatten wir in Platz drei Spitzen: Elber, Jancka und dann Zickler. Wir m�ssen nicht vergessen Zickler. Zickler ist eine Spitzen mehr, Mehmet eh mehr Basler. Ist klar diese W�rter, ist m�gli\r\n', '2014-04-08 16:43:16'),
	(33, 8, 'Er h�rte leise', 'Beschreibung 8', 20, 'Er h�rte leise Schritte hinter sich. Das bedeutete nichts Gutes. Wer w�rde ihm schon folgen, sp�t in der Nacht und dazu noch in dieser engen Gasse mitten im �bel beleumundeten Hafenviertel?\r\n\r\nGerade jetzt, wo er das Ding seines Lebens gedreht hatte und mit der Beute verschwinden wollte! Hatte einer seiner zahllosen Kollegen dieselbe Idee gehabt, ihn beobachtet und abgewartet, um ihn nun um die Fr�chte seiner Arbeit zu erleichtern? Oder geh�rten die Schritte hinter ihm zu einem der unz�hligen Geset', '2014-04-08 16:43:17'),
	(34, 9, 'Typoblindtext', 'Beschreibung 9', 30, 'Dies ist ein Typoblindtext. An ihm kann man sehen, ob alle Buchstaben da sind und wie sie aussehen. Manchmal benutzt man Worte wie Hamburgefonts, Rafgenduks oder Handgloves, um Schriften zu testen.\r\n\r\nManchmal S�tze, die alle Buchstaben des Alphabets enthalten - man nennt diese S�tze �Pangrams�. Sehr bekannt ist dieser: The quick brown fox jumps over the lazy old dog. Oft werden in Typoblindtexte auch fremdsprachige Satzteile eingebaut (AVAIL� and Wefox� are testing aussi la Kerning), um die Wirkun', '2014-04-08 16:43:18'),
	(35, 10, 'Webstandards ', 'Beschreibung 10', 10, '�berall dieselbe alte Leier. Das Layout ist fertig, der Text l�sst auf sich warten. Damit das Layout nun nicht nackt im Raume steht und sich klein und leer vorkommt, springe ich ein: der Blindtext.\r\n\r\nGenau zu diesem Zwecke erschaffen, immer im Schatten meines gro�en Bruders �Lorem Ipsum�, freue ich mich jedes Mal, wenn Sie ein paar Zeilen lesen. Denn esse est percipi - Sein ist wahrgenommen werden. Und weil Sie nun schon die G�te haben, mich ein paar weitere S�tze lang zu begleiten, m�chte ich die', '2014-04-08 16:43:19'),
	(36, 11, 'Pangramm', 'Beschreibung 11', 0, 'Zwei flinke Boxer jagen die quirlige Eva und ihren Mops durch Sylt. Franz jagt im komplett verwahrlosten Taxi quer durch Bayern. Zw�lf Boxk�mpfer jagen Viktor quer �ber den gro�en Sylter Deich.', '2014-04-25 14:28:10'),
	(37, 12, 'a-z A-Z 123 ���', 'Beschreibung 12', 0, 'a�b cde fgh ijk lmn o�p qrs� tu� vwx yz A�BC DEF GHI JKL MNO �PQ RST U�V WXYZ ! "� $%& /() =? * \'<> |; ��~ @`� ��� �� {} a�b cde fgh ijk lmn o�p qrs� tu� vwx yz A�BC DEF GHI JKL MNO �PQ RST U�V WXYZ ! "� $%& /() =? * \'<> |; ��~ @`� ��� �� {} a�b cde fgh ijk lmn o�p qrs� tu� vwx yz A�BC DEF GHI JKL MNO �PQ RST U�V WXYZ ! "� $%& /() =? * \'<> |; ��~ @`� ��� �� {} a�b cde fgh ijk lmn o�p qrs� tu� vwx yz A�BC DEF GHI JKL MNO �PQ RST U�V WXYZ ! "� $%& /() =? * \'<> |; ��~ @`� ��� �� {} a�b cde fgh ijk lmn.', '2014-04-25 14:29:50');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
