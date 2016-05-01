

/* THE CINEMA AND ITS REPERTOIRE */
INSERT INTO repertoire (id) VALUES(null);
INSERT INTO cinema (name,repertoire) VALUES('Lasses Biograf',1);
/* ANOTHER CINEMA WITH REPERTOIRE */
INSERT INTO repertoire (id) VALUES(null);
INSERT INTO cinema (name,repertoire) VALUES('Lisas Biograf',2);


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
INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 05', 20, 2, FALSE);
INSERT INTO theater (name, number_of_seats, cinema_id, disabled) VALUES ('Bergakungen sal 08', 20, 2, FALSE);


/* SHOWINGS */
/*# for movie 1, theater 1*/
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-02 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-02 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-02 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-02 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 21:00', 1, 1);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 12:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 15:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 19:00', 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 21:00', 1, 1);
                                                                            
                                                                            
/* for movie 2, theater 2*/                                                 
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 21:00', 2, 2);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 21:00', 2, 2);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 21:00', 2, 2);
                                                                            
/*                                                                          
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 21:00', 2, 2);
*/                                                                          
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 21:00', 2, 2);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 21:00', 2, 2);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 10:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 13:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 15:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 17:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 19:00', 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 21:00', 2, 2);
                                                                            
                                                                            
                                                                            
/* for movie 3, theater 3 */                                                
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-04 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-05 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-06 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-07 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-08 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-09 18:00', 3, 3);
                                                                            
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 12:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 14:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 16:00', 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES ('2016-05-10 18:00', 3, 3);


/* FILL REPETOIRE OF LASSES CINEMA WITH TWO MOVIES */
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,1);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(1,2);

/* FILL REPETOIRE OF LISAS CINEMA WITH TWO MOVIES */
INSERT INTO repertoire_movies (repertoire,movies) VALUES(2,3);
INSERT INTO repertoire_movies (repertoire,movies) VALUES(2,4);

