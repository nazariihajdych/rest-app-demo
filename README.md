# REST APP DEMO

### Improved requirements:

---
**It has the following fields:**
1. Email (required). Add validation against email pattern
2. First name (required)
3. Last name (required)
4. Birthdate (required). Value must be earlier than current date
5. Address (optional)
6. Phone number (optional)
---
**It has the following functionality:**
1. Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
2. Update one/some user fields
3. Update all user fields
4. Delete user
5. Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects.
6. Code is covered by unit tests using Spring
7. Code has error handling for REST
8. API responses are in JSON format

