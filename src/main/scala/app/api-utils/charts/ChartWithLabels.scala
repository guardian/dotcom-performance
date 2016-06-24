package app.api

import app.apiutils.PerformanceResultsObject


/**
 * Created by mmcnamara on 20/06/16.
 */

case class ChartWithLabels(chart: Chart, title: TextObject, subtitle: TextObject, xAxis: List[Category], yAxis: YAxis, plotOptions: PlotOptions, series: Seq[Series])

case class Category(categoryName: String)

case class YAxis(title: TextObject)

case class Chart(chartType: ChartType)

case class ChartType(charType: String)

case class TextObject(text: String)

case class PlotOptions(line: Line)

case class Line(dataLabels: DataLabels, enableMouseTracking: Boolean)

case class DataLabels(enabled: Boolean)

case class Series(name: String, data: Array[Double])




object ChartWithLabels {
  val chartType = "line"


// todo - simplify creation - dont need this complexity here - json write will handle that
// todo - add multiple series - speed index, start render, visually complete
/*implicit def generateChart(resultsList: List[PerformanceResultsObject], parameter: String, title: String, subtitle: String): ChartWithLabels = {
  match (parameter){
  case ("speedIndex") => {
              val chartType = ChartType("line")
              val chart = Chart(chartType)
              val title = TextObject(title)
              val subtitle = TextObject(subtitle)
              val xAxis = resultsList.map(result => Category(resultsList.indexOf(result).toString))
              val yAxisTitle = TextObject("Time in ms")
              val yAxis = YAxis(yAxisTitle)
              val dataLabels = DataLabels(true)
              val line = Line(dataLabels, true)
              val plotOptions = PlotOptions(line)
              val chartSeries = Series("speedIndex", resultsList.map(_.speedIndex.toDouble).toArray)
              ChartWithLabels(chart, title, subtitle, xAxis, yAxis, plotOptions, Seq(chartSeries))
  }
  }
  ...
}*/


}
