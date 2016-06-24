/**
 * Created by mmcnamara on 24/06/16.
 */
// fix dimensions of chart that was in a hidden element
jQuery(document).on( 'shown.bs.tab', 'a[data-toggle="tab"]', function (e) { // on tab selection event
    jQuery( ".contains-chart" ).each(function() { // target each element with the .contains-chart class
        var chart = jQuery(this).highcharts(); // target the chart itself
        chart.reflow() // reflow that chart
    });
})
