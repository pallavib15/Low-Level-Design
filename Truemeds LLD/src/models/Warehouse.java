package models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {

    private String warehouseId;
    private Location location;
    private ConcurrentHashMap<LocalDate,ConcurrentHashMap<String,Integer>> stockMap;
    private ConcurrentHashMap<String, TreeSet<LocalDate>> orderedMedicines;
    // march 3rd, dolo-50
    //            , ecospirin-100
    // March 6th, dolo-200

    public Warehouse(){
        stockMap = new ConcurrentHashMap<>();
        orderedMedicines = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, TreeSet<LocalDate>> getOrderedMedicines() {
        return orderedMedicines;
    }

    public void setOrderedMedicines(ConcurrentHashMap<String, TreeSet<LocalDate>> orderedMedicines) {
        this.orderedMedicines = orderedMedicines;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ConcurrentHashMap<LocalDate, ConcurrentHashMap<String, Integer>> getStockMap() {
        return stockMap;
    }

    public void setStockMap(ConcurrentHashMap<LocalDate, ConcurrentHashMap<String, Integer>> stockMap) {
        this.stockMap = stockMap;
    }
}
