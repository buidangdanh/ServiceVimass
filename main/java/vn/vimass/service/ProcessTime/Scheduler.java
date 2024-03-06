package vn.vimass.service.ProcessTime;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    public static void scheduleTask(int hour,int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), calendar.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }
}

