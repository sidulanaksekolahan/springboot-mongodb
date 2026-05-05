# HOW TO RUN:

---

1. Change directory to the compose folder:
```bash
cd compose
```

2. Run the docker compose command:
```bash
docker compose up mongo -d
```

3. Back to the root directory:
```bash
cd ..
```

4. Create a jar file:
```bash
./mvnw clean package
```

5. Create docker image for spring boot application:
```bash
docker build -t springboot-mongodb:v1.0 .
```

6. Go back to the compose folder:
```bash
cd compose
```

7. Stop the docker compose:
```bash
docker compose down
```

8. Run the docker compose command:
```bash
docker compose up -d
```

