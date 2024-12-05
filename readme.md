# Project Advanced Programming Topics

1. ❔ **ALGEMENE EISEN & DOCUMENTATIE** (alles samen _+60%_ op Project)

- Basics:

  - Minstens 3 GET, 1 POST, 1 PUT en 1 DELETE endpoints op een API Gateway, gebaseerd op je eigen services
  - Minstens minstens 1 keer MongoDB als databank en minstens 1 keer SQL
  - Logisch gebruik van path parameters, query parameters en body

- Documentatie:

  - Beschrijving van het gekozen thema, je microservices en andere componenten zoals gateways in lijst en schema, en je uitbreidingen + link naar de zaken die hosted zijn op GitHub README.md
  - Aantoonbare werking van alle endpoints door screenshots van Postman requests op GitHub README.md

- Deployment:

  - Docker container voor de componenten, welke automatisch door GitHub Actions opgebouwd wordt
  - Deployment van de container(s) via Docker Compose

- Security:

  - Auth op Gateway via GCP OAuth2, met secured/unsecured endpoints

- Testing:
  - Unit testing all the Service classes

2. 🔧 SUGGESTIES VOOR AANVULLINGEN: FUNCTIE

   - Maak een front-end voor je applicatie (ook in container). (+15%)
   - Zet de deployment docker-compose.yml om naar Kubernetes Manifest .yml-files (+5%)
   - Zet monitoring op met Prometheus en demonstreer met screenshots. (+20%)
   - Zet een Grafana om te gebruiken in plaats van de standaard Prometheus Expression Browser met PromQL (+15%)
   - Gebruik ClusterIP & Nodeport op een logische manier (+5%)
   - Maak en gebruik je eigen Auth service i.p.v. GCP OAuth2 (+25%)
   - Implementeer rate-limiting op je Spring Cloud Gateway (+5%)
   - Maak de interactie met minstens 1 service event-driven door gebruik te maken van een message queue zoals ActiveMQ en async (+20%)
   - Gebruik Kafka i.p.v. ActiveMQ (dit heeft twee pods nodig) (+15%)
