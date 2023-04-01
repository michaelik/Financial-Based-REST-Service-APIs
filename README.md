## Introduction🖖

This is a financial application that enables businesses and individuals to send and receive money across different currencies. The REST APIs allow a user to transfer funds from one currency to another.

### Specifications
- Users are able to sign up using their email and password. 
- On successful profile creation, they are auto (randomly) assigned an account number with a balance in either currency A or B
- Users are able to log in using a username and password
- Users are able to transfer money from their account to another account regardless of the currencies
- Users are able to get their account balance and transaction history
- There are only 2 currencies, currency A & B, which exchange rate A → B: 1.3455
---

### Step One - Tools and Technologies used 🎼

- Spring Boot(Latest Version)
- Spring Data JPA
- Lombok Library
- JDK 18
- Embedded Tomcat
- Mysql Database(Mysql Workbench)
- Maven
- Java IDE (IntelliJ)
- Postman Client

---

### Step Two - Steps to Run the project Locally ⚙️

[MySQL Workbench](https://www.mysql.com/products/workbench) was used to run the database locally. Navigate to the project application.properties file and modify the database credential per your database server requirement such as `username` and `password`
```properties
spring.datasource.username= #Database-username
spring.datasource.password= #Database-password
```
## Installation

* Clone this repo:

```bash
git clone https://github.com/michaelik/Financial-Based-REST-Service-APIs.git
```
* Navigate to the root directory of the project.
* Build the application
```bash
./mvnw clean install
```
* Run the application
```bash
./mvnw spring-boot:run
```
---

## Usage 🧨

>REST APIs for Financial Service Resource

| HTTP METHOD |             ROUTE             | STATUS CODE |              DESCRIPTION              |
|:------------|:-----------------------------:|:------------|:-------------------------------------:|
| POST        |      `/api/v1/register`       | 201         |          Create new account           |
| POST        |        `/api/v1/login`        | 200         | Login into your newly created account |
| GET         |     `/api/v1/user-detail`     | 200         |          Read account detail          |
| GET         |   `/api/v1/account-balance`   | 200         |         Read account balance          |
| POST        |      `/api/v1/transfer`       | 200         |             Make transfer             |
| GET         | `/api/v1/transaction-history` | 200         |       Read Transaction History        |
| POST        |    `/api/v1/fund-account`     | 200         |    Fund your newly created account    |

---

### The Client should be able to:

**SignUp**

The url will have the following request:
- `userName`:
- `firstName`:
- `lastName`:
- `email`:
- `password`:
- `accountCurrency`:

Request

```
curl -X POST http://localhost:8181/api/v1/register \
-H 'Content-type: application/json' \
-d '{
    "userName": "Ope",
    "firstName": "dolapo",
    "lastName": "adeoluwa",
    "email": "dolapo@gmail.com",
    "password": "123456789",
    "accountCurrency": "A"
}'
```

Response

```json
{
    "createdAt": "2023-03-30T06:45:35.4455598",
    "status": "Registration Successful"
}
```
**SignIn**

The url will have the following request:
- `userName`:
- `password`:

Request

```
curl -X POST http://localhost:8181/api/v1/register \
-H 'Content-type: application/json' \
-d '{
    "userName": "Ope",
    "password": "123456789"
}'
```

Response

```json
{
  "createdAt": "2023-03-30T06:48:00.7825062",
  "status": "Login Successful"
}
```
**Read Account Detail**

Request

```
curl -X GET http://localhost:8181/api/v1/user-detail
```

Response

```json
{
  "userName": "Ope",
  "name": "dolapo adeoluwa",
  "email": "dolapo@gmail.com",
  "accountNumber": "8548132731",
  "accountCurrency": "A"
}
```
**Read Account Balance**

Request

```
curl -X GET http://localhost:8181/api/v1/account-balance
```

Response

```json
{
  "accountName": "dolapo adeoluwa",
  "accountNumber": "8548132731",
  "totalAccountBalance": "A200.00"
}
```
**Make Transfer**

The url will have the following request:
- `accountNumber`:
- `amount`:

Request

```
curl -X POST http://localhost:8181/api/v1/transfer \
-H 'Content-type: application/json' \
-d '{
    "accountNumber": "9835852895",
    "amount": "50"
}'
```

Response

```json
{
  "timeframes": "2023-03-30T06:46:15.1346362",
  "status": "Success"
}
```
**Read Transaction History**

Request

```
curl -X GET http://localhost:8181/api/v1/transaction-history
```

Response

```json
[
  {
    "transactionId": 5,
    "debitAccount": "8548132731",
    "recipient": "mike ikechi | 9835852895",
    "amount": "A50.00",
    "balance": "A150.00",
    "timestamp": "2023-03-30T06:46:15"
  }
]
```
**Fund Account**

The url will have the following request:
- `amount`:

Request

```
curl -X POST http://localhost:8181/api/v1/fund-account \
-H 'Content-type: application/json' \
-d '{
    "amount": "8548132731"
}'
```

Response

```json
{
  "createdAt": "2023-03-30T06:45:44.3610845",
  "status": "Success"
}
```