package com.ericadiogo.mycycle;

import java.io.Serializable;

public class DailyInfo implements Serializable {
    private String id;
    private SelDate selDate;

    public static class SelDate implements Serializable{
        private String selD;
        private CurrentInfo currentInfo;

        public static class CurrentInfo implements Serializable{
            private Boolean perStart;

            public CurrentInfo(Boolean perStart) {
                this.perStart = perStart;
            }

            public CurrentInfo(){

            }

            public Boolean getPerStart() {
                return perStart;
            }

            public void setPerStart(Boolean perStart) {
                this.perStart = perStart;
            }
        }

        public void SelD(String selDate, Boolean perStart) {
            this.selD = selDate;
        }

        public void SelD(){

        }

        public String getSelD() {
            return selD;
        }

        public void setSelD(String selS) {
            this.selD = selD;
        }
    }

    public DailyInfo(String id,SelDate selDate) {
        this.id = id;
        this.selDate = selDate;
    }

    public DailyInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
