# README #

### Compiling and running the project locally

##### Prerequisites

* Maven
* Java 8
* PostgreSQL client (psql command)
* PostgreSQL server
* curl

##### Configuring database

The database is first created with the `psql` tool.
Then the table structure is configured using MVN with the LiquiBase plugin.

The following script executes both of these steps:

``setup_locally.sh``


Edit the script and set the database endpoint if the PostgreSQL server is not running on the localhost. 

##### Compiling the project and running tests

Execute the following command:

``mvn verify``

##### Running the project and testing the API

Execute the following command to run the application locally:



Then test if it works with these ``curl`` commands:

``curl -v --header "Content-Type: application/json" --request PUT --data '{"dateOfBirth":"2021-07-20"}' http://localhost:5000/hello/myuser``

### Deploying on AWS

##### Prerequisites

* AWS CLI (https://aws.amazon.com/cli/)
* AWS Elastic Bean CLI (https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3.html)
