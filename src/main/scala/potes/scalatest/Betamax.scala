package potes.scalatest

import co.freeside.betamax.TapeMode
import co.freeside.betamax.Recorder
import co.freeside.betamax.proxy.jetty.ProxyServer

trait Betamax extends Wrapped{

  def betamax(tape: String, mode: Option[TapeMode] = None)(testFun: => Unit) = {
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

trait Wrapped {
  implicit def wrapPartialFunction(f: (=> Unit) => Unit) = new wrapped(f)

  class wrapped(f: (=> Unit) => Unit) {
    def using(f1: => Unit) = f {
      f1
    }
  }
}