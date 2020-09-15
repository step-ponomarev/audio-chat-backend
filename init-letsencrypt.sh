#!/bin/bash

domains=(example.org)
domain=example.org
rsa_key_size=4096
data_path="./data/certbot"

echo "### Creating dummy certificate for $domains ..."
path="$data_path/conf/live/$domain"
mkdir -p $path
openssl req -x509 -nodes -newkey rsa:${rsa_key_size} -days 365 \
    -keyout "$path/privkey.pem" \
    -out "$path/fullchain.pem"