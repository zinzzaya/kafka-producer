package pe.producer.pub;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pe.producer.pub.dto.MessageDTO;

@SpringBootTest
public class KafkaProducerTest {

  @Value(value = "${kafka.test.topic.name}")
  private String testTopicName;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public KafkaProducerTest(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @DisplayName("Kafka Producer 메시지 전송")
  @Test
  public void sendMessageQue() {
    System.out.println("Kafka Producer Start!");

    List<MessageDTO> dtoList = new ArrayList<>();
    dtoList.add(new MessageDTO("1", "메시지 1번 전송"));
    dtoList.add(new MessageDTO("2", "메시지 2번 전송"));
    dtoList.add(new MessageDTO("3", "메시지 3번 전송"));

    sendMessage(testTopicName, dtoList);

    System.out.println("Kafka Producer End!");
  }

  private void sendMessage(String topic, List<MessageDTO> testDto) {
    for (MessageDTO dto : testDto) {
      Message<MessageDTO> message = MessageBuilder
          .withPayload(dto)
          .setHeader(KafkaHeaders.TOPIC, topic)
          .build();
      kafkaTemplate.send(message);
    }
  }
}
