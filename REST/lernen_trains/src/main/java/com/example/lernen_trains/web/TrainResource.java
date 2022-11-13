package com.example.lernen_trains.web;

import com.example.lernen_trains.database.TrainMockDB;
import com.example.lernen_trains.pojos.Train;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/trains")
public class TrainResource {
    private TrainMockDB database = TrainMockDB.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTrains(@QueryParam("start") @DefaultValue("0") int start, @QueryParam("end") @DefaultValue("10") int end) {
        List<Train> trainList = database.getAllTrains();

        start = Math.max(0, start);
        end = Math.min(end, trainList.size());
        end = Math.max(end, start+1);

        return Response.ok(trainList.subList(start, end)).status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfStations(@PathParam("id") long id){
        try {
            Optional<Train> trainOptional = database.getTrainById(id);
            List<String> stations = trainOptional.get().getStations();

            return Response.ok(stations).status(Response.Status.OK).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrain(Train train, @Context UriInfo uriInfo){
        try{
            Optional<Train> trainOptional = database.addTrain(train);

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(trainOptional.get().getId())).build();
            return Response.created(uri).status(Response.Status.CREATED).build();
        } catch (KeyAlreadyExistsException e){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeTrain(@PathParam("id") long id){
        try {
            database.removeTrain(id);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTrain(Train train, @Context UriInfo uriInfo){
        try{ //created or changed
            Optional<Train> trainOptional = database.updateTrain(train);

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(trainOptional.get().getId())).build();
            return Response.created(uri).status(Response.Status.CREATED).build();
        } catch (KeyAlreadyExistsException e){ //already exists
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStation(@PathParam("id") long id, @QueryParam("station") String station){
        try{
            Optional<Train> trainOptional = database.addStation(id, station);
            return Response.ok(trainOptional.get()).status(Response.Status.CREATED).build();
        } catch (KeyAlreadyExistsException e){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/stations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStations(@PathParam("id") long id){
        try{
            Optional<Train> trainOptional = database.removeStations(id);
            return Response.ok(trainOptional.get()).status(Response.Status.ACCEPTED).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}