package pojo;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Booking {
     private String firstname;
     private String lastname;
     private long totalprice;
     private boolean depositpaid;
     private bookingdates bookingdates;
 //OR
    // @JsonProperty("bookingdates")
     //private Bookingdates bookingDates;
     private String additionalneeds;
     
     
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public static class bookingdates{
	private Date checkin;
	private Date checkout;
}

}