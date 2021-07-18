#!/bin/bash

eb init helloworld --platform "Corretto 8 running on 64bit Amazon Linux 2" --region eu-central-1
eb create green
eb clone green --clone_name blue
