package com.rentevent.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.load();
        String cloudName = dotenv.get("CLOUD_NAME");
        String apiKey = dotenv.get("API_KEY");
        String apiSecret = dotenv.get("API_SECRET");

        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

}
