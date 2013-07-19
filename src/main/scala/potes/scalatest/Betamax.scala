package potes.scalatest

import co.freeside.betamax.TapeMode
import co.freeside.betamax.Recorder
import co.freeside.betamax.proxy.jetty.ProxyServer
import org.scalatest.Tag
import scala.runtime.AbstractFunction0
import scala.runtime.BoxedUnit

trait Betamax {

  private[Betamax]
  class betamax(tape: String, mode: Option[TapeMode])(testFun: => Unit) {
    def using_:(f: (=> Unit) => Unit) = {
      println("Registering test")
      f {
        println("Starting Betamax")
        val recorder = new Recorder
        val proxyServer = new ProxyServer(recorder)
        recorder.insertTape(tape)
        recorder.getTape.setMode(mode.getOrElse(recorder.getDefaultMode()))
        proxyServer.start()
        try {
          testFun
        } finally {
          recorder.ejectTape()
          proxyServer.stop()
        }
      }
    }
  }
  object betamax {
    def apply(tape: String, mode: Option[TapeMode] = None)(testFun: => Unit) = new betamax(tape, mode)(testFun)
  }

}