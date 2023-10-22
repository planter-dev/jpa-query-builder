package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import domain.PersonV1;
import domain.PersonV2;
import domain.PersonV3;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import sources.AnnotationBinder;
import sources.MetaData;
import sources.MetadataGenerator;
import sources.MetadataGeneratorImpl;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DDLTest {
    DatabaseServer server;
    final Dialect dialect = new H2Dialect();
    final QueryBuilder queryBuilder = new QueryBuilder(dialect);
    final AnnotationBinder annotationBinder = new AnnotationBinder();
    final MetadataGenerator metadataGenerator = new MetadataGeneratorImpl(annotationBinder);
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws SQLException {
        this.server = new H2();
        server.start();
        this.jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Test
    @DisplayName("요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기")
    void createTest1() {
        MetaData personv1 = metadataGenerator.generator(PersonV1.class);
        StringBuilder sb = new StringBuilder();
        StringBuilder createQuery = queryBuilder.create(personv1, sb);
        jdbcTemplate.execute(String.valueOf(createQuery));
        Assertions.assertThat("create table PersonV1 (id INT AUTO_INCREMENT PRIMARY KEY , name varchar , age int  )")
                .isEqualTo(String.valueOf(createQuery));
    }

    @Test
    @DisplayName("요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기")
    void createTest2() {
        MetaData personv2 = metadataGenerator.generator(PersonV2.class);
        StringBuilder sb = new StringBuilder();
        StringBuilder createQuery = queryBuilder.create(personv2, sb);
        jdbcTemplate.execute(String.valueOf(createQuery));
        System.out.println(String.valueOf(createQuery));
        Assertions.assertThat("create table PersonV2 (id INT AUTO_INCREMENT PRIMARY KEY , nick_name varchar , old int , email varchar not null )")
                .isEqualTo(String.valueOf(createQuery));
    }

    @Test
    @DisplayName("요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기")
    void createTest3() {
        MetaData personv3 = metadataGenerator.generator(PersonV3.class);
        StringBuilder sb = new StringBuilder();
        StringBuilder createQuery = queryBuilder.create(personv3, sb);
        jdbcTemplate.execute(String.valueOf(createQuery));
        System.out.println(String.valueOf(createQuery));
        Assertions.assertThat("create table users (id INT AUTO_INCREMENT PRIMARY KEY , nick_name varchar , old int , email varchar not null )")
                .isEqualTo(String.valueOf(createQuery));
    }

    @Test
    @DisplayName("요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기")
    void dropTest() {
        MetaData personv3 = metadataGenerator.generator(PersonV3.class);
        StringBuilder sb = new StringBuilder();
        StringBuilder dropQuery = queryBuilder.drop(personv3, sb);
        jdbcTemplate.execute(String.valueOf(dropQuery));
        System.out.println(String.valueOf(dropQuery));
        Assertions.assertThat("drop table users")
                .isEqualTo(String.valueOf(dropQuery));
    }

    @AfterAll
    void close() {
        server.stop();
    }

}