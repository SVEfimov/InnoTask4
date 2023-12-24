DROP TABLE IF EXISTS LOGINS;
DROP TABLE IF EXISTS USERS;

CREATE TABLE IF NOT EXISTS USERS (
                                     id serial  primary key ,
                                     username varchar(255) UNIQUE not null ,
                                     fio varchar(255) not null
                                 );
 CREATE TABLE IF NOT EXISTS LOGINS (
                                      id serial  primary key ,
                                      access_date timestamp,
                                      user_id bigint references USERS(id),
                                      application varchar(255) not null
                                  );


