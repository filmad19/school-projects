package com.example.lernen_trains.database;

import com.example.lernen_trains.io.IO_Access;
import com.example.lernen_trains.pojos.Train;
import jakarta.ws.rs.NotFoundException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainMockDB {
    private List<Train> trainList;

//    SINGLETON
    private static TrainMockDB instance;

    private TrainMockDB() {
        try {
            trainList = IO_Access.getTrainsFromCsv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TrainMockDB getInstance(){
        if(instance == null){
            instance = new TrainMockDB();
        }

        return instance;
    }
//    ////////////////////

    public List<Train> getAllTrains() {
        return trainList;
    }

    public Optional<Train> getTrainById(long id){
        Optional<Train> trainOptional = trainList.stream()
                .filter(train -> train.getId() == id)
                .findFirst();

        if(!trainOptional.isPresent()){
            throw new NotFoundException("train with id " + id + "  not found");
        }

        return trainOptional;
    }

    public Optional<Train> addTrain(Train train){
//        no train with same type, stations
        if(trainList.contains(train)){
            throw new KeyAlreadyExistsException("train with this type and stations already exists");
        }

        long nextId = trainList.stream()
                .mapToLong(Train::getId)
                .max()
                .orElse(0)+ 1;

        train.setId(nextId);

        trainList.add(train);
        return Optional.of(train);
    }

    public Optional<Train> removeTrain(long id){
        Optional<Train> trainOptional = getTrainById(id); //throws NotFound

        trainList.remove(trainOptional.get());
        return  trainOptional;
    }

    public Optional<Train> updateTrain(Train train){
        try{// change train
            Optional<Train> trainOptional = getTrainById(train.getId()); //throws NotFound

            if(trainList.contains(train)){
                throw new KeyAlreadyExistsException("train with this type and stations already exists");
            }

            trainList.set(trainList.indexOf(trainOptional.get()), train);
            return Optional.of(train);
        } catch (NotFoundException e){ // add train
            Optional<Train> trainOptional = addTrain(train); //throws KeyAlreadyExistsException
            return trainOptional;
        }
    }

    public Optional<Train> addStation(long id, String station){
        Optional<Train> trainOptional = getTrainById(id); //throws NotFound

        List<String> stations = trainOptional.get().getStations();
        if(stations.contains(station)){
            throw new KeyAlreadyExistsException("station already exists");
        }

        stations.add(station);
        return trainOptional;
    }

    public Optional<Train> removeStations(long id){
        Optional<Train> trainOptional = getTrainById(id); //throws NotFound

        trainOptional.get().setStations(new ArrayList<>());

        return trainOptional;
    }
}
