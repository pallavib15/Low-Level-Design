import javax.xml.stream.Location;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Flight {

    private String flightNo;
    private String Source;
    private String Destination;
    private Aircraft aircraft;
    private LocalDate travelDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ConcurrentHashMap<String,Seat> seatmap=new ConcurrentHashMap<>();
    private ReentrantLock  lock=new ReentrantLock();

    private List<Seat> selectedSeats=new ArrayList<>();

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    /*
    Think of a movie theatre
🎬 Theatre (Aircraft)

Fixed structure

Seat layout never changes

Example:

Seats: A1, A2, A3, B1, B2

🎥 Show (Flight)

A specific show at a specific time

Seats can be booked or free per show

Show	Seat A1	Seat A2
10 AM	Booked	Free
7 PM	Free	Booked

👉 Same theatre, different seat status per show

Mapping to airline domain
Real life	Airline system
Theatre	Aircraft
Seat layout	SeatLayout
Show	Flight
Seat booking	Seat (with status)
🔑 Key rule (memorize this)

Aircraft owns the seat layout (template)
Flight owns the seat booking state (runtime)

That’s why:

Aircraft → List<SeatLayout>

Flight → Map<seatNo, Seat>

2️⃣ How a passenger books a list of seats

Now let’s say a passenger wants to book:

Seats: 12A, 12B, 12C

What can go wrong if we’re careless?

Another passenger books 12B at the same time

Partial booking happens

Data inconsistency

So we need atomic booking:

Either all seats are booked, or none are






    Flight A101

Aircraft: Aircraft-1

Route: BLR → DEL

Date: 20 Jan

Flight A201

Aircraft: Aircraft-2

Route: BLR → DEL

Date: 20 Jan

Both:

Same source

Same destination

Same date

Same seat numbers (1A, 1B, …)

Two users:

User A books seat 1A in A101

User B books seat 1A in A201

Your question:

How do we ensure these seats don’t clash and belong to the correct aircraft + flight?

🔑 The core answer (one line)

Seats are not global. Seats live inside a Flight object, and each Flight has its own seatMap created from its own Aircraft.

Now let’s prove this step by step.

1️⃣ What uniquely identifies a flight in the system?

NOT

(source, destination, date)


That is only for search.

YES

flightNumber (A101, A201)


So internally:

A101 ≠ A201

They are two different Flight objects

2️⃣ Object graph in memory (THIS is the key)

When flights are created:

Aircraft aircraft1 = new Aircraft("Aircraft-1", seatLayout);
Aircraft aircraft2 = new Aircraft("Aircraft-2", seatLayout);

Flight A101 = new Flight("A101", "BLR", "DEL", date, aircraft1);
Flight A201 = new Flight("A201", "BLR", "DEL", date, aircraft2);

Memory picture (important)
Flight A101
 └── aircraft → Aircraft-1
 └── seatMap
       ├── "1A" → Seat@101A
       ├── "1B" → Seat@101B

Flight A201
 └── aircraft → Aircraft-2
 └── seatMap
       ├── "1A" → Seat@201A
       ├── "1B" → Seat@201B


👉 Even though seat numbers look the same,
👉 Seat objects are different in memory
     */

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }



    public ConcurrentHashMap<String, Seat> getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(ConcurrentHashMap<String, Seat> seatmap) {
        this.seatmap = seatmap;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void addSeat(String seatId,SeatStatus seatStatus,String flightNo ){
        Seat seat=new Seat();
        seat.setSeatId(seatId);
        seat.setSeatStatus(seatStatus);
        seat.setFlightNo(flightNo);
        this.seatmap.put(seatId,seat);
    }

    public void removeSeat(String seatId){
        this.seatmap.remove(seatId);
    }

    public void bookSeat(Seat seat){
        try {
            lock.tryLock(5, TimeUnit.MILLISECONDS);
            Seat st = seatmap.get(seat.getSeatId());
            if(st.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                st.setSeatStatus(SeatStatus.TEMPORARY);
            }
            if(selectedSeats.contains(seat)){
                throw new IllegalArgumentException("Seat is already selected by other passenger");
            }
            selectedSeats.add(seat);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public synchronized void reserveSeat(){
        List<Seat> selectedSeats=new ArrayList<>(this.selectedSeats);
        for(Seat seat:selectedSeats) {
            Seat st = seatmap.get(seat.getSeatId());
            st.setSeatStatus(SeatStatus.BOOKED);
            selectedSeats.remove(seat);
        }
    }

    public synchronized  void cancelSeat(String seatId){
        selectedSeats.remove(seatId);
        Seat st = seatmap.get(seatId);
        st.setSeatStatus(SeatStatus.AVAILABLE);
    }
}
