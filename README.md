# Cocktail Telegram bot

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![Platform](https://img.shields.io/badge/platform-Telegram-blue.svg)](https://t.me/CocktailSearchBot)
[![License](https://img.shields.io/badge/license-MIT-yellow.svg)](/LICENSE)


Bot for searching cocktails by their names, ingredients and tags.  
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
There are 3 tables: 
### cocktail
Example: 

| id  | name    | ingredient_names                                    | tag_names                                  | recipe                                                                                                                                                                         |
|-----|---------|-----------------------------------------------------|--------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | дайкири | белый ром,сахарный сироп,лаймовый сок,лед в кубиках | крепкие/кислые/цитрусовые/на роме/классика | 1. Налей в шейкер лаймовый сок 30 мл, сахарный сироп 15 мл и белый ром 60 мл 2. Наполни шейкер кубиками льда и взбей 3. Перелей через стрейнер в охлажденное шампанское блюдце |

**NOTE!** See temporary  [workaround](#redundant-columns-for-tags-and-ingredients) for details.

### ingredient
Example:  

| id  | name           | amount | unit | 🔑 cocktail_id | 🔑 cocktail_name  |
|-----|----------------|--------|------|----------------|-------------------|
| 1   | белый ром      | 60     | мл   | 1              | дайкири           |
| 2   | сахарный сироп | 15     | мл   | 1              | дайкири           |



### tag
Example:

| id  | name     | 🔑 cocktail_id | 🔑 cocktail_name |
|-----|----------|----------------|------------------|
| 1   | крепкие  | 1              | дайкири          |
| 2   | классика | 1              | дайкири          |



## Getting Started
### Prerequisites
- Java 11 or higher

### Installing
Simple clone this repository :) 

## Usage
### Run program
cd into cloned repo and run program by  
`mvn spring-boot:run
`
### Update database
There is REST controller with `updateCocktailDb` mapping for database update.  
So you can run `curl <ip>:<port>/updateCocktailDb` to update db.

## Build using
- OpenJDK 11
- [Maven](https://maven.apache.org)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org)

## Workarounds
This project is still under development, so there are some temporary workarounds, which should be fixed in the future.

### Redundant columns for tags and ingredients
There are columns ___tag_names___ and ___ingredient_names___ in the ___cocktail___ table, while there are separate tables for ___tags___ and ___ingredients___.

By this way I am able to search cocktails by substring with multiple ingredients or tags.

Way to fix:  
Select cocktail from ___ingredient table___ or ___tag table___, that contains all ingredients or tags from user input (use substring search for each element)




