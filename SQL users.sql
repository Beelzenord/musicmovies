USE kTunes;

CREATE USER IF NOT EXISTS 'musicemp'@'localhost' identified BY 'musicp';
GRANT SELECT, INSERT, UPDATE ON T_album TO 'musicemp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_artist TO 'musicemp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_albumdirectory TO 'musicemp'@'localhost';

CREATE USER IF NOT EXISTS 'movieemp'@'localhost' identified BY 'moviep';
GRANT SELECT, INSERT, UPDATE ON T_movie TO 'movieemp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_director TO 'movieemp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_moviedirectory TO 'movieemp'@'localhost';

CREATE USER IF NOT EXISTS 'emp'@'localhost' identified BY 'empp';
GRANT SELECT, INSERT, UPDATE ON T_album TO 'emp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_artist TO 'emp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_albumdirectory TO 'emp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_movie TO 'emp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_director TO 'emp'@'localhost';
GRANT SELECT, INSERT, UPDATE ON T_moviedirectory TO 'emp'@'localhost';

CREATE USER IF NOT EXISTS 'visitor'@'localhost' identified BY 'pass';
GRANT SELECT ON T_album TO 'visitor'@'localhost';
GRANT SELECT ON T_artist TO 'visitor'@'localhost';
GRANT SELECT ON T_albumdirectory TO 'visitor'@'localhost';
GRANT SELECT ON T_movie TO 'visitor'@'localhost';
GRANT SELECT ON T_director TO 'visitor'@'localhost';
GRANT SELECT ON T_moviedirectory TO 'visitor'@'localhost';
/*REVOKE UPDATE ON T_album FROM 'visitor'@'localhost';
REVOKE UPDATE ON T_artist FROM 'visitor'@'localhost';
REVOKE UPDATE ON T_albumdirectory FROM 'visitor'@'localhost';*/

CREATE USER IF NOT EXISTS 'rater'@'localhost' identified BY 'raterp';
GRANT SELECT, UPDATE ON T_album TO 'rater'@'localhost';
GRANT SELECT, UPDATE ON T_artist TO 'rater'@'localhost';
GRANT SELECT, UPDATE ON T_albumdirectory TO 'rater'@'localhost';
GRANT SELECT, UPDATE ON T_movie TO 'rater'@'localhost';
GRANT SELECT, UPDATE ON T_director TO 'rater'@'localhost';
GRANT SELECT, UPDATE ON T_moviedirectory TO 'rater'@'localhost';


CREATE USER IF NOT EXISTS 'open'@'localhost' identified BY '';
GRANT SELECT ON T_album TO 'open'@'localhost';
GRANT SELECT ON T_artist TO 'open'@'localhost';
GRANT SELECT ON T_albumdirectory TO 'open'@'localhost';
GRANT SELECT ON T_movie TO 'open'@'localhost';
GRANT SELECT ON T_director TO 'open'@'localhost';
GRANT SELECT ON T_moviedirectory TO 'open'@'localhost';

/*SHOW GRANTS FOR 'reviewer'@'localhost';
SHOW GRANTS FOR 'visitor'@'localhost';
SHOW GRANTS FOR 'movieemp'@'localhost';
SHOW GRANTS FOR 'musicemp'@'localhost';*/