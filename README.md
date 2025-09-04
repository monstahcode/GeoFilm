# GeoFilm

GeoFilm is a Spring Boot application developed as a small project during a 2-week internship by a team of colleagues [1](#1-0) . The platform allows users to explore filming locations from movies and TV shows around the world, integrating with external APIs to fetch movie data and using web scraping to extract filming location information <cite />.

## Project Context

This project was completed in just 2 weeks during an internship program, showcasing rapid development capabilities and collaborative teamwork <cite />. The development team consisted of multiple contributors including monstahcode, IvanR05, and Grifo999, as evidenced by the git commit history [2](#1-1) .

## Features

- **Movie Search**: Search for movies and TV shows using the OMDb API [3](#1-2) 
- **Location Discovery**: Automatically scrape filming locations from IMDb using Selenium WebDriver <cite />
- **Geocoding**: Convert location addresses to coordinates using DistanceMatrix AI API [4](#1-3) 
- **User Favorites**: Save and manage favorite filming locations through many-to-many relationships [5](#1-4) 
- **RESTful API**: Complete REST API for frontend integration [6](#1-5) 

## Technology Stack

- **Framework**: Spring Boot 3.5.0 [7](#1-6) 
- **Language**: Java 17 [8](#1-7) 
- **Database**: MySQL with JPA/Hibernate [9](#1-8) 
- **Web Scraping**: Selenium WebDriver with Firefox [10](#1-9) 
- **HTML Parsing**: Jsoup [11](#1-10) 
- **Security**: Spring Security [12](#1-11) 

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL database
- Firefox browser installed
- GeckoDriver executable

### Installation

1. Clone the repository
2. Configure your MySQL database connection in `application.properties`
3. Ensure Firefox and GeckoDriver are properly installed
4. Run the application:

```bash
mvn spring-boot:run
```

## API Endpoints

### Movie Search
- `POST /api/v1/media/search/name` - Search for movies by name [13](#1-12) 
- `POST /api/v1/media/search/predict/name` - Get search predictions [14](#1-13) 
- `POST /api/v1/media/search/id` - Search by IMDb ID [15](#1-14) 

## Architecture

The application follows a layered architecture with clear separation of concerns:

- **Entities**: JPA entities for `User` and `Location` with many-to-many relationships [16](#1-15) 
- **Media Objects**: Transient objects for external API data (`Media`, `MediaLocation`) <cite />
- **Services**: Business logic layer for search and location management [17](#1-16) 
- **Controllers**: REST API endpoints with CORS support [18](#1-17) 
- **Repositories**: Data access layer using Spring Data JPA [19](#1-18) 

## External Integrations

- **OMDb API**: Movie metadata retrieval [20](#1-19) 
- **IMDb Web Scraping**: Filming location extraction using Selenium <cite />
- **DistanceMatrix AI**: Geocoding services for coordinate resolution [21](#1-20) 

## Development Timeline

This project demonstrates what can be accomplished in a short timeframe with focused collaboration <cite />. The rapid development cycle included implementing complex features like web scraping, API integration, and database management within the 2-week internship period <cite />.

## Contributing

This internship project was collaboratively developed by monstahcode, IvanR05, and Grifo999 [2](#1-1) . The codebase demonstrates effective teamwork and follows established Spring Boot conventions <cite />.

## Notes

The project showcases the capabilities of a small development team working under tight deadlines. Despite the 2-week timeframe, the application includes sophisticated features like web scraping with Selenium, external API integrations, and a complete REST API architecture. The Spanish description in the POM file indicates this was likely developed in a Spanish-speaking environment: "Plataforma que permite explorar lugares del mundo donde se filmaron escenas icónicas de películas y series."
