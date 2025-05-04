Feature: Booking Feature
@headerFile:src/test/resources/testdata/header.json
@scenarioName:GET_BOOKINGIDS
Scenario: Retreive and validate booking details by id and firstname
Given user launches the API url after authentication
When user sends a request to retrieve all booking ids
Then user should receive list of booking ids 

And user extracts <2> booking id from the list
When user sends a request using the extracted booking id 
Then user should receive the booking details for the id

And user extracts the firstname from the booking details
When user sends a request with extracted firstname
Then user should receive the booking details matching the firstname

And user extracts checkin date from the booking deatils
When user sends a request with checkin date
Then user should receive the booking details matching to checkin
#@scenarioName:GET_BOOKINGBYNAME
#Scenario: Retreive booking by name
#Given user launches API url after authentication
#When user sends request to get booking by name
#Then user able to see the booking details
