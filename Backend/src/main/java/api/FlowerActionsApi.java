package api;

import annotations.POST;
import annotations.Path;
import annotations.PathParam;
import annotations.RequestBody;
import handlers.Response;

public interface FlowerActionsApi {

    @POST
    @Path("/actions/{id}")
    Response takeAction (@PathParam("id") int id, @RequestBody String action);

}
