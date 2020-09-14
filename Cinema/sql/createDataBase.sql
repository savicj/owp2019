DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS projections;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS halls;
DROP TABLE IF EXISTS projectionTypes;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS users;


CREATE TABLE halls(
    id INTEGER primary key,
    name VARCHAR(10) NOT NULL,
    projectionTypes VARCHAR(500) NOT NULL
);

INSERT INTO halls(name, projectionTypes) VALUES('hall 1', 'twodim,threedim');
INSERT INTO halls(name, projectionTypes) VALUES('hall 2', 'twodim,fourdim');
INSERT INTO halls(name, projectionTypes) VALUES('hall 3', 'threedim,fourdim');
INSERT INTO halls(name, projectionTypes) VALUES('hall 4', 'threedim,fourdim');
INSERT INTO halls(name, projectionTypes) VALUES('hall 5', 'twodim,fourdim');


CREATE TABLE movies(
    id INTEGER primary key,
    name VARCHAR(30) NOT NULL,
    directors VARCHAR(500) NOT NULL,
    actors VARCHAR(500) NOT NULL,
    genres VARCHAR(500) NOT NULL,
    duration INTEGER NOT NULL,
    distributor VARCHAR(30) NOT NULL,
    originCountry VARCHAR(40) NOT NULL,
    year INTEGER NOT NULL,
    overview VARCHAR(500) NOT NULL,
    deleted BOOLEAN
);

INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Parasite', 'Bong Joon Ho', 'Kang-ho Song, Sun-kyun Lee, Yeo-jeong Jo', 'Comedy, Drama, Thriller', 132, 'CJ Entertainment', 'South Corea', 2019, 'A poor family, the Kims, con their way into becoming the servants of a rich family, the Parks. But their easy life gets complicated when their deception is threatened with exposure.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Black Widow', 'Cate Shortland', 'Florence Pugh, Rachel Weisz, Scarlett Johansson', ' Action, Adventure, Sci-Fi', 160, 'Marvel Studios', 'USA', 2020, 'A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('A Quiet Place Part II', 'John Krasinski', 'Emily Blunt, Millicent Simmonds, Cillian Murphy', 'Horror, Thriller', 97, 'Buffalo FilmWorks', 'USA', 2020, 'Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize the creatures that hunt by sound are not the only threats lurking beyond the sand path.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Mulan', 'Niki Caro', 'Yifei Liu, Donnie Yen, Jet Li', 'Action, Adventure, Drama', 115, 'Walt Disney Pictures', 'USA', 2020, 'A young Chinese maiden disguises herself as a male warrior in order to save her father. A live-action feature film based on Disneys Mulan.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Bloodshot', 'Dave Wilson', 'Vin Diesel, Eiza González, Sam Heughan', 'Action, Drama, Sci-Fi', 109, 'Sony Pictures Entertainment (SPE)', 'USA', 2020, 'Ray Garrison, a slain soldier, is re-animated with superpowers.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Bad Boys for Life', 'Adil El Arbi, Bilall Fallah', 'Will Smith, Martin Lawrence, Vanessa Hudgens', 'Action, Comedy, Crime', 124, 'Columbia Pictures', 'USA', 2020, 'The Bad Boys Mike Lowrey and Marcus Burnett are back together for one last ride in the highly anticipated Bad Boys for Life.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Jungle Cruise', 'Jaume Collet-Serra', 'Emily Blunt, Dwayne Johnson, Jesse Plemons', 'Action, Adventure, Comedy', 90, 'Davis Entertainment', 'USA', 2020, 'Based on Disneylands theme park ride where a small riverboat takes a group of travelers through a jungle filled with dangerous animals and reptiles but with a supernatural element.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('The Kings Man', 'Matthew Vaughn', 'Gemma Arterton, Matthew Goode, Aaron Taylor-Johnson', 'Action, Adventure, Comedy', 117, '20th Century Fox Film Corporation', 'UK', 2020, 'As a collection of historys worst tyrants and criminal masterminds gather to plot a war to wipe out millions, one man must race against time to stop them.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('No Time to Die', 'Cary Joji Fukunaga', 'Ana de Armas, Daniel Craig, Léa Seydoux', 'Action, Adventure, Thriller', 163, 'Metro-Goldwyn-Mayer (MGM)', 'UK', 2020, 'James Bond has left active service. His peace is short-lived when Felix Leiter, an old friend from the CIA, turns up asking for help, leading Bond onto the trail of a mysterious villain armed with dangerous new technology.', false);
INSERT INTO movies(name, directors, actors, genres, duration, distributor, originCountry, year, overview, deleted) 
	VALUES('Wonder Woman 1984', 'Patty Jenkins', 'Pedro Pascal, Gal Gadot, Robin Wright', 'Action, Adventure, Fantasy', 138, 'DC Entartainment', 'USA', 2020, 'A sequel to the 2017 superhero film "Wonder Woman".', false);



CREATE TABLE users(
	id INTEGER PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL,
    registrationDate DATE NOT NULL,
    role VARCHAR(5) NOT NULL DEFAULT 'USER',
    deleted BOOL NOT NULL
);

INSERT INTO users(username, password, registrationDate, role, deleted) VALUES('a', 'a', '2020-01-03 10:30:45.000', 'ADMIN', false);
INSERT INTO users(username, password, registrationDate, role, deleted) VALUES('admin', 'admin', '2020-01-14 10:30:45.000', 'ADMIN', false);
INSERT INTO users(username, password, registrationDate, role, deleted) VALUES('b', 'b', '2020-02-15 10:30:45.000', 'USER', false);
INSERT INTO users(username, password, registrationDate, role, deleted) VALUES('user', 'user', '2020-03-05 10:30:45.000', 'USER', false);

--UPDATE USERS DA SREDIM FORMAT VREMENA NEKAKO

CREATE TABLE projections(
    id INTEGER primary key,
    movie INTEGER NOT NULL,
    projectionType VARCHAR(10) NOT NULL,
    hall INTEGER NOT NULL,
    datetime DATETIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    admin VARCHAR(30) NOT NULL,
    deleted BOOLEAN,
    FOREIGN KEY(movie) REFERENCES movies(id) ON DELETE RESTRICT,
    FOREIGN KEY(hall) REFERENCES halls(id) ON DELETE RESTRICT,
    FOREIGN KEY(admin) REFERENCES users(username) ON DELETE RESTRICT
);

INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 1, '2020-09-13 16:00:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 2, '2020-09-13 15:50:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'threedim', 1, '2020-09-13 18:20:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'fourdim', 2, '2020-09-13 18:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'threedim', 3, '2020-09-13 19:25:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'threedim', 3, '2020-09-13 21:45:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 3, '2020-09-13 16:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'fourdim', 2, '2020-09-13 22:45:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 2, '2020-09-13 20:25:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'twodim', 1, '2020-09-13 20:00:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'threedim', 4, '2020-09-13 19:45:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 5, '2020-09-13 16:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'fourdim', 4, '2020-09-13 22:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 5, '2020-09-13 18:25:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'twodim', 1, '2020-09-13 22:30:00.000', 390, 'a', false);
	


INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'twodim', 1, '2020-09-14 15:40:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(6, 'twodim', 1, '2020-09-14 18:30:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'threedim', 1, '2020-09-14 20:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'fourdim', 2, '2020-09-14 17:00:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'twodim', 2, '2020-09-14 19:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'twodim', 2, '2020-09-14 21:30:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'fourdim', 3, '2020-09-14 16:30:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'fourdim', 3, '2020-09-14 19:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'threedim', 3, '2020-09-14 21:35:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'threedim', 3, '2020-09-14 23:15:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'threedim', 4, '2020-09-14 19:45:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'fourdim', 4, '2020-09-14 22:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 5, '2020-09-14 16:00:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 5, '2020-09-14 18:25:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'twodim', 1, '2020-09-14 22:30:00.000', 390, 'a', false);
	

	
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 1, '2020-09-15 15:40:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'twodim', 1, '2020-09-15 18:00:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'threedim', 1, '2020-09-15 20:05:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'twodim', 1, '2020-09-15 21:45:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 2, '2020-09-15 17:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'twodim', 2, '2020-09-15 19:50:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'fourdim', 2, '2020-09-15 22:35:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'fourdim', 3, '2020-09-15 18:20:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'threedim', 3, '2020-09-15 20:35:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'threedim', 3, '2020-09-15 22:50:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'threedim', 4, '2020-09-15 18:25:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'fourdim', 4, '2020-09-15 20:30:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'threedim', 4, '2020-09-15 22:20:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 5, '2020-09-15 19:45:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 5, '2020-09-15 21:30:00.000', 450, 'a', false);



INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 1, '2020-09-16 15:40:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'twodim', 1, '2020-09-16 18:00:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'threedim', 1, '2020-09-16 20:05:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'twodim', 1, '2020-09-16 21:45:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(1, 'twodim', 2, '2020-09-16 17:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'twodim', 2, '2020-09-16 19:50:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'fourdim', 2, '2020-09-16 22:35:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'fourdim', 3, '2020-09-16 18:20:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'threedim', 3, '2020-09-16 20:35:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'threedim', 3, '2020-09-16 22:50:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(6, 'threedim', 4, '2020-09-16 18:15:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(3, 'fourdim', 4, '2020-09-16 20:30:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'threedim', 4, '2020-09-16 22:20:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 5, '2020-09-16 19:45:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 5, '2020-09-16 21:30:00.000', 450, 'a', false);


INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'twodim', 1, '2020-09-17 15:30:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'twodim', 1, '2020-09-17 18:20:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(6, 'threedim', 1, '2020-09-17 20:25:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'twodim', 1, '2020-09-17 22:40:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(6, 'twodim', 2, '2020-09-17 17:25:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'twodim', 2, '2020-09-17 19:40:00.000', 390, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(4, 'fourdim', 2, '2020-09-17 22:35:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'fourdim', 3, '2020-09-17 18:20:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'threedim', 3, '2020-09-17 20:30:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(10, 'threedim', 3, '2020-09-17 22:30:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(8, 'threedim', 4, '2020-09-17 18:25:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(7, 'fourdim', 4, '2020-09-17 20:30:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(9, 'threedim', 4, '2020-09-17 22:20:00.000', 410, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(2, 'fourdim', 5, '2020-09-17 19:25:00.000', 450, 'a', false);
INSERT INTO projections(movie, projectionType, hall, datetime, price, admin, deleted) VALUES(5, 'fourdim', 5, '2020-09-17 22:15:00.000', 450, 'a', false);



CREATE TABLE seats(
    num VARCHAR(3),
    hall INTEGER NOT NULL,
    available BIT DEFAULT '1',
    PRIMARY KEY(num, hall),
    FOREIGN KEY(hall) REFERENCES halls(id)
);

INSERT INTO seats(num, hall, available) VALUES('1A', 1, 1);
INSERT INTO seats(num, hall, available) VALUES('2A', 1, 1);
INSERT INTO seats(num, hall, available) VALUES('3A', 1, 1);
INSERT INTO seats(num, hall, available) VALUES('1B', 1, 1);
INSERT INTO seats(num, hall, available) VALUES('2B', 1, 1);


INSERT INTO seats(num, hall, available) VALUES('1A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('2A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('3A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('4A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('5A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('6A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('7A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('8A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('9A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('10A', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('1B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('2B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('3B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('5B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('6B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('7B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('8B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('9B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('10B', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('1C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('2C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('3C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('4C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('5C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('6C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('7C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('8C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('9C', 2, 1);
INSERT INTO seats(num, hall, available) VALUES('10C', 2, 1);

INSERT INTO seats(num, hall, available) VALUES('1A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('2A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('3A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('4A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('5A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('6A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('7A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('8A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('9A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('10A', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('1B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('2B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('3B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('5B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('6B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('7B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('8B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('9B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('10B', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('1C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('2C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('3C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('4C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('5C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('6C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('7C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('8C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('9C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('10C', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('1D', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('2D', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('3D', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('4D', 3, 1);
INSERT INTO seats(num, hall, available) VALUES('5D', 3, 1);


INSERT INTO seats(num, hall, available) VALUES('1A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('2A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('3A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('4A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('5A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('6A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('7A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('8A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('9A', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('1B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('2B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('3B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('5B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('6B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('7B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('8B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('9B', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('10C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('2C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('3C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('4C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('5C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('6C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('7C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('8C', 4, 1);
INSERT INTO seats(num, hall, available) VALUES('9C', 4, 1);

INSERT INTO seats(num, hall, available) VALUES('1A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('2A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('3A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('4A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('5A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('6A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('7A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('8A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('9A', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('1B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('2B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('3B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('5B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('6B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('7B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('8B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('9B', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('1C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('2C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('3C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('4C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('5C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('6C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('7C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('8C', 5, 1);
INSERT INTO seats(num, hall, available) VALUES('9C', 5, 1);



DROP TABLE IF EXISTS tickets;

CREATE TABLE tickets(
    id INTEGER PRIMARY KEY,
    projection INTEGER NOT NULL,
    seat INTEGER NOT NULL,
    tdate DATE NOT NULL,
    tuser VARCHAR(500) NOT NULL
--    FOREIGN KEY(projection) REFERENCES projections(id)
--    FOREIGN KEY(seat, hall) REFERENCES seats(num, hall),
--    FOREIGN KEY(tuser) REFERENCES users(username)
);
	

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(1, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(1, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(2, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(2, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(2, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(2, '4B', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(3, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(3, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(4, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(4, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(5, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(5, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(6, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(6, '1B', '2020-07-02 18:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(6, '2B', '2020-07-02 18:14:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(7, '6C', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(7, '7C', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(7, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(7, '4B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(8, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(8, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(9, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(9, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(10, '3A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(10, '2B', '2020-07-02 12:10:00.000', 'user');



INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(11, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(11, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(12, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(12, '7C', '2020-18-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(12, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(12, '4B', '2020-10-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(13, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(13, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(14, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(14, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(15, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(15, '2B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(16, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(16, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(17, '2A', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(17, '1A', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(17, '2B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(17, '1B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(18, '1A', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(18, 'AB', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(19, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(19, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(20, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(20, '7B', '2020-07-02 12:10:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(21, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(21, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(22, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(22, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(22, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(22, '4B', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(23, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(23, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(24, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(24, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(25, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(25, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(26, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(26, '1B', '2020-07-02 18:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(26, '2B', '2020-07-02 18:14:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(27, '6C', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(27, '7C', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(27, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(27, '4B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(28, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(28, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(29, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(29, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(30, '2A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(30, '2B', '2020-07-02 12:10:00.000', 'user');



INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(31, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(31, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(32, '1A', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(32, '2A', '2020-18-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(32, '1B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(32, '2B', '2020-10-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(33, '2A', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(33, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(34, '1C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(34, '2A', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(35, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(35, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(36, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(36, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(37, '6C', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(37, '7C', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(37, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(37, '4B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(38, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(38, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(39, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(39, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(40, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(40, '7B', '2020-07-02 12:10:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(41, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(41, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(42, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(42, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(42, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(42, '4B', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(43, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(43, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(44, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(44, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(45, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(45, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(46, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(46, '1B', '2020-07-02 18:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(46, '2B', '2020-07-02 18:14:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(47, '2A', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(47, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(47, '2B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(48, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(48, '3B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(49, '1A', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(49, '2B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(50, '3A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(50, '2B', '2020-07-02 12:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(51, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(51, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(52, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(52, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(52, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(52, '4B', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(53, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(53, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(54, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(54, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(55, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(55, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(56, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(56, '1B', '2020-07-02 18:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(56, '2B', '2020-07-02 18:14:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(57, '6C', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(57, '7C', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(57, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(57, '4B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(58, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(58, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(59, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(59, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(60, '3A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(60, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(60, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(60, '2B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(61, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(61, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(62, '1A', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(62, '3A', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(63, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(63, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(64, '1B', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(64, '3A', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(65, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(65, '7B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(66, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(66, '1B', '2020-07-02 18:10:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(66, '2B', '2020-07-02 18:14:00.000', 'user');


INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(67, '6C', '2020-19-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(67, '7C', '2020-19-02 16:30:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(67, '3B', '2020-18-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(67, '4B', '2020-18-02 14:05:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(68, '6C', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(68, '7B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(69, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(69, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(70, '3A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(70, '2B', '2020-07-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(71, '1A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(71, '1B', '2020-07-02 18:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(72, '6C', '2020-18-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(72, '7C', '2020-18-02 12:17:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(72, '3B', '2020-10-02 11:11:00.000', 'user');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(72, '4B', '2020-10-02 11:15:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(73, '1B', '2020-21-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(73, '2B', '2020-20-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(74, '6C', '2020-09-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(74, '4B', '2020-17-02 12:10:00.000', 'user');

INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(75, '5A', '2020-04-02 12:10:00.000', 'b');
INSERT INTO tickets(projection, seat, tdate, tuser) VALUES(75, '7B', '2020-07-02 12:10:00.000', 'user');