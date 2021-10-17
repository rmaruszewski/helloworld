#!/bin/bash -x

db_type=${1:-postgres}
resource_group="helloworld-rg"
database_server_name="rmaruszewski-helloworld-db"
app_name="helloworld-app"
service_name="helloworld-service"

az extension update --name spring-cloud

source ./set_db_connection_vars.sh ${db_type} ${database_server_name} ${resource_group}

az spring-cloud create --name ${service_name} --resource-group ${resource_group} --sku Basic
az spring-cloud app create --name ${app_name} --resource-group ${resource_group} --service ${service_name}
az spring-cloud app deploy --name ${app_name} --artifact-path ../../target/helloworld-1.0-SNAPSHOT.jar \
  --resource-group ${resource_group} --service ${service_name} \
  --env database.endpoint=${database_endpoint} database.username=${database_username} database.password=${database_password} database.type=${db_type}
#  # --jvm-options="-Xms2048m -Xmx2048m"
