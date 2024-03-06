package vn.vimass.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import vn.vimass.service.BackUp.BackUpRoutes;

import vn.vimass.service.crawler.bhd.RaVaoWebservice;
import vn.vimass.service.offline.OfflineRoutes;

@ApplicationPath("/services")
public class Startup extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<>();
//        result.add(SMSWebservice.class);
        result.add(RaVaoWebservice.class);
        result.add(MOXyJsonContentResolver.class);
        result.add(BackUpRoutes.class);
        result.add(OfflineRoutes.class);
        return result;
    }

}