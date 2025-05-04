package endpoints;

public enum Endpoint {
      AUTH("/auth"),
      GET_BOOKINGIDS("/booking"),
      GET_BOOKINGBYNAME("/booking"),
      GET_BOKKING_BYDATE("/booking"),
    //url parameter,  path parameter placeholder ex. booking/123
    //  GET_BOOKINGBYID("/booking/:id"),   in restassured it should be:
      GET_BOOKINGBYID("/booking/{id}"),
      POST_BOOKING("/booking"),
      PUT_BOOKING("/booking"),
      PATCH_BOOKING("/booking"),
      DELETE_BOOKING("/booking/{id}"),
      GET_PING("/ping");
      
      private String value;
      private Endpoint(String value) {
    	  this.value = value;
      }
	 public String get() {
		 return this.value;
	 }
}
