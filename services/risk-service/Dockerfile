# Build stage
FROM python:3.12-slim AS builder

# Install security updates
RUN apt-get update && apt-get install -y --no-install-recommends \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /build

# Copy only requirements first
COPY requirements.txt .

# Create virtual environment and install dependencies
RUN python -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# Install with hash verification (if requirements.txt has hashes)
RUN pip install --no-cache-dir \
    --require-hashes \
    -r requirements.txt 2>&1 | grep -v "Skipping" || pip install --no-cache-dir -r requirements.txt

# Runtime stage - minimal slim Python
FROM python:3.12-slim

# Install only runtime essentials
RUN apt-get update && apt-get install -y --no-install-recommends \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/* /var/tmp/* /tmp/*

# Create non-root user
RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

# Copy virtual environment from builder
COPY --from=builder /opt/venv /opt/venv

# Copy application code
COPY --chown=appuser:appuser ./app ./app
COPY --chown=appuser:appuser main.py .

# Set environment variables
ENV PATH="/opt/venv/bin:$PATH" \
    PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1 \
    PYTHONFAULTHANDLER=1

# Switch to non-root user
USER appuser

EXPOSE 8001

# Use python directly (not shell)
ENTRYPOINT ["python"]
CMD ["main.py"]