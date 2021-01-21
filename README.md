
## Spring Data Cassandra with GraphQL Demo

This is a work-in-progress sample repository to demonstrate how GraphQl can be Configured with Spring Data Cassandra.

### Pre-requisite
This code assumes a local instance of Cassandra Running @localhost:9047 with data-center as datacenter1

### Instructions to run
```bash
gradlew clean bootRun
```    

After Application starts; open browser and enter [http://localhost:8080/gui](http://localhost:8080/gui) to test following queries.

#### 1. Get All Books
##### Request:
```text
{
  getAllBooks{
    book {
      name
      price
    }
    authors {
      name
    }
  }
}
```

##### Response:
```json
{
  "data": {
    "getAllBooks": [
      {
        "book": {
          "name": "Another Book",
          "price": 150
        },
        "authors": [
          {
            "name": "Author 1"
          },
          {
            "name": "Author 3"
          }
        ]
      },
      {
        "book": {
          "name": "Some Book",
          "price": 100.05
        },
        "authors": [
          {
            "name": "Author 1"
          },
          {
            "name": "Author 2"
          }
        ]
      }
    ]
  }
}
```
      
#### 2. Find All Books by Author Name:
##### Request:
```text
{
 findBooksByAuthor(author:"Author 1") {
   book {
     name
     price
   }
   authors {
     name
   }
 }
}
```

##### Response:
```json
{
  "data": {
    "findBooksByAuthor": [
      {
        "book": {
          "name": "Another Book",
          "price": 150
        },
        "authors": [
          {
            "name": "Author 1"
          },
          {
            "name": "Author 3"
          }
        ]
      },
      {
        "book": {
          "name": "Some Book",
          "price": 100.05
        },
        "authors": [
          {
            "name": "Author 1"
          },
          {
            "name": "Author 2"
          }
        ]
      }
    ]
  }
}
```

### Help and Reference Documentation
Please refer to [this page.](HELP.md)