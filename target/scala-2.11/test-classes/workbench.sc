import java.net.Proxy
import java.util.Properties
import javax.mail.{PasswordAuthentication, Session}

import app.apiutils.PerformanceResultsObject
import com.fasterxml.jackson.databind.JsonNode
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.json.JsonString
import com.gu.contentapi.client.model.v1.CapiDateTime
import com.squareup.okhttp._
import org.joda.time.DateTime
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.functional.syntax._

val myUrlString = "http://www.theguardian.co.uk/uk"
val domainName= "www.theguardian.co.uk"
val myId = myUrlString.substring(myUrlString.indexOf(domainName)+domainName.length+1,myUrlString.length)


val countCats = "Onece a cat sat on my lap. I called this cat laptop cat and so the cat sat"
def countWord(word:String, phrase: String): Int = {
  if(phrase.isEmpty){
    println("emptystring")
    0
  } else{
    if(phrase.contains(word)){
      println("found a cat")
      val phrasefragment = phrase.substring(phrase.indexOf(word) + word.length,phrase.length)
      1 + countWord(word, phrasefragment)
    }else{
      println("no more cats")
      0
    }
  }
}

val trialcat = countCats.substring(countCats.indexOf("cat"),countCats.length)


val catCount = countWord("cat", countCats)
println("counted "+ catCount + " cats")



case class JsonPage(pageType: String, id: String, webPublicationDate: String, findDate: String, headline: String, url: String, types: Seq[Type])
case class Type(elementType: String, alt: String, canonicalUrl: String, bootUrl: String )

//val jsonString = "{\"pageType\": \"article\", \"id\": \"law/2016/may/23/supreme-court-ruling-timothy-tyrone-foster-death-sentence\", \"webPublicationDate\": \"2016-05-23T14:38:05Z\", \"findDate\": \"2016-05-23T14:58:04.912Z\", \"headline\": \"US supreme court voids Georgia man's death sentence over racial bias on jury\", \"url\": \"https://www.theguardian.com/law/2016/may/23/supreme-court-ruling-timothy-tyrone-foster-death-sentence\"}"
/*val jsonString = """
"{pages":[{
  "pageType": "article",
  "id": "law/2016/may/23/supreme-court-ruling-timothy-tyrone-foster-death-sentence",
  "webPublicationDate": "2016-05-23T14:38:05Z",
  "findDate": "2016-05-23T14:58:04.912Z",
  "headline": "US supreme court voids Georgia man's death sentence over racial bias on jury",
  "url": "https://www.theguardian.com/law/2016/may/23/supreme-court-ruling-timothy-tyrone-foster-death-sentence",
  "types": [
  {
    "type": "iframe",
    "alt": "The prosecution’s notes revealed prosecutors’ focus on the black people in the jury pool",
    "canonicalUrl": "https://interactive.guim.co.uk/embed/documentcloud/index.html?docid=2841205-supreme-court-timothy-tyrone-foster",
    "bootUrl": "https://interactive.guim.co.uk/embed/iframe-wrapper/0.1/boot.js"
  }
  ]
},
{
  "pageType": "article",
  "id": "world/2016/may/23/far-right-candidate-defeated-austrian-presidential-election-norbert-hofer",
  "webPublicationDate": "2016-05-23T14:27:01Z",
  "findDate": "2016-05-23T15:17:04.531Z",
  "headline": "Far-right candidate narrowly defeated in Austrian presidential election",
  "url": "https://www.theguardian.com/world/2016/may/23/far-right-candidate-defeated-austrian-presidential-election-norbert-hofer",
  "types": [
  {
    "type": "iframe",
    "alt": "Austrian election result",
    "canonicalUrl": "https://interactive.guim.co.uk/charts/embed/may/2016-05-23T15:12:49/embed.html",
    "bootUrl": "https://interactive.guim.co.uk/embed/iframe-wrapper/0.1/boot.js"
  }
  ]
}]}"""
/*,
{
  "pageType": "article",
  "id": "politics/2016/may/23/cameron-warns-against-self-destruct-vote-to-leave-eu",
  "webPublicationDate": "2016-05-23T10:28:47Z",
  "findDate": "2016-05-23T10:50:04.259Z",
  "headline": "Cameron warns against 'self-destruct' vote to leave EU",
  "url": "https://www.theguardian.com/politics/2016/may/23/cameron-warns-against-self-destruct-vote-to-leave-eu",
  "types": [
  {
    "type": "iframe",
    "alt": "Brexit explained: numbers carousel",
    "canonicalUrl": "https://interactive.guim.co.uk/testing/2016/05/brexit-companion/embed/embed.html?sheet=beginner&id=numbers_1&format=carousel",
    "bootUrl": "https://interactive.guim.co.uk/embed/iframe-wrapper/0.1/boot.js"
  }
  ]
}]"""*/*/


  def convertStringToCapiDateTime(time: String) = {
    val timeAsDateTime = new DateTime(time)
    val timeAsLong = timeAsDateTime.getMillis
    val timeAsCAPI = new CapiDateTime {
      override def dateTime: Long = timeAsLong
    }
    timeAsCAPI
  }

def capiTimeToString(time: CapiDateTime): String = {
    val capiDT:CapiDateTime = time
    val timeLong = capiDT.dateTime
    val moreUseableTime = new DateTime(timeLong)
    moreUseableTime.toDate.toString
}


val timeString = "2016-05-23T14:27:01Z"

  val myCapiTime = convertStringToCapiDateTime(timeString)

  val capiString = capiTimeToString(myCapiTime)

/*

  val myjson = Json.parse(jsonString)
//val page = Json.fromJson(json, jsonPage.getClass)
println("Hi")
println(myjson.toString())
val pageTypeReads: Reads[String] = (JsPath \ "pageType").read[String]
val idReads: Reads[String] = (JsPath \ "id").read[String]
val webPubDateReads: Reads[String] = (JsPath \ "webPublicationDate").read[String]
val findDateReads: Reads[String] = (JsPath \ "findDate").read[String]
val headlineReads: Reads[String] = (JsPath \ "headline").read[String]
val urlReads: Reads[String] = (JsPath \ "url").read[String]
/*implicit val jsonPageReadsBuilder =
  (JsPath \ "pageType").read[String] and
    (JsPath \ "id").read[String] and
    (JsPath \ "webPublicationDate").read[String] and
    (JsPath \ "findDate").read[String] and
    (JsPath \ "headline").read[String] and
    (JsPath \ "url").read[String]


implicit val jsonPageReads: Reads[JsonPage] = jsonPageReadsBuilder.apply(JsonPage.apply _)
*/

implicit val typeReads: Reads[VisualsElementType] = (
  (JsPath \ "type").read[String] and
    (JsPath \ "alt").read[String] and
    (JsPath \ "canonicalUrl").read[String] and
    (JsPath \ "bootUrl").read[String]
  )(VisualsElementType.apply _)

implicit val JsonPageReads: Reads[JsonPage] = (
  (JsPath \ "pageType").read[String] and
    (JsPath \ "id").read[String] and
    (JsPath \ "webPublicationDate").read[String] and
    (JsPath \ "findDate").read[String] and
    (JsPath \ "headline").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "types").read[Seq[VisualsElementType]]
  )(JsonPage.apply _)





implicit val jsonPageArrayReads: Reads[Seq[JsonPage]] =
  (JsPath \ "pages").read[Seq[JsonPage]]


myjson.validate[Seq[JsonPage]] match {
  case s: JsSuccess[Seq[JsonPage]] => {
    val jsonPage = s.get
    println("extracted page from json. page values are: \n" + jsonPage.foreach(page => page.toString))
  }
  case e: JsError => {
    println("couldn't extract page from json")
  }
}



//def getPageType(json: JsValue) = json.validate[String](pageTypeReads)

//val myjsonPage = new JsonPage(
//
//)







