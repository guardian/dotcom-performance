package app

// note an _ instead of {} would get everything

import java.io._
import java.util

import app.api._
import app.apiutils._
import com.gu.contentapi.client.model.v1.{Office, MembershipTier, CapiDateTime, ContentFields}
import com.typesafe.config.{Config, ConfigFactory}
import org.joda.time.DateTime
import sbt.complete.Completion

import scala.collection.parallel.immutable.ParSeq
import scala.io.Source


object App {
  def main(args: Array[String]) {
    /*  This value stops the forces the config to be read and the output file to be written locally rather than reading and writing from/to S3
    #####################    this should be set to false before merging!!!!################*/

    /*#####################################################################################*/
    println("Job started at: " + DateTime.now)

    //  Define names of s3bucket, configuration and output Files
    val amazonDomain = "https://s3-eu-west-1.amazonaws.com"
//    val s3BucketName = "dotcom-performance-monitor"
    val s3BucketName = "capi-wpt-querybot"
    val configFileName = "dotcom-performance-monitor/config.conf"
    val emailFileName = "addresses.conf"
    val interactiveSampleFileName = "interactivesamples.conf"
    val visualsPagesFileName = "visuals.conf"

    val currentPerformanceIndicatorResults = "currentperformanceindicators.html"

    val articleWithManyImagesRecordDesktop = "dotcom-performance-monitor/articlewithmanyimagesdesktop.csv"
    val cartoonRecordDesktop = "dotcom-performance-monitor/cartoondesktop.csv"
    val articleWithVideosAndMapsRecordDesktop = "dotcom-performance-monitor/articlewithvideosandmapsdesktop.csv"
    val longReadRecordDesktop = "dotcom-performance-monitor/longreaddesktop.csv"
    val liveBlogRecordDesktop = "dotcom-performance-monitor/liveblogdesktop.csv"
    val footballMatchReportRecordDesktop = "dotcom-performance-monitor/footballmatchreportdesktop.csv"
    val interactivePageRecordDesktop = "dotcom-performance-monitor/interactivepagedesktop.csv"
    val videoPageRecordDesktop = "dotcom-performance-monitor/videopagedesktop.csv"
    val audioPageRecordDesktop = "dotcom-performance-monitor/audiopagedesktop.csv"
    val networkFrontUKRecordDesktop = "dotcom-performance-monitor/networkfrontukdesktop.csv"
    val networkFrontUSRecordDesktop = "dotcom-performance-monitor/networkfrontusdesktop.csv"
    val networkFrontAuRecordDesktop = "dotcom-performance-monitor/networkfrontaudesktop.csv"
    val sectionFrontRecordDesktop = "dotcom-performance-monitor/sectionfrontdesktop.csv"
    val footballPageRecordDesktop = "dotcom-performance-monitor/footballpagedesktop.csv"
    val tagPageRecordDesktop = "dotcom-performance-monitor/tagpagedesktop.csv"

    val articleWithManyImagesRecordMobile = "dotcom-performance-monitor/articlewithmanyimagesmobile.csv"
    val cartoonRecordMobile = "dotcom-performance-monitor/cartoonmobile.csv"
    val articleWithVideosAndMapsRecordMobile = "dotcom-performance-monitor/articlewithvideosandmapsmobile.csv"
    val longReadRecordMobile = "dotcom-performance-monitor/longreadmobile.csv"
    val liveBlogRecordMobile = "dotcom-performance-monitor/liveblogmobile.csv"
    val footballMatchReportRecordMobile = "dotcom-performance-monitor/footballmatchreportmobile.csv"
    val interactivePageRecordMobile = "dotcom-performance-monitor/interactivepagemobile.csv"
    val videoPageRecordMobile = "dotcom-performance-monitor/videopagemobile.csv"
    val audioPageRecordMobile = "dotcom-performance-monitor/audiopagemobile.csv"
    val networkFrontUKRecordMobile = "dotcom-performance-monitor/networkfrontukmobile.csv"
    val networkFrontUSRecordMobile = "dotcom-performance-monitor/networkfrontusmobile.csv"
    val networkFrontAuRecordMobile = "dotcom-performance-monitor/networkfrontaumobile.csv"
    val sectionFrontRecordMobile = "dotcom-performance-monitor/sectionfrontmobile.csv"
    val footballPageRecordMobile = "dotcom-performance-monitor/footballpagemobile.csv"
    val tagPageRecordMobile = "dotcom-performance-monitor/tagpagemobile.csv"



//    val resultsFromPreviousTests = "dotcom-performance-monitor/resultsFromPreviousTests.csv"

//    val duplicateResultList = "dotcom-performance-monitor/duplicateresultsfromlastrun.csv"


    //Define colors to be used for average values, warnings and alerts
    val averageColor: String = "#d9edf7"
    //    val warningColor: String = "#fcf8e3"
    val warningColor: String = "rgba(227, 251, 29, 0.32)"
    val alertColor: String = "#f2dede"

    //initialize combinedResultsLists - these will be used to sort and accumulate test results
    // for the combined page and for long term storage file

    val articleWithManyImages = "https://www.theguardian.com/environment/2016/jun/09/satellite-eye-on-earth-may-2016-in-pictures"
    val stephencollinscartoon = "https://www.theguardian.com/lifeandstyle/ng-interactive/2016/may/21/stephen-collins-on-donald-trump-cartoon"
    val articleWithVideosAndMaps = "https://www.theguardian.com/world/2016/may/20/egyptian-military-says-debris-from-egyptair-flight-ms804-found-in-sea"
    val longRead = "https://www.theguardian.com/news/2016/apr/13/how-boots-went-rogue"
    val liveBlog = "https://www.theguardian.com/politics/live/2016/jun/10/eu-referendum-live-remain-prospect-defeat-itv-debate-boris-johnson"
    val footballMatchReport = "https://www.theguardian.com/football/live/2016/feb/17/gent-wolfsburg-champions-league-live"
    val interactivePage = "https://www.theguardian.com/us-news/ng-interactive/2016/jun/07/live-primary-results-new-jersey-california"
    val videoPage = "https://www.theguardian.com/politics/video/2016/jun/07/how-would-brexit-affect-you-eu-referendum-video-explainer"
    val audioPage = "https://www.theguardian.com/news/audio/2016/jun/03/how-the-pentagon-punished-nsa-whistleblowers"
    val networkFrontUK = "https://www.theguardian.com/uk"
    val networkFrontUS = "https://www.theguardian.com/us"
    val networkFrontAu = "https://www.theguardian.com/au"
    val sectionFront = "https://www.theguardian.com/uk/culture"
    val footballPage = "https://www.theguardian.com/football"
    val tagPage = "https://www.theguardian.com/sport/boxing"

    val alternativeLiveBlog = "https://www.theguardian.com/sport/live/2016/jun/10/muhammad-ali-funeral-procession-louisville-live-updates"


    val listOfIndicatorPageUrls = List(articleWithManyImages,
    stephencollinscartoon,
    articleWithVideosAndMaps,
    longRead,
    liveBlog,
    footballMatchReport,
    interactivePage,
    videoPage,
    audioPage,
    networkFrontUK,
    networkFrontUS,
    networkFrontAu,
    sectionFront,
    footballPage,
    tagPage)


    //Create new S3 Client
    println("defining new S3 Client (this is done regardless but only used if 'iamTestingLocally' flag is set to false)")
    val s3Interface = new S3Operations(s3BucketName, configFileName, emailFileName)
    var configArray: Array[String] = Array("", "", "", "", "", "")
    var urlFragments: List[String] = List()

    //Get config settings
    println("Extracting configuration values")
    println(DateTime.now + " retrieving config from S3 bucket: " + s3BucketName)
    val returnTuple = s3Interface.getConfig
    configArray = Array(returnTuple._1,returnTuple._2,returnTuple._3,returnTuple._4,returnTuple._5,returnTuple._6,returnTuple._7)
    urlFragments = returnTuple._8

    println("checking validity of config values")
    if ((configArray(0).length < 1) || (configArray(1).length < 1) || (configArray(2).length < 1) || (configArray(3).length < 1)) {
      println("problem extracting config\n" +
        "contentApiKey length: " + configArray(0).length + "\n" +
        "wptBaseUrl length: " + configArray(1).length + "\n" +
        "wptApiKey length: " + configArray(2).length + "\n" +
        "wptLocation length: " + configArray(3).length + "\n" +
        "emailUsername length: " + configArray(4).length + "\n" +
        "emailPassword length: " + configArray(5).length) + "\n" +
        "visuals URL length: " + configArray(6).length

      System exit 1
    }
    println("config values ok")
    val contentApiKey: String = configArray(0)
    val wptBaseUrl: String = configArray(1)
    val wptApiKey: String = configArray(2)
    val wptLocation: String = configArray(3)

    //obtain list of interactive samples to determine average size
    //val listofLargeInteractives: List[String] = s3Interface.getUrls(interactiveSampleFileName)

    //obtain list of items previously alerted on
//    val previousResults: List[PerformanceResultsObject] = s3Interface.getResultsFileFromS3(resultsFromPreviousTests)

    val prevResultsArticleWithManyImagesDesktop = s3Interface.getResultsFileFromS3(articleWithManyImagesRecordDesktop)
    val prevResultsCartoonDesktop = s3Interface.getResultsFileFromS3(cartoonRecordDesktop)
    val prevResultsArticleWithVideosAndMapsDesktop = s3Interface.getResultsFileFromS3(articleWithVideosAndMapsRecordDesktop)
    val prevResultsLongReadDesktop = s3Interface.getResultsFileFromS3(longReadRecordDesktop)
    val prevResultsLiveBlogDesktop = s3Interface.getResultsFileFromS3(liveBlogRecordDesktop)
    val prevResultsFootballMatchReportDesktop = s3Interface.getResultsFileFromS3(footballMatchReportRecordDesktop)
    val prevResultsInteractivePageDesktop = s3Interface.getResultsFileFromS3(interactivePageRecordDesktop)
    val prevResultsVideoPageDesktop = s3Interface.getResultsFileFromS3(videoPageRecordDesktop)
    val prevResultsAudioPageDesktop = s3Interface.getResultsFileFromS3(audioPageRecordDesktop)
    val prevResultsNetworkFrontUKDesktop = s3Interface.getResultsFileFromS3(networkFrontUKRecordDesktop)
    val prevResultsNetworkFrontUSDesktop = s3Interface.getResultsFileFromS3(networkFrontUSRecordDesktop)
    val prevResultsNetworkFrontAuDesktop = s3Interface.getResultsFileFromS3(networkFrontAuRecordDesktop)
    val prevResultsSectionFrontDesktop = s3Interface.getResultsFileFromS3(sectionFrontRecordDesktop)
    val prevResultsFootballPageDesktop = s3Interface.getResultsFileFromS3(footballPageRecordDesktop)
    val prevResultsTagPageDesktop = s3Interface.getResultsFileFromS3(tagPageRecordDesktop)

    val prevResultsArticleWithManyImagesMobile = s3Interface.getResultsFileFromS3(articleWithManyImagesRecordMobile)
    val prevResultsCartoonMobile = s3Interface.getResultsFileFromS3(cartoonRecordMobile)
    val prevResultsArticleWithVideosAndMapsMobile = s3Interface.getResultsFileFromS3(articleWithVideosAndMapsRecordMobile)
    val prevResultsLongReadMobile = s3Interface.getResultsFileFromS3(longReadRecordMobile)
    val prevResultsLiveBlogMobile = s3Interface.getResultsFileFromS3(liveBlogRecordMobile)
    val prevResultsFootballMatchReportMobile = s3Interface.getResultsFileFromS3(footballMatchReportRecordMobile)
    val prevResultsInteractivePageMobile = s3Interface.getResultsFileFromS3(interactivePageRecordMobile)
    val prevResultsVideoPageMobile = s3Interface.getResultsFileFromS3(videoPageRecordMobile)
    val prevResultsAudioPageMobile = s3Interface.getResultsFileFromS3(audioPageRecordMobile)
    val prevResultsNetworkFrontUKMobile = s3Interface.getResultsFileFromS3(networkFrontUKRecordMobile)
    val prevResultsNetworkFrontUSMobile = s3Interface.getResultsFileFromS3(networkFrontUSRecordMobile)
    val prevResultsNetworkFrontAuMobile = s3Interface.getResultsFileFromS3(networkFrontAuRecordMobile)
    val prevResultsSectionFrontMobile = s3Interface.getResultsFileFromS3(sectionFrontRecordMobile)
    val prevResultsFootballPageMobile = s3Interface.getResultsFileFromS3(footballPageRecordMobile)
    val prevResultsTagPageMobile = s3Interface.getResultsFileFromS3(tagPageRecordMobile)


    //  Define new CAPI Query object
    val capiQuery = new ArticleUrls(contentApiKey)
    //get all content-type-lists
    val indicatorPages: List[(Option[ContentFields],String)] = listOfIndicatorPageUrls.map(page => capiQuery.getSinglePage(page))
    println(DateTime.now + " Closing Content API query connection")
    println(indicatorPages.length + " pages returned successfully")
    capiQuery.shutDown



 //todo - work in visuals list
 //   val visualsCapiResults = for(result <- combinedCapiResults if untestedVisualsTeamPagesFromToday.map(_.pageUrl).contains(result._2)) yield result
 //   val nonVisualsCapiResults = for(result <- combinedCapiResults if !untestedVisualsTeamPagesFromToday.map(_.pageUrl).contains(result._2)) yield result

 //   val nonCAPIResultsToRetest = for (result <- previousResultsToRetest if !combinedCapiResults.map(_._2).contains(result.testUrl)) yield result

//    val dedupedResultsToRetestUrls: List[String] = for (result <- nonCAPIResultsToRetest) yield result.testUrl
/*    val pagesToRetest: List[String] = previousResultsToRetest.map(_.testUrl)
    val articleUrls: List[String] = for (page <- newOrChangedArticles) yield page._2
    val liveBlogUrls: List[String] = for (page <- newOrChangedLiveBlogs) yield page._2
    val interactiveUrls: List[String] = for (page <- newOrChangedInteractives) yield page._2
    val frontsUrls: List[String] = for (page <- fronts) yield page._2
    val videoUrls: List[String] = for (page <- newOrChangedVideoPages) yield page._2
    val audioUrls: List[String] = for (page <- newOrChangedAudioPages) yield page._2
*/
    //get all pages from the visuals team api




    // sendPageWeightAlert all urls to webpagetest at once to enable parallel testing by test agents
    val urlsToSend: List[String] = listOfIndicatorPageUrls
    println("Combined list of urls contains: " + urlsToSend.length + "urls")

    val resultUrlList: List[(String, String)] = getResultPages(urlsToSend, urlFragments, wptBaseUrl, wptApiKey, wptLocation)
    println("resultUrlList length = " + resultUrlList.length)

    // build result page listeners
    // first format alerts from previous test that arent in the new capi queries

    // munge into proper format and merge these with the capi results


    //obtain results for articles


    println("Generating average values for articles")
    val articleAverages: PageAverageObject = new ArticleDefaultAverages(averageColor)
    //pageResults = pageResults.concat(articleAverages.toHTMLString)

    val pageResultsList = listenForResultPages(indicatorPages, "Indicator Page", resultUrlList, articleAverages, wptBaseUrl, wptApiKey, wptLocation, urlFragments)
    val pageResultsMobile = pageResultsList.filter(_.typeOfTest.contains("Android"))
    val pageResultsDesktop = pageResultsList.filter(_.typeOfTest.contains("Desktop"))
    val indicatorHTMLResults = new  PageSpeedDashboardTabbed(pageResultsList, pageResultsDesktop, pageResultsMobile)

    val indicatorMobileResultsArray: Array[PerformanceResultsObject] = pageResultsMobile.toArray
    val indicatorDesktopResultsArray: Array[PerformanceResultsObject] = pageResultsDesktop.toArray

      // write article results to string
      //Create a list of alerting pages and write to string
      //write article results to file
      println("Indicator Performance Test Complete")
    println("Desktop Results array length: " + indicatorDesktopResultsArray.length)
    println("Mobile Results array length: " + indicatorMobileResultsArray.length)

    val desktopLatestArticleWithManyImages = indicatorDesktopResultsArray(0)
    val desktopLatestCartoon = indicatorDesktopResultsArray(1)
    val desktopLatestArticleWithVideosAndMaps = indicatorDesktopResultsArray(2)
    val desktopLatestLongRead = indicatorDesktopResultsArray(3)
    val desktopLatestLiveBlog = indicatorDesktopResultsArray(4)
    val desktopLatestFootballMatchReport = indicatorDesktopResultsArray(5)
    val desktopLatestInteractivePage = indicatorDesktopResultsArray(6)
    val desktopLatestVideoPage = indicatorDesktopResultsArray(7)
    val desktopLatestAudioPage = indicatorDesktopResultsArray(8)
    val desktopLatestNetworkFrontUK = indicatorDesktopResultsArray(9)
    val desktopLatestNetworkFrontUS = indicatorDesktopResultsArray(10)
    val desktopLatestNetworkFrontAu = indicatorDesktopResultsArray(11)
    val desktopLatestSectionFront = indicatorDesktopResultsArray(12)
    val desktopLatestFootballPage = indicatorDesktopResultsArray(13)
    val desktopLatestTagPage = indicatorDesktopResultsArray(14)

    val mobileLatestArticleWithManyImages = indicatorMobileResultsArray(0)
    val mobileLatestCartoon = indicatorMobileResultsArray(1)
    val mobileLatestArticleWithVideosAndMaps = indicatorMobileResultsArray(2)
    val mobileLatestLongRead = indicatorMobileResultsArray(3)
    val mobileLatestLiveBlog = indicatorMobileResultsArray(4)
    val mobileLatestFootballMatchReport = indicatorMobileResultsArray(5)
    val mobileLatestInteractivePage = indicatorMobileResultsArray(6)
    val mobileLatestVideoPage = indicatorMobileResultsArray(7)
    val mobileLatestAudioPage = indicatorMobileResultsArray(8)
    val mobileLatestNetworkFrontUK = indicatorMobileResultsArray(9)
    val mobileLatestNetworkFrontUS = indicatorMobileResultsArray(10)
    val mobileLatestNetworkFrontAu = indicatorMobileResultsArray(11)
    val mobileLatestSectionFront = indicatorMobileResultsArray(12)
    val mobileLatestFootballPage = indicatorMobileResultsArray(13)
    val mobileLatestTagPage = indicatorMobileResultsArray(14)


    val postTestingArticleWithManyImagesListDesktop = (desktopLatestArticleWithManyImages :: prevResultsArticleWithManyImagesDesktop).take(1000)
    val postTestingCartoonListDesktop = (desktopLatestCartoon :: prevResultsCartoonDesktop).take(1000)
    val postTestingArticleWithVideosAndMapsDesktop = (desktopLatestArticleWithVideosAndMaps :: prevResultsArticleWithVideosAndMapsDesktop).take(1000)
    val postTestingLongReadDesktop = (desktopLatestLongRead :: prevResultsLongReadDesktop).take(1000)
    val postTestingLiveBlogDesktop = (desktopLatestLiveBlog :: prevResultsLiveBlogDesktop).take(1000)
    val postTestingFootballMatchReportDesktop = (desktopLatestFootballMatchReport :: prevResultsFootballMatchReportDesktop).take(1000)
    val postTestingInteractivePageDesktop = (desktopLatestInteractivePage :: prevResultsInteractivePageDesktop).take(1000)
    val postTestingVideoPageDesktop = (desktopLatestVideoPage :: prevResultsVideoPageDesktop).take(1000)
    val postTestingAudioPageDesktop = (desktopLatestAudioPage :: prevResultsAudioPageDesktop).take(1000)
    val postTestingNetworkFrontUKDesktop = (desktopLatestNetworkFrontUK :: prevResultsNetworkFrontUKDesktop).take(1000)
    val postTestingNetworkFrontUSDesktop = (desktopLatestNetworkFrontUS :: prevResultsNetworkFrontUSDesktop).take(1000)
    val postTestingNetworkFrontAuDesktop = (desktopLatestNetworkFrontAu :: prevResultsNetworkFrontAuDesktop).take(1000)
    val postTestingSectionFrontDesktop = (desktopLatestSectionFront :: prevResultsSectionFrontDesktop).take(1000)
    val postTestingFootballPageDesktop = (desktopLatestFootballPage :: prevResultsFootballPageDesktop).take(1000)
    val postTestingTagPageDesktop = (desktopLatestTagPage :: prevResultsTagPageDesktop).take(1000)

    val postTestingArticleWithManyImagesListMobile = (mobileLatestArticleWithManyImages :: prevResultsArticleWithManyImagesMobile).take(1000)
    val postTestingCartoonListMobile = (mobileLatestCartoon :: prevResultsCartoonMobile).take(1000)
    val postTestingArticleWithVideosAndMapsMobile = (mobileLatestArticleWithVideosAndMaps :: prevResultsArticleWithVideosAndMapsMobile).take(1000)
    val postTestingLongReadMobile = (mobileLatestLongRead :: prevResultsLongReadMobile).take(1000)
    val postTestingLiveBlogMobile = (mobileLatestLiveBlog :: prevResultsLiveBlogMobile).take(1000)
    val postTestingFootballMatchReportMobile = (mobileLatestFootballMatchReport :: prevResultsFootballMatchReportMobile).take(1000)
    val postTestingInteractivePageMobile = (mobileLatestInteractivePage :: prevResultsInteractivePageMobile).take(1000)
    val postTestingVideoPageMobile = (mobileLatestVideoPage :: prevResultsVideoPageMobile).take(1000)
    val postTestingAudioPageMobile = (mobileLatestAudioPage :: prevResultsAudioPageMobile).take(1000)
    val postTestingNetworkFrontUKMobile = (mobileLatestNetworkFrontUK :: prevResultsNetworkFrontUKMobile).take(1000)
    val postTestingNetworkFrontUSMobile = (mobileLatestNetworkFrontUS :: prevResultsNetworkFrontUSMobile).take(1000)
    val postTestingNetworkFrontAuMobile = (mobileLatestNetworkFrontAu :: prevResultsNetworkFrontAuMobile).take(1000)
    val postTestingSectionFrontMobile = (mobileLatestSectionFront :: prevResultsSectionFrontMobile).take(1000)
    val postTestingFootballPageMobile = (mobileLatestFootballPage :: prevResultsFootballPageMobile).take(1000)
    val postTestingTagPageMobile = (mobileLatestTagPage :: prevResultsTagPageMobile).take(1000)


    //write combined results to file
    println(DateTime.now + " Writing liveblog results to S3")
    s3Interface.writeFileToS3(currentPerformanceIndicatorResults, indicatorHTMLResults.toString())

    s3Interface.writeFileToS3(articleWithManyImagesRecordDesktop, postTestingArticleWithManyImagesListDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(cartoonRecordDesktop, postTestingCartoonListDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(articleWithVideosAndMapsRecordDesktop, postTestingArticleWithVideosAndMapsDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(longReadRecordDesktop, postTestingLongReadDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(liveBlogRecordDesktop, postTestingLiveBlogDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(footballMatchReportRecordDesktop, postTestingFootballMatchReportDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(interactivePageRecordDesktop, postTestingInteractivePageDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(videoPageRecordDesktop, postTestingVideoPageDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(audioPageRecordDesktop, postTestingAudioPageDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontUKRecordDesktop, postTestingNetworkFrontUKDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontUSRecordDesktop, postTestingNetworkFrontUSDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontAuRecordDesktop, postTestingNetworkFrontAuDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(sectionFrontRecordDesktop, postTestingSectionFrontDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(footballPageRecordDesktop, postTestingFootballPageDesktop.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(tagPageRecordDesktop, postTestingTagPageDesktop.map(_.toCSVString()).mkString)

    s3Interface.writeFileToS3(articleWithManyImagesRecordMobile, postTestingArticleWithManyImagesListMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(cartoonRecordMobile, postTestingCartoonListMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(articleWithVideosAndMapsRecordMobile, postTestingArticleWithVideosAndMapsMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(longReadRecordMobile, postTestingLongReadMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(liveBlogRecordMobile, postTestingLiveBlogMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(footballMatchReportRecordMobile, postTestingFootballMatchReportMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(interactivePageRecordMobile, postTestingInteractivePageMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(videoPageRecordMobile, postTestingVideoPageMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(audioPageRecordMobile, postTestingAudioPageMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontUKRecordMobile, postTestingNetworkFrontUKMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontUSRecordMobile, postTestingNetworkFrontUSMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(networkFrontAuRecordMobile, postTestingNetworkFrontAuMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(sectionFrontRecordMobile, postTestingSectionFrontMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(footballPageRecordMobile, postTestingFootballPageMobile.map(_.toCSVString()).mkString)
    s3Interface.writeFileToS3(tagPageRecordMobile, postTestingTagPageMobile.map(_.toCSVString()).mkString)


    println("Job complete")
  }

  def getResultPages(urlList: List[String], urlFragments: List[String], wptBaseUrl: String, wptApiKey: String, wptLocation: String): List[(String, String)] = {
    val wpt: WebPageTest = new WebPageTest(wptBaseUrl, wptApiKey, urlFragments)
    val desktopResults: List[(String, String)] = urlList.map(page => {
      (page, wpt.sendPage(page))
    })
    val mobileResults: List[(String, String)] = urlList.map(page => {
      (page, wpt.sendMobile3GPage(page, wptLocation))
    })
    desktopResults ::: mobileResults
  }

  def listenForResultPages(capiPages: List[(Option[ContentFields],String)], contentType: String, resultUrlList: List[(String, String)], averages: PageAverageObject, wptBaseUrl: String, wptApiKey: String, wptLocation: String, urlFragments: List[String]): List[PerformanceResultsObject] = {
    println("ListenForResultPages called with: \n\n" +
      capiPages.length + " urls: \n" +
      " List of Urls: \n" + capiPages.map(page => page._2 + "\n").mkString +
      "\n\nList of WebPage Test results: \n" + resultUrlList.mkString)

    val listenerList: List[WptResultPageListener] = capiPages.flatMap(page => {
      for (element <- resultUrlList if element._1 == page._2) yield new WptResultPageListener(element._1, contentType, page._1, element._2)
    })

    println("Listener List created: \n" + listenerList.map(element => "list element: \n" + "url: " + element.pageUrl + "\n" + "resulturl" + element.wptResultUrl + "\n"))
    println("Listener List length = " + listenerList.length)

    val resultsList: ParSeq[WptResultPageListener] = listenerList.par.map(element => {
      val wpt = new WebPageTest(wptBaseUrl, wptApiKey, urlFragments)
      val newElement = new WptResultPageListener(element.pageUrl, element.pageType, element.pageFields,element.wptResultUrl)
      println("getting result for page element")
      newElement.testResults = wpt.getResults(newElement.wptResultUrl)
      println("result received\n setting headline")
      newElement.testResults.setHeadline(newElement.headline)
      println("headline set\n setting pagetype")
      newElement.testResults.setPageType(newElement.pageType)
      println("pagetype set\n setting FirstPublished")
      newElement.testResults.setFirstPublished(newElement.firstPublished)
      println("FirstPublished set\n setting LastUpdated")
      newElement.testResults.setPageLastUpdated(newElement.pageLastModified)
      println("Lastupdated set\n setting LiveBloggingNow")
      newElement.testResults.setLiveBloggingNow(newElement.liveBloggingNow.getOrElse(false))
      println("all variables set for element")
      newElement
    })
    println("results list length: " + resultsList.length)
    val testResults = resultsList.map(element => element.testResults).toList
    val resultsWithAlerts: List[PerformanceResultsObject] = testResults.map(element => setAlertStatus(element, averages))
    println("Number of immediate results: " + testResults.length)
    println("Number of results after alerts set" + resultsWithAlerts.length)
    testResults
  }

  def confirmAlert(initialResult: PerformanceResultsObject, averages: PageAverageObject, urlFragments: List[String],wptBaseUrl: String, wptApiKey: String, wptLocation: String): PerformanceResultsObject = {
    val webPageTest = new WebPageTest(wptBaseUrl, wptApiKey, urlFragments)
    val testCount: Int = if (initialResult.timeToFirstByte > 1000) {
      5
    } else {
      3
    }
    println("TTFB for " + initialResult.testUrl + "\n therefore setting test count of: " + testCount)
    val AlertConfirmationTestResult: PerformanceResultsObject = setAlertStatus(webPageTest.testMultipleTimes(initialResult.testUrl, initialResult.typeOfTest, wptLocation, testCount), averages)
    AlertConfirmationTestResult
  }

  def setAlertStatus(resultObject: PerformanceResultsObject, averages: PageAverageObject): PerformanceResultsObject = {
    //  Add results to string which will eventually become the content of our results file
    if (resultObject.typeOfTest == "Desktop") {
      if (resultObject.kBInFullyLoaded >= averages.desktopKBInFullyLoaded) {
        println("PageWeight Alert Set")
        resultObject.pageWeightAlertDescription = "the page is too heavy. Please examine the list of embeds below for items that are unexpectedly large."
        resultObject.alertStatusPageWeight = true
      }
      else {
        println("PageWeight Alert not set")
        resultObject.alertStatusPageWeight = false
      }
      if ((resultObject.timeFirstPaintInMs >= averages.desktopTimeFirstPaintInMs) ||
          (resultObject.speedIndex >= averages.desktopSpeedIndex)) {
        println("PageSpeed alert set")
        resultObject.alertStatusPageSpeed = true
        if ((resultObject.timeFirstPaintInMs >= averages.desktopTimeFirstPaintInMs) && (resultObject.speedIndex >= averages.desktopSpeedIndex)) {
          resultObject.pageSpeedAlertDescription = "Time till page is scrollable (time-to-first-paint) and time till page looks loaded (SpeedIndex) are unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
        } else {
          if (resultObject.speedIndex >= averages.desktopSpeedIndex) {
            resultObject.pageSpeedAlertDescription = "Time till page looks loaded (SpeedIndex) is unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
          }
          else {
            resultObject.pageSpeedAlertDescription = "Time till page is scrollable (time-to-first-paint) is unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
          }
        }
      } else {
        println("PageSpeed alert not set")
        resultObject.alertStatusPageSpeed = false
      }
    } else {
      //checking if status of mobile test needs an alert
      if (resultObject.kBInFullyLoaded >= averages.mobileKBInFullyLoaded) {
        println("PageWeight Alert Set")
        resultObject.pageWeightAlertDescription = "the page is too heavy. Please examine the list of embeds below for items that are unexpectedly large."
        resultObject.alertStatusPageWeight = true
      }
      else {
        println("PageWeight Alert not set")
        resultObject.alertStatusPageWeight = false
      }
      if ((resultObject.timeFirstPaintInMs >= averages.mobileTimeFirstPaintInMs) ||
        (resultObject.speedIndex >= averages.mobileSpeedIndex)) {
        println("PageSpeed alert set")
        resultObject.alertStatusPageSpeed = true
        if ((resultObject.timeFirstPaintInMs >= averages.mobileTimeFirstPaintInMs) && (resultObject.speedIndex >= averages.mobileSpeedIndex)) {
          resultObject.pageSpeedAlertDescription = "Time till page is scrollable (time-to-first-paint) and time till page looks loaded (SpeedIndex) are unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
        } else {
          if (resultObject.speedIndex >= averages.mobileSpeedIndex) {
            resultObject.pageSpeedAlertDescription = "Time till page looks loaded (SpeedIndex) is unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
          }
          else {
            resultObject.pageSpeedAlertDescription = "Time till page is scrollable (time-to-first-paint) is unusually high. Please investigate page elements below or contact <a href=mailto:\"dotcom.health@guardian.co.uk\">the dotcom-health team</a> for assistance."
          }
        }
      } else {
        println("PageSpeed alert not set")
        resultObject.alertStatusPageSpeed = false
      }
    }
    println("Returning test result with alert flags set to relevant values")
    resultObject
  }

  def generateInteractiveAverages(urlList: List[String], wptBaseUrl: String, wptApiKey: String, wptLocation: String, urlFragments: List[String], itemtype: String, averageColor: String): PageAverageObject = {
    val setHighPriority: Boolean = true
    val webpageTest: WebPageTest = new WebPageTest(wptBaseUrl, wptApiKey, urlFragments)

    val resultsList: List[Array[PerformanceResultsObject]] = urlList.map(url => {
      val webPageDesktopTestResults: PerformanceResultsObject = webpageTest.desktopChromeCableTest(url, setHighPriority)
      val webPageMobileTestResults: PerformanceResultsObject = webpageTest.mobileChrome3GTest(url, wptLocation, setHighPriority)
      val combinedResults = Array(webPageDesktopTestResults, webPageMobileTestResults)
      combinedResults
    })

    val pageAverages: PageAverageObject = new GeneratedInteractiveAverages(resultsList, averageColor)
    pageAverages
  }


  def retestUrl(initialResult: PerformanceResultsObject, wptBaseUrl: String, wptApiKey: String, wptLocation: String, urlFragments: List[String]): PerformanceResultsObject = {
    val webPageTest = new WebPageTest(wptBaseUrl, wptApiKey, urlFragments)
    val testCount: Int = if (initialResult.timeToFirstByte > 1000) {
      5
    } else {
      3
    }
    println("TTFB for " + initialResult.testUrl + "\n therefore setting test count of: " + testCount)
    //   val AlertConfirmationTestResult: PerformanceResultsObject = setAlertStatusPageWeight(webPageTest.testMultipleTimes(initialResult.testUrl, initialResult.typeOfTest, wptLocation, testCount), averages)
    webPageTest.testMultipleTimes(initialResult.testUrl, initialResult.typeOfTest, wptLocation, testCount)
  }

  //This is to resolve issues where there is a missing Desktop or Mobile test so the tuple sorting gets borked - it wont give a perfect sort in this case, but better than the current state of things.
  def returnValidListOfPairs(list: List[PerformanceResultsObject]): (List[PerformanceResultsObject],List[PerformanceResultsObject]) = {
    val desktopList = for (result <- list if result.typeOfTest.contains("Desktop")) yield result
    val mobileList = for (result <- list if result.typeOfTest.contains("Android/3G")) yield result
    val missingFromDesktop = for (result <- mobileList if!desktopList.map(_.testUrl).contains(result.testUrl)) yield result
    val missingFromMobile = for (result <- desktopList if!mobileList.map(_.testUrl).contains(result.testUrl)) yield result
    val validListOfPairs = for (result <- list if(!missingFromDesktop.map(_.testUrl).contains(result.testUrl)) && (!missingFromMobile.map(_.testUrl).contains(result.testUrl))) yield result
    println("list has been validated")
    (validListOfPairs, missingFromDesktop ::: missingFromMobile)
  }

  def orderListByWeight(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
    println("orderListByWeightCalled with " + list.length + "elements")
      val validatedList = returnValidListOfPairs(list)
      println("validated list has " + validatedList._1.length + " paired items, and " + validatedList._2.length + " leftover items")
      val tupleList = listSinglesToPairs(validatedList._1)
      println("tuple List returned " + tupleList.length + " tuples")
      val leftOverAlerts = for (result <- validatedList._2 if result.alertStatusPageWeight) yield result
      val leftOverNormal = for (result <- validatedList._2 if !result.alertStatusPageWeight) yield result
      println("listSinglesToPairs returned a list of " + tupleList.length + " pairs.")
      val alertsList: List[(PerformanceResultsObject, PerformanceResultsObject)] = (for (element <- tupleList if element._1.alertStatusPageWeight || element._2.alertStatusPageWeight) yield element)
      val okList: List[(PerformanceResultsObject, PerformanceResultsObject)] = for (element <- tupleList if !element._1.alertStatusPageWeight && !element._2.alertStatusPageWeight) yield element

      sortByWeight(alertsList) ::: leftOverAlerts ::: sortByWeight(okList) ::: leftOverNormal
  }

  def orderListBySpeed(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
      println("orderListBySpeed called. \n It has " + list.length + " elements.")
      val validatedList = returnValidListOfPairs(list)
    println("validated list has " + validatedList._1.length + " paired items, and " + validatedList._2.length + " leftover items")
      val tupleList = listSinglesToPairs(validatedList._1)
      println("tuple List returned " + tupleList.length + " tuples")
      val leftOverAlerts = for (result <- validatedList._2 if result.alertStatusPageSpeed) yield result
      val leftOverNormal = for (result <- validatedList._2 if !result.alertStatusPageSpeed) yield result
      println("listSinglesToPairs returned a list of " + tupleList.length + " pairs.")
      val alertsList: List[(PerformanceResultsObject, PerformanceResultsObject)] = for (element <- tupleList if element._1.alertStatusPageSpeed || element._2.alertStatusPageSpeed) yield element
      val okList: List[(PerformanceResultsObject, PerformanceResultsObject)] = for (element <- tupleList if !element._1.alertStatusPageSpeed && !element._2.alertStatusPageSpeed) yield element

      sortBySpeed(alertsList) ::: leftOverAlerts ::: sortBySpeed(okList) ::: leftOverNormal
  }

  def orderInteractivesBySpeed(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
      println("orderInteractivesBySpeed called. \n It has " + list.length + " elements.")
      val validatedList = returnValidListOfPairs(list)
      val tupleList = listSinglesToPairs(validatedList._1)
      val leftOverAlerts = for (result <- validatedList._2 if result.alertStatusPageSpeed) yield result
      val leftOverNormal = for (result <- validatedList._2 if !result.alertStatusPageSpeed) yield result
      println("listSinglesToPairs returned a list of " + tupleList.length + " pairs.")
      val alertsList: List[(PerformanceResultsObject, PerformanceResultsObject)] = for (element <- tupleList if element._1.alertStatusPageSpeed || element._2.alertStatusPageSpeed || element._1.alertStatusPageWeight || element._2.alertStatusPageWeight) yield element
      val okList: List[(PerformanceResultsObject, PerformanceResultsObject)] = for (element <- tupleList if !element._1.alertStatusPageSpeed && !element._2.alertStatusPageSpeed && !element._1.alertStatusPageWeight && !element._2.alertStatusPageWeight) yield element

      sortBySpeed(alertsList) ::: leftOverAlerts ::: sortBySpeed(okList) ::: leftOverNormal
  }

  def sortHomogenousResultsByWeight(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
    val alertsResultsList: List[PerformanceResultsObject] = for (result <- list if result.alertStatusPageWeight) yield result
    val okResultsList: List[PerformanceResultsObject] = for (result <- list if !result.alertStatusPageWeight) yield result
    val sortedAlertList: List[PerformanceResultsObject] = alertsResultsList.sortWith(_.bytesInFullyLoaded > _.bytesInFullyLoaded)
    val sortedOkList: List[PerformanceResultsObject] = okResultsList.sortWith(_.bytesInFullyLoaded > _.bytesInFullyLoaded)
    sortedAlertList ::: sortedOkList
  }

  def sortHomogenousResultsBySpeed(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
    val alertsResultsList: List[PerformanceResultsObject] = for (result <- list if result.alertStatusPageSpeed) yield result
    val okResultsList: List[PerformanceResultsObject] = for (result <- list if !result.alertStatusPageSpeed) yield result
    val sortedAlertList: List[PerformanceResultsObject] = alertsResultsList.sortWith(_.speedIndex > _.speedIndex)
    val sortedOkList: List[PerformanceResultsObject] = okResultsList.sortWith(_.speedIndex > _.speedIndex)
    sortedAlertList ::: sortedOkList
  }

  def sortHomogenousInteractiveResultsBySpeed(list: List[PerformanceResultsObject]): List[PerformanceResultsObject] = {
    val alertsResultsList: List[PerformanceResultsObject] = for (result <- list if result.alertStatusPageSpeed || result.alertStatusPageWeight) yield result
    val okResultsList: List[PerformanceResultsObject] = for (result <- list if !result.alertStatusPageSpeed && !result.alertStatusPageWeight) yield result
    val sortedAlertList: List[PerformanceResultsObject] = alertsResultsList.sortWith(_.speedIndex > _.speedIndex)
    val sortedOkList: List[PerformanceResultsObject] = okResultsList.sortWith(_.speedIndex > _.speedIndex)
    sortedAlertList ::: sortedOkList
  }

  def sortByWeight(list: List[(PerformanceResultsObject,PerformanceResultsObject)]): List[PerformanceResultsObject] = {
    if(list.nonEmpty){
      val sortedTupleList = sortTupleListByWeight(list)
      makeList(sortedTupleList)
    }
    else {
        println("sortByWeight has noElements in list. Passing back empty list")
        List()
    }
  }

  def sortBySpeed(list: List[(PerformanceResultsObject,PerformanceResultsObject)]): List[PerformanceResultsObject] = {
    if(list.nonEmpty){
      val sortedTupleList = sortTupleListBySpeed(list)
      makeList(sortedTupleList)
    }
    else {
      println("sortByWeight has noElements in list. Passing back empty list")
      List()
    }
  }



  def listSinglesToPairs(list: List[PerformanceResultsObject]): List[(PerformanceResultsObject, PerformanceResultsObject)] = {
    if (list.nonEmpty && list.length % 2 == 0) {
      println("list Singles to pairs has " + list.length + " perf results objects.")
      val tupleList = makeTuple(List((list.head, list.tail.head)), list.tail.tail)
      println("makeTuple called - returned a list of " + tupleList.length + "pairs")
      tupleList
    }
    else {
      if (list.isEmpty) {
        println("listSinglesToPairs passed empty list. Returning empty list of correct type")
        val returnList: List[(PerformanceResultsObject, PerformanceResultsObject)] = List()
        returnList
      } else {
        if(list.length == 1){
          println("listSinglesToPairs passed list of 1. Returning tuple of single element - will introduce a duplicate result")
          val dummyTestType = if(list.head.typeOfTest.contains("Desktop")){
            "Android/3G"
          } else {
            "Desktop"
          }
          List((list.head, new PerformanceResultsObject("Missing test of type", dummyTestType, " for the above url", -1, -1, -1, -1, -1, -1, -1, -1, -1, "", false, false, true)))
        } else {
          println("listSinglesToPairs has been passed an empty or odd number of elements: list has " + list.length + "elements")
          makeTuple(List((list.head, list.tail.head)), list.tail.tail)
        }
      }
    }
  }

  def makeTuple(tupleList: List[(PerformanceResultsObject, PerformanceResultsObject)], restOfList: List[PerformanceResultsObject]): List[(PerformanceResultsObject, PerformanceResultsObject)] = {
    println("maketuple function here: tuple list has: " + tupleList.length + " elements.\n" + "               and the rest of list has: " + restOfList.length + " elements remaining.")
    if (restOfList.isEmpty) {
      tupleList
    }
    else {
      if (restOfList.length < 2) {
        println("make tuple has odd number of items in list"); tupleList
      }
      else {
        makeTuple(tupleList ::: List((restOfList.head, restOfList.tail.head)), restOfList.tail.tail)
      }
    }
  }


  def makeList(tupleList: List[(PerformanceResultsObject,PerformanceResultsObject)]): List[PerformanceResultsObject] = {
    val fullList: List[PerformanceResultsObject] = tupleList.flatMap(a => List(a._1,a._2))
    fullList
  }

  def sortTupleListByWeight(list: List[(PerformanceResultsObject,PerformanceResultsObject)]): List[(PerformanceResultsObject,PerformanceResultsObject)] = {
    list.sortWith{(leftE:(PerformanceResultsObject, PerformanceResultsObject),rightE:(PerformanceResultsObject, PerformanceResultsObject)) =>
      leftE._1.bytesInFullyLoaded + leftE._2.bytesInFullyLoaded > rightE._1.bytesInFullyLoaded + rightE._2.bytesInFullyLoaded}
  }

  def sortTupleListBySpeed(list: List[(PerformanceResultsObject,PerformanceResultsObject)]): List[(PerformanceResultsObject,PerformanceResultsObject)] = {
    list.sortWith{(leftE:(PerformanceResultsObject, PerformanceResultsObject),rightE:(PerformanceResultsObject, PerformanceResultsObject)) =>
      leftE._1.speedIndex + leftE._2.speedIndex > rightE._1.speedIndex + rightE._2.speedIndex}
  }

  def applyAnchorId(resultsObjectList: List[PerformanceResultsObject], lastIDAssigned: Int): (List[PerformanceResultsObject], Int) = {
    var iterator = lastIDAssigned + 1
    val resultList = for (result <- resultsObjectList) yield {
      result.anchorId = Option(result.headline.getOrElse(iterator) + result.typeOfTest)
      iterator = iterator + 1
      result
    }
    (resultList,iterator)
  }


  def jodaDateTimetoCapiDateTime(time: DateTime): CapiDateTime = {
    new CapiDateTime {
      override def dateTime: Long = time.getMillis
    }
  }

}


