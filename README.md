[![My Skills](https://skillicons.dev/icons?i=java,spring,hibernate,mysql,html,bootstrap,docker&theme=light)](https://skillicons.dev)
# employees-directory

An employee directory example.

## Usage

1. Rebuild the project:
```
mvn clean install
```
2. Build and run docker containers:
```
docker compose up -d
```
3. Apply [new-employee-directory.sql](./new-employee-directory.sql) script to MySQL.

Default users:

![users](/src/main/resources/static/images/users.jpg?raw=true)

5. UI: http://localhost:8080/

![screenshot](/src/main/resources/static/images/screenshot.jpg?raw=true)

6. HAL Explorer: http://localhost:8080/explorer/index.html#uri=/api/employees

![screenshot2](/src/main/resources/static/images/screenshot2.jpg?raw=true)

## Attributions

Icon by <a href="https://goodstuffnononsense.com/hand-drawn-icons/space-icons/?ref=svgrepo.com" target="_blank">Good Stuff No Nonsense</a> in CC Attribution License via <a href="https://www.svgrepo.com/" target="_blank">SVG Repo</a>
