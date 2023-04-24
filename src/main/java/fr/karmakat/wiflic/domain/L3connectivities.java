package fr.karmakat.wiflic.domain;

import java.util.List;

public class L3connectivities {
        public Boolean active;
        public String addr;
        public String af;
        public Long last_activity;
        public Long last_time_reachable;
        public Boolean reachable;

        public L3connectivities() {
        }

        public L3connectivities(Boolean active, String addr, String af, Long last_activity, Long last_time_reachable, Boolean reachable) {
                this.active = active;
                this.addr = addr;
                this.af = af;
                this.last_activity = last_activity;
                this.last_time_reachable = last_time_reachable;
                this.reachable = reachable;
        }

        public Boolean getActive() {
                return active;
        }

        public void setActive(Boolean active) {
                this.active = active;
        }

        public String getAddr() {
                return addr;
        }

        public void setAddr(String addr) {
                this.addr = addr;
        }

        public String getAf() {
                return af;
        }

        public void setAf(String af) {
                this.af = af;
        }

        public Long getLast_activity() {
                return last_activity;
        }

        public void setLast_activity(Long last_activity) {
                this.last_activity = last_activity;
        }

        public Long getLast_time_reachable() {
                return last_time_reachable;
        }

        public void setLast_time_reachable(Long last_time_reachable) {
                this.last_time_reachable = last_time_reachable;
        }

        public Boolean getReachable() {
                return reachable;
        }

        public void setReachable(Boolean reachable) {
                this.reachable = reachable;
        }
}
