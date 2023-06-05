drop table if exists users cascade;

CREATE TABLE IF NOT EXISTS films(
    film_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar,
    description varchar(200),
    rating varchar,
    release_date date,
    duration integer
);

CREATE TABLE IF NOT EXISTS users(
    user_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar,
    login varchar UNIQUE NOT NULL,
    email varchar UNIQUE NOT NULL,
    birthday date
);

CREATE TABLE IF NOT EXISTS genre(
    genre_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS film_genre(
    film_id integer PRIMARY KEY REFERENCES films (film_id),
    genre_id integer REFERENCES genre (genre_id)
);

CREATE TABLE IF NOT EXISTS likes(
    film_id integer PRIMARY KEY REFERENCES film (film_id),
    user_id integer REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS friends(
    follower_user_id integer PRIMARY KEY REFERENCES users (user_id),
    followed_user_id integer REFERENCES users (user_id),
    status varchar
);