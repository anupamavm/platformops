from pydantic import BaseModel
from datetime import datetime

class PaymentEvent(BaseModel):
    id: str
    user_id: str
    amount: float
    currency: str
    status: str
    created_at: datetime

class RiskScoreResponse(BaseModel):
    payment_id: str
    risk_score: float
    flagged: bool