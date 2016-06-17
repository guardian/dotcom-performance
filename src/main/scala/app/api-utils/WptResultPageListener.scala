package app.api

import app.apiutils.PerformanceResultsObject
import com.gu.contentapi.client.model.v1.{CapiDateTime, ContentFields}

/**
 * Created by mmcnamara on 15/03/16.
 */
class WptResultPageListener(page: String, tone: String, fields: Option[ContentFields],  resultUrl: String) {

  val pageUrl: String = page
  val pageType: String = tone
  val pageFields: Option[ContentFields] = fields
  val headline: Option[String] = pageFields.flatMap(_.headline)
  val firstPublished: Option[CapiDateTime] = pageFields.flatMap(_.firstPublicationDate)
  val pageLastModified: Option[CapiDateTime] = pageFields.flatMap(_.lastModified)
  val liveBloggingNow: Option[Boolean] = pageFields.flatMap(_.liveBloggingNow)
  val wptResultUrl: String = resultUrl
  var testComplete: Boolean = false
  var confirmationNeeded: Boolean = false
  var wptConfirmationResultUrl: String = ""
  var confirmationComplete: Boolean = false
  var testResults: PerformanceResultsObject = null





}
