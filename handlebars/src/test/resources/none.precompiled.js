function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [2,'>= 1.0.0-rc.3'];
helpers = helpers || Handlebars.helpers; data = data || {};
  var buffer = "", functionType="function", escapeExpression=this.escapeExpression;


  buffer += "Hi "
    + escapeExpression((typeof depth0 === functionType ? depth0.apply(depth0) : depth0))
    + "!";
  return buffer;
  }