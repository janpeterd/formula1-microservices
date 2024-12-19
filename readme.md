# Formula 1 Microservices

![Frontend GP page](./images/frontend_gp_page.png)

- Documentatie:

  - Beschrijving van het gekozen thema, je microservices en andere componenten zoals gateways in lijst en schema, en je uitbreidingen + link naar de zaken die hosted zijn op GitHub README.md
  - Aantoonbare werking van alle endpoints door screenshots van Postman requests op GitHub README.md

## Thema

Ik heb gekozen om een microservices project te maken rond Formule 1. Hiervoor
heb ik 3 services gemaakt. De eerste service bevat **'Drivers'** (chauffeurs),
de tweede bevat **Teams** en de laatste **Grand Prix**. Ik heb voor deze
entiteiten gekozen, omdat er hiervoor wel wat interactie nodig is tussen de
verschillende services (team heeft drivers nodig, GP (Grand Prix) heeft zowel
teams en drivers nodig, etc.), wat het interessant en leerrijk maakte voor dit
project.

Bepaalde endpoints van deze services kunnen bereikt worden via de
**gateway-service**. Deze gateway bevat ook een implementatie van OAuth2 met
Google Account.

Ik wou ook voor elke service wat foto's opslaat (om een mooiere frontend te
maken), maar ik besefte dat het in een microservices-applicatie niet echt het
plan is om in elke service foto's op te slaan. In de realiteit zal er een
andere centrale service (zoals _S3-buckets_) gebruikt worden om media in op te
slaan, maar aangezien ik hier geen toegang tot had heb ik besloten om dit
gedrag wel te centraliseren in een service die noemt **image-service**. In deze
service staan alle foto's opgeslagen en de frontend gebruikt deze om foto's op
te halen.

Zoals hierboven al kort vermeld heb ik ook **frontend** gemaakt. Deze heb ik
gemaakt met behulp van **React**. Op deze frontend wordt heel wat gegevens uit
de services weergegeven en kan iemand met login toegang ook Grand Prix aanmaken
via een formulier. Om dit klaar te krijgen was natuurlijk ook Google
OAuth2-login nodig op de frontend.

Alle services kunnen zeer makkelijk gedeployed worden, door middel van
_docker_. Er is ook een _docker-compose_-bestand dat alle services samen
opstart.

Als laatste heb ik Prometheus, Grafana toegevoegd aan de stack. Deze
applicaties worden via docker mee met al de rest gedeployed. Beiden zijn nuttig
om data te visualiseren, maar omdat ik niet blij was met de beschikbare data in
Prometheus heb ik nog twee extra docker applicaties toegevoegd, namelijk
_cadvisor_ en _node_exporter_. Cadvisor is verantwoordelijk voor het verzamelen
van data over de Docker Containers en _node_exporter_ verzamelt data over het
host-systeem.

## Componenten en informatie

Services:

- Driver service
  - Port: 8081
  - Database `mysql`
  - `DriverService`-klasse volledig getest
- Grand Prix service
  - Port: 8082
  - Database `mysql`
  - `GpService`-klasse volledig getest
- Team service
  - Port: 8080
  - Database `mongodb`
  - `TeamService`-klasse volledig getest
- Gateway
  - Port: 8083
- Image service
  - Port: 8084

Extra:

- `docker-compose`
- `Prometheus` monitoring
- `Grafana` visualisatie
- `Frontend (React)`
  - Port: 5173 (`npm run dev`)

## Postman requests

Hieronder zie je de API in werking via HTTP-aanvragen die ik via Postman uitgevoerd heb.

### Driver service

All drivers:
![Get All Drivers](./images/get_drivers.png)
Add driver:
![Post Driver](./images/post_driver.png)
Update driver:
![Put Driver](./images/put_driver.png)
Get driver by code:
![Get Driver](./images/get_driver_code.png)

### Team service

All teams:
![Get All Teams](./images/get_teams.png)
Add team:
![Post Team](./images/post_team.png)
Get team by code:
![Get Team](./images/get_team_code.png)

### Grand Prix service

All Grand Prix:
![Get All Grand Prix](./images/get_gps.png)
Add Grand Prix:
![Post Grand Prix](./images/post_gp.png)
Update Grand Prix:
![Put Grand Prix](./images/put_gp.png)
Get Grand Prix by code:
![Get Grand Prix](./images/get_gp_id.png)
Delete Grand Prix by code
![Delete Grand Prix](./images/delete_gp.png)
en na het verwijderen returned de API-call een 404 http-code.
![Grand Prix after delete](./images/get_gps_after_delete.png)

### Image service

Image uploaden:
![Post Image](./images/post_image.png)
Image openen:
![Get Image](./images/get_image.png)
