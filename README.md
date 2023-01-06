# GuitarStoreProject
Spring Project for Guitar Web Store

This GuitarStoreProject is a RESTful Spring-based java backend web app for a musical instruments web store.
Its main logic is as follows:
1) Every website visitor may watch guitars information, incl. guitar price, specifications and any related information, incl. real instock placement & availability. 
Moreover, lists of instruments by musical genre are also viewable.  
2) To make an order a visitor shall register an account. Registered customer may make and cancel orders.
3) Store managers-roled users deal with all the price-list, instock goods, other web-site information and order management (changing order status and make orders for customers, 
e.g. by phone).
4) Admin-roled user has unique permissions to hard-delete any of the information and may appoint Manager and Admin roles.

### The project specification:
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

### DB Diagram:
![This is an image](https://i.ibb.co/8DXVnbL/guitarshop-DB.png)
