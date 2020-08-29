# CommunityTelegramBot

Java 14 with feature preview

Gradle 6.6.1

Spring boot

Docker

Docker-compose


# Installation 

Create app.env file with content:

```code
COMMUNITY_BOT_TOKEN={{bot_token}}
MYSQL_USER={{msql user}}
MYSQL_PASSWORD={{msql pass}}
MYSQL_DATABASE={{msql db}}
```

Create db.env file with content:

```code
MYSQL_ROOT_PASSWORD={{msql root pass}}
MYSQL_USER={{msql user}}
MYSQL_PASSWORD={{msql pass}}
MYSQL_DATABASE={{msql db}}
```

# Full build from source files in docker and run


```code
./buildAndRun.sh
```

# Run with code reload using Gradle in container and local ./build folder

```code
./run.sh
```

