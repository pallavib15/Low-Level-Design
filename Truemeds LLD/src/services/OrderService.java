package services;

import models.Warehouse;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {

    StockService stockService;
    Lock lock=  new ReentrantLock();

    public OrderService(StockService stockService) {
        this.stockService = stockService;
    }

    //dolo  -> expireDates
    // dolo - march 3rd
    //        -march 6th
    //         -march 9th

    public boolean fulFillOrder(String warehouseId, String medicineId, LocalDate orderDate,int quantity){
        ConcurrentHashMap<String , Warehouse> warehousemap=stockService.getWarehouseMap();
        Warehouse warehouse=warehousemap.get(warehouseId);
        if(warehouse==null){
            throw new RuntimeException("No warehouse is present");
        }

        ConcurrentHashMap<String, TreeSet<LocalDate>> orderedMedicinesMap=warehouse.getOrderedMedicines();
        for(Map.Entry<String,TreeSet<LocalDate>> entry:orderedMedicinesMap.entrySet()){
            if(entry.getKey().equals(medicineId)){
                TreeSet<LocalDate> dateSet=entry.getValue();
                for(LocalDate date:dateSet){
                    if(orderDate.isAfter(date)){
                        ConcurrentHashMap<LocalDate,ConcurrentHashMap<String,Integer>> dateRelatedStock=warehouse.getStockMap();
                       ConcurrentHashMap<String,Integer> medicineStock= dateRelatedStock.get(date);
                       int currQuantity=medicineStock.get(medicineId);
                       if(currQuantity>quantity){
                           try {
                               lock.tryLock(5, TimeUnit.MILLISECONDS);
                               currQuantity-=quantity;
                               medicineStock.put(medicineId,currQuantity);
                               return true;
                           } catch (InterruptedException e) {
                               throw new RuntimeException(e);
                           }
                       }
                    }
                }
            }
        }
        return false;
    }
}
