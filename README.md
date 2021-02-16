## Aestivate_p1

Aestivate is a basic Java ORM that works through inheritance. It Currently works with
just postgresql, but this may expand in the future.

# SETUP

For Aestivate to work, it requires an aestivate.xml file in the resources directory.
This file informs Aestivate of the database type it is connecting to (eg. postgresql),
the url, login, and password. In addition it takes arguments for the minimum number of
connections to idle, the max number of connections to idle, and the max open prepared
statements there can be. An Example of the xml file is provided here:

-----------------------------------------------
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration>

    <!-- Database connection info -->
    <Database name = "postgresql">
        <Url url="url"/>
        <Login login="login name"/>
        <Password password="password/>
        <MinIdle minIdle="5"/>
        <MaxIdle maxIdle="10"/>
        <maxOpenPreparedStatements maxOpen="100"/>
    </Database>

</Configuration>
--------------------------------------------------------

Reminder, does not work if the Database name is different than "postgresql".

The current features of Aestivate are that it manages connections to the database, and that it can
create tables of a class that extends its base model, BaseModel.

Future plans are to make the variable types of the allowed columns to be more robust, along with
Saving the current instance of the object to the table, update an existing entry an instance,
and deleting class instances from the class table.

Stretch goals are to implement foreign keys to connect classes, and even further down the line is
the ability to connect to multiple databases at once.