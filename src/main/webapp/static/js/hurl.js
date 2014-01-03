var bb = new BankersBox(1);

var oAuthSignatureType = function(value, label) {
  var self = this;
  self.label = label;
  self.value = value;
};

var keyPair = function(key, val, hash) {
  var self = this;
  self.key = key;
  self.val = val;
  self.hash = hash || Math.random().toString(36).substr(2, 5);
};

var hurlViewModel = function(original) {
  var self = this;

  self.addAuth = function() {
    self.hasBasicAuth(true);
    $("input[name=username]").focus();
  };
  self.addHeaders = function() {
    if (self.headers.length === 0) {
      self.addBlankHeader();
    }
  };
  self.addParams = function() {
    if (self.bodyParams.length === 0) {
      self.addBlankParam();      
    }
  };
  self.addBody = function() {
    self.hasBody(true);
  };

  self.addBlankHeader = function() {
    self.headers.push(new keyPair("", ""));
    $("input[name^=header_name_]:last").focus();
    self.registerTypeahead();
  };
  self.addBlankParam = function() {
    self.bodyParams.push(new keyPair("", ""));
    $("input[name^=parameter_name_]").focus();
  };

  self.clearAuth = function() {
    self.hasAuth(false); // set explicitly to kill override
    self.hasBasicAuth(false); // set explicitly to kill override
    self.username(self.original.username || "");
    self.password(self.original.password || "");
    self.digest_username(self.original.digest_username || "");
    self.digest_password(self.original.digest_password || "");
    self.consumer_key(self.original.consumer_key || "");
    self.consumer_secret(self.original.consumer_secret || "");
    self.access_token(self.original.access_token || "");
    self.token_secret(self.original.token_secret || "");
  };
  self.clearHeaders = function() {
    self.headers.removeAll();
  };
  self.clearData = function() {
    self.bodyParams.removeAll();
    self.body("");
    self.hasBody(false);
  };

  self.removeHeader = function(data, event) {
    self.headers.remove(function(item) {
      if (item.key === "" && item.val === "") {
        return item.hash == data.hash;
      }
      else {
        return item.key === data.key && item.val === data.val;
      }
    });
  };
  self.removeParam = function(data, event) {
    self.bodyParams.remove(function(item) {
      if (item.key === "" && item.val === "") {
        return item.hash == data.hash;
      }
      else {
        return item.key === data.key && item.val === data.val;
      }
    });
  };

  self.executeRequest = function() {
    self.hideRequestMessages();
    self.isExecuting(true);
    $(".message-result-overlay").show();

    // executing request
    $form = $("#hurl");

    self.request = $.ajax(
      $form.attr("action"),
      {
        type: $form.attr("method"),
        data: $form.serialize(),
        // Default timeout is 15sec for now
        timeout: 15000
      }
    ).done(function(data, status, xhr) {
      var newHtml = $.parseHTML(data);
      
      $(".message-result").html(newHtml);
      $(".message-result").show();
      $(".prettyprint").each(format_element);
      $(".formattedJson").click(generalClick);

      $('html, body').animate({ scrollTop: $(".message-result").offset().top }, 500);
      self.hideRequestMessages();

    }).fail(function(xhr, status, error) {
      self.hideRequestMessages();
      if (status === "timeout") {
        $("#request-timeout-message").show();
      } else {
        $("#request-status-message").html(xhr.responseText);
        $("#request-status-message").addClass("alert-error");
        $("#request-status-message").show();
      }
    }).always(function() {
      window.clearTimeout(self.timeoutId);
      $(".message-result-overlay").hide();
      self.isExecuting(false);
    });
    
    self.timeoutId = window.setTimeout(self.promptForCancel, 5000);
    $(document).keyup(function(e) {
      if (e.keyCode == 27) {
        self.stopRequest();
      }
    });
  };

  self.hideRequestMessages = function() {
    $("#request-cancel-message").hide();
    $("#request-status-message").hide();
    $("#request-status-message").removeClass("alert-error");
    $("#request-timeout-message").hide();
  };

  self.promptForCancel = function() {
    window.clearTimeout(self.timeoutId);
    if (self.isExecuting) {
      $("#request-cancel-message").show();
    }
  };

  self.stopRequest = function() {
    self.hideRequestMessages();
    if (self.isExecuting) {
      self.request.abort();
      self.isExecuting(false);
      $("#request-status-message").html("Request canceled.");
      $("#request-status-message").show();
    }
  };

  self.contentTypes = function(query) {
    return ["Accept","Accept-Charset","Accept-Encoding","Accept-Language","Accept-Datetime","Authorization","Cache-Control","Connection","Cookie","Content-Length","Content-MD5","Content-Type","Date","Expect","From","Host","If-Match","If-Modified-Since","If-None-Match","If-Range","If-Unmodified-Since","Max-Forwards","Pragma","Proxy-Authorization","Range","Referer","TE","Upgrade","User-Agent","Via","Warning"];
  };

  self.commonHeaders = function() {
    return ["application/json", "text/xml", "application/x-www-form-urlencoded", "application/atom+xml", "text/plain", "text/html"];
  };

  self.registerTypeahead = function() {
    $("input[name^='header_name']").typeahead({
      source: self.contentTypes
    });
    $("input[name^='header_value']").typeahead({
      source: self.commonHeaders
    });    
  };

  self.revertBody = function() {
    self.body(original.body);
  };
  self.revertHeaders = function() {
    self.headers(original.headers);
  };
  self.revertParams = function() {
    self.bodyParams(original.bodyParams);
  };

  self.toggleRedirects = function() {
    self.followRedirects(!self.followRedirects());
    bb.set("follow_redirects", self.followRedirects());
  };

  // self.updateTools = function(mid) {
  //   if (mid) {
  //     $("#share").attr("href", "/share/" + bucket_key + "/" + mid);
  //     $("#playback").attr("href", "https://" + bucket_key + ".runscope.net/?Runscope-Playback-Id=" + mid);
  //     if (original.uuid) {
  //       $("#compare").attr("href", "/compare/" + bucket_key + "/" + original.uuid + "/" + mid);
  //     } else {
  //       $("#compare").hide();
  //     }

  //     $("#addToCollection").data("id", mid);
  //     delete(vms.cpvm);
  //     vms["cpvm"] = new collectionPickerViewModel(csrf_token, bucket_key);
  //     ko.applyBindings(vms.cpvm, $("section.message-result")[0]);
  //   } else {
  //     $("#share").hide();
  //     $("#playback").hide();
  //   }
  // };

  self.isExecuting = ko.observable(false);
  self.isIdle = ko.computed(function() { return !self.isExecuting(); });

  self.original = ko.observable(original || {});

  self.method = ko.observable(original.method || "GET");
  self.style = ko.computed(function() {
    return (["POST", "PUT", "PATCH", "DELETE"].indexOf(self.method()) > -1) ? "post" : "get";
  });
  self.url = ko.observable(original.url || "");
  self.readOnlyHeaders = ko.observableArray([]);
  self.readOnlyHeaderKeys = ko.observableArray(["Connection", "connection", "Content-Length", "content-length", "Host", "host", "Accept-Encoding", "accept-encoding"]);

  self.headers = ko.observableArray([]);

  if (original.headers) {
    for (header in original.headers) {
      for (val in original.headers[header]) {
        if (self.readOnlyHeaderKeys().indexOf(header) === -1) {
          self.headers.push(new keyPair(header, original.headers[header][val]));
        }
        else {
          self.readOnlyHeaders.push(new keyPair(header, original.headers[header][val]));
          self.removeHeader(new keyPair(header, original.headers[header][val]));
        }

        // if basic auth, add Authorization header to readonly
        if (header == "Authorization" && original.headers[header][val].indexOf("Basic ") === 0) {
          self.readOnlyHeaders.push(new keyPair(header, original.headers[header][val]));
          self.removeHeader(new keyPair(header, original.headers[header][val]));
        }
        // if user-agent contains runscope, add User-Agent to readonly
        if (header == "User-Agent" && original.headers[header][val].indexOf("runscope/") === 0) {
          self.readOnlyHeaders.push(new keyPair(header, original.headers[header][val]));
          self.removeHeader(new keyPair(header, original.headers[header][val]));
        }
      }
    }
  }

  self.bodyParams = ko.observableArray([]);
  // transform headers and post bodyParams into array of keyPairs
  if (original.form) {
    for (prm in original.form) {
      for (val in original.form[prm]) {
        self.bodyParams.push(new keyPair(prm, original.form[prm][val]));
      }
    }
  }  

  self.body = ko.observable(original.body || "");

  self.username = ko.observable(original.username || "");
  self.password = ko.observable(original.password || "");

  self.digest_username = ko.observable(original.digest_username || "");
  self.digest_password = ko.observable(original.digest_password || "");

  self.ntlm_username = ko.observable(original.ntlm_username || "");
  self.ntlm_password = ko.observable(original.ntlm_password || "");

  self.consumer_key = ko.observable(original.oauth_params.consumer_key || "");
  self.consumer_secret = ko.observable(original.oauth_params.consumer_secret || "");
  self.access_token = ko.observable(original.oauth_params.access_token || "");
  self.token_secret = ko.observable(original.oauth_params.token_secret || "");
  self.signature_type = ko.observable(original.oauth_params.signature_type || "");

  self.methods = ["GET", "POST", "PUT", "PATCH", "HEAD", "OPTIONS", "DELETE"];
  self.oAuthSignatureTypes = [
    new oAuthSignatureType("query", "Querystring"),
    new oAuthSignatureType("auth_header", "Auth header"),
    new oAuthSignatureType("body", "Body")
  ];

  follow_redirects_default = original.follow_redirects;
  if (!follow_redirects_default) {
    // not specified by URL default, get last saved version
    follow_redirects_default = bb.get("follow_redirects");
  }

  self.followRedirects = ko.observable(follow_redirects_default);

  self.authOverride = ko.observable(false);
  self.basicAuthOverride = ko.observable(false);
  self.hasBasicAuth = ko.computed({
    read: function() {
      return self.basicAuthOverride() ||
             self.username().length > 0 ||
             self.password().length > 0;
    },
    write: function(value) {
      self.basicAuthOverride(value);
    }
  });

  self.hasDigestAuth = ko.computed(function() {
    return self.digest_username().length > 0 ||
           self.digest_password().length;
  });

  self.hasOAuth1 = ko.computed(function() {
    return self.consumer_key().length > 0 ||
             self.consumer_secret().length > 0 ||
             self.access_token().length > 0 ||
             self.token_secret().length > 0;
  });

  self.hasAuth = ko.computed({
    read: function() {
      return self.authOverride() || 
             self.hasBasicAuth() ||
             self.hasDigestAuth() ||
             self.hasOAuth1();
    },
    write: function(value) {
      self.authOverride(value);
    }
  });

  self.hasHeaders = ko.computed(function() {
    return self.headers().length > 0 || self.readOnlyHeaders().length > 0;
  });

  self.hasReadOnlyHeaders = ko.computed(function() {
    return self.readOnlyHeaders().length > 0;
  });

  self.hasBodyParams = ko.computed(function() {
    return self.bodyParams().length > 0;
  });

  self.hasBodyOverride = ko.observable(false);
  self.hasBody = ko.computed({
    read : function() {
      if (self.hasBodyParams()) {
        self.body("");
        return false;
      }
      return self.hasBodyOverride() || self.body().length > 0;
    },
    write: function(value) {
      self.hasBodyOverride(value);
    }
  });

  self.hasData = ko.computed(function() {
    return self.hasBodyParams() || self.hasBody();
  });

  self.isPostStyle = ko.computed(function() {
    return self.style() === "post";
  });
  self.isGetStyle = ko.computed(function() {
    return !self.isPostStyle();
  });
};
