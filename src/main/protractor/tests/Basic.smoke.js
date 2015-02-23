describe('Basic test: ', function(){
	
	var basePage = new browser.params.pages.BasePage ();
	
	//////////////////////////////////////////
	//Test for home page
	//////////////////////////////////////////
	describe ('Home Page test:', function () {
		
		it ('Check that it loads', function () {
			basePage.getPage ("").then (function () {
				console.log ("got home page");		
			});
		});
		
		it ('and has main section', function () {
			basePage.waitOnCondition (function () {
				return element(by.css('.jumbotron')).isDisplayed();
			});
		});
		
		it ('and has "Welcome to Tony sample web app" section', function () {
			expect(element(by.css('h1')).getText()).toEqual("Welcome to Tony's sample web app");
		});
	})

	//////////////////////////////////////////
	//Test for search page
	//////////////////////////////////////////
	describe ('About Search test:', function () {
		it ('Check that it loads', function () {
			basePage.getPage ("search").then (function () {
				console.log ("got search page");		
			});
		});
		
	});
	
});
