package potes.junit

import org.junit.Test
import org.junit.Assert._
import potes.WeatherClient
import org.junit.Rule
import co.freeside.betamax.Recorder
import co.freeside.betamax.Betamax

class WeatherTest {
  /*
   * In Scala, JUnit complains that the @Rule isn't public if you annotate
   * the val, so instead we can annotate a def for it.
   * However, for Betamax this is blocked by 
   * https://github.com/junit-team/junit/issues/589
   */
  val recorder = new Recorder
  @Rule def recorderDef = recorder 
  
  @Betamax(tape="junit")
  @Test def findLondon = assertEquals("London, GB", WeatherClient.weatherFor("london,gb").location)

  @Test def findParis = assertEquals("Paris, FR", WeatherClient.weatherFor("paris,fr").location)
  
}
