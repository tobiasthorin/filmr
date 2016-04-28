

/* THE CINEMA AND ITS REPERTOIRE */
INSERT INTO repertoire (id) VALUES(null);
INSERT INTO cinema (name,repertoire) VALUES('Lasses Biograf',1);



/* MOVIES*/

INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion King', 122, 'Story about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Queen', 123, 'Tale about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Prince', 124, 'Epic story about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Princess', 125, 'Old tale about lions');
	
/* THEATERS */

INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 01', 20, 1, FALSE);
INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 04', 20, 1, FALSE);
INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 05', 20, 1, FALSE);
INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 08', 20, 1, FALSE);


/* SHOWINGS */
/*# for movie 1, theater 1*/
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-22 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-22 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-22 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-22 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 21:00', 1, 1);


/* for movie 2, theater 2*/
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 21:00', 2, 2);

/*INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 21:00', 2, 2);*/

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 21:00', 2, 2);



/* for movie 3, theater 3 */
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-24 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-25 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-26 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-27 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-28 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-29 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-04-30 18:00', 3, 3);


/* FILL REPETOIRE OF THE THE CINEMA WITH TWO MOVIES */
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,1);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,2);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,2);

