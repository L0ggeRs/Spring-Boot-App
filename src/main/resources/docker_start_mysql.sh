#!/usr/bin/env bash

sudo docker run \
  --rm \
  --name imdb \
  --publish 3306:3306 \
  --env MYSQL_DATABASE=imdb \
  --env MYSQL_ROOT_PASSWORD=secret \
  --volume "$(pwd)/sql/imdb.sql:/docker-entrypoint-initdb.d/imdb.sql" \
  mysql:8

#Indítási probléma esetén
#ha foglalt a 3306 port -> sudo netstat -nlpt | grep 3306 -> ha mysql foglalja -> sudo service mysql stop -> docker script futtatása -> App start-> sudo service mysql restart -> maven - clean -> App start
