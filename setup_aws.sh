#!/bin/bash

# Setup database
aws cloudformation deploy --template-file aws/cf_database.yml --stack-name db --region eu-central-1
endpoint=`aws cloudformation describe-stacks --stack-name db --region eu-central-1 --output text --query Stacks[*].Outputs[*].OutputValue`
mvn liquibase:update -Ddatabase.endpoint.address=${endpoint}

# Run Maven

# Setup Elastic Beanstalk
eb init helloworld --platform "Corretto 8 running on 64bit Amazon Linux 2" --region eu-central-1
eb create green
eb clone green --clone_name blue
