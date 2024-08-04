# CURL Request of Challenge

curl --request GET --url http://localhost:8080/prices/76708

## Overview

The PriceService responds to user request with the price from our advertiser data provided via files.
The current implementation supports JSON and YAML file formats for price data. 

## Approach

The code provided is a part of a service class responsible for fetching accommodation prices from 
two different data sources (JSON and YAML files).The primary focus is to get the prices based on 
an accommodation ID, using caching to improve performance and reduce redundant file reads because for larger datasets
I/O Operations are expensive.
The nature of challenge required quick retrievals so implementing cache would help in performance optimizing.
It is found that retrieving data from cache took 26 ms which is comparatively less than retrieving it from file 
which took 56 ms. 
The code is designed to handle exceptions gracefully and throw a FileParsingException if an error occurs.

## Unit Test 

Unit tests are crucial for verifying that your method behaves correctly under various conditions.
Cases that are covered in unit test case are: 

Successful JSON Fetch: Test that the method correctly returns accommodations from a JSON file.
Successful YAML Fetch: Test that the method correctly returns accommodations from a YAML file.
File not found: Test that the method gracefully handles if file is not found.
Successful Price Fetch: Test that the method correctly returns accommodations from a JSON file against a given accomodation ID.
Not Found Price Fetch: Test that the method correctly returns response message.
Test mock to endpoint: Test that the endpoint correctly returns accommodations from a JSON file against a given accomodation ID.


In terms of extensibility, the use of helper methods (searchInFiles) and separation of file reading logic makes 
the code modular and easier to extend or modify. The configuration files are also used of extensibility in order to support
other formats.
In terms of efficiency, the method first searches in the JSON file and only proceeds to the YAML file if no matching accommodations 
are found, optimizing the search process.

## Point of failure
The optimise approach in the given scenario has been used but there is always some room to improve which is as
follow:
1. More efficient handling of request if user enters any invalid datatype other than integer.
2. Cache expiry needs to be configured in order to avoid provide stale data.


## Files

- `src/main/java`: Java source files
- `resources/static/prices`: Price files in JSON and YAML formats

## How to Run

1. Place your price files in the `resources/static/prices` directory.
2. Ensure you have the required dependencies (Jackson for JSON, SnakeYAML for YAML,Redis for Cache).
3. Run redis locally on desktop before project starts.
4. Run the main method in `Main.java` to start the service.

## Questions

### How could a partner with a potentially slow REST interface be integrated?

To integrate a partner with a slow REST interface, we can use asynchronous requests or some scheduled jobs to fetch data periodically from their API and store it 
in redis cache. In this way, the service can quickly response to user query without any delay from our side.
Once data is loaded from partners REST API, we can use it. 

### How could your solution scale for multiple thousand requests per second?

There are multiple approaches to scale the solution for multiple requests per second:

1. We can use load balancer to distribute request to multiple instances of our service. We can use auto-scaling feature.
2. Instead of reading from files, use of database for heavy reads is efficient approach. IN-memory as well as SQL or NOSQL databases 
can handle higher throughput efficiently. We can also use indexing to make our search more efficient and faster.
3. Use Concept of Data Sharding. For extremely high loads, consider sharding your database to distribute the load across multiple database instances.
4. Try to process data asynchornously, if data fetching process experience delays we can use kafka or other messaging queues
where we can store request and can process it later and send the result as an event.


## Dependencies

- Jackson (for JSON parsing)
- SnakeYAML (for YAML parsing)
-  Redis Cache (for Caching)
