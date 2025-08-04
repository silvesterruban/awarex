# Awarex API

## Base URL
`http://localhost:8080/awarex/api`

## Endpoints

### 1. Create a Post
Creates a new post by a user. If the user doesn't exist, it will be created.

- **URL**: `/writeAPost`
- **Method**: `POST`
- **Content-Type**: `application/json`

#### Request Body
```json
{
  "name": "Ram Kumar",
  "email": "ramkumar@gmail.com",
  "gender": "male",
  "title": "Sample Post Title",
  "body": "This is the content of the post."
}
```
#### Success Response (200 OK)
```json
{    
  "userName": "Ram Kumar",
  "userEmail": "ramkumar@gmail.com",
  "userGender": "male",
  "userStatus": "active",
  "postTitle": "Sample Post Title",
  "postBody": "This is the content of the post."
}
```
#### Error Responses
- 400 Bad Request: Invalid input data
- 500 Internal Server Error: Server error
### Models
#### User
```json
{
  "userId": 123,
  "userName": "Ram Kumar",
  "userEmail": "ramkumar@gmail.com",
  "userGender": "male",
  "userStatus": "active"
}
```
#### Post
```json
{
  "id": 456,
  "userId": 123,
  "postTitle": "Sample Post Title",
  "postBody": "This is the content of the post."
}
```
#### Running the Application
#### 1. Build the project:
 ```json
mvn clean package
```
#### 2. Deploy the WAR file to a servlet container like Tomcat
#### 3. The API will be available at http://localhost:8080/awarex/api
#### Testing
Run the test suite with:
 ```json
mvn test
```
#### Dependencies
- Java 8+
- Maven 3.6+
- Spring Framework 5.x
- JUnit 5 for testing EOL






