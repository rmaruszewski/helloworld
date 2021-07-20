#!/bin/bash

# Setup database
endpoint=localhost
username=postgres
psql -U {username} postgresql://postgres:postgres@${endpoint} -c "CREATE DATABASE helloworld WITH owner ${username} encoding 'UTF8';"
mvn liquibase:update -Ddatabase.endpoint.address=${endpoint}
