from app.models.payment import PaymentEvent, RiskScoreResponse

class RiskService:

    def score_payment(self, payment: PaymentEvent) -> RiskScoreResponse:
        # Placeholder simple scoring
        risk_score = 0.0

        if payment.amount > 1000:  # high amount -> higher risk
            risk_score += 0.7
        if payment.currency != "USD":  # cross-currency payments
            risk_score += 0.2

        flagged = risk_score > 0.5

        return RiskScoreResponse(payment_id=payment.id, risk_score=risk_score, flagged=flagged)