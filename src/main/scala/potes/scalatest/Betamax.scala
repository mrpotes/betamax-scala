package potes.scalatest

import co.freeside.betamax.TapeMode
import co.freeside.betamax.Recorder
import co.freeside.betamax.proxy.jetty.ProxyServer
import org.scalatest.Tag

trait Betamax {
  
  protected def test(testName: String, testTags: Tag*)(testFun: => Unit)
  
  def testWithBetamax(tape: String, mode: Option[TapeMode] = None)(testName: String, testTags: Tag*)(testFun: => Unit) = {
    test(testName, testTags: _*) {
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