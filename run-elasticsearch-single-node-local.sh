#!/bin/bash

ip="$(ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')"

docker run -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node"  \
  -e "network.bind_host=0.0.0.0" \
  -e "network.host=localhost" \
  docker.elastic.co/elasticsearch/elasticsearch:7.4.0

#
