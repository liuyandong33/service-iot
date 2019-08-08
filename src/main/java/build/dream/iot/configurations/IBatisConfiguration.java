package build.dream.iot.configurations;

import build.dream.common.utils.DatabaseUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class IBatisConfiguration {
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        return new DatabaseIdProvider() {
            @Override
            public void setProperties(Properties p) {

            }

            @Override
            public String getDatabaseId(DataSource dataSource) throws SQLException {
                return DatabaseUtils.obtainDatabaseId(dataSource.getConnection(), true);
            }
        };
    }
}
