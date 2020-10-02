# i40-aas-data-manager-spring
An implementation of the data-manager based on spring
# data-manager

The data manager offers an REST API for handling AAS-Objects, persists them into a Postgres DB and forwards requests to the responsible application connector services.

After running the service the API-Docs can be found at:
`http://localhost:2001/swagger-ui.html`

Prerequisite: [Install Maven](https://maven.apache.org/install.html)

Build jar (.jar can be found in /target folder)
`mvn install -DskipTests`

Build docker container:
`docker build -t i40aas/data-manager .`

Run container
`docker run -p 2001:2001 i40aas/data-manager`

Application properties can be found under /src/main/resources
