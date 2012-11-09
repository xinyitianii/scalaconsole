package org.scalaconsole

import swing.event.Key
import swing.SwingWorker
import javax.swing.SwingUtilities
import akka.actor._


object `package` {
  type AIG = Map[String, List[Map[String, String]]]
  type AIMap = Map[String, AIG]

  private[scalaconsole] val ScalaCoreLibraries = Set("scala-compiler", "scala-library", "scala-swing", "scalap", "scala-dbc")
  val SupportedScalaVersions = Map(
    "2.9.0.final" -> "2.9.0",
    "2.9.0.1" -> "2.9.0-1",
    "2.9.1.final" -> "2.9.1",
    "2.9.1.1" -> "2.9.1-1",
    "2.9.2" -> "2.9.2",
    "2.10.0-RC2" -> "2.10.0-RC2"
  )
  val isMac = System.getProperty("os.name").toLowerCase.contains("mac")
  val ControlOrMeta = if (isMac) Key.Modifier.Meta else Key.Modifier.Control

  class Times(val n: Int) {
    def times(b: => Unit) {
      var count = 0
      def worker: javax.swing.SwingWorker[Unit, Unit] = new javax.swing.SwingWorker[Unit, Unit]() {
        override def doInBackground() {
          if (count < n) {
            b
            count += 1
            worker.execute()
          }
        }
      }
      worker.execute()
    }
  }

  implicit def int2Times(n: Int) = new Times(n)

  val actorSystem = ActorSystem()

  trait ActorFactory {
    def naked(action: => Any): ActorRef
  }

  class ActorFactoryImpl extends ActorFactory{
    def naked(action: => Any) = TypedActor.context.actorOf(Props(new Actor() {
      action

      override def receive = Actor.emptyBehavior
    }))
  }
}