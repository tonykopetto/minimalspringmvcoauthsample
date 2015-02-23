var BasePage = function () {
}

//these methods are inherited by other pages
//so cannot use them in the constructor

//////////////////////////////////////
//Go to URL
//////////////////////////////////////
BasePage.prototype.getPage = function (url) {
	return browser.get (browser.params.baseUrl + url)
}

//////////////////////////////////////
//Delay
//////////////////////////////////////
BasePage.prototype.wait = function(delay) {
    browser.driver.sleep(delay ? delay : 500);
};

//////////////////////////////////////
//Delay
//////////////////////////////////////
BasePage.prototype.progressBarDone = function() {
    return element(by.css('#nprogress')).isPresent().then(function(present) { return !present; } );
};

//////////////////////////////////////
//Wait for some condition
//////////////////////////////////////
BasePage.prototype.waitOnCondition = function(conditionFn) {
    browser.driver.wait(function() {
        return conditionFn();
    }, 60000, conditionFn.toString() + " timed out after 60 seconds");
};

browser.params.pages.BasePage=BasePage;
