

/* THE CINEMA AND ITS REPERTOIRE */
INSERT INTO repertoire (id) VALUES(null);
INSERT INTO cinema (name,repertoire) VALUES('Lasses Biograf',1);
/* ANOTHER CINEMA WITH REPERTOIRE */
INSERT INTO repertoire (id) VALUES(null);
INSERT INTO cinema (name,repertoire) VALUES('Lisas Biograf',2);


/* MOVIES*/

INSERT INTO movie (title, length_in_minutes, description, default_price) 
	VALUES ('Lion King', 122, 'Story about lions', 65.00);
INSERT INTO movie (title, length_in_minutes, description, default_price) 
	VALUES ('Lion Queen', 123, 'Tale about lions', 70.00);
INSERT INTO movie (title, length_in_minutes, description, default_price) 
	VALUES ('Lion Prince', 124, 'Epic story about lions', 55.50);
INSERT INTO movie (title, length_in_minutes, description, default_price) 
	VALUES ('Lion Princess', 125, 'Old tale about lions', 72.33);
	
/* THEATERS */

INSERT INTO theater (name, cinema_id, disabled) VALUES ('Bergakungen sal 01', 1, FALSE);
INSERT INTO theater (name, cinema_id, disabled) VALUES ('Bergakungen sal 04', 1, FALSE);
INSERT INTO theater (name, cinema_id, disabled) VALUES ('Bergakungen sal 05', 2, FALSE);
INSERT INTO theater (name, cinema_id, disabled) VALUES ('Bergakungen sal 08', 2, FALSE);


/* SHOWINGS */
/*# for movie 1, theater 1*/
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-12 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-12 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-12 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-12 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 21:00', 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 21:00', 1, 1);


/* for movie 2, theater 2*/
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 21:00', 2, 2);
                                                                            
/*                                                                          
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 21:00', 2, 2);
*/

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 21:00', 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 21:00', 2, 2);


/* for movie 3, theater 3 */
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-14 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-15 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-16 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-17 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-18 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-19 18:00', 3, 3);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-20 18:00', 3, 3);


/* FILL REPETOIRE OF LASSES CINEMA WITH TWO MOVIES */
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,1);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,2);

/* FILL REPETOIRE OF LISAS CINEMA WITH TWO MOVIES */
INSERT INTO repertoire_movies (repertoire,movies) VALUES(2,3);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(2,4);


/* SOME "DYNAMIC" DATA THAT GUARANTIES THAT THERE ARE ALWAYS A FEW SHOWINGS TODAY (now()). GETS THEIR OWN THEATERS SO THEY CAN'T INTERFERE WITH OTHER SHOWINGS ABOVE */
/* theater will get id 5 and 6 */
INSERT INTO theater (name, cinema_id, disabled) VALUES ('sal 98', 2, FALSE);
INSERT INTO theater (name, cinema_id, disabled) VALUES ('sal 99', 2, FALSE);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 10:00'), 3, 5);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 13:00'), 3, 5);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 16:00'), 3, 5);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 19:00'), 3, 5);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 22:00'), 3, 5);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 09:00'), 2, 6);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 12:00'), 2, 6);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 15:00'), 2, 6);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 18:00'), 2, 6);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 21:00'), 2, 6);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (date_format(now(), '%Y-%m-%d 23:30'), 2, 6);

