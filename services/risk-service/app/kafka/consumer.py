from kafka import KafkaConsumer
import json
from app.models.payment import PaymentEvent
from app.services.risk_service import RiskService
from app.core.config import settings

class PaymentConsumer:

    def __init__(self):
        self.consumer = KafkaConsumer(
            settings.KAFKA_TOPIC,
            bootstrap_servers=[settings.KAFKA_BOOTSTRAP_SERVERS],
            group_id="risk-service",
            value_deserializer=lambda m: json.loads(m.decode("utf-8"))
        )
        self.risk_service = RiskService()

    def consume(self):
        for message in self.consumer:
            payment_data = PaymentEvent(**message.value)
            risk_result = self.risk_service.score_payment(payment_data)
            print(f"Processed Payment {payment_data.id}: {risk_result}")