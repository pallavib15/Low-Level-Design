package services;

import models.Location;
import models.Warehouse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class StockService {

    ConcurrentHashMap<String, Warehouse> warehouseMap=new ConcurrentHashMap<>();

    public Warehouse addWareHouse(String warehouseId, Location location){
        Warehouse warehouse=new Warehouse();
        warehouse.setWarehouseId(warehouseId);
        warehouse.setLocation(location);
        warehouseMap.put(warehouseId, warehouse);
        return warehouse;
    }

    public ConcurrentHashMap<String, Warehouse> getWarehouseMap() {
        return warehouseMap;
    }

    public synchronized void addStock(String warehouseId,String medicineId, LocalDate expireDate, int quantity){
        Warehouse warehouse=warehouseMap.get(warehouseId);
        ConcurrentHashMap<String,Integer> medicineQuantityMap=new ConcurrentHashMap<>();
        medicineQuantityMap.put(medicineId,quantity);
        ConcurrentHashMap<LocalDate,ConcurrentHashMap<String,Integer>> stockQuantityMap=new ConcurrentHashMap<>();
        stockQuantityMap.put(expireDate,medicineQuantityMap);
        ConcurrentHashMap<String, TreeSet<LocalDate>> orderedMedicinesMap=new ConcurrentHashMap<>();
        orderedMedicinesMap.computeIfAbsent(medicineId,k->new TreeSet<>()).add(expireDate);
        warehouse.setOrderedMedicines(orderedMedicinesMap);

        warehouse.setStockMap(stockQuantityMap);
    }

    public synchronized int getValidStockLevel(String warehouseId,String medicineId,LocalDate currentDate){
        Warehouse warehouse=warehouseMap.get(warehouseId);

        ConcurrentHashMap<LocalDate,ConcurrentHashMap<String,Integer>> stockQuantityMap=warehouse.getStockMap();
        for(Map.Entry<LocalDate,ConcurrentHashMap<String,Integer>> entry:stockQuantityMap.entrySet()){
                if(entry.getKey().isAfter(currentDate)) {
                    if (entry.getValue().containsKey(medicineId)) {
                        return entry.getValue().get(medicineId);
                    }
                }
        }
        throw new RuntimeException("this medicine"+medicineId+"is not available with in this time period");
    }








}
