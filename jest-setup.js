const {pass, fail} = require("jest-extended");
expect.extend({pass,fail});

require('@testing-library/jest-dom');

const {toEq} = require("./dist-test/jest_cljs.matchers");
expect.extend({toEq});
