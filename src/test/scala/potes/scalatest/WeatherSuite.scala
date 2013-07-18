package potes.scalatest

import org.scalatest.FunSuite
import potes.WeatherClient
import co.freeside.betamax.TapeMode

class WeatherSuite extends FunSuite with Betamax {

  testWithBetamax("scala-test", Some(TapeMode.READ_ONLY))("weather for london") {
    assert(WeatherClient.weatherFor("london,gb").location === "London, GB")
  }

}