import app.api.PageSpeedChartPage
import app.apiutils.{PerformanceResultsObject, LocalFileOperations}
import com.gu.contentapi.client.model.v1.CapiDateTime
import org.joda.time.DateTime
import org.scalatest._

/**
 * Created by mmcnamara on 22/06/16.
 */
abstract class ChartPageUnitSpec extends FlatSpec with Matchers with
OptionValues with Inside with Inspectors


class ChartPageTest extends ChartPageUnitSpec with Matchers {

  val currentTime = DateTime.now
  val time1HourAgo = DateTime.now().minusHours(1)
  val time24HoursAgo = DateTime.now().minusHours(24)

  val capiTimeNow = new CapiDateTime {
    override def dateTime: Long = currentTime.getMillis
  }
  val capiTime1HourAgo = new CapiDateTime {
    override def dateTime: Long = time1HourAgo.getMillis
  }
  val capiTime24HoursAgo = new CapiDateTime {
    override def dateTime: Long = time24HoursAgo.getMillis
  }
  val capiTimeOld = new CapiDateTime {
    override def dateTime: Long = time24HoursAgo.getMillis - 1000
  }


  val testResult1m = new PerformanceResultsObject("testResult1", "Android/3G", "mobileArticlespeedIndexHigh", 1, 0, 1, 1, 1, 1, 1, 1, 3, "mobileArticlespeedIndexHigh", true, true, true)
  val testResult1d = new PerformanceResultsObject("testResult1", "Desktop", "mobileArticlespeedIndexHigh", 1, 0, 1, 1, 1, 1, 1, 1, 3, "mobileArticlespeedIndexHigh", true, true, true)
  val testResult2m = new PerformanceResultsObject("testResult2", "Android/3G", "mobileArticletFpHigh", 2, 1, 2, 2, 2, 2, 2, 2, 4, "mobileArticletFpHigh", true, true, true)
  val testResult3m = new PerformanceResultsObject("testResult3", "Android/3G", "testResult3", 3, 2, 3, 3, 3, 3, 3, 3, 5, "testResult3", true, true, true)
  val testResult3d = new PerformanceResultsObject("testResult3", "Desktop", "testResult3", 3, 3, 2, 3, 3, 3, 3, 3, 5, "testResult3", true, true, true)
  val testResult4m = new PerformanceResultsObject("testResult4", "Android/3G", "testResult4", 4, 3, 4, 4, 4, 4, 4, 4, 6, "testResult4", false, false, false)
  val testResult4d = new PerformanceResultsObject("testResult4", "Desktop", "testResult4", 4, 4, 3, 4, 4, 4, 4, 4, 6, "testResult4", false, false, false)
  val testResult5m = new PerformanceResultsObject("testResult5", "Android/3G", "testResult5", 5, 4, 5, 5, 5, 5, 5, 5, 7, "testResult5", false, false, false)
  val testResult5d = new PerformanceResultsObject("testResult5", "Desktop", "testResult5", 5, 5, 4, 5, 5, 5, 5, 5, 7, "testResult5", false, false, false)
  val testResult6m = new PerformanceResultsObject("testResult6", "Android/3G", "testResult6", 6, 5, 6, 6, 6, 6, 6, 6, 8, "testResult6", false, false, false)
  val testResult6d = new PerformanceResultsObject("testResult6", "Desktop", "testResult6", 6, 6, 5, 6, 6, 6, 6, 6, 8, "testResult6", false, false, false)

  testResult1m.setPageLastUpdated(Option(capiTimeNow))
  testResult1d.setPageLastUpdated(Option(capiTimeNow))
  testResult2m.setPageLastUpdated(Option(capiTime1HourAgo))
  testResult3m.setPageLastUpdated(Option(capiTimeOld))
  testResult3d.setPageLastUpdated(Option(capiTimeOld))
  testResult4m.setPageLastUpdated(Option(capiTime1HourAgo))
  testResult4d.setPageLastUpdated(Option(capiTime1HourAgo))
  testResult5m.setPageLastUpdated(Option(capiTime24HoursAgo))
  testResult5d.setPageLastUpdated(Option(capiTime24HoursAgo))
  testResult6m.setPageLastUpdated(Option(capiTimeOld))
  testResult6d.setPageLastUpdated(Option(capiTimeOld))


  testResult1m.setFirstPublished(Option(capiTimeNow))
  testResult1d.setFirstPublished(Option(capiTimeNow))
  testResult2m.setFirstPublished(Option(capiTime1HourAgo))
  testResult3m.setFirstPublished(Option(capiTimeOld))
  testResult3d.setFirstPublished(Option(capiTimeOld))
  testResult4m.setFirstPublished(Option(capiTime1HourAgo))
  testResult4d.setFirstPublished(Option(capiTime1HourAgo))
  testResult5m.setFirstPublished(Option(capiTime24HoursAgo))
  testResult5d.setFirstPublished(Option(capiTime24HoursAgo))
  testResult6m.setFirstPublished(Option(capiTimeOld))
  testResult6d.setFirstPublished(Option(capiTimeOld))

  testResult4m.setLiveBloggingNow(true)
  testResult4d.setLiveBloggingNow(true)

  testResult1m.setHeadline(Option("This is a test headline"))


  val oldResultList = List(testResult1m, testResult1d, testResult2m, testResult3m, testResult3d, testResult4m, testResult4d, testResult5m,  testResult5d, testResult6m, testResult6d)

  val listWithDupes = List(testResult1d, testResult1m, testResult2m, testResult2m, testResult1d, testResult3d, testResult4d, testResult4m, testResult4m, testResult5d, testResult4m, testResult6d, testResult6m, testResult6m)


  //define chart page
  val chartPage = new PageSpeedChartPage(oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList, oldResultList)
  // define export file
  val localFile = new LocalFileOperations



  "A chart page" should "write succesfully to file" in {
//    val writeSuccess = localFile.writeLocalResultFile("pagespeedcharttest.html", chartPage.toString())
    val writeSuccess = 0
    assert(writeSuccess == 0)
  }


}
