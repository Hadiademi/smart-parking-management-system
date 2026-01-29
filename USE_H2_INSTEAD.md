# Përdor H2 Database (Pa Password!)

Nëse ke probleme me PostgreSQL passwordin, përdor H2 që është më e thjeshtë:

## Hapat:

1. **Hap**: `src/main/resources/application.properties`

2. **Comment PostgreSQL lines** (shto # në fillim):
```properties
#spring.datasource.url=jdbc:postgresql://localhost:5432/smartparkingdb
#spring.datasource.username=postgres
#spring.datasource.password=postgres
#spring.datasource.driver-class-name=org.postgresql.Driver
```

3. **Uncomment H2 lines** (hiq # nga fillimi):
```properties
spring.datasource.url=jdbc:h2:mem:smartparkingdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

4. **Ndrysho dialect** (line ~31):
```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

5. **RUAJ dhe RUN!**

## Përparësitë:
- ✅ Nuk duhet password
- ✅ Funksionon menjëherë
- ✅ Perfekt për testing dhe demo
- ✅ Ke H2 Console: http://localhost:8080/h2-console

## Të metat:
- ❌ Të dhënat humben kur mbyll aplikacionin (in-memory)
- ❌ Nuk është production database
