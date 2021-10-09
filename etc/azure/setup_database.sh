#!/bin/bash

resource_group="helloworld"
location="West Europe"
database_name="rmaruszewskiHelloworldDb"

az group create --name ${resource_group} --location "${location}"
az postgres server create --resource-group ${resource_group} --name ${database_name} --location "${location}" --admin-user postgres --admin-password ${POSTGRES_ADMIN_DB_PASSWORD} --sku-name B_Gen5_1
az postgres server firewall-rule create --resource-group ${resource_group} --server ${database_name} --name AllowAllIps --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
az postgres db create --name helloworld --resource-group ${resource_group} --server-name ${database_name}
endpoint=`az postgres server show --resource-group ${resource_group} --name ${database_name} --query fullyQualifiedDomainName -o tsv`
mvn ../../pom.xml liquibase:update -Ddatabase.endpoint.address=${endpoint} -Ddatabase.admin.username="postgres@${database_name}"