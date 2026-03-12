package service

import (
	"github.com/anupamavm/platformops/services/payment-service/internal/kafka"
	"github.com/anupamavm/platformops/services/payment-service/internal/model"
	"time"

	"github.com/google/uuid"
)

type PaymentService struct {
    KafkaProducer *kafka.Producer
}

func (s *PaymentService) ProcessPayment(req model.PaymentRequest) model.Payment {
    payment := model.Payment{
        ID:        uuid.New().String(),
        UserID:    req.UserID,
        Amount:    req.Amount,
        Currency:  req.Currency,
        Status:    "AUTHORIZED",
        CreatedAt: time.Now(),
        UpdatedAt: time.Now(),
    }

    // Emit payment event to Kafka
    s.KafkaProducer.Publish("payments", payment)

    return payment
}