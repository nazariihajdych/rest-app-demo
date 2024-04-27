# REST APP DEMO
### Overview
The Rest App Demo project is designed to showcase a basic RESTful API for managing user information. It adheres to specific requirements for field validation, functionality, and best practices for error handling and testing. Below is an overview of its features and functionality.

---
#### Fields:
1. **Email:** A required field that must adhere to the email pattern.
2. **First Name:** A required field for the user's first name.
3. **Last Name**: A required field for the user's last name.
4. **Birthdate**: A required field indicating the user's birthdate. The value must be earlier than the current date.
5. **Address**: An optional field for the user's address.
6. **Phone Number**: An optional field for the user's phone number.
---
#### Functionality:
1. **Create User:** Allows registration of users who are more than 18 years old. The minimum age requirement (18) is configurable through a properties file.
2. **Update User Fields:** Enables updating of one or more user fields.
3. **Update All User Fields:** Facilitates updating of all user fields.
4. **Delete User:** Deletes a user from the system.
5. **Search Users by Birthdate Range:** Allows searching for users within a specified birthdate range. Validates that the "From" date is less than the "To" date and returns a list of matching user objects.
6. **Unit Test Coverage:** The codebase is thoroughly covered by unit tests using the Spring framework to ensure reliability.
7. **Error Handling:** Implements error handling mechanisms for RESTful API operations to provide informative and user-friendly error responses.
8. **JSON API Responses:** API responses are formatted in JSON for consistency and ease of consumption by client applications.

