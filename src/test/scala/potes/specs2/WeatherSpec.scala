package potes.specs2

import org.specs2.mutable._
import potes.WeatherClient

class WeatherSpec extends Specification {

  "The Weather Client" should {
    "find London, GB" in Betamax("weather client") {
      WeatherClient.weatherFor("london,gb").location must beEqualTo("London, GB")
    }
    "find Paris, FR" in {
      WeatherClient.weatherFor("paris,fr").location must beEqualTo("Paris, FR")
    }
  }
  
}