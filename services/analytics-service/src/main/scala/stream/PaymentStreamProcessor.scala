object PaymentStreamProcessor {

  def start(): Unit = {
    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "analytics-service")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")

    val builder = new StreamsBuilder()

    val payments: KStream[String, String] =
      builder.stream("payments")

    payments.foreach((key, value) => {
      println(s"Processing payment: $value")
      // Later: parse JSON and aggregate
    })

    val streams = new KafkaStreams(builder.build(), props)
    streams.start()
  }
}