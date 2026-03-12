# Build stage
FROM golang:1.24.2-alpine3.20 AS builder

# Install security updates
RUN apk update && apk add --no-cache ca-certificates tzdata

WORKDIR /build

# Copy only dependency files first (better layer caching)
COPY go.mod go.sum ./

# Download dependencies with checksum verification
RUN go mod download && go mod verify

# Copy source code
COPY . .

# Build with security flags
# -ldflags disables symbol table/debugging info (reduces size & attack surface)
# CGO_ENABLED=0 creates static binary (no libc dependencies)
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build \
    -ldflags="-s -w -X main.Version=${VERSION:-dev}" \
    -a -installsuffix cgo \
    -o payment-service ./cmd/main.go

# Create non-root user in build stage
RUN addgroup -g 65534 appuser && adduser -D -u 65534 appuser

# Runtime stage - distroless for minimal surface
FROM gcr.io/distroless/base-debian12:nonroot

# Copy CA certificates for HTTPS
COPY --from=builder /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/

# Copy timezone data
COPY --from=builder /usr/share/zoneinfo /usr/share/zoneinfo

# Copy user from builder
COPY --from=builder /etc/passwd /etc/passwd
COPY --from=builder /etc/group /etc/group

WORKDIR /app

# Copy only binary (nothing else needed)
COPY --from=builder --chown=65534:65534 /build/payment-service .

# Run as non-root user (65534)
USER 65534

EXPOSE 8000

ENTRYPOINT ["/app/payment-service"]