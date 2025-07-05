# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot lottery game application that simulates and analyzes Powerball and Mega Millions lottery games. The application provides both REST API endpoints and standalone command-line programs for ticket generation, checking, and analysis.

## Common Development Commands

### Build and Run
```bash
# Run Spring Boot application
./mvnw spring-boot:run

# Build project
./mvnw clean package

# Run tests
./mvnw test

# Run a specific main class
./mvnw exec:java -Dexec.mainClass="com.folautech.games.powerball.PowerballMain"
```

### Standalone Programs
- **Powerball**: PowerballMain, PowerballAutoMain, GeneratePowerballTickets, PowerballCheckTickets
- **Mega Millions**: PlayMegaMillionMain, GenerateMegaMillionTickets, MegaMillionCheckTickets

## Architecture Overview

### Package Structure
- `com.folautech.games.powerball/` - Powerball lottery functionality
  - REST services for fetching results
  - Ticket generation and checking
  - Interactive and automated gameplay
- `com.folautech.games.megamillion/` - Mega Millions lottery functionality
  - Similar structure to Powerball
  - Additional prediction capabilities
- `com.folautech.games.utils/` - Shared utilities
  - HttpInterceptor for logging HTTP requests
  - LocalRestClient wrapper around RestTemplate

### Key Design Patterns
1. **REST Services**: Each lottery type has its own REST service class using Spring's RestTemplate
2. **File-based Data**: Lottery numbers are stored in text files (e.g., powerball_winning_numbers.txt)
3. **Standalone Executables**: Each major function has its own main class for independent execution
4. **Spring Boot Integration**: The application can run as a web service or standalone programs

### Data Files
The application relies on several text files for storing lottery data:
- `powerball_winning_numbers.txt` - Historical Powerball results
- `powerball_ticket_numbers.txt` - Generated/stored ticket numbers
- `mega_winning_numbers.txt` - Historical Mega Millions results
- `mega_ticket_numbers.txt` - Generated/stored ticket numbers

### Dependencies
- Spring Boot 3.1.3 with Spring Web
- Java 17
- Lombok for reducing boilerplate
- JSoup 1.17.2 for web scraping capabilities
- Jackson for JSON processing (via Spring Boot)