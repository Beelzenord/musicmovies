/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Niklas
 * Created: Nov 30, 2016
 */

CREATE DATABASE IF NOT EXISTS MediaLibrary;

USE MediaLibrary;


CREATE TABLE IF NOT EXISTS T_Album (
albumId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
title VARCHAR(40), 
genre VARCHAR(40), 
rating VARCHAR(40), 
releaseDate DATE
);


CREATE TABLE IF NOT EXISTS T_Movie (
movieId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
title VARCHAR(40),
genre VARCHAR(40), 
rating VARCHAR(40), 
releaseDate DATE
);


CREATE TABLE IF NOT EXISTS T_Artist(
artistId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(40),
rating VARCHAR(40),
);


CREATE TABLE IF NOT EXISTS T_Director(
directorId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(40),
rating VARCHAR(40),
);


CREATE TABLE IF NOT EXISTS T_User (
userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(40),
);


CREATE TABLE IF NOT EXISTS T_AlbumDirectory (
artistId INT NOT NULL,
albumId INT NOT NULL,
CONSTRAINT T_AlbumDirectory_artistId_albumId_pk PRIMARY KEY (artistId, albumId), 
CONSTRAINT T_AlbumDirectory_artisttId_fk FOREIGN KEY (artistId)
REFERENCES T_Artist (artistId) ON DELETE CASCADE, 
CONSTRAINT T_AlbumDirectory_albumId_fk FOREIGN KEY (albumId)
REFERENCES T_Album (albumId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_MovieDirectory(
movieId INT NOT NULL,
directorId INT NOT NULL,
CONSTRAINT T_MovieDirectory_directorId_movieId_pk PRIMARY KEY(directorId,movieId),
CONSTRAINT T_MovieDirectory_movieId_fk FOREIGN KEY(movieId)
REFERENCES T_Movie(movieId) ON DELETE CASCADE,
CONSTRAINT T_MovieDirectory_directorId_fk FOREIGN KEY(directorId)
REFERENCES T_Director(directorId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_AlbumReview (
albumId INT NOT NULL,
userId INT NOT NULL,
review VARCHAR(1000), 
rating VARCHAR(1), 
CONSTRAINT T_AlbumReview_albumId_userId_pk PRIMARY KEY (albumId, userId), 
CONSTRAINT T_AlbumReview_albumId_fk FOREIGN KEY (albumId)
REFERENCES T_Album (albumId) ON DELETE CASCADE, 
CONSTRAINT T_AlbumReview_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_MovieReview(
movieId INT NOT NULL,
userId INT NOT NULL,
review VARCHAR(1000),
rating VARCHAR(1),
CONSTRAINT T_MovieReview_movieId_userId_pk PRIMARY KEY(movieId,userId),
CONSTRAINT T_MovieReview_moviId_fk FOREIGN KEY(movieId) 
REFERENCES T_Movie(movieId) ON DELETE CASCADE,
CONSTRAINT T_MovieReview_userId_fk FOREIGN KEY(userId)
REFERENCES T_User(userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_ArtistReview (
artistId INT NOT NULL,
userId INT NOT NULL,
review VARCHAR(1000), 
rating VARCHAR(1), 
CONSTRAINT T_ArtistReview_artistId_userId_pk PRIMARY KEY (artistId, userId), 
CONSTRAINT T_Artist_artistId_fk FOREIGN KEY (artistId)
REFERENCES T_Artist (artistId) ON DELETE CASCADE, 
CONSTRAINT T_ArtistReview_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_DirectorReview (
directorId INT NOT NULL,
userId INT NOT NULL,
review VARCHAR(1000), 
rating VARCHAR(1), 
CONSTRAINT T_DirectorReview_directorId_userId_pk PRIMARY KEY (directorId, userId), 
CONSTRAINT T_DirectorReview_directorId_fk FOREIGN KEY (directorId)
REFERENCES T_Director (directorId) ON DELETE CASCADE, 
CONSTRAINT T_DirectorReview_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_ArtistAddedBy(
artistId INT NOT NULL,
userId INT NOT NULL,
CONSTRAINT T_ArtistAddedBy_artistId_iserId_pk PRIMARY KEY (artistId, userId),
CONSTRAINT T_ArtistAddedBy_artistId_fk FOREIGN KEY (artistId)
REFERENCES T_Artist (artistId) ON DELETE CASCADE, 
CONSTRAINT T_ArtistAddedBy_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_AlbumAddedBy (
albumId INT NOT NULL,
userId INT NOT NULL,
CONSTRAINT T_AlbumAddedBy_albumId_userId_pk PRIMARY KEY (albumId, userId), 
CONSTRAINT T_AlbumAddedBy_albumId_fk FOREIGN KEY (albumId)
REFERENCES T_Album (albumId) ON DELETE CASCADE, 
CONSTRAINT T_AlbumAddedBy_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_MovieAddedBy (
movieId INT NOT NULL,
userId INT NOT NULL,
CONSTRAINT T_MovieAddedBy_movieId_userId_pk PRIMARY KEY(movieId,userId),
CONSTRAINT T_MovieAddedBy_movieId_fk FOREIGN KEY(movieId) REFERENCES T_Movie(movieId) ON DELETE CASCADE,
CONSTRAINT T_MovieAddedBy_userId_fk FOREIGN KEY(userId)
REFERENCES T_User(userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS T_DirectorAddedBy (
directorId INT NOT NULL,
userId INT NOT NULL,
CONSTRAINT T_DirectorAddedBy_directorId_userId_pk PRIMARY KEY (directorId, userId), 
CONSTRAINT T_DirectorAddedBy_directorId_fk FOREIGN KEY (directorId)
REFERENCES T_Director (directorId) ON DELETE CASCADE, 
CONSTRAINT T_DirectorAddedBy_userId_fk FOREIGN KEY (userId)
REFERENCES T_User (userId) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS T_DirectorsNationality (
directorId INT NOT NULL,
nationality VARCHAR(40), 
CONSTRAINT T_DirectorsNationality_directorId_pk PRIMARY KEY (directorId), 
CONSTRAINT T_DirectorsNationality_directorId_fk FOREIGN KEY (directorId)
REFERENCES T_Director (directorId) ON DELETE CASCADE 
);


CREATE TABLE IF NOT EXISTS T_ArtistsNationality (
artistId INT NOT NULL,
nationality VARCHAR(40), 
CONSTRAINT T_ArtiststNationality_artistId_pk PRIMARY KEY (artistId), 
CONSTRAINT T_ArtiststNationality_artistId_fk FOREIGN KEY (artistId)
REFERENCES T_Artist (artistId) ON DELETE CASCADE
);
