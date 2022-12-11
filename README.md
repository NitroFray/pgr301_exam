# Del 1 DevOps-prinsipper

* _**Hva er utfordringene med dagens systemutviklingsprosess - og hvordan vil innføring av DevOps kunne være med på å løse disse? Hvilke DevOps prinsipper blir brutt?**_
  * Den største utfordringen med dagens systemutviklingsprosess er at kode kun kan deployes 4 ganger i året. Det bryter med flyt prinsippet i DevOps. Å vente 3 måneder for en ny feature er langt fra optimalt. Det burde ideelt sett være deployinger mye oftere. Om man kun har store og sjeldene deployinger kan selv små feil forårsake store forsinkelser.
* _**En vanlig respons på mange feil under release av ny funksjonalitet er å gjøre det mindre hyppig, og samtidig forsøke å legge på mer kontroll og QA. Hva er problemet med dette ut ifra et DevOps perspektiv, og hva kan være en bedre tilnærming?**_
  * Det gjør at det tar mye lenger tid før ny funksjonalitet blir tilgjengelig for kunde. Ny funksjonalitet burde testes, men det burde testes automatisk så langt det lar seg gjøre. Manuell testing er tidskrevende og muligens bortkastede ressurser.
* _**Teamet overleverer kode til en annen avdeling som har ansvar for drift - hva er utfordringen med dette ut ifra et DevOps perspektiv, og hvilke gevinster kan man få ved at team har ansvar for både drift- og utvikling?**_
  * Et team som har ansvar for både drift og utvikling vil ha bedre kontroll over hele systemet, noe som kan gjøre det enklere å oppdage og fikse feil. Det kan også føre til at det er enklere å ha oftere deployinger.
* _**Å release kode ofte kan også by på utfordringer. Beskriv hvilke- og hvordan vi kan bruke DevOps prinsipper til å redusere eller fjerne risiko ved hyppige leveraner.**_
  * Om man releaser kode ofte og det gjøres manuelt kan det være veldig tids-/resurskrevende. Det burde gjøres automatiske tester og releaser, og ha en rollback plan om noe ikke går etter planen.

# Del 2 - CI
**Oppgave 3)**
* Ingen kan pushe kode direkte på main branch
* Kode kan merges til main branch ved å lage en Pull request med minst en godkjenning
* Kode kan merges til main bare når feature branchen som pull requesten er basert på,
* er verifisert av GitHub Actions.

Gå inn i settings og velg branches. Trykk på `Add` knappen. Velg `main` som branch (skriv i feltet). Huk av for `Require a pull request before merging`, og deretter `Require approvals`, og velg `1` som antall godkjenninger. Huk deretter av for `Require status checks to pass before merging`, og skriv inn `build` i feltet. Trykk `Save changes`.

# Del 3 - Docker
**Oppgave 1)**

_Beskriv hva du må gjøre for å få workflow til å fungere med din DockerHub konto? Hvorfor feiler workflowen?_

For å få workflow til å fungere må man legge til sitt DockerHub brukernavn og en access token i secrets. Dette kan enkelt gjøres ved å gå inn i settings og legge til ny secret (token må selvfølgelig genereres i docker hub først)

**Oppgave 3)**

For å laste opp container image til eget ECR repo må det legges til følgende secrets i repoet:
`AWS_ACCESS_KEY_ID` og `AWS_SECRET_ACCESS_KEY`. 

De må genereres og hentes fra egen bruker i AWS, fra Security Credentials.

Dersom ECR repo er i en annen region enn `eu-west-1` må det også endres i `docker.yml` filen i workflows mappa.

# Del 5 - Terraform og CloudWatch Dashboards

**Oppgave 1)**

Terraform forsøker å opprette en bucket fordi den ikke har noe statefil. Det løses ved å legge til koden under i `provider.tf`, og opprette en S3 bucket med samme navn.

```
backend "s3" {
  bucket = "1033-terraform-state"
  key    = "1033.state"
  region = "eu-west-1"
}
```