#!/bin/bash -x

db_type=$1
database_server_name=$2
resource_group=$3

if [ "${db_type}" = "postgres" ]; then
  database_endpoint=`az postgres server show --resource-group ${resource_group} --name ${database_server_name} --query fullyQualifiedDomainName -o tsv`
  database_username="postgres@${database_server_name}"
else
  database_endpoint=`az sql server show --resource-group ${resource_group} --name ${database_server_name} --query fullyQualifiedDomainName -o tsv`
  database_username="postgres"
fi

database_password=${POSTGRES_ADMIN_DB_PASSWORD}