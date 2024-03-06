package vn.vimass.service.table.object;

import com.google.gson.Gson;


    public class ObjectQr {
        public String idQR;
        public String getIdQR() {
            return idQR;
        }

        public void setIdQR(String idQR) {
            this.idQR = idQR;
        }

        @Override
        public String toString() {

            return new Gson().toJson(this);
        }
}
