
# MOVIES

INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion King', 122, 'Story about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Queen', 123, 'Tale about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Prince', 124, 'Epic story about lions');
INSERT INTO movie (title, length_in_minutes, description) 
	VALUES ('Lion Princess', 125, 'Old tale about lions');
	
# THEATERS

INSERT INTO theater (name) VALUES ('Bergakungen sal 01');
INSERT INTO theater (name) VALUES ('Bergakungen sal 04');
INSERT INTO theater (name) VALUES ('Bergakungen sal 05');
INSERT INTO theater (name) VALUES ('Bergakungen sal 08');


# SHOWINGS

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 1, 1);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 1, 1);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 2, 2);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 2, 2);

INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 3, 3);
INSERT INTO showing (show_date_time, movie_id, theater_id) VALUES (null, 3, 3);
