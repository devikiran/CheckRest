function detailToggle(e) {
  e.preventDefault();
  var id = $(this).data("id");
  toggleDetail(id);
}

function toggleDetail(id) {
  var arrow = $("#toggle-arrow-" + id);
  var collapsed = arrow.data("state") === "collapsed";
  arrow.data("state", collapsed ? "expanded" : "collapsed");
  var detail = $("#detail-" + id);
  $(".message-wrapper").removeClass("selected");
  $('.message-wrapper').removeClass('current');
  $wrapper = $('#message-wrapper-' + id);
  $wrapper.addClass('current');
  if (collapsed) {
    detail.show();
    runscope.animate.verticallyCenter($wrapper, 100);
    arrow.removeClass("icon-caret-right");
    arrow.addClass("icon-caret-down");
    $wrapper.addClass("selected");
    format_and_color_message(id);
  } else {
    detail.hide();
    arrow.removeClass("icon-caret-down");
    arrow.addClass("icon-caret-right");
  }
}

function detailViewToggle(e) {
  e.preventDefault();
  var id = $(this).data("id");
  var target = $(this).data("type");
  var other = target === "request" ? "response" : "request";
  $("#" + other + "-detail-" + id).hide();
  $("#" + target + "-detail-" + id).show();
  $(this).siblings().removeClass("active");
  $(this).addClass("active");
  if ($(this).data("linked-id")) {
    linkedId = $(this).data("linked-id");
    $("#response-detail-" + linkedId).hide();
    $("#request-detail-" + linkedId).hide();
  }
}

$(function() {
  $(document).on("click", ".detail-toggle", detailToggle);
  $(document).on("click", ".detail-view-toggle", detailViewToggle);

  // raw body toggling
  $(document).on("click", ".response-body-toggle", function(e) {
    e.preventDefault();
    id = $(this).data("id");
    var currentText = $(this).text();
    if (currentText == "view raw") {
      $(this).text("view formatted");
    } else {
      $(this).text("view raw");
    }
    $(".response-body[data-id=" + id + "]").toggleClass("hide");
    $(".raw-response-body[data-id=" + id + "]").toggleClass("hide");
  });

  $(document).on("click", ".request-body-toggle", function(e) {
    e.preventDefault();
    id = $(this).data("id");
    var currentText = $(this).text();
    if (currentText == "view raw") {
      $(this).text("view formatted");
    } else {
      $(this).text("view raw");
    }
    $(".request-body[data-id=" + id + "]").toggleClass("hide");
    $(".raw-request-body[data-id=" + id + "]").toggleClass("hide");
  });

});

function format_and_color_message(id) {
  $(".prettyprint", "#detail-" + id).each(format_element);
  $("#detail-" + id).click(generalClick);
}

function format_element(ix, el) {
  var element = $(el);
  var contentType = element.attr("data-content-type");
  if (contentType === undefined || contentType.indexOf('text/plain') > -1
      || contentType.indexOf('image/') > -1) {
    return;
  }

  if (contentType.indexOf('json') > -1) {
    try {
      var json = JSON.parse(element.text());
      var newHtml = jsonObjToHTML(json, null);
      element.click(generalClick);
      element.replaceWith(newHtml);
      return;
    } catch(err) {
      console.log(err.message);
    }
  }

  // Fall through catch and try to run beautify.
  beautify(element);
  prettyPrint(null, element.parent()[0]);
}

function beautify(jqelements) {
  try {
    jqelements.each(function(ix) {
      var text = $(this).text();
      if (text == "(empty)") {
        return;
      }
      var first_char = text[0];
      var contentType = $(this).attr("data-content-type");
      if(contentType.indexOf("html") > -1) {
        $(this).text(vkbeautify.xml(text, 2));
        $(this).addClass("lang-html");
      } else if (contentType.indexOf("xml") > -1) {
        $(this).text(vkbeautify.xml(text, 2));
        $(this).addClass("lang-xml");
      } else if (contentType.indexOf("json") > -1) {
        $(this).text(vkbeautify.json(text, 2));
        $(this).addClass("lang-json");
      }
    });
  } catch(err) {
    // do nothing
    console.log(err.message);
  }
}
