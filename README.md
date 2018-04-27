# Java API

A Java API bootstrap.

## Development configurations

### PostgreSQL datasource on Wildfly

- Download the JDBC Driver for PostgreSQL to directory __{WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main/__
- In the same directory, create the file __module.xml__ with the following content. The _postgresql-[version].jar_  value is the name of the jar you just downloaded.

```xml
<?xml version="1.0" encoding="UTF-8"?>
	<module xmlns="urn:jboss:module:1.1" name="org.postgresql">
	<resources>
		<resource-root path="postgresql-[version].jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.transaction.api"/>
	</dependencies>
</module>
```

- In the file __{WILDFLY_HOME}/standalone/configuration/standalone.xml__ search for the node __datasources__ and add the following snippet inside it:

```xml
<datasource jta="true" jndi-name="java:/apiDS" pool-name="apiDS" enabled="true" use-ccm="true">
    <connection-url>jdbc:postgresql://localhost:5432/java-api</connection-url>
    <driver>postgresql</driver>
    <pool>
        <min-pool-size>10</min-pool-size>
        <max-pool-size>20</max-pool-size>
    </pool>
    <security>
        <user-name>username</user-name>
        <password>password</password>
    </security>
    <statement>
      <prepared-statement-cache-size>32</prepared-statement-cache-size>
      <share-prepared-statements>true</share-prepared-statements>
  </statement>
</datasource>
```
- And, finally, in the __drivers__ node, add the following snippet:

```xml
<driver name="postgresql" module="org.postgresql">
  <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
</driver>
```
