package com.example.myapplication;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@TestConfiguration
public class DBConfig {

    @Bean
    public DataSource dataSource() throws IOException, InterruptedException, SQLException, URISyntaxException {
        final String pgUrl = TestUtil.execCmd("pg_tmp -t -w 60");
        final String jdbcUrl = TestUtil.formatJdbcUri(URI.create(pgUrl), "postgresql");
        final DriverManagerDataSource ds = new DriverManagerDataSource(jdbcUrl);
        TestUtil.runMigrations(ds);
        TestUtil.populateDB(ds, "db/initial_data.sql");
        return ds;
    }
}
