FROM ubuntu as ubuntu
RUN apt-get update
RUN apt-get install -y curl
RUN curl 'https://www.dropbox.com/s/0u8ioe59nl3rq7r/imdb.sql?dl=0' -L -o imdb.sql
FROM mysql:8
COPY --from=ubuntu /imdb.sql /docker-entrypoint-initdb.d/imdb.sql
