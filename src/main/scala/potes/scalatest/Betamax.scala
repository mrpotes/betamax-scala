package potes.scalatest

import co.freeside.betamax.TapeMode
import co.freeside.betamax.Recorder
import co.freeside.betamax.proxy.jetty.ProxyServer
import org.scalatest.Tag
import scala.runtime.AbstractFunction0
import scala.runtime.BoxedUnit

trait Betamax {

  class betamax(tape: String, mode: Option[TapeMode])(testFun: => Unit) {
    def using_:(f: ( => Unit) => Unit) = f(wrapTest(testFun))
    def wrapTest(f: => Unit) = {
      def inner = {
        println("fred")
        val recorder = new Recorder
        val proxyServer = new ProxyServer(recorder)
        recorder.insertTape(tape)
        recorder.getTape.setMode(mode.getOrElse(recorder.getDefaultMode()))
        proxyServer.start()
        try {
          f
        } finally {
          recorder.ejectTape()
          proxyServer.stop()
        }
      }
      inner _
    }
  }
  object betamax {
    def apply(tape: String, mode: Option[TapeMode] = None)(testFun: => Unit) = new betamax(tape, mode)(testFun)
  }

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