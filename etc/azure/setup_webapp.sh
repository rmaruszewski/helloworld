#!/bin/bash -x

db_type=${1:-postgres}
resource_group="helloworld-rg"
database_server_name="rmaruszewski-helloworld-db"
plan_name="helloworld-plan"
app_name="rmaruszewski-helloworld-app"

source ./set_db_connection_vars.sh ${db_type} ${database_server_name} ${resource_group}

az appservice plan create --name ${plan_name} --resource-group ${resource_group} --sku FREE
az webapp create --name ${app_name} --resource-group ${resource_group} --plan ${plan_name} --runtime "java|1.8|Java SE|8"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.endpoint="${database_endpoint}"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.username="${database_username}"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.password="${database_password}"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.type="${db_type}"
az webapp deploy --resource-group ${resource_group} --name ${app_name} --type jar --src-path ../../target/helloworld-1.0-SNAPSHOT.jar

az webapp log config --application-logging filesystem --level verbose --name ${app_name} --resource-group ${resource_group}