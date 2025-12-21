import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SearchService {

    private ConcurrentHashMap<String, List<Flight>> searchFlights;

    public SearchService(){
        searchFlights = new ConcurrentHashMap<>();
    }

    public void addFlights(Flight flight) {
        String s=flight.getSource()+"_"+flight.getDestination()+"_"+flight.getTravelDate();
        searchFlights.computeIfAbsent(s,k->new ArrayList<>()).add(flight);
    }

    public List<Flight> searchFlight(String source, String destination, LocalDate startdate){
       return searchFlights.get(source+"_"+destination+"_"+startdate);
    }

}
