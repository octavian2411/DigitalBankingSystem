# SDT Project 4 - Digital Banking System (Part 1)


## Design patterns

Factory Method (account creation), Strategy (transfer fees), State (transaction lifecycle), Observer (notifications and audit).

## Running

Backend (http://localhost:8081):

```
cd backend
.\mvnw spring-boot:run

```

Frontend (http://localhost:5173):

```
cd frontend
npm install
npm run dev
```

Sample data is created at startup: Alice (checking id 1, savings id 2) and Bob (checking id 3).


