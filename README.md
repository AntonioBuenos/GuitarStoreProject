# GuitarStoreProject
Spring Project for Guitar Web Store
___

### Description:
This GuitarStoreProject is a RESTful Spring-based java backend web app for a musical instruments web store.
Its main logic is as follows:
1) Every website visitor may watch guitars information, incl. guitar price, specifications and any related information, incl. real instock placement & availability. 
Moreover, lists of instruments by musical genre are also viewable.  
2) To make an order a visitor shall register an account. Registered customer may make and cancel orders.
3) Store managers-roled users deal with all the price-list, instock goods, other web-site information and order management (changing order status and make orders for customers, 
e.g. by phone).
4) Admin-roled user has unique permissions to hard-delete any of the information and may appoint Manager and Admin roles.
___

### The project specification:
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
- JDK 17 and Spring 3;
- Spring Boot Rest Api;
- 2-module structure (common & api);
- Spring Data JPA Repositories;
- DB: PostgreSQL DB, Flyway;
- Authentication & authorisation (Spring Security);
- Verification of user's email (Spring Mail);
- Validation of data entries, incl. custom Enum validation;
- Entity/DTO converters;
- Spring Caches;
- OpenAPI documentation, incl. swagger UI.
___ 

### DB Diagram:
![This is an image](https://i.ibb.co/8DXVnbL/guitarshop-DB.png)
