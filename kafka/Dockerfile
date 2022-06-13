FROM azul/zulu-openjdk-alpine:latest
RUN apk update -y ; \
    apk add bash
RUN mkdir /app
WORKDIR /app
RUN wget https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz
RUN tar -xvf ./kafka_2.13-3.2.0.tgz
WORKDIR /app/kafka_2.13-3.2.0
COPY run.sh .
RUN chmod +x ./run.sh
CMD ["bash", "/app/kafka_2.13-3.2.0/run.sh"]