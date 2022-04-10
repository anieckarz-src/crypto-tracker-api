# crypto-tracker-api
Main project

all requests are passing by api gateway and i'm using docker-compse to run my microservices and database. I'm using JUnit, MockMVC and DataJpaTest to test my code
![image](https://user-images.githubusercontent.com/63502583/162628247-8cb1e3c7-065d-4394-a88a-d58c43b88f2a.png)

in cryptocurrency-api we are able to get data about crypto that symbols we wrote in path variable. If we dont have any information about specific crypto in database this microservice will send request to coinmarketcap.com to get data and aggregate it to save in our database. Every 60s this microservice sends request to our provider to update prices.
![image](https://user-images.githubusercontent.com/63502583/162628631-f1ca3291-4878-47af-a9e1-a71b469d60a0.png)

in user-api we are albe to register new users:
![image](https://user-images.githubusercontent.com/63502583/162629295-655ca136-1679-4c52-8a6f-b9e307c4960e.png)

tables relationships: (1 user - n transactions) 

![image](https://user-images.githubusercontent.com/63502583/162629786-8b103ab8-153b-498b-b67c-85504684427e.png)


regex: 

![image](https://user-images.githubusercontent.com/63502583/162629316-e6308126-1d9f-48e0-a1cf-3bbc753ba269.png)

post new transaction:
![image](https://user-images.githubusercontent.com/63502583/162629402-2a579df4-e032-4aff-8911-15607a3e04fe.png)

get all transactions:
![image](https://user-images.githubusercontent.com/63502583/162629472-48da4508-788f-4cd9-b71b-520a8d367106.png)

and delete it by id in request param:
![image](https://user-images.githubusercontent.com/63502583/162629518-091046f6-dbee-4c90-9d01-961603eff2d5.png)

eureka and api gateway are expose on specific port but other microservices using random ports but eureka registering them. 

eureka dashboard:
![image](https://user-images.githubusercontent.com/63502583/162629567-55078d9f-f806-4a23-b2b7-ac98f3a612dd.png)


