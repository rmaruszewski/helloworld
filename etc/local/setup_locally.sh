#!/bin/bash

# Setup database
database_endpoint="${DATABASE_ENDPOINT:-localhost}"
database_name="${DATABASE_NAME:-helloworld}"
database_username="${DATABASE_USERNAME:-postgres}"
database_password="${DATABASE_PASSWORD:-postgres}"

psql postgresql://${database_username}:${database_password}@${database_endpoint} -c "CREATE DATABASE helloworld WITH owner ${database_username} encoding 'UTF8';"
mvn liquibase:update -Ddatabase.endpoint.address=${database_endpoint}
