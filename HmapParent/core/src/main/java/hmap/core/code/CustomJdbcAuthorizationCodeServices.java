package hmap.core.code;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by welcome on 2016/9/21.
 */
public class CustomJdbcAuthorizationCodeServices extends CustomLengthRandomValueAuthorizationCodeServices {
    private static final String DEFAULT_SELECT_STATEMENT = "select code, authentication from oauth_code where code = ?";
    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_code (code, authentication) values (?, ?)";
    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_code where code = ?";
    private String selectAuthenticationSql = "select code, authentication from oauth_code where code = ?";
    private String insertAuthenticationSql = "insert into oauth_code (code, authentication) values (?, ?)";
    private String deleteAuthenticationSql = "delete from oauth_code where code = ?";
    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcAuthorizationCodeServices(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected void store(String code, OAuth2Authentication authentication) {
        this.jdbcTemplate.update(this.insertAuthenticationSql, new Object[]{code, new SqlLobValue(SerializationUtils.serialize(authentication))}, new int[]{12, 2004});
    }

    public OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication;
        try {
            authentication = (OAuth2Authentication)this.jdbcTemplate.queryForObject(this.selectAuthenticationSql, new RowMapper() {
                public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return (OAuth2Authentication)SerializationUtils.deserialize(rs.getBytes("authentication"));
                }
            }, new Object[]{code});
        } catch (EmptyResultDataAccessException var4) {
            return null;
        }

        if(authentication != null) {
            this.jdbcTemplate.update(this.deleteAuthenticationSql, new Object[]{code});
        }

        return authentication;
    }

    public void setSelectAuthenticationSql(String selectAuthenticationSql) {
        this.selectAuthenticationSql = selectAuthenticationSql;
    }

    public void setInsertAuthenticationSql(String insertAuthenticationSql) {
        this.insertAuthenticationSql = insertAuthenticationSql;
    }

    public void setDeleteAuthenticationSql(String deleteAuthenticationSql) {
        this.deleteAuthenticationSql = deleteAuthenticationSql;
    }
}
