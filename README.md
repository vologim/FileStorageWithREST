# REST application for working with file storage

Console CRUD application that interacts with the database 
Entities:
- User (Long id, String name, List<UserFile> files)
- UserFile (Long id, String fileName, String filePath)
- Event (Long id, String event)

File storage path
```sh
src/main/resources/application.properties
```

Managing data through REST WebService
in POST and PUT you need to send JSON in the body
| HTTP method |           URL           |                Operation  |             Information  |
|:----------:|:-----------------------:|:--------------------------:|:--------------------------:|
| GET        | /users             | get json users                  |           |
| GET        | /users/{id}        | get json user                   |           |
| POST       | /users             | create user                     |   you need to send JSON in the body        |
| PUT        | /users             | change user                     |     you need to send JSON in the body      |
| DELETE     | /users/{id}        | delete user                     |           |


| HTTP method |           URL           |                Operation  |             Information  |
|:----------:|:-----------------------:|:--------------------------:|:--------------------------:|
| GET        | /files             | get information about files in json  |   you need to send "userId" in Header        |
| GET        | /files/{id}        | download file from file storage     |    you need to send "userId" in Header       |
| POST       | /files/{userId}    | add file in file storage            |   you need to send "userId" in Header and need to send JSON in the body        |
| PUT        | /files/{userId}    | change file in file storage         |     you need to send "userId" in Header and need to send JSON in the body      |
| DELETE     | /users/{id}        | delete file                          |    you need to send "userId" in Header       |

Technologies:
```sh
Java
MySQL
Hibernate
Servlets
Maven
Flyway
HTTP
GSON
Lombok
```
