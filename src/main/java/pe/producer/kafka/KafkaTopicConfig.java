package pe.producer.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

  @Value("${kafka.bootstrapAddress}")
  private String bootstrapServers;

  @Value(value = "${kafka.test.topic.name}")
  private String testTopicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {

    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic createPipTopic() {
    /// 토픽이름, 파티션갯수, 복제갯수
    return new NewTopic(testTopicName, 2, (short) 1);
  }
}