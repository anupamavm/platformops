val route =
  path("analytics" / "health") {
    get {
      complete("Analytics Service Running")
    }
  }