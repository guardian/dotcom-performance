

$(document).ready(function(){

 $("#report tr").not.hasClass("page").hide();
// $("#report tr:not(.odd)").hide();
 $("#report tr:first-child").show();

 $("#report tr").hasClass("page").click(function(){
  $(this).next("tr").toggle();
  $(this).find(".arrow").toggleClass("up");
 });
 $("#report").jExpand();

 $(".data tr:odd").show();
 $(".data tr:even").show();

});






