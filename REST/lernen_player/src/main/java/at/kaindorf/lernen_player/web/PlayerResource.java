package at.kaindorf.lernen_player.web;

import at.kaindorf.lernen_player.database.PlayerMockDB;
import at.kaindorf.lernen_player.pojos.Player;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/player")
public class PlayerResource {
    private PlayerMockDB database = PlayerMockDB.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers(@QueryParam("start") @DefaultValue("0") int start, @QueryParam("end") @DefaultValue("10") int end){
        List<Player> playerList = database.getAllPlayers();

        start = Math.max(0, start);
        end = Math.min(playerList.size(), end);
        end = Math.max(start+1, end);

        return Response.ok(playerList.subList(start, end)).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@PathParam("id") long id){
        try {
            Optional<Player> playerOptional = database.getPlayerById(id);
            return Response.ok(playerOptional.get()).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(Player player, @Context UriInfo uriInfo){
        try{
            Optional<Player> playerOptional = database.addPlayer(player);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(playerOptional.get().getId())).build();

            return Response.created(uri).build();
        } catch (KeyAlreadyExistsException e){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removePlayer(@PathParam("id") long id){
        try {
            database.removePlayer(id);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayer(Player player, @Context UriInfo uriInfo){
        try{
            Optional<Player> playerOptional = database.updatePlayer(player);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(playerOptional.get().getId())).build();

            return Response.created(uri).build();
        } catch (KeyAlreadyExistsException e){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }



}