package geektime.http2.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.util.List;


@SpringBootApplication
@Slf4j
public class H2ClientDemoApplication {
	@Value("${server.ssl.key-store}")
	private Resource keyStore;
	@Value("${server.ssl.key-store-password}")
	private String keyPass;

	public static void main(String[] args) {
		SpringApplication.run(H2ClientDemoApplication.class, args);
	}

	@Bean
	public OkHttpClient h2OkHttpClient() {
		OkHttpClient okHttpClient = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(this.keyStore.getInputStream(), keyPass.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tmf.getTrustManagers(), null);

			okHttpClient = new OkHttpClient.Builder()
					.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0])
					.hostnameVerifier((hostname, session) -> true)
					.build();
		} catch (Exception e) {
			log.error("Exception occurred!", e);
		}
		return okHttpClient;
	}

	@Bean
	public OkHttpClient h2cOkHttpClient() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.protocols(List.of(Protocol.H2_PRIOR_KNOWLEDGE))
				.build();
		return okHttpClient;
	}

	@Bean
	public ClientHttpRequestFactory requestFactory() {
		return new OkHttp3ClientHttpRequestFactory(h2OkHttpClient());
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.requestFactory(this::requestFactory).build();
	}
}
