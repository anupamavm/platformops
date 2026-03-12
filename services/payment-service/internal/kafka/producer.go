package kafka

import (
	"context"
	"encoding/json"
	"log"

	"github.com/segmentio/kafka-go"
)

type Producer struct {
    Writer *kafka.Writer
}

func (p *Producer) Publish(topic string, message interface{}) {
    data, _ := json.Marshal(message)
    err := p.Writer.WriteMessages(
        context.Background(),
        kafka.Message{
            Topic: topic,
            Value: data,
        },
    )
    if err != nil {
        log.Printf("Kafka publish error: %v", err)
    }
}