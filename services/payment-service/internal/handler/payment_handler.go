package handler

import (
	"net/http"
	"time"

	"github.com/anupamavm/platformops/services/payment-service/internal/model"
	"github.com/anupamavm/platformops/services/payment-service/internal/service"

	"github.com/gin-gonic/gin"
)

type PaymentHandler struct {
    PaymentService *service.PaymentService
}

func (h *PaymentHandler) CreatePayment(c *gin.Context) {
    var req model.PaymentRequest
    if err := c.ShouldBindJSON(&req); err != nil {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        return
    }

    payment := h.PaymentService.ProcessPayment(req)

    resp := model.PaymentResponse{
        ID:        payment.ID,
        Status:    payment.Status,
        Amount:    payment.Amount,
        Currency:  payment.Currency,
        CreatedAt: payment.CreatedAt.Format(time.RFC3339),
    }

    c.JSON(http.StatusOK, resp)
}