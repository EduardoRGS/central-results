package com.results.central_results.infrastructure.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        
        // Verificar PostgreSQL
        if (isPostgreSQLUp()) {
            builder.up().withDetail("postgresql", "UP");
        } else {
            builder.down().withDetail("postgresql", "DOWN");
        }
        
        // Verificar Redis
        if (isRedisUp()) {
            builder.up().withDetail("redis", "UP");
        } else {
            builder.down().withDetail("redis", "DOWN");
        }
        
        // Verificar Kafka
        if (isKafkaUp()) {
            builder.up().withDetail("kafka", "UP");
        } else {
            builder.down().withDetail("kafka", "DOWN");
        }
        
        return builder.build();
    }
    
    private boolean isPostgreSQLUp() {
        try (Socket socket = new Socket("localhost", 5432)) {
            return socket.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isRedisUp() {
        try (Socket socket = new Socket("localhost", 6379)) {
            return socket.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isKafkaUp() {
        try (Socket socket = new Socket("localhost", 9092)) {
            return socket.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
} 