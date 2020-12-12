# utily

A Domestic Utilities E-commerce to subject of Engineering Software Lab at Fatec Mogi das Cruzes.

# Configuring Database

Execute the following instructions to create database, user and grant privileges to user and database created. Everything via _psql_ CLI.

```
DROP DATABASE IF EXISTS utily_db;
CREATE DATABASE utily_db;

DROP USER IF EXISTS utily_user;
CREATE USER utily_user WITH PASSWORD 'utily_password';

GRANT ALL PRIVILEGES ON DATABASE "utily_db" to utily_user;
```
