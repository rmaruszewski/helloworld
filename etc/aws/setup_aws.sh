#!/bin/bash

# Setup database
aws cloudformation deploy --template-file cf_database.yml --stack-name db --region eu-central-1
endpoint=`aws cloudformation describe-stacks --stack-name db --region eu-central-1 --output text --query Stacks[*].Outputs[*].OutputValue`
mvn liquibase:update -Ddatabase.endpoint.address=${endpoint}

# Setup Elastic Beanstalk
eb init helloworld --platform "Corretto 8 running on 64bit Amazon Linux 2" --region eu-central-1
eb create green
eb setenv DATABASE_ENDPOINT=${endpoint} SERVER_PORT=5000
eb clone green --clone_name blue
eb status green | grep CNAME