package id.co.bca.camsbatch.utility;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataParserUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataParserUtils.class);

    /**
     * Parse a date string with the given format.
     * 
     * @param dateField The date field (string or Date)
     * @param format The date format pattern (e.g., "dd-MM-yyyy")
     * @return The parsed Date or null if parsing fails
     */
    public static Date parseDate(Object dateField, String format) {
        if (dateField == null) {
            return null;
        }

        // If it's already a Date, return it
        if (dateField instanceof Date) {
            return (Date) dateField;
        }

        // If it's a String, parse it
        if (dateField instanceof String) {
            String dateStr = (String) dateField;
            if (dateStr.trim().isEmpty()) {
                return null;
            }

            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
                return dateFormatter.parse(dateStr);
            } catch (ParseException e) {
                logger.error("Error parsing date: {} with format {}", dateStr, format, e);
                return null;
            }
        }

        return null;
    }

    /**
     * Parse a date using the default format "dd-MM-yyyy"
     */
    public static Date parseDate(Object dateField) {
        return parseDate(dateField, "dd-MM-yyyy");
    }

    /**
     * Parse an integer value that might be a string from the flat file
     */
    public static Integer parseInteger(Object intField) {
        if (intField == null) {
            return null;
        }

        // If it's already an Integer, return it
        if (intField instanceof Integer) {
            return (Integer) intField;
        }

        // If it's a String, parse it
        if (intField instanceof String) {
            String intStr = (String) intField;
            if (intStr.trim().isEmpty()) {
                return null;
            }

            try {
                return Integer.parseInt(intStr.trim());
            } catch (NumberFormatException e) {
                logger.error("Error parsing integer: {}", intStr, e);
                return null;
            }
        }

        return null;
    }

    /**
     * Parse a Long value
     */
    public static Long parseLong(Object longField) {
        if (longField == null) {
            return null;
        }

        // If it's already a Long, return it
        if (longField instanceof Long) {
            return (Long) longField;
        }

        // If it's an Integer, convert it
        if (longField instanceof Integer) {
            return ((Integer) longField).longValue();
        }

        // If it's a String, parse it
        if (longField instanceof String) {
            String longStr = (String) longField;
            if (longStr.trim().isEmpty()) {
                return null;
            }

            try {
                return Long.parseLong(longStr.trim());
            } catch (NumberFormatException e) {
                logger.error("Error parsing long: {}", longStr, e);
                return null;
            }
        }

        return null;
    }

    /**
     * Parse a BigDecimal value that might be a string from the flat file
     */
    public static BigDecimal parseBigDecimal(Object decimalField) {
        if (decimalField == null) {
            return null;
        }

        // If it's already a BigDecimal, return it
        if (decimalField instanceof BigDecimal) {
            return (BigDecimal) decimalField;
        }

        // If it's a number type, convert it
        if (decimalField instanceof Number) {
            return new BigDecimal(decimalField.toString());
        }

        // If it's a String, parse it
        if (decimalField instanceof String) {
            String decimalStr = (String) decimalField;
            if (decimalStr.trim().isEmpty()) {
                return null;
            }

            try {
                return new BigDecimal(decimalStr.trim());
            } catch (NumberFormatException e) {
                logger.error("Error parsing decimal: {}", decimalStr, e);
                return null;
            }
        }

        return null;
    }

    /**
     * Parse a Boolean value 
     */
    public static Boolean parseBoolean(Object boolField) {
        if (boolField == null) {
            return null;
        }

        // If it's already a Boolean, return it
        if (boolField instanceof Boolean) {
            return (Boolean) boolField;
        }

        // If it's a String, parse it
        if (boolField instanceof String) {
            String boolStr = ((String) boolField).trim().toLowerCase();
            if (boolStr.isEmpty()) {
                return null;
            }

            // Handle various boolean representations
            return "true".equals(boolStr) || 
                   "yes".equals(boolStr) || 
                   "y".equals(boolStr) || 
                   "1".equals(boolStr);
        }

        // If it's a number, treat 1 as true, 0 as false
        if (boolField instanceof Number) {
            return ((Number) boolField).intValue() == 1;
        }

        return null;
    }

    /**
     * Parse a Double value
     */
    public static Double parseDouble(Object doubleField) {
        if (doubleField == null) {
            return null;
        }

        // If it's already a Double, return it
        if (doubleField instanceof Double) {
            return (Double) doubleField;
        }

        // If it's another number type, convert it
        if (doubleField instanceof Number) {
            return ((Number) doubleField).doubleValue();
        }

        // If it's a String, parse it
        if (doubleField instanceof String) {
            String doubleStr = (String) doubleField;
            if (doubleStr.trim().isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(doubleStr.trim());
            } catch (NumberFormatException e) {
                logger.error("Error parsing double: {}", doubleStr, e);
                return null;
            }
        }

        return null;
    }

    /**
     * Safe convert to String
     */
    public static String parseString(Object field) {
        if (field == null) {
            return null;
        }

        return field.toString().trim();
    }

}
