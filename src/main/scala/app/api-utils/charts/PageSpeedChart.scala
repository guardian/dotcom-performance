package app.api

import app.apiutils.PerformanceResultsObject
import play.api.libs.json.{JsObject, Writes, JsValue}
import play.libs.Json


/**
 * Created by mmcnamara on 22/06/16.
 */
class PageSpeedChart(resultList: List[PerformanceResultsObject] ) {
//val xAxisList: List[Int] = List.range(1 , (resultList.length + 1))
val xAxisList: List[String] = resultList.map(_.timeOfTest)

//case classes
//xaxis categories
  case class XAxis(categories: Seq[Int])
//yaxis
  case class YAxis(title: String, plotLines: Seq[PlotLine])
// plotLines
  case class PlotLine(value: Int, width: Int, color: String)
// tooltip
  case class ToolTip(valueSuffix: String)
// legend
  case class Legend(layout: String, align: String, verticalAlign: String, borderWidth: Int)
// seriesList
  case class Series(name: String, data: List[Data])
// data
  case class Data(dataList: List[Int])
// chart
  case class Chart(title: String, subtitle: String, xAxis: XAxis, yAxis: YAxis, tooltip: ToolTip, legend: Legend, series: Seq[Series])

  val quickAndDirtyString =     """
                                  | {
                                  |                                title: {
                                  |                                text: 'Performance',
                                  |                                            x: -20 //center
                                  |                                        },
                                  |                                        subtitle: {
                                  |                                            text: 'Speed Index',
                                  |                                            x: -20
                                  |                                        },
                                  |                                        xAxis: {
                                  |                                            categories: """.stripMargin + getStringOfDates(xAxisList) +
                                  """
                                  |                                        },
                                  |                                        yAxis: {
                                  |                                            title: {
                                  |                                                text: 'Time ms'
                                  |                                            },
                                  |                                            plotLines: [{
                                  |                                                value: 0,
                                  |                                                width: 1,
                                  |                                                color: '#808080'
                                  |                                            }]
                                  |                                        },
                                  |                                        tooltip: {
                                  |                                            valueSuffix: 'ms'
                                  |                                        },
                                  |                                        legend: {
                                  |                                            layout: 'vertical',
                                  |                                            align: 'right',
                                  |                                            verticalAlign: 'middle',
                                  |                                            borderWidth: 0
                                  |                                        },
                                  |                                        series: [
                                  |                                                {
                                  |                                                    name: 'Speed Index',
                                  |                                                data: """.stripMargin + getStringOfNumbers(resultList.map(result => result.speedIndex)) +
                                  """
                                  |                                                },
                                  |                                                {
                                  |                                                    name: 'Start Render',
                                  |                                                data: """.stripMargin + getStringOfNumbers(resultList.map(result => result.startRenderInMs)) +
                                  """
                                  |                                                },
                                  |                                                {
                                  |                                                    name: 'Visually Complete',
                                  |                                                data: """.stripMargin + getStringOfNumbers(resultList.map(result => result.visualComplete)) +
                                  """
                                  |                                                }
                                  |                                            ]
                                  |                                }
                                """.stripMargin

  def getStringOfNumbers(list: List[Int]): String = {
    "[" + list.head + list.tail.map(number => ", " + number).mkString + "]"

  }

def getStringOfDates(list: List[String]): String = {
  "[" + list.head + list.tail.map(date => ", " + date).mkString + "]"

}


  override def toString(): String = {
    quickAndDirtyString
  }
}




class PageSpeedTestChart {

  val chartJson: String =
    """
      | {
      |                                title: {
      |                                text: 'Performance',
      |                                            x: -20 //center
      |                                        },
      |                                        subtitle: {
      |                                            text: 'Speed Index',
      |                                            x: -20
      |                                        },
      |                                        xAxis: {
      |                                            categories: ['1', '2', '3', '4', '5', '6',
      |                                                '7', '8', '9', '10', '11', '12']
      |                                        },
      |                                        yAxis: {
      |                                            title: {
      |                                                text: 'Time ms'
      |                                            },
      |                                            plotLines: [{
      |                                                value: 0,
      |                                                width: 1,
      |                                                color: '#808080'
      |                                            }]
      |                                        },
      |                                        tooltip: {
      |                                            valueSuffix: 'ms'
      |                                        },
      |                                        legend: {
      |                                            layout: 'vertical',
      |                                            align: 'right',
      |                                            verticalAlign: 'middle',
      |                                            borderWidth: 0
      |                                        },
      |                                        series: [
      |                                                {
      |                                                    name: 'Speed Index',
      |                                                data: [2034, 2000, 1950, 1999, 2050, 2060, 2100, 3034, 2559, 2313, 2565, 2098]
      |                                                },
      |                                                {
      |                                                    name: 'Start Render',
      |                                                data: [0512, 0613, 0515, 0723, 0643, 0758, 0834, 0612, 0534, 0234, 0523, 0598]
      |                                                },
      |                                                {
      |                                                    name: 'Visually Complete',
      |                                                data: [2904, 2305, 2567, 3000, 3302, 3032, 3001, 2987, 2988, 2450, 2678, 2903]
      |                                                }
      |                                            ]
      |                                }
    """.stripMargin

  val chartDiv = "<div id=\"mychart\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>"
  def getChartJson(): String = {chartJson}

}
