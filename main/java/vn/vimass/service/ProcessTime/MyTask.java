package vn.vimass.service.ProcessTime;

import vn.vimass.service.log.Log;

import java.util.TimerTask;

public class MyTask extends TimerTask {
    @Override
    public void run() {
        // Code của hàm cần thực thi
        Log.logServices("Chạy tự động thành công");
        Log.logServices("Hàm kích hoạt tự động.");
    }
}

