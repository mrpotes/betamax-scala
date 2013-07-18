package potes.scalatest

import org.scalatest.FunSuite
import potes.WeatherClient
import co.freeside.betamax.TapeMode

class WeatherSuite extends FunSuite with Betamax {

  test("weather for london using betamax") _ using_: betamax("scala-test", Some(TapeMode.READ_ONLY)) {
    assert(WeatherClient.weatherFor("london,gb").location === "London, GB")
  }


//  testWithBetamax("scala-test", Some(TapeMode.READ_ONLY))("weather for london") {
//    assert(WeatherClient.weatherFor("london,gb").location === "London, GB")
//  }

}