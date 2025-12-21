import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FlightService {

    SearchService searchService;
    Flightrepo flightrepo;

    public FlightService(Flightrepo flightrepo,SearchService searchService) {
        this.searchService = searchService;
        this.flightrepo = flightrepo;
    }

    public Flight addFlight(String flightno, String source, String destination, Aircraft aircraft, LocalDateTime startTime, LocalDateTime endTime, LocalDate startDate) {
        Flight flight = new Flight();
        flight.setFlightNo(flightno);
        flight.setSource(source);
        flight.setDestination(destination);
        flight.setStartTime(startTime);
        flight.setEndTime(endTime);
        flight.setAircraft(aircraft);
        flight.setTravelDate(startDate);
        flightrepo.save(flight);
        searchService.addFlights(flight);
return  flight;
    }

    public Flight getFlight(String flightno){
        return flightrepo.findByFlightNo(flightno);
    }

    public List<Flight> searchFlight(String source, String destination, LocalDate startdate){
       return searchService.searchFlight(source,destination,startdate);
    }

}
