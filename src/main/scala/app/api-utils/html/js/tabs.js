/**
 * Created by mmcnamara on 15/04/16.
 */
jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');

        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

        e.preventDefault();
        var highChart = Highcharts.charts[$(this).data('highchartsChart')];
        var highChartCont = $(highChart.container).parent();
        highChart.setSize(highChartCont.width(), highChartCont.height());
        highChart.hasUserSize = undefined;

    });
});