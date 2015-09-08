package com.github.thiagosqr.conf.converters;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateParam {

    static Logger log = Logger.getLogger(DateParam.class);

    private Date date;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static DateParam fromString(String rep) {

        DateParam dp = new DateParam();

        try {

            LocalDate dateTime = LocalDate.parse(rep, formatter);
            dp.date = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        } catch (Exception e) {
            log.debug(e);
        }

        return dp;

    }

    public Date getDate(){
        return this.date;
    }

}