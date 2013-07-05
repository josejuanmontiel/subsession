// Auxilar function endsWith...
if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

(function($) {
	var COOKIE_LIVE = 60 * 30; 		// 30 minutes

	var subsession='';
	var subsession_breadcrumb='';
	
	// Sets a cookie, exsecs is time to expiry, in seconds, always with absolute path....
	function setCookie(c_name, value, exsecs) {
	  var exdate = new Date();
	  exdate.setSeconds(exdate.getSeconds() + exsecs);
	  var c_value = escape(value) + ((exsecs == null) ? "" : "; expires=" + exdate.toUTCString()) +";path=/;";
	  document.cookie = c_name + "=" + c_value;
	}
	
	// Gets a cookie.
	function getCookie(c_name) {
	  var i, x, y, ARRcookies = document.cookie.split(";");
	  for (i = 0 ; i < ARRcookies.length ; i++) {
	    x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
	    y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
	    x = x.replace(/^\s+|\s+$/g,"");
	    if (x == c_name) {
	      return unescape(y);
	    }
	  }
	  return '';
	}
	
	// Delete a cookie, setting expires...
	function deleteCookie(name) {
	    document.cookie = name + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
	}
	
	// This runs when the page loads.
	$(document).ready(function() {
		// If new page, window.name is empty...
		if (window.name=='') {
			// Setting timestamp value...
			subsession = new Date().getTime();
			window.name = subsession; 
			
			// Save old value to use in breadcrum...
			var old_subsession = getCookie('subsession');
			setCookie('subsession', subsession, COOKIE_LIVE);
			subsession_breadcrumb = getCookie('subsession_breadcrumb');
			
			// fist time without breadcrum...
			if (subsession_breadcrumb == '') {
				subsession_breadcrumb = subsession;
				setCookie('subsession_breadcrumb', subsession_breadcrumb, COOKIE_LIVE);
			// second time with breadcrum... and other tab...
			} else if (!subsession_breadcrumb.endsWith(subsession)) {
				subsession_breadcrumb = old_subsession + "/" + subsession;
				setCookie('subsession_breadcrumb', subsession_breadcrumb, COOKIE_LIVE);
			}
		} 
	});

	// Refresh subsession value when click...
	$(document).click(function() {
		setCookie('subsession', window.name, COOKIE_LIVE);
	});

	// Refresh subsession value when page refresh...
	$(window).load(function() {
		// if IsRefresh cookie exists
		var IsRefresh = getCookie("IsRefresh");
		if (IsRefresh != null && IsRefresh != "") {
			setCookie('subsession', subsession, COOKIE_LIVE);
			// cookie exists then you refreshed this page(F5, reload button or right click and reload)
			DeleteCookie("IsRefresh");
		} else {
			// cookie doesnt exists then you landed on this page
			setCookie("IsRefresh", "true", 1);
		}
	});

})(jQuery);