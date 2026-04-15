Core Workflow

Auth: Register via /user/signUp ➔ Login via /user/login.

Session: Post-login, userId is stored in HttpSession.

Context: Services resolve the active user from the session to manage Carts/Orders.

Database: Runs on H2 In-Memory by default (dev profile). No setup required.

Technical Stack

Spring Boot 4.0.5 | Java 21 | Spring Data JPA

AOP: 5 Advices implemented in GeneralInterceptorAspect for global logging.

Transactions: @Transactional enforced on User/Order service layers.

Efficiency: Pagination & Sorting on Product catalog; JPA Auditing for timestamps.

Cascading: CascadeType.ALL used for automated User-Address-Cart cleanup.

Primary Endpoints

Swagger UI: http://localhost:8080/swagger-ui/index.html

Health Check: http://localhost:8080/actuator/health

H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:ecommerce_dev)