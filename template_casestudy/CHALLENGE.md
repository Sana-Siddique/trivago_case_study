## THE CHALLENGE

The objective of this case study is to implement one of our core components: The
“Priceservice”. It responds to a user request with the prices from our partners provided via
file.

For this task, we would like you to complete the provided implementation.

* The partners with `ID 100` and `ID 200` have agreed to provide us files with real prices in EUR
* The data consists of <`partner_id`, `item_id`, `currency`, `price`> tuples in some
  representation
* 

The last files provided by these partners are added in resources/static/prices. We would like
you to use these prices.

To complete the task, you should:
* Implement code to return price for a given accommodation from partners 100 and 200 when requested. The accommodation should be unique for each advertiser.
* Design your solution to be extensible for further use cases 
* Consider possible points of failure.

Please don't forget to provide a valid README.md file, and include answers to the following questions:
  * How could a partner with a potentially slow REST interface be integrated?
  * How could your solution scale for multiple thousand requests per second? 

Implement your solution following best coding practices. 
If you have any concerns, leave a comment with a justification.

### What you get from us:
* A zip file with the source code of a Java Spring Boot application and the input files 
*  The source code contains a bare skeleton of the application

Please make sure that the signature of `com.trivago.casestudy.priceservice.controllers.PriceserviceController#getPrices` does not change.

What we expect from you:
* A zip file with the source code to the completed Java Spring Boot application and the README.md file.
* Complete implementation of the requirements mentioned above. 