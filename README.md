# Mshando: A Tasker Platform Like TaskRabbit

Mshando is a tasker platform designed to connect users with local service providers, similar to TaskRabbit. It allows users to post tasks and hire qualified individuals to complete them, facilitating efficient and reliable service exchanges.

## 🚀 Features

- **User Registration & Authentication**: Secure sign-up and login functionalities for both taskers and clients.
- **Task Posting & Management**: Users can post tasks, set deadlines, and manage task details.
- **Task Bidding**: Taskers can browse available tasks and place bids to offer their services.
- **Real-Time Notifications**: Instant updates on task status, bids, and messages.
- **User Profiles & Ratings**: Comprehensive profiles with ratings to build trust within the community.
- **Admin Dashboard**: Admin panel to monitor platform activities and manage users and tasks.

## 🛠️ Technologies Used

- **Backend**: Java with Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **Containerization**: Docker
- **Build Tool**: Maven

## 📦 Installation & Setup

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized setup)
- MySQL Database

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/TyroneZeka/mshando.git
   cd mshando
# Mshando: A Tasker Platform Like TaskRabbit

Mshando is a tasker platform designed to connect users with local service providers, similar to TaskRabbit. It allows users to post tasks and hire qualified individuals to complete them, facilitating efficient and reliable service exchanges.

## 🚀 Features

- **User Registration & Authentication**: Secure sign-up and login functionalities for both taskers and clients.
- **Task Posting & Management**: Users can post tasks, set deadlines, and manage task details.
- **Task Bidding**: Taskers can browse available tasks and place bids to offer their services.
- **Real-Time Notifications**: Instant updates on task status, bids, and messages.
- **User Profiles & Ratings**: Comprehensive profiles with ratings to build trust within the community.
- **Admin Dashboard**: Admin panel to monitor platform activities and manage users and tasks.

## 🛠️ Technologies Used

- **Backend**: Java with Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **Containerization**: Docker
- **Build Tool**: Maven

## 📦 Installation & Setup

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized setup)
- MySQL Database

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/TyroneZeka/mshando.git
   cd mshando
2. Configure the database connection in ```application.properties```
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mshando
  spring.datasource.username=root
  spring.datasource.password=yourpassword
```
3. Build the project
  ```bash
  ./mvnw clean install
```
4. Run the application:
```bash
  ./mvnw spring-boot:run
```
5. To run with docker:
```bash
  docker-compose up
```
## Contributing
Contributions are welcome! Please fork the repo, create new branch and submit a pull request with a description of your changes.

## Licence
The project is licensed under the MIT Licence.
