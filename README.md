# tracking-number-generator

## 📁 Project Structure
tracking-number-generator/\
├── application/ # Entry point for the Spring Boot application\
├── controller/ # REST controller for /next-tracking-number\
├── dto/ # Data Transfer Objects (request/response)\
├── service/ # Business logic for generating tracking numbers\
├── validation/ # Custom validators or annotations\
├── Dockerfile # Container configuration

## 🌐 Deployed Application

The API is publicly accessible here: https://tracking-number-generator-1003214753637.asia-southeast2.run.app/swagger-ui/index.html


## ⚙️ Running Locally

### 1. pre-requisites
```bash
1. jdk 17
2. make sure port 8080 is currently not used
```

### 2. Compile
```bash
./gradlew clean build
```

### 3. Run compiled file
#### Option 1: local machine directly

```bash
java -jar build/libs/tracking-number-generator-0.0.1-SNAPSHOT.jar
```

#### Option 2: Using Docker
```bash
docker build -t tracking-number-generator .
docker run -p 8080:8080 tracking-number-generator
```

### 4. access the application through this link : http://localhost:8080/swagger-ui/index.html