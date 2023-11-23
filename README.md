# CarParkSystem


Created rest web service for allocate, get and release car park.

Allocate Car Park - Allocates car park with start time and gives first free parking slot

Get Car Park - Gives the parking details till the parking is active

Release Car Park- Releases the space and displays the cost



ParkingSystem.postman_collection.json is the postman collection to test the system.




Improvements:

1. If this service runs as multiple pods/instances we can make use of distributed atomic cache to achieve synchronization.
2. More test cases can be written.
3. Swagger can also be written with better explanation of API(s)
