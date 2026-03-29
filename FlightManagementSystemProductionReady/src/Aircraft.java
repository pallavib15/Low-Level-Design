import java.util.ArrayList;
import java.util.List;

public class Aircraft {

    private String aircraftId;

    List<SeatLayout> seatList=new ArrayList<>();
    /*
    Think of a bus company 🚌

Bus model: Volvo AC
→ Defines seat layout: 1A, 1B, 1C

Bus trip:
→ “Volvo AC – Trip at 10 AM”
→ “Volvo AC – Trip at 6 PM”

Now ask:

Are seats 1A on 10 AM trip and 1A on 6 PM trip the same seat?
     */

    public List<SeatLayout> getSeatList() {
        return seatList;
    }

    public void setSeatList(String seatId,String seatType) {
        SeatLayout seatlayout=new SeatLayout();
        seatlayout.setSeatId(seatId);
        seatlayout.setSeatType(seatType);
        seatList.add(seatlayout);
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Aircraft() {
        this.seatList = new ArrayList<>();
    }


}
