#!/bin/bash -x

db_type=${1:-postgres}
resource_group="helloworld-rg"
location="West Europe"
database_server_name="rmaruszewski-helloworld-db"

az group create --name ${resource_group} --location "${location}"

if [ "${db_type}" = "postgres" ]; then
  az postgres server create --resource-group ${resource_group} --name ${database_server_name} --location "${location}" --admin-user postgres --admin-password ${POSTGRES_ADMIN_DB_PASSWORD} --sku-name B_Gen5_1 --storage-size 5120
  az postgres server firewall-rule create --resource-group ${resource_group} --server ${database_server_name} --name AllowAllIps --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
  az postgres db create --name helloworld --resource-group ${resource_group} --server-name ${database_server_name}
  database_endpoint=`az postgres server show --resource-group ${resource_group} --name ${database_server_name} --query fullyQualifiedDomainName -o tsv`
  database_username="postgres@${database_server_name}"
else
  az sql server create --resource-group ${resource_group} --name ${database_server_name} --location "${location}" --admin-user postgres --admin-password ${POSTGRES_ADMIN_DB_PASSWORD}
  az sql server firewall-rule create --resource-group ${resource_group} --server ${database_server_name} --name AllowAllIps --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
  az sql db create --name helloworld --resource-group ${resource_group} --server ${database_server_name}
  database_endpoint=`az sql server show --resource-group ${resource_group} --name ${database_server_name} --query fullyQualifiedDomainName -o tsv`
  database_username="postgres"
fi

mvn -f ../../pom.xml liquibase:update -P${db_type} -Ddatabase.endpoint=${database_endpoint} -Ddatabase.username=${database_username}
