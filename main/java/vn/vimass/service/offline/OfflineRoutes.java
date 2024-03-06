package vn.vimass.service.offline;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.DieuPhoiFunc;
import vn.vimass.service.entity.*;
import vn.vimass.service.log.Log;

import javax.ws.rs.*;

import static vn.vimass.service.crawler.bhd.DieuPhoiFunc.*;

@Path("/offline")
@Produces("application/json;charset=utf-8")
public class OfflineRoutes {
    @POST
    @Path("/general")
    public static ResponseMessage1 checkIn(String input) {
        ResponseMessage1 res = new ResponseMessage1();

        Log.logServices("checkIn input: " + input);

        return res;
    }

}
