package com.example.demo.infra.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

public class PostgresTestContainer extends PostgreSQLContainer {
  private static final Logger logger = LoggerFactory.getLogger(PostgresTestContainer.class);
  private static final String POSTGRES_IMAGE_NAME = "postgres:15-alpine";
  private static final Network SHARED_NETWORK = Network.newNetwork();
  private static final String MIGRATE_IMAGE_NAME = "migrate/migrate:v4.19.1";

  public PostgresTestContainer() {
    super(POSTGRES_IMAGE_NAME);
    this.withNetwork(SHARED_NETWORK)
        .withNetworkAliases("postgres-db");
  }

  @Override
  public void start() {
    // 1. まず Postgres 本体を起動
    super.start();

    // 2. 起動直後にマイグレーションを実行
    runMigration();
  }

  private void runMigration() {
    logger.info("Starting database migration...");

    try (GenericContainer<?> migrator = new GenericContainer<>(MIGRATE_IMAGE_NAME)) {
      migrator.withNetwork(SHARED_NETWORK);

      // プロジェクトルートを探し、SQLファイルをフラットにコピー（prepare_links の再現）
      Path migrationsDir = findMigrationsDir();
      try (Stream<Path> paths = Files.walk(migrationsDir)) {
        paths.filter(Files::isRegularFile)
            .filter(p -> p.toString().endsWith(".up.sql"))
            .forEach(sqlPath -> {
              migrator.withCopyFileToContainer(
                  MountableFile.forHostPath(sqlPath),
                  "/migrations/" + sqlPath.getFileName().toString());
            });
      }

      // DB接続情報の構築（コンテナ間通信用のURL）
      String dbUrl = String.format("postgres://%s:%s@%s:5432/%s?sslmode=disable",
          this.getUsername(), this.getPassword(), "postgres-db", this.getDatabaseName());

      migrator.withCommand("-path", "/migrations", "-database", dbUrl, "up")
          .withLogConsumer(new Slf4jLogConsumer(logger))
          .start();

      // 完了まで待機
      while (migrator.isRunning()) {
        Thread.sleep(100);
      }
      logger.info("Database migration completed successfully.");
    } catch (Exception e) {
      throw new RuntimeException("Database migration failed", e);
    }
  }

  private Path findMigrationsDir() {
    // プロジェクトルート(settings.gradleがある場所)まで遡る
    Path path = Paths.get(".").toAbsolutePath().normalize();
    while (path != null && !Files.exists(path.resolve("settings.gradle"))) {
      path = path.getParent();
    }
    if (path == null)
      throw new IllegalStateException("Project root not found");

    // scripts/db-migration/migrations を特定
    return path.resolve("scripts/db-migration/migrations");
  }
}