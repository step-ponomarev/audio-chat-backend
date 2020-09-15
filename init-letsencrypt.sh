#!/bin/bash

if ! [ -x "$(command -v docker-compose)" ]; then
  echo 'Error: docker-compose is not installed.' >&2
  exit 1
fi

domains=(example.org)
domain=example.org
rsa_key_size=4096
data_path="./data/certbot"
email="step.ponomarev@gmail.com" # Adding a valid address is strongly recommended
staging=0 # Set to 1 if you're testing your setup to avoid hitting request limits

echo "### Creating dummy certificate for $domains ..."
path="$data_path/conf/live/$domain"
mkdir -p $path
openssl req -x509 -nodes -newkey rsa:2048 -days 365 \
    -keyout "$path/privkey.pem" \
    -out "$path/fullchain.pem"