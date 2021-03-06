<p align="center">
  <a href="" rel="noopener">
 <img width=200px height=200px src="https://i.imgur.com/RaZ0VEh.png" alt="Bot logo"></a>
</p>
<h3 align="center">Cocktail Telegram bot</h3> 

<div align="center">  

[![Status](https://img.shields.io/badge/status-inactive-yellow.svg)]()
[![Platform](https://img.shields.io/badge/platform-Telegram-blue.svg)](https://t.me/CocktailSearchBot)
[![License](https://img.shields.io/badge/license-MIT-yellow.svg)](/LICENSE.md)  
[Link](https://t.me/CocktailSearchBot)

</div>

<p align="center"> Bot for searching cocktails by their names, ingredients and tags. 
    <br> 
</p>

---

 
**NOTE!** Only Russian language is supported.


## How bot works
The bot suggest you to choose search method:
- **by name** ( e.g `маргарита` ):   
Bot will search cocktail by its name ( [levenshtein distance](https://en.wikipedia.org/wiki/Levenshtein_distance) is used for fuzzy search ) 
- **by ingredients** ( e.g. `джин, содовая`):  
Bot will search cocktail by ingredients, separated by comma 
- **by tags** ( e.g. `шоты, цитрусовые`):  
Bot will search cocktail by tags, separated by comma

## How database works
One-to-many relationship ( cocktail - ingredient, cocktail - tag ).  
There are 3 tables: 
#### cocktail
Example: 

| id  | name    | ingredient_names                                    | tag_names                                  | recipe                                                                                                                                                                         |
|-----|---------|-----------------------------------------------------|--------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | дайкири | белый ром,сахарный сироп,лаймовый сок,лед в кубиках | крепкие/кислые/цитрусовые/на роме/классика | 1. Налей в шейкер лаймовый сок 30 мл, сахарный сироп 15 мл и белый ром 60 мл 2. Наполни шейкер кубиками льда и взбей 3. Перелей через стрейнер в охлажденное шампанское блюдце |

**NOTE!** See temporary  [workaround](#redundant-columns-for-tags-and-ingredients) for details.

#### ingredient
Example:  

| id  | name           | amount | unit | 🔑 cocktail_id | 🔑 cocktail_name  |
|-----|----------------|--------|------|----------------|-------------------|
| 1   | белый ром      | 60     | мл   | 1              | дайкири           |
| 2   | сахарный сироп | 15     | мл   | 1              | дайкири           |



#### tag
Example:

| id  | name     | 🔑 cocktail_id | 🔑 cocktail_name |
|-----|----------|----------------|------------------|
| 1   | крепкие  | 1              | дайкири          |
| 2   | классика | 1              | дайкири          |

### Parser
Parser will parse one url, which contains all cocktails. Then all cocktail data will be parsed and saved to database.


## Getting Started
### Prerequisites
- Java 11 or higher

### Installing
Simple clone this repository :) 

### Configuring
#### Database 
Configure [application.properties](src/main/resources/application.properties)   
`spring.datasource.url` - url to your PostgreSQL database  
`spring.datasource.username` - database username  
`spring.datasource.password` - database password

Also, you need to install ***fuzzystrmatch*** extension to PostgreSQL database:  
simple execute query: `CREATE EXTENSION fuzzystrmatch;`

#### Parser 
Configure [parser.properties](src/main/resources/parser.properties)  
**NOTE!** Only one site is supported, so there is no need to configure parser manually.  
`domain` - main url  
`fullCocktailLink` - url, which contains all cocktails.

#### Bot 
Configure [bot.properties](src/main/resources/bot.properties)  
`token` - your Telegram bot token  
`username` - your Telegram bot username


## Usage
### Run program
cd into cloned repo and run program by  
`mvn spring-boot:run
`
### Update database
There is REST controller with `updateCocktailDb` mapping for database update.  
So you can run `curl <ip>:<port>/updateCocktailDb` to update db.

## Built using
- OpenJDK 11
- [Maven](https://maven.apache.org)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org)
- [Telegram Bot Java Library](https://github.com/rubenlagus/TelegramBots)
- [Jsoup](https://jsoup.org)


## Workarounds
This project is still under development, so there are some temporary workarounds, which should be fixed in the future.

### Redundant columns for tags and ingredients
There are columns ___tag_names___ and ___ingredient_names___ in the ___cocktail___ table, while there are separate tables for ___tags___ and ___ingredients___.

By this way I am able to search cocktails by substring with multiple ingredients or tags.

Way to fix:  
Select cocktail from ___ingredient table___ or ___tag table___, that contains all ingredients or tags from user input (use substring search for each element)




