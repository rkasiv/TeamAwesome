# TeamAwesome
To build Status API and service

##Deployment Steps:

###Git:
1. Add & Commit all final code changes
2. Push to remote
3. Tag your master branch: `git tag <application>-<version>` e.g. `git tag order-status-service-0.0.2`
4. Push your new tag to remote `git push --tags`

###Maven
1. Run Maven package build & verify jar file created

###Prepare the server for your jar and place on server:
####If first time application deployed create the directory to hold the jar
1. Login via Putty
2. Execute `sudo mkdir /var/data/<application directory>` to create a new directory to house your jar file. e.g. `sudo mkdir /var/data/orderstatus`
3. Execute `sudo chown <userid>:<userid> <application directory>` to change the owner of the new directory. e.g. `sudo chown ubuntu:ubuntu orderstatus`
####Place Jar on server:
1. Copy jar across into the application directory
2. Copy your environment-specific application.properties file across to same directory (if required)

###Change the version of your application in Maven in preparation for any new changes after deployment:
1. Change version in POM file.
2. Commit, and push to remote

###Run your jar
1. Execute `java -jar <jar filename>.jar` e.g. `java -jar order-status-service-0.0.2.jar` (make sure to run this command from the directory containing the jar otherwise the application.properties file will not be used)
