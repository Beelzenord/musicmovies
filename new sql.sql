USE kTunes;
SELECT T_Album.title, T_Album.genre, T_Album.rating, T_Album.releaseDate, T_Artist.name
FROM T_Album, T_Artist, T_Albumdirectory