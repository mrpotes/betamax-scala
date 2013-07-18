package potes.specs2

import org.specs2.mutable.Around
import co.freeside.betamax.Recorder
import org.specs2.execute.AsResult
import co.freeside.betamax.proxy.jetty.ProxyServer
import co.freeside.betamax.TapeMode

class Betamax(tape: String, mode: Option[TapeMode] = None) extends Around {
  def around[T: AsResult](t: => T) = Betamax.around(t, tape, mode)
}

object Betamax {
  def apply(tape: String, mode: Option[TapeMode] = None) = new Betamax(tape, mode)

  def around[T: AsResult](t: => T, tape: String, mode: Option[TapeMode]) = {
    synchronized {
      val recorder = new Recorder
      val proxyServer = new ProxyServer(recorder)
      recorder.insertTape(tape)
      recorder.getTape.setMode(mode.getOrElse(recorder.getDefaultMode()))
      proxyServer.start()
      try {
        AsResult(t)
      } finally {
        recorder.ejectTape()
        proxyServer.stop()
      }
    }
  }
}