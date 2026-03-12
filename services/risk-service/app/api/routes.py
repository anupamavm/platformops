from fastapi import APIRouter
from app.models.payment import PaymentEvent
from app.services.risk_service import RiskService

router = APIRouter()
risk_service = RiskService()

@router.post("/api/v1/risk/score")
def score_payment(payment: PaymentEvent):
    return risk_service.score_payment(payment)