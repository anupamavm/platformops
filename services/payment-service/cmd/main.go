package main

import (
	"os"
	"github.com/anupamavm/platformops/services/payment-service/internal/handler"
	"github.com/anupamavm/platformops/services/payment-service/internal/kafka"
	"github.com/anupamavm/platformops/services/payment-service/internal/service"

	"github.com/gin-gonic/gin"
	"github.com/segmentio/kafka-go"
)

func main() {
    r := gin.Default()

    // Kafka setup
    producer := &kafka.Producer{
        Writer: &kafka.Writer{
            Addr:     kafka.TCP(os.Getenv("KAFKA_BOOTSTRAP_SERVERS")),
            Topic:    "payments",
            Balancer: &kafka.LeastBytes{},
        },
    }

    paymentService := &service.PaymentService{KafkaProducer: producer}
    paymentHandler := &handler.PaymentHandler{PaymentService: paymentService}

    r.POST("/api/v1/payments", paymentHandler.CreatePayment)

    r.Run(":8000")
}