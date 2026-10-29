# Module 9: Spring AI — Java Full Stack

A hands-on Spring Boot project exploring Generative AI integration using Spring AI and locally running Ollama models.

## Overview

This project demonstrates how to integrate AI capabilities into a Java Spring Boot application using Spring AI. It uses Ollama to run AI models locally, without requiring a paid AI API.

## Technologies Used

- **Java:** 21
- **Spring Boot:** 4.1.2
- **Spring AI Ollama Starter:** 2.0.2
- **Maven:** Maven Wrapper
- **Chat Model:** `qwen2.5:3b`
- **Embedding Model:** `nomic-embed-text`
- **IDE:** IntelliJ IDEA

## Concepts Covered

### 1. ChatClient and Generative AI

Uses Spring AI's `ChatClient` to send prompts to a locally running AI model and return generated responses.

### 2. Embeddings

Converts text into numerical vectors using an embedding model.

### 3. Vector Search

Compares text embeddings using cosine similarity to find semantically similar content.

### 4. Retrieval-Augmented Generation (RAG)

Retrieves relevant information from a small knowledge base and provides it to the AI model as context for answering questions.

### 5. Spring AI Advisors

Uses `SimpleLoggerAdvisor` to add logging behavior around AI interactions.

### 6. Tool Calling

Exposes a Java method using Spring AI's `@Tool` annotation, allowing the AI model to request a tool call. The demonstration checks sample job application statuses.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/hello` | Generates an AI response |
| GET | `/embed` | Generates an embedding for text |
| GET | `/vector-search` | Finds similar documents |
| GET | `/rag` | Retrieves context and generates an answer |

### Example Requests

**Generate an AI response**

`http://localhost:8080/hello?message=Explain%20Spring%20Boot`

**Generate a text embedding**

`http://localhost:8080/embed?text=Java%20dependency%20injection`

**Search similar documents**

`http://localhost:8080/vector-search?query=How%20do%20I%20build%20Java%20applications`

**Ask a question using RAG**

`http://localhost:8080/rag?question=What%20statuses%20can%20I%20use`

The job application tool is demonstrated through the `/hello` endpoint using prompts that request the tool to check a company's status.

## Prerequisites

Install the following before running the project:

1. Java JDK 21
2. IntelliJ IDEA or another Java IDE
3. Ollama
4. Git, if you want to upload the project to GitHub

## Local AI Model Setup

Install the required Ollama models:

```powershell
ollama pull qwen2.5:3b
ollama pull nomic-embed-text
```

Verify that the models are installed:

```powershell
ollama list
```

Ensure the Ollama service is running before starting the Spring Boot application.

## Configuration

In `src/main/resources/application.properties`, configure the models:

```properties
spring.ai.ollama.chat.model=qwen2.5:3b
spring.ai.ollama.embedding.model=nomic-embed-text
logging.level.org.springframework.ai.chat.client.advisor=DEBUG
```

Keep any other required configuration already present in your project.

## Run the Application

Open a terminal in the project root.

On Windows PowerShell, run:

```powershell
.\mvnw.cmd clean verify
```

Start the application from IntelliJ by running `Module9Application`.

Alternatively, after building the project, run the packaged JAR:

```powershell
java -jar target/module_9-0.0.1-SNAPSHOT.jar
```

Open `http://localhost:8080/hello` in your browser to test the application.

## Project Structure

```text
module_9/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/module9/
│   │   │       ├── Module9Application.java
│   │   │       ├── JobApplicationTools.java
│   │   │       └── controller/
│   │   │           ├── ChatController.java
│   │   │           ├── EmbeddingController.java
│   │   │           ├── VectorSearchController.java
│   │   │           └── RagController.java
│   │   └── resources/
│   │       └── application.properties
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

*Note: The structure above reflects the classes developed during the practicals. Confirm the actual filenames in your project before publishing.*

## Learning Outcomes

By completing this project, I practiced:

- Integrating a locally running AI model into Spring Boot.
- Sending prompts and processing generated responses.
- Generating and comparing embeddings.
- Building a basic vector similarity search.
- Implementing a simple RAG workflow.
- Registering a Spring AI Advisor.
- Exposing a Java method as an AI tool.
- Building and packaging a Spring Boot application using Maven.

## Future Improvements

- Store embeddings in a persistent vector database.
- Retrieve multiple relevant document chunks for RAG.
- Connect job application tools to a real database.
- Add input validation and more comprehensive error handling.
- Create automated tests for the AI endpoints.

## Author

Java Full Stack Learning Project — Module 9: Spring AI.
