package vlfsoft.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nonnull;

final public class JdbcUtils {
    private JdbcUtils() {
    }

    public static <T> Optional<T> getOptionalColumnValue(T aColumnValue, ResultSet aResultSet) throws SQLException {
        return aResultSet.wasNull() ? Optional.empty() : Optional.of(aColumnValue);
    }

    /**
     * See <a href="http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date_Convert/Convert_java_sql_Timestamp_to_LocalDateTime.htm">Java Data Type How to - Convert java.sql.Timestamp to LocalDateTime</a>
     * To avoid dependencies doubled this method from vlfsoft.common.nui.rxjdbc.RxJdbcQuery
     */
    public static Optional<LocalDateTime> getLocalDateTime(final ResultSet aResultSet, int i) throws SQLException {
        Timestamp timestamp = aResultSet.getTimestamp(i);
        return !aResultSet.wasNull() && timestamp != null ? Optional.of(LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.ofHours(0))) : Optional.empty();
    }

    // http://stackoverflow.com/questions/21162753/jdbc-resultset-i-need-a-getdatetime-but-there-is-only-getdate-and-gettimestamp
    public static Date getDateTime(final ResultSet aResultSet, int i) throws SQLException {
        Timestamp timestamp = aResultSet.getTimestamp(i);
        return !aResultSet.wasNull() && timestamp != null ? new java.util.Date(timestamp.getTime()) : null;
    }

    public static String getIsNullSqlFragment(final @Nonnull Optional<String> aParameter) throws SQLException {
        return aParameter.map((aReplyToCommentValue) -> " = '" + aReplyToCommentValue + "'").orElse("is null");
    }
}
