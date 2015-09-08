package com.github.thiagosqr;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

/**
 * Define API for object conversions
 */
public interface Convertible {

    final Logger log = Logger.getLogger(Convertible.class);

    default List<String> asListofValues(final String... fields) {
        return asListofValues(null, 0, null, fields);
    }

    default List<String> asListofValues(final Function<Object, String> f, final Integer additionalValOrder, final String fieldForAdditionalVal, final String... fields) {
        final List<String> values = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            try {
                if(additionalValOrder == i && f != null){
                    values.add(asString(f.apply(getMethodValue(fieldForAdditionalVal))));
                }
                values.add(asString(getMethodValue(fields[i])));
            } catch (IllegalAccessException e) {
                log.error(e);
            } catch (InvocationTargetException e) {
                log.error(e);
            }
        }
        return values;
    }

    default Map<String, String> asMapofValues(final String... fields) {
        return asMapofValues(null, null, null, fields);
    }

    default Map<String,String> asMapofValues(final Function<Object, String> f, final String additionalField, final String fieldForAdditionalVal, final String... fields) {
        final Map<String,String> map = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            try {
                if(f != null && !map.containsKey(additionalField)) {
                    map.put(additionalField,asString(f.apply(getMethodValue(fieldForAdditionalVal))));
                }
                map.put(fields[i], asString(getMethodValue(fields[i])));
            } catch (IllegalAccessException e) {
                log.error(e);
            } catch (InvocationTargetException e) {
                log.error(e);
            }
        }
        return map;
    }

    default String asString(final Object fieldValue) {
        if (Date.class.isInstance(fieldValue)) {
            return ((Date) fieldValue).toInstant().atZone( ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return String.valueOf(fieldValue);
        }
    }

    default Object getMethodValue(final String field) throws InvocationTargetException, IllegalAccessException {
        final String format = "get".concat(String.valueOf(field.charAt(0)).toUpperCase()).concat(field.substring(1));
        final Method method = Arrays.asList(this.getClass().getDeclaredMethods()).stream().filter(m -> m.getName().equals(format)).findFirst().orElse(null);
        return method.invoke(this,null);
    }

}