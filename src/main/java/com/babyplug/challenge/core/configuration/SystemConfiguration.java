package com.babyplug.challenge.core.configuration;

import com.babyplug.challenge.core.SystemDateTimeProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Locale;

@Configuration
public class SystemConfiguration {

	@Bean
	SystemDateTimeProvider systemDateTimeProvider() {
		return new SystemDateTimeProvider() {
            public Calendar getNow() {
                return Calendar.getInstance(Locale.US);
            }
		};
	}
	
	@Bean
	@Primary
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		return builder.modulesToInstall(new JavaTimeModule());
	}
	
	@Bean
	public RestTemplate initialRestTemplate() {
		RestTemplate restTemplate = null;

		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
			
		};
		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();
			
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);

			restTemplate = new RestTemplate(requestFactory);
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}
		return restTemplate;

	}
}
