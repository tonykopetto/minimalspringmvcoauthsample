browser.driver.manage().window().maximize();

// JASMINE SETTINGS
require('jasmine-reporters');

// UTILITY FUNCTIONS
var fs = require('fs');

browser.params.pages={}
browser.params.baseUrl = '@APP_URL@';
browser.ignoreSynchronization = true;//do not to wait for angular

console.log("AAA:" + __filename + ":" + process.cwd ());
// SET UP PAGE OBJECTS
require(process.cwd () + '/build/protractor/pages/BasePage.js');

