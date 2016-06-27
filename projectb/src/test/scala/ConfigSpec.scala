import com.typesafe.config.ConfigFactory
import org.scalatest._

class ConfigSpec extends FlatSpec with BeforeAndAfter with Matchers {
  // copied from https://gist.github.com/jessitron/8376139
	def urlses(cl: ClassLoader): Array[java.net.URL] = cl match {
		case null => Array()
		case u: java.net.URLClassLoader => u.getURLs() ++ urlses(cl.getParent)
		case _ => urlses(cl.getParent)
	}

  before {
    val urls = urlses(getClass.getClassLoader)
		println(urls.filter(_.toString.contains("projecta")).mkString("\n"))
  }

  "Project B Config" should "be read successfully" in {
    val configValue1 = ConfigFactory.load().getConfig("config").getString("value1")
    val configValue2 = ConfigFactory.load().getConfig("config").getString("value2")
    val configValue3 = ConfigFactory.load().getConfig("config").getString("value3")

    configValue1 should be === "uno"
    configValue2 should be === "two"
    configValue3 should be === "three"

    val urls = urlses(getClass.getClassLoader)
		println(urls.filter(_.toString.contains("projecta")).mkString("\n"))
  }
}
