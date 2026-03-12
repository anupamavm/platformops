import uvicorn
from fastapi import FastAPI
from app.api.routes import router
from app.kafka.consumer import PaymentConsumer
import threading
from app.core.config import settings

app = FastAPI(title="Risk Service")
app.include_router(router)

def start_consumer():
    consumer = PaymentConsumer()
    consumer.consume()

# Run Kafka consumer in a separate thread
threading.Thread(target=start_consumer, daemon=True).start()

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=settings.SERVICE_PORT)