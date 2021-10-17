#!/bin/bash

db_type=mssql

./setup_database.sh ${db_type}
./setup_webapp.sh ${db_type}
