import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AircraftRepo {

    ConcurrentHashMap<String ,Aircraft> aircraftMap=new ConcurrentHashMap<>();

    public void save(Aircraft aircraft){
        aircraftMap.put(aircraft.getAircraftId(),  aircraft);
    }

    public Aircraft findByFlightNo(String aircraftId){
        return aircraftMap.get(aircraftId);
    }

    public List<Aircraft> findAll(){
        return (List<Aircraft>) aircraftMap.values();
    }
}
