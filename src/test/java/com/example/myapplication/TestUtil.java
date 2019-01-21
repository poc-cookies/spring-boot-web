package com.example.myapplication;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtil {

    public static String execCmd(final String cmd) throws IOException, InterruptedException {
        final Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        final String output = input.readLine();
        input.close();
        return output;
    }

    /**
     * Simplified version of https://github.com/pupeno/to-jdbc-uri.
     *
     * @param uri Database uri
     * @param db  Database name
     * @return Database uri according to the JDBC format
     */
    public static String formatJdbcUri(final URI uri, final String db) {
        return "jdbc:" + db + "://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?user=" + uri.getUserInfo();
    }

    public static void runMigrations(final DataSource ds) {
        final Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
    }

    public static void populateDB(final DataSource ds, final String initSql) throws SQLException, IOException, URISyntaxException {
        try (final Connection c = ds.getConnection();
             final PreparedStatement ps = c.prepareStatement(readFileToString(initSql))) {
            ps.executeUpdate();
        }
    }

    public static String readFileToString(final String filename) throws URISyntaxException, IOException {
        final Path path = Paths.get(TestUtil.class.getClassLoader().getResource(filename).toURI());
        try (final Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining(System.lineSeparator())).trim();
        }
    }
}
