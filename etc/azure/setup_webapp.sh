#!/bin/bash -x

resource_group="helloworld-rg"
location="West Europe"
database_server_name="rmaruszewski-helloworld-db"
plan_name="helloworld-plan"
app_name="rmaruszewski-helloworld-app"
service_name="helloworld-service"

#az group create --name ${resource_group} --location "${location}"
#az extension update --name spring-cloud
#
database_endpoint=`az postgres server show --resource-group ${resource_group} --name ${database_server_name} --query fullyQualifiedDomainName -o tsv`
database_username="postgres@${database_server_name}"
database_password=${POSTGRES_ADMIN_DB_PASSWORD}
#
#az spring-cloud create --name ${service_name} --resource-group ${resource_group} --sku Basic
#az spring-cloud app create --name ${app_name} --resource-group ${resource_group} --service ${service_name}
#az spring-cloud app deploy --name ${app_name} --artifact-path ../../target/helloworld-1.0-SNAPSHOT.jar \
#  --resource-group ${resource_group} --service ${service_name} \
#  --env database.endpoint=${database_endpoint} database.username=${database_username} database.password=${database_password}
#  # --jvm-options="-Xms2048m -Xmx2048m"

az appservice plan create --name ${plan_name} --resource-group ${resource_group} --sku FREE
az webapp create --name ${app_name} --resource-group ${resource_group} --plan ${plan_name} --runtime "java|1.8|Java SE|8"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.endpoint="${database_endpoint}"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.username="${database_username}"
az webapp config appsettings set --name ${app_name} --resource-group ${resource_group} --settings database.password="${database_password}"
az webapp deploy --resource-group ${resource_group} --name ${app_name} --type jar --src-path ../../target/helloworld-1.0-SNAPSHOT.jar
