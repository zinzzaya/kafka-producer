package pe.producer.pub;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pe.producer.pub.dto.MessageDTO;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

  @Value(value = "${kafka.test.topic.name}")
  private String testTopicName;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendMessageQue() {
    log.info("Kafka Producer Start! {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    //샘플..
    List<MessageDTO> dtoList = new ArrayList<>();
    dtoList.add(new MessageDTO("1", "메시지 1번 전송"));
    dtoList.add(new MessageDTO("2", "메시지 2번 전송"));
    dtoList.add(new MessageDTO("3", "메시지 3번 전송"));

    //send
    sendMessage(testTopicName, dtoList);

    log.info("Kafka Producer End! {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
