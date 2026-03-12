FROM flyway/flyway:9

# Copy migration scripts into container
COPY sql /flyway/sql
COPY flyway.conf /flyway/conf/flyway.conf

# Flyway will run automatically on container start
CMD ["migrate"]