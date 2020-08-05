# i40-aas-data-manager-spring
An implementation of the data-manager based on spring


Prerequisite: [Install Maven](https://maven.apache.org/install.html)

Build jar (.jar can be found in /target folder)
`mvn install -DskipTests`

Build docker container:
`docker build -t i40aas/data-manager .`

Run container
`docker run -p 8080:8080 i40aas/data-manager`