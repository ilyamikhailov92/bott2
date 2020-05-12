package Com.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "bot")
@Data
public class BotYmlConfig {

    private String telegramTokenName;
    private String telegramTokenValue;
    private String telegramProxyHost;
    private int telegramProxyPort;
    private String telegramProxyLogin;
    private String telegramProxyPassword;

}
