package com.iam725.kunal.goaltracker;

import java.util.HashMap;
import java.util.Map;

public class Goal {

        final String DESCRIPTION = "description";
        final String START_DATE = "startDate";
        final String END_DATE = "endDate";
        final String STATUS = "status";
        final String KEY = "key";

        private String description;
        private String startDate;
        private String key;
        private String endDate;
        private String status;
        public Goal() {}

        public Goal(String key, String description, String startDate, String endDate, String status) {
                this.key = key;
                this.description = description;
                this.startDate = startDate;
                this.endDate = endDate;
                this.status = status;
        }

        public String getDescription() {
                return description;
        }

        public String getStartDate() {
                return startDate;
        }

        public String getEndDate() {
                return endDate;
        }

        public String getStatus() {
                return status;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public void setStartDate(String startDate) {
                this.startDate = startDate;
        }

        public void setEndDate(String endDate) {
                this.endDate = endDate;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public Map<String, String> toMap() {
                Map<String, String> map = new HashMap<>();
                map.put(KEY, key);
                map.put(DESCRIPTION, description);
                map.put(START_DATE, startDate);
                map.put(END_DATE, endDate);
                map.put(STATUS, status);
                return map;
        }

        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        public String toString() {
                return key + ", "+ description + ", "+ startDate + ", "+ endDate+", "+ status;
        }
}
