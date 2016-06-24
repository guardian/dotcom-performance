package app.api

import app.apiutils.PerformanceResultsObject
import com.gu.contentapi.client.model.v1.CapiDateTime
import org.joda.time.DateTime


/**
 * Created by mmcnamara on 22/06/16.
 */
class PageSpeedChartPage(articleWithManyImagesDesktop: List[PerformanceResultsObject],
                         articleWithManyImagesMobile: List[PerformanceResultsObject],
                         articleWithVideosAndMapsDesktop: List[PerformanceResultsObject],
                         articleWithVideosAndMapsMobile: List[PerformanceResultsObject],
                         audioPageDesktop: List[PerformanceResultsObject],
                         audioPageMobile: List[PerformanceResultsObject],
                         cartoonDesktop: List[PerformanceResultsObject],
                         cartoonMobile: List[PerformanceResultsObject],
                         footballMatchReportDesktop: List[PerformanceResultsObject],
                         footballMatchReportMobile: List[PerformanceResultsObject],
                         footballPageDesktop: List[PerformanceResultsObject],
                         footballPageMobile: List[PerformanceResultsObject],
                         interactivePageDesktop: List[PerformanceResultsObject],
                         interactivePageMobile: List[PerformanceResultsObject],
                         liveBlogDesktop: List[PerformanceResultsObject],
                         liveBlogMobile: List[PerformanceResultsObject],
                         longReadDesktop: List[PerformanceResultsObject],
                         longReadMobile: List[PerformanceResultsObject],
                         networkFrontAUDesktop: List[PerformanceResultsObject],
                         networkFrontAUMobile: List[PerformanceResultsObject],
                         networkFrontUKDesktop: List[PerformanceResultsObject],
                         networkFrontUKMobile: List[PerformanceResultsObject],
                         networkFrontUSDesktop: List[PerformanceResultsObject],
                         networkFrontUSMobile: List[PerformanceResultsObject],
                         sectionFrontDesktop: List[PerformanceResultsObject],
                         sectionFrontMobile: List[PerformanceResultsObject],
                         tagPageDesktop: List[PerformanceResultsObject],
                         tagPageMobile: List[PerformanceResultsObject],
                         videoPageDesktop: List[PerformanceResultsObject],
                         videoPageMobile: List[PerformanceResultsObject]) {
  //HTML Page elements
  //Page Header
  val HTML_PAGE_HEAD: String = "<!DOCTYPE html><html lang=\"en\">" + "\n" +
    "<head> <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>" + "\n" +
    "<title>Editorial PageWeight Dashboard - [Editorial Pageweight Dashboard]</title>" + "\n" +
    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\"/>" + "\n" +
    "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js\"></script>" + "\n" +
    "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>" + "\n" +
    "<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" rel=\"stylesheet\"/>" + "\n" +
    "<link rel=\"stylesheet\" href=\"/capi-wpt-querybot/assets/css/tabs.css\"/>" + "\n" +
    "<link rel=\"stylesheet\" href=\"/capi-wpt-querybot/assets/css/style.css\"/>" + "\n" +
    "<script src=\"/capi-wpt-querybot/assets/js/script.js\"></script>" + "\n" +
    "<script src=\"/capi-wpt-querybot/dotcom-performance-monitor/assets/js/tabs.js\"></script>" + "\n" +
    "<script src=\"/capi-wpt-querybot/dotcom-performance-monitor/assets/js/charttabs.js\"></script>" + "\n" +
    "<script src=\"https://code.highcharts.com/adapters/standalone-framework.js\"></script>\n" +
    "<script src=\"https://code.highcharts.com/highcharts.js\"></script>\n" +
    "<script src=\"https://code.highcharts.com/modules/data.js\"></script>\n" +
    "<script src=\"https://code.highcharts.com/modules/drilldown.js\"></script>\n" +
    "<script src=\"https://code.highcharts.com/modules/exporting.js\"></script>\n" +
    "</head>"

  //Page Container
  val HTML_PAGE_CONTAINER: String = "<body>" + "\n" +
    "<div id=\"container\">" + "\n" +
    "<div id=\"head\">" + "\n" +
    "<h1>Current performance of today's Pages</h1>" + "\n" +
    "<p>Job started at: " + DateTime.now + "</p>" + "\n" +
    "</div>" + "\n"

  val HTML_PAGE_TABS_LIST: String = "<div class=\"tabs\">" + "\n" +
    "<ul class=\"tab-links\">" + "\n" +
    "<li class=\"active\">" + "<a href=\"#articlewithmanyimages\">Article with many images</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#articlewithvideosandmaps\">Article with videos and maps</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#audiopage\">Audio page view</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#cartoon\">Cartoon</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#footballmatchreport\">Football Match Report</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#footballpage\">Football Page</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#interactivepage\">Interactive Page</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#liveblog\">Liveblog</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#longread\">Long Read</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#networkfrontau\">Network Front AU</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#networkfrontuk\">Network Front UK</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#networkfrontus\">Network Front US</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#sectionfront\">Section Front</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#tagpage\">Tag Page</a>" + "</li>" + "\n" +
    "<li>" + "<a href=\"#videopage\">Video Page</a>" + "</li>" + "\n" +
    "</ul>" + "\n"
  //close div added to HTML_FOOTER

  //Page Content
  val HTML_TAB_CONTENT: String = "<div id=\"tab-content\">" + "\n"

  val HTML_TAB_HEADER: String = "<div class=\"tab-content\">" + "\n"


  val HTML_ARTICLEWITHMANYIMAGES_TAB_CONTENT_HEADER: String = "<div id=\"articlewithmanyimages\" class=\"tab active\">" + "\n" +
    "<p>" + "<h2>Article with many images: " + "<a href=\"" + articleWithManyImagesDesktop.head.testUrl + "\">" + articleWithManyImagesDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_ARTICLEWITHVIDEOSANDMAPS_TAB_CONTENT_HEADER: String = "<div id=\"articlewithvideosandmaps\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Article with videos and maps: " + "<a href=\"" + articleWithVideosAndMapsDesktop.head.testUrl + "\">" + articleWithVideosAndMapsDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_AUDIOPAGEVIEW_TAB_CONTENT_HEADER: String = "<div id=\"audiopage\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Audio page view: " + "<a href=\"" + audioPageDesktop.head.testUrl + "\">" + audioPageDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_CARTOON_TAB_CONTENT_HEADER: String = "<div id=\"cartoon\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Cartoon: " + "<a href=\"" + cartoonDesktop.head.testUrl + "\">" + cartoonDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_FOOTBALLMATCHREPORT_TAB_CONTENT_HEADER: String = "<div id=\"footballmatchreport\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Football Match Report: " + "<a href=\"" + footballMatchReportDesktop.head.testUrl + "\">" + footballMatchReportDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_FOOTBALLPAGE_TAB_CONTENT_HEADER: String = "<div id=\"footballpage\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Football Page: " + "<a href=\"" + footballPageDesktop.head.testUrl + "\">" + "Football front" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_INTERACTIVE_TAB_CONTENT_HEADER: String = "<div id=\"interactivepage\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Interactive: " + "<a href=\"" + interactivePageDesktop.head.testUrl + "\">" + interactivePageDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_LIVEBLOG_TAB_CONTENT_HEADER: String = "<div id=\"liveblog\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Liveblog: " + "<a href=\"" + liveBlogDesktop.head.testUrl + "\">" + liveBlogDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_LONGREAD_TAB_CONTENT_HEADER: String = "<div id=\"longread\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Longread: " + "<a href=\"" + longReadDesktop.head.testUrl + "\">" + longReadDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_NETWORKFRONTAU_TAB_CONTENT_HEADER: String = "<div id=\"networkfrontau\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Network Front AU: " + "<a href=\"" + networkFrontAUDesktop.head.testUrl + "\">" + "Network front: AU" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_NETWORKFRONTUK_TAB_CONTENT_HEADER: String = "<div id=\"networkfrontuk\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Network Front UK: " + "<a href=\"" + networkFrontUKDesktop.head.testUrl + "\">" + "Network front: UK" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_NETWORKFRONTUS_TAB_CONTENT_HEADER: String = "<div id=\"networkfrontus\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Network Front US: " + "<a href=\"" + networkFrontUSDesktop.head.testUrl + "\">" + "Network front: US" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_SECTIONFRONT_TAB_CONTENT_HEADER: String = "<div id=\"sectionfront\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Section Front: " + "<a href=\"" + sectionFrontDesktop.head.testUrl + "\">" + "UK Culture Section Front" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_TAGPAGE_TAB_CONTENT_HEADER: String = "<div id=\"tagpage\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Tag Page: " + "<a href=\"" + tagPageDesktop.head.testUrl + "\">" + "Boxing tag page" + "</a>" + "</h2>" + "</p>" + "\n"

  val HTML_VIDEOPAGE_TAB_CONTENT_HEADER: String = "<div id=\"videopage\" class=\"tab\">" + "\n" +
    "<p>" + "<h2>Video Page: " + "<a href=\"" + videoPageDesktop.head.testUrl + "\">" + videoPageDesktop.head.headline.getOrElse("Click Here") + "</a>" + "</h2>" + "</p>" + "\n"



  val HTML_TAB_CONTENT_FOOTER: String = "</div>" + "\n"


  val HTML_TAB_CONTENT_DESKTOP_LABEL: String = "<div style=\"padding-left: 15%; margin: 0 auto\"><p>Performance on Desktop:</p></div>"
  val HTML_TAB_CONTENT_MOBILE_LABEL: String = "<div style=\"padding-left: 15%; margin-top: 3%; margin-left: auto; margin-right: auto;\"><p> Performance on Mobile with 3G connection:</p></div>\n"

  val HTML_CHART_DIV_PART_1: String = "<div id=\""
  val HTML_CHART_DIV_PART_2: String = "\" class=\"contains-chart\" style=\"width:75%; height: 400px; margin: 0 auto\"></div>\n"

  val HTML_CHART_JS_PART_1: String = "<script>\n$(function () {\n$('#"
  val HTML_CHART_JS_PART_2: String = "').highcharts("
  val HTML_CHART_JS_FOOTER: String = ");\n });\n </script>"

  //Page Footer
  val HTML_FOOTER: String = "</div>" + "\n" +
    "<div id=\"footer\">" + "<p>Job completed at: [DATA]</p>" + "</div>" + "\n" +
    "</div>" + "\n" +
    "</div>" + "\n" +
    "</body>" + "\n" +
    "</html>"

  //HTML_PAGE
  val HTML_PAGE: String = HTML_PAGE_HEAD + HTML_PAGE_CONTAINER + HTML_PAGE_TABS_LIST +
    HTML_TAB_CONTENT + HTML_ARTICLEWITHMANYIMAGES_TAB_CONTENT_HEADER +
      HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("articlewithmanyimagesdesktop", articleWithManyImagesDesktop) +
      HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("articlewithmanyimagesmobile", articleWithManyImagesMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_ARTICLEWITHVIDEOSANDMAPS_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("articlewithvideosandmapsdesktop", articleWithVideosAndMapsDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("articlewithvideosandmapsmobile", articleWithVideosAndMapsMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_AUDIOPAGEVIEW_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("audiopagedesktop", audioPageDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("audiopagemobile", audioPageMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_CARTOON_TAB_CONTENT_HEADER + generateChart("cartoondesktop", cartoonDesktop) +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("cartoonmobile", cartoonMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_FOOTBALLMATCHREPORT_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("footballmatchreportdesktop", footballMatchReportDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("footballmatchreportmobile", footballMatchReportMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_FOOTBALLPAGE_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("footballpagedesktop", footballPageDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("footballpagemobile", footballPageMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_INTERACTIVE_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("interactivepagedesktop", interactivePageDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("interactivepagemobile", interactivePageMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_LIVEBLOG_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("liveblogdesktop", liveBlogDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("liveblogmobile", liveBlogMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_LONGREAD_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("longreaddesktop", longReadDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("longreadmobile", longReadMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_NETWORKFRONTAU_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("networkfrontaudesktop", networkFrontAUDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("networkfrontaumobile", networkFrontAUMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_NETWORKFRONTUK_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("networkfrontukdesktop", networkFrontUKDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("networkfrontukmobile", networkFrontUKMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_NETWORKFRONTUS_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("networkfrontusdesktop", networkFrontUSDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("networkfrontusmobile", networkFrontUSMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_SECTIONFRONT_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("sectionfrontdesktop", sectionFrontDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("sectionfrontmobile", sectionFrontMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_TAGPAGE_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("tagpagedesktop", tagPageDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("tagpagemobile", tagPageMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_VIDEOPAGE_TAB_CONTENT_HEADER +
    HTML_TAB_CONTENT_DESKTOP_LABEL + generateChart("videopagedesktop", videoPageDesktop) +
    HTML_TAB_CONTENT_MOBILE_LABEL + generateChart("videopagemobile", videoPageMobile) + HTML_TAB_CONTENT_FOOTER +
  HTML_FOOTER




  //HTML_PAGE
//  val HTML_PAGE: String = HTML_PAGE_HEAD + HTML_PAGE_CONTAINER + generateChart("pagespeed", articleWithManyImages) + HTML_FOOTER




  def generateChart(title: String, results: List[PerformanceResultsObject]): String = {
    HTML_CHART_DIV_PART_1 + title + HTML_CHART_DIV_PART_2 + HTML_CHART_JS_PART_1 + title + HTML_CHART_JS_PART_2 +
      new PageSpeedChart(results).toString() + HTML_CHART_JS_FOOTER
  }

  override def toString(): String = {
    HTML_PAGE
  }

}
