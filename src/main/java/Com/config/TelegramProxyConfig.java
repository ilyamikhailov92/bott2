package Com.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@RequiredArgsConstructor
public class TelegramProxyConfig {

    private final BotYmlConfig config;

    @Bean
    public DefaultBotOptions getDefaultBotOptions() {
        DefaultBotOptions defaultBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(config.getTelegramProxyHost(), config.getTelegramProxyPort()),
                new UsernamePasswordCredentials(config.getTelegramProxyLogin(), config.getTelegramProxyPassword()));
        HttpHost httpHost = new HttpHost(config.getTelegramProxyHost(), config.getTelegramProxyPort());
        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
        defaultBotOptions.setRequestConfig(requestConfig);
        defaultBotOptions.setCredentialsProvider(credsProvider);
        defaultBotOptions.setHttpProxy(httpHost);
        return defaultBotOptions;
    }
}
