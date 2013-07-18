package potes
import scala.xml.XML
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

object WeatherClient {
  
  val xmlDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

  def weatherFor(query: String) = {
    val xml = XML.load(new URL("http://api.openweathermap.org/data/2.5/forecast?q="+query+"&mode=xml"))
    val forecast = (xml \\ "forecast" \ "time").map(time => {
      Forecast(
          xmlDateTime parse (time \ "@from").head.text,
          xmlDateTime parse (time \ "@to").head.text,
          (time \ "symbol" \ "@name").head.text,
          (time \ "windSpeed" \ "@name").head.text + " - " + (time \ "windDirection" \ "@code").head.text,
          (time \ "temperature" \ "@value").head.text.toDouble.toInt,
          (time \ "pressure" \ "@value").head.text.toDouble.toInt,
          (time \ "humidity" \ "@value").head.text.toInt,
          (time \ "clouds" \ "@all").head.text.toInt
      )
    })
    val loc = xml \ "location"
    Result((loc \ "name").head.text + ", " + (loc \ "country").head.text,
        (loc \ "location" \ "@latitude").head.text.toDouble,
        (loc \ "location" \ "@longitude").head.text.toDouble,
        forecast.toList)
  }
}

case class Result (location: String, latitude: Double, longitude: Double, forecast: List[Forecast])

case class Forecast (
    from: Date, 
    to: Date, 
    summary: String, 
    wind: String, 
    temp: Int, 
    pressure: Int, 
    humidityPercent: Int, 
    cloudsPercent: Int)