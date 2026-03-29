import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Flightrepo {

    ConcurrentHashMap<String ,Flight>  flightMap=new ConcurrentHashMap<>();

    public void save(Flight flight){
        flightMap.put(flight.getFlightNo(),flight);
    }

    public Flight findByFlightNo(String flightNo){
        return flightMap.get(flightNo);
    }

    public List<Flight> findAll(){
        return (List<Flight>) flightMap.values();
    }
}
