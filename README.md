# CommunityTelegramBot

Java 14 with feature preview

Gradle 6.6.1

Spring boot

Docker

Docker-compose


# Installation 

Create app.env file with content:

```code
COMMUNITY_BOT_TOKEN={{bot_token}} # check @BotFather in telegram
MYSQL_USER={{mysql user}}
MYSQL_PASSWORD={{mysql pass}}
MYSQL_DATABASE={{mysql db}}
MYSQL_HOST={{mysql host}} # localhost:3306
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

# Run db for development

```code
./run.sh
```

