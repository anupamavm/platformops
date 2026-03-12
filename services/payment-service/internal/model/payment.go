package model

import "time"

type Payment struct {
    ID          string    `json:"id"`
    UserID      string    `json:"user_id"`
    Amount      float64   `json:"amount"`
    Currency    string    `json:"currency"`
    Status      string    `json:"status"` // CREATED, AUTHORIZED, FAILED
    CreatedAt   time.Time `json:"created_at"`
    UpdatedAt   time.Time `json:"updated_at"`
}

type PaymentRequest struct {
    UserID   string  `json:"user_id" binding:"required"`
    Amount   float64 `json:"amount" binding:"required"`
    Currency string  `json:"currency" binding:"required"`
}

type PaymentResponse struct {
    ID        string  `json:"id"`
    Status    string  `json:"status"`
    Amount    float64 `json:"amount"`
    Currency  string  `json:"currency"`
    CreatedAt string  `json:"created_at"`
}