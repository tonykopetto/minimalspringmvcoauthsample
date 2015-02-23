exports.config = {

  // The address of a running selenium server.
  seleniumAddress: '@SELENIUM_URL@',

  // Capabilities to be passed to the webdriver instance.
  // https://code.google.com/p/selenium/wiki/DesiredCapabilities
  multiCapabilities: [
/*  {
    	'browserName': 'firefox',
    	shardTestFiles: false,
    	maxInstances: 1
  },
  */
 
  {
	  'browserName': 'chrome',
	  shardTestFiles: false,
	  maxInstances: 1
  }
  ],

  // Maximum number of total browser sessions to run. 
  maxSessions: -1,

  // When run without a command line parameter, all suites will run.
  // If run with --suite=smoke or --suite=smoke,full only the patterns matched by the specified suites will run.
  suites: {
    smoke: 'tests/*.smoke.js',
    full: './**/*.full.js'
  },

  // Options to be passed to Jasmine-node.
  jasmineNodeOpts: {
    isVerbose: true,
    showColors: true, // Use colors in the command line report.
    includeStackTrace: true,
    defaultTimeoutInterval: 60000
  },

  allScriptsTimeout: 60000,

  onPrepare: 'protractor.onload.js'
};
