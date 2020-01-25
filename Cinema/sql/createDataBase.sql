DROP TABLE IF EXIST halls;
DROP TABLE IF EXIST movies;
DROP TABLE IF EXIST projections;
DROP TABLE IF EXIST projectionTypes;
DROP TABLE IF EXIST seats;
DROP TABLE IF EXIST tickets;
DROP TABLE IF EXIST users;


CREATE TABLE halls(
    id INTEGER autoincrement primary key,
    name TEXT NOT NULL
)



CREATE TABLE movies(
    id INTEGER primary key,
    name VARCHAR(30) NOT NULL,
    directors VARCHAR(MAX) NOT NULL,
    actors VARCHAR(MAX) NOT NULL,
    genres VARCHAR(MAX) NOT NULL,
    duration INTEGER NOT NULL,
    distributor VARCHAR(30) NOT NULL,
    originCountry VARCHAR(40) NOT NULL,
    year INTEGER NOT NULL,
    overview VARCHAR(MAX) NOT NULL
);

CREATE TABLE projectionTypes(
    id INTEGER PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

CREATE TABLE users(
    username VARCHAR(30) UNIQUE PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    registrationDate DATE NOT NULL,
    role VARCHAR(5) NOT NULL DEFAULT 'USER'
);

CREATE TABLE projections(
    id INTEGER primary key,
    movie INTEGER NOT NULL,
    projectionType INTEGER NOT NULL,
    hall INTEGER NOT NULL,
    datetime DATETIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    admin VARCHAR(30) NOT NULL,
    FOREIGN KEY(movie) REFERENCES movies(id) ON DELETE RESTRICT,
    FOREIGN KEY(projectionType) REFERENCES projectionTypes(id) ON DELETE RESTRICT,
    FOREIGN KEY(hall) REFERENCES hall(id) ON DELETE RESTRICT,
    FOREIGN KEY(admin) REFERENCES users(username) ON DELETE RESTRICT
);



CREATE TABLE seats(
    num INTEGER PRIMARY KEY,
    hall INTEGER NOT NULL,
    available BIT DEFAULT '1',
    FOREIGN KEY(hall) REFERENCES hall(id)
);

CREATE TABLE tickets(
    id INTEGER PRIMARY KEY,
    projection INTEGER NOT NULL,
    seat INTEGER UNIQUE NOT NULL,
    tdate DATE NOT NULL,
    tuser INTEGER NOT NULL,
    FOREIGN KEY(projection) REFERENCES projections(id) ON DELETE RESTRICT,
    FOREIGN KEY(seat) REFERENCES seats(id) ON DELETE RESTRICT,
    FOREIGN KEY(tuser) REFERENCES users(username) ON DELETE RESTRICT,
);




